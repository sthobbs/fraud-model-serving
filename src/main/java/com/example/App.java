package com.example;

// import java.util.Arrays;
import org.apache.beam.sdk.extensions.jackson.ParseJsons;
import org.apache.beam.sdk.io.TextIO;

// import java.io.Serializable;

// import java.lang.invoke.TypeDescriptor;

// import org.apache.arrow.flatbuf.Null;
import org.apache.beam.sdk.Pipeline;
// import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.values.PCollection;
// import org.apache.beam.sdk.values.TypeDescriptors;
// import org.apache.beam.sdk.transforms.Count;
// import org.apache.beam.sdk.transforms.Filter;
// import org.apache.beam.sdk.transforms.FlatMapElements;
// import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.transforms.Combine;
// import org.apache.beam.sdk.values.TypeDescriptors;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.Filter;
// import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.Values;
// import org.apache.beam.sdk.transforms.WithKeys;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;

import com.example.config.ModelPipelineOptions;
import com.example.storage.Event;
import com.example.storage.Session;
import com.example.processors.EventToKV;
import com.example.processors.SessionCombineFn;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.beam.sdk.coders.KvCoder;
// import java.io.Serializable;
import org.apache.beam.sdk.coders.SerializableCoder;
import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubIO;
import com.example.processors.FilterSession;


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
            records = p.apply(
                "read from disk", TextIO.read().from(options.getInputPath()));
        }
        else { // if (options.getInputType().equalsIgnoreCase("pubsub")){
            records = p.apply(
                "read from pubsub", PubsubIO.readStrings().fromSubscription(options.getInputPath()));
        }

        // 2. ----- Transform Data -----

        // Parse records into Events
        PCollection<Event> events = records
            .apply("Parse JSON", ParseJsons.of(Event.class))
            .setCoder(SerializableCoder.of(Event.class));

        // Combine events into Session
        PCollection<Session> sessions = events
            .apply("Convert to KV<SessionId, Event> pairs",
                ParDo.of(new EventToKV()))
            .apply("Combine into KV<SessionId, Session> pairs",
                Combine.<String, Event, Session>perKey(new SessionCombineFn()))
            .apply("Extract Sessions from KV pair",
                Values.create());
            


            // sessions
            // .apply("Convert back to String", ParDo.of(new sessionToString()))
            // .apply(TextIO.write().to(options.getOutputPath()));



        p.run();


        System.out.println("Hello World!");
    }
}

