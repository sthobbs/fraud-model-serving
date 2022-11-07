package com.example;

// import java.util.Arrays;
import org.apache.beam.sdk.extensions.jackson.ParseJsons;
import org.apache.beam.sdk.io.TextIO;
// import org.apache.arrow.flatbuf.Null;
import org.apache.beam.sdk.Pipeline;
// import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.values.PCollection;
// import org.apache.beam.sdk.transforms.Count;
// import org.apache.beam.sdk.transforms.Filter;
// import org.apache.beam.sdk.transforms.FlatMapElements;
// import org.apache.beam.sdk.transforms.MapElements;
// import org.apache.beam.sdk.values.KV;
// import org.apache.beam.sdk.values.TypeDescriptors;
// import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;

import com.example.config.ModelPipelineOptions;
import com.example.storage.Event;

import org.apache.commons.configuration.ConfigurationException;
// import java.io.Serializable;
import org.apache.beam.sdk.coders.SerializableCoder;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubIO;


/**
 *
 */
public class App 
{


    static class eventToString extends DoFn<Event, String> {
        @ProcessElement
        public void processElement(@Element Event element, OutputReceiver<String> receiver) {
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
        



        //  .apply("Convert back to String", ParDo.of(new eventToString()))
         
        //  .apply(TextIO.write().to(options.getOutputPath()));




//  .apply("WriteCounts", TextIO.write().to(options.getOutput()));
        // PCollection<String> lines = p.apply(TextIO.read().from("C:/Users/hobbs/Documents/Programming/model-serving/test.txt"))
        // p.apply(TextIO.read().from("gs://apache-beam-samples/shakespeare/kinglear.txt"))

        // By default, it will write to a set of files with names like wordcounts-00001-of-00005
        // lines.apply(TextIO.write().to("wordcounts").withNumShards(1));

        p.run();


        System.out.println("Hello World!");
    }
}

        // A simple Write to a local file (only runs locally):
        // PCollection<String> lines = 
        //     p.apply(TextIO.read().from("C:/Users/hobbs/Documents/Programming/model-serving/test.txt"));
        
        // lines.apply("Print",ParDo.of(new PrintElementFn()));
        
        // lines.apply(TextIO.write().t o("test_output"));

        // // Same as above, only with Gzip compression:
        // PCollection<String> lines = ...;
        // lines.apply(TextIO.write().to("C:/Users/hobbs/Documents/Programming/model-serving/test.txt"))
        //     .withSuffix(".txt"));

        // String currentPath = new java.io.File(".").getCanonicalPath();
        // System.out.println("Current dir:" + currentPath);








    // private static class PrintElementFn extends DoFn<String,Void>{
    //     @ProcessElement
    //     public void processElement(@Element String input){
    //         System.out.println(input);
    //     }
    // }