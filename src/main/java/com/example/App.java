package com.example;

import com.example.config.ModelPipelineOptions;
import com.example.config.SetupPipelineOptions;
import org.apache.beam.sdk.Pipeline;
import org.apache.commons.configuration.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Run Dataflow pipeline to process raw data detect fraud with an XGBoost model.
 */
public class App {

    public static void main(String[] args) throws ConfigurationException {

        final Logger logger = LoggerFactory.getLogger(App.class);

        // Setup pipeline options
        logger.info("Setting up pipeline options...");
        ModelPipelineOptions options = new SetupPipelineOptions(args).setup();

        // Build pipeline
        logger.info("Building pipeline...");
        Pipeline p = new ModelPipeline(options).build();

        // Run pipeline
        logger.info("Starting pipeline...");
        p.run();

        logger.info("Pipeline started sucessfully...");
    }
}
