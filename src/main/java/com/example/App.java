package com.example;

// import java.util.Arrays;
import org.apache.beam.sdk.extensions.jackson.ParseJsons;
import org.apache.beam.sdk.io.GenerateSequence;
import org.apache.beam.sdk.io.TextIO;

// import java.io.FileNotFoundException;
// import java.io.BufferedReader;
// import java.io.FileReader;
// import java.io.IOException;
import java.util.HashMap;
// import java.util.Map;

// import java.io.Serializable;

// import java.lang.invoke.TypeDescriptor;

// import org.apache.arrow.flatbuf.Null;
import org.apache.beam.sdk.Pipeline;
// import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionView;
// import org.apache.beam.sdk.values.TypeDescriptors;
// import org.apache.beam.sdk.transforms.Count;
// import org.apache.beam.sdk.transforms.Filter;
// import org.apache.beam.sdk.transforms.FlatMapElements;
// import org.apache.beam.sdk.transforms.MapElements;
// import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.transforms.Combine;
// import org.apache.beam.sdk.values.TypeDescriptors;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.Filter;
// import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.Values;
import org.apache.beam.sdk.transforms.View;
import org.apache.beam.sdk.transforms.windowing.AfterProcessingTime;
import org.apache.beam.sdk.transforms.windowing.GlobalWindows;
import org.apache.beam.sdk.transforms.windowing.Repeatedly;
import org.apache.beam.sdk.transforms.windowing.Window;
// import org.apache.beam.sdk.transforms.WithKeys;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.joda.time.Duration;

import com.example.config.ModelPipelineOptions;
import com.example.storage.CustInfoRecord;
import com.example.storage.Event;
import com.example.storage.Features;
import com.example.storage.ProfileRecord;
import com.example.storage.ScoreEvent;
import com.example.storage.Session;
import com.example.storage.Transaction;
// import com.google.gson.Gson;
import com.example.processors.EventToKV;
import com.example.processors.FeaturesToScoreEvent;
import com.example.processors.LoadCustInfoSideInput;
import com.example.processors.LoadProfileSideInput;
import com.example.processors.SessionCombineFn;
import com.example.processors.SessionFilter;
import com.example.processors.SessionToTxn;
import com.example.processors.TxnToFeatures;

import org.apache.commons.configuration.ConfigurationException;
// import org.apache.beam.sdk.coders.KvCoder;
// import java.io.Serializable;
import org.apache.beam.sdk.coders.SerializableCoder;
// import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubIO;

// import org.json.simple.JSONArray;
// import org.json.simple.JSONObject;
// import org.json.simple.parser.JSONParser;
// import org.json.simple.parser.ParseException;
// import org.json.JSONArray;
// import org.json.JSONObject;
// import org.json.simple.parser.JSONParser;
// import org.json.simple.parser.ParseException;

/**
 *
 */
public class App// implements Serializable
{




    static class sessionToString extends DoFn<Session, String> {
        @ProcessElement
        public void processElement(@Element Session element, OutputReceiver<String> receiver) {
            String s = element.toString();
            receiver.output(s);
        }
    }

    static class txnToString extends DoFn<Transaction, String> {
        @ProcessElement
        public void processElement(@Element Transaction element, OutputReceiver<String> receiver) {
            String s = element.toString();
            receiver.output(s);
        }
    }

    static class classToString<T> extends DoFn<T, String> {
        @ProcessElement
        public void processElement(@Element T element, OutputReceiver<String> receiver) {
            String s = element.toString();
            receiver.output(s);
        }
    }

    public static void main(String[] args) throws ConfigurationException
    {
        // 1. ----- Pipeline Setup -----

        // register interface (to improve documentation)
        PipelineOptionsFactory.register(ModelPipelineOptions.class);

        // set execution options for pipeline
        ModelPipelineOptions options = PipelineOptionsFactory.fromArgs(args)
                                                  .withValidation()
                                                  .as(ModelPipelineOptions.class);
        // set options from config
        Configuration config = new PropertiesConfiguration("config.properties");
        options.setInputPath(config.getString("inputPath"));
        options.setOutputPath(config.getString("outputPath"));

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
            .apply(GenerateSequence.from(0).withRate(1, Duration.standardHours(1L)))
            .apply(ParDo.of(new LoadProfileSideInput()))
            .apply(Window.<HashMap<String, ProfileRecord>>into(new GlobalWindows())
                    .triggering(Repeatedly.forever(AfterProcessingTime.pastFirstElementInPane()))
                    .discardingFiredPanes())
            .apply(View.asSingleton());

        // read customer info table every hour
        PCollectionView<HashMap<String, CustInfoRecord>> custInfoMap = p
            .apply(GenerateSequence.from(0).withRate(1, Duration.standardHours(1L)))
            .apply(ParDo.of(new LoadCustInfoSideInput()))
            .apply(Window.<HashMap<String, CustInfoRecord>>into(new GlobalWindows())
                    .triggering(Repeatedly.forever(AfterProcessingTime.pastFirstElementInPane()))
                    .discardingFiredPanes())
            .apply(View.asSingleton());

        // // combine side inputs into single object
        // HashMap<String, Object> sideInputs = new HashMap<String, Object>();
        // sideInputs.put("profileMap", profileMap);
        // sideInputs.put("custInfoMap", custInfoMap);

        // 4. ----- Generate Features -----

        // generate features that just use transactions
        PCollection<Features> features = txns
            .apply("Generate features from transactions",
                ParDo.of(new TxnToFeatures(profileMap, custInfoMap))
                     .withSideInputs(profileMap, custInfoMap));

        // 5. ----- Generate Scores -----
        PCollection<ScoreEvent> scores = features
            .apply("Generate score from features",
                ParDo.of(new FeaturesToScoreEvent()));


        scores
            .apply("Convert back to String",
                ParDo.of(new classToString<ScoreEvent>()))
            .apply(TextIO.write().to(options.getOutputPath()));


        // features
        //     .apply("Convert back to String",
        //         ParDo.of(new classToString<Features>()))
        //     .apply(TextIO.write().to(options.getOutputPath()));

        p.run();


        System.out.println("Hello World!");
    }
}

