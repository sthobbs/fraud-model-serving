package com.example;

import com.example.config.ModelPipelineOptions;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;

import org.apache.beam.sdk.options.PipelineOptionsFactory;
// import org.apache.beam.sdk.options.PipelineOptions.DirectRunner;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import org.apache.beam.runners.dataflow.DataflowRunner;
import org.apache.beam.sdk.Pipeline;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;


// import DataflowPipelineWorkerPoolOptions.AutoscalingAlgorithmType.THROUGHPUT_BASED;
import org.apache.beam.runners.dataflow.options.DataflowPipelineWorkerPoolOptions.AutoscalingAlgorithmType;
import org.apache.beam.vendor.guava.v26_0_jre.com.google.common.collect.ImmutableList;


/**
 *
 */
public class App// implements Serializable
{

    public static void main(String[] args) throws ConfigurationException
    {
        // 1. ----- Pipeline Setup -----

        // register interface (to improve documentation of help command)
        PipelineOptionsFactory.register(ModelPipelineOptions.class);

        // set execution options for pipeline
        ModelPipelineOptions options = PipelineOptionsFactory.fromArgs(args)
                                                //   .withValidation()
                                                  .as(ModelPipelineOptions.class);

        Configuration config = new PropertiesConfiguration("config.properties");

        GoogleCredentials credentials = null;
        try {
            credentials = ServiceAccountCredentials
                .fromStream(new FileInputStream(config.getString("serviceAccountKeyPath")))
                .createScoped(Arrays.asList(
                    new String[] { "https://www.googleapis.com/auth/cloud-platform" }));
        }
        catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // set options from config file
        options.setProject(config.getString("projectId"));
        options.setInputPath(config.getString("inputPath"));
        options.setProfileSideInputPath(config.getString("profilePath"));
        options.setCustomerInfoSideInputPath(config.getString("custInfoPath"));
        options.setModelPath(config.getString("modelPath"));
        options.setOutputPath(config.getString("outputPath"));

        options.setRunner(DataflowRunner.class);
        options.setAutoscalingAlgorithm(AutoscalingAlgorithmType.THROUGHPUT_BASED);
        options.setNumWorkers(config.getInt("numWorkers"));
        options.setMaxNumWorkers(config.getInt("maxNumWorkers"));
        options.setGcpCredential(credentials);
        options.setWorkerMachineType(config.getString("machineType"));
        options.setServiceAccount(config.getString("serviceAccountEmail"));
        options.setStreaming(config.getBoolean("isStreaming"));
        options.setRegion(config.getString("region"));

        // Need to use runner v2 to use docker contrainer for workers
        List<String> experiments;
        if (options.getExperiments() == null) {
            experiments = new ArrayList<>();
            // options.setExperiments(ImmutableList.of("use_runner_v2"));
        } else {
            experiments = new ArrayList<>(options.getExperiments());
        }
        if (!experiments.contains("use_runner_v2")) {
            experiments.add("use_runner_v2");
            options.setExperiments(ImmutableList.copyOf(experiments));
        }
        
        options.setSdkContainerImage("gcr.io/analog-arbor-367702/model-serving:latest");



        ModelPipeline pipeline = new ModelPipeline(options);
        Pipeline p = pipeline.build();

        p.run();


        System.out.println("Pipeline finished");
    }
}

