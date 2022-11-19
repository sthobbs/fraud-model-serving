package com.example;

import java.io.Serializable;

import com.example.config.ModelPipelineOptions;
import com.example.processors.EventToKV;
import com.example.processors.FeaturesToScoreEvent;
import com.example.processors.LoadCustInfoSideInput;
import com.example.processors.LoadProfileSideInput;
import com.example.processors.SessionCombineFn;
import com.example.processors.SessionFilter;
import com.example.processors.SessionToTxn;
import com.example.processors.TxnToFeatures;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionView;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubIO;
import org.apache.beam.sdk.io.TextIO;
import com.example.storage.Event;
import com.example.storage.Features;
import com.example.storage.ScoreEvent;
import com.example.storage.Session;
import com.example.storage.Transaction;
import com.example.storage.CustInfoRecord;
import com.example.storage.ProfileRecord;
import org.apache.beam.sdk.io.GenerateSequence;
import java.util.HashMap;


import org.apache.beam.sdk.coders.SerializableCoder;
import org.apache.beam.sdk.extensions.jackson.ParseJsons;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.Filter;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.Combine;
import org.apache.beam.sdk.transforms.View;
import org.apache.beam.sdk.transforms.Values;
import org.apache.beam.sdk.transforms.windowing.AfterProcessingTime;
import org.apache.beam.sdk.transforms.windowing.GlobalWindows;
import org.apache.beam.sdk.transforms.windowing.Repeatedly;
import org.apache.beam.sdk.transforms.windowing.Window;
import org.joda.time.Duration;





public class ModelPipeline implements Serializable {
    
    ModelPipelineOptions options;

    public ModelPipeline(ModelPipelineOptions options) {
        this.options = options;
    }

    public Pipeline build() {
    
        // Create the Pipeline object with the options we defined above
        Pipeline p = Pipeline.create(options);


        // 2. ----- Load Data -----
   
        // read data
        PCollection<String> records;
        // ToDO: assert getInputType() in ("disk", "pubsub")
        if (options.getInputType().equalsIgnoreCase("disk")){
            records = p.apply("read from disk",
                TextIO.read().from(options.getInputPath()));
        }
        else { // if (options.getInputType().equalsIgnoreCase("pubsub")){
            records = p.apply("read from pubsub",
                PubsubIO.readStrings().fromSubscription(options.getInputPath()));
        }

        // 3. ----- Transform Data -----

        // Parse records into Events
        PCollection<Event> events = records
            .apply("Parse JSON",
                ParseJsons.of(Event.class))
            .setCoder(SerializableCoder.of(Event.class));

        // Combine events into Session
        PCollection<Session> sessions = events
            .apply("Convert to KV<SessionId, Event> pairs",
                ParDo.of(new EventToKV()))
            .apply("Combine into KV<SessionId, Session> pairs",
                Combine.<String, Event, Session>perKey(new SessionCombineFn()))
            .apply("Extract Sessions from KV pair",
                Values.create());
            
        // Filter out sessions without transactions
        PCollection<Transaction> txns = sessions
            .apply("Filter out sessions without transactions",
                Filter.by(new SessionFilter()))
            .apply("Convert Sessions to Transactions",
                ParDo.of(new SessionToTxn()));

        // 3. ----- Side Inputs -----

        // read profile table every hour
        PCollectionView<HashMap<String, ProfileRecord>> profileMap = p
            .apply("Generate hourly ticks to trigger profile side input load",
                GenerateSequence.from(0).withRate(1, Duration.standardHours(1L)))
            .apply("put profile ticks into global window",
                Window.<Long>into(new GlobalWindows())
                    .triggering(Repeatedly.forever(AfterProcessingTime.pastFirstElementInPane()))
                    .discardingFiredPanes())
            .apply("load profile table side input",
                ParDo.of(new LoadProfileSideInput(options)))
            .apply("get PCollectionView for profile table side input",
                View.asSingleton());

        // read customer info table every hour
        PCollectionView<HashMap<String, CustInfoRecord>> custInfoMap = p
            .apply("Generate hourly ticks to trigger customer info side input load",
                GenerateSequence.from(0).withRate(1, Duration.standardHours(1L)))
            .apply("put customer info ticks into global window",
                Window.<Long>into(new GlobalWindows())
                .triggering(Repeatedly.forever(AfterProcessingTime.pastFirstElementInPane()))
                .discardingFiredPanes())
            .apply("load customer info table side input",
                ParDo.of(new LoadCustInfoSideInput(options)))
            .apply("get PCollectionView for customer info table side input",
                View.asSingleton());

        // 4. ----- Generate Features -----

        // generate features that just use transactions
        PCollection<Features> features = txns
            .apply("Generate features from transactions",
                ParDo.of(new TxnToFeatures(profileMap, custInfoMap))
                     .withSideInputs(profileMap, custInfoMap));

        // 5. ----- Generate Scores -----
        PCollection<ScoreEvent> scores = features
            .apply("Generate score from features",
                ParDo.of(new FeaturesToScoreEvent(options)));


        scores
            .apply("Convert back to String",
                ParDo.of(new classToString<ScoreEvent>()))
            .apply(TextIO.write().to(options.getOutputPath()));

        return p;
    }

    static class classToString<T> extends DoFn<T, String> {
        @ProcessElement
        public void processElement(@Element T element, OutputReceiver<String> receiver) {
            String s = element.toString();
            receiver.output(s);
        }
    }

}
