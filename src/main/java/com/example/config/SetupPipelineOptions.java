package com.example.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.beam.runners.dataflow.DataflowRunner;
import org.apache.beam.runners.dataflow.options.DataflowPipelineWorkerPoolOptions.AutoscalingAlgorithmType;
// import org.apache.beam.sdk.options.PipelineOptions.DirectRunner;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.vendor.guava.v26_0_jre.com.google.common.collect.ImmutableList;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Setup pipeline options.
 */
public class SetupPipelineOptions {

    private final static Logger logger = LoggerFactory.getLogger(SetupPipelineOptions.class);

    private String[] args;
    private ModelPipelineOptions options;

    public SetupPipelineOptions(String[] args) {
        this.args = args;
    }

    // Setup pipeline options
    public ModelPipelineOptions setup() throws ConfigurationException {

        // Register interface (to improve documentation of --help command)
        PipelineOptionsFactory.register(ModelPipelineOptions.class);

        // Set execution options for pipeline
        options = PipelineOptionsFactory.fromArgs(args)
                                        .as(ModelPipelineOptions.class);

        // Get configuration from file
        Configuration config = new PropertiesConfiguration("config.properties");

        // Get credentials from file
        GoogleCredentials credentials = null;
        try {
            credentials = ServiceAccountCredentials
                .fromStream(new FileInputStream(config.getString("serviceAccountKeyPath")))
                .createScoped(Arrays.asList(
                    new String[] { "https://www.googleapis.com/auth/cloud-platform" }));
        }
        catch (IOException e) {
            logger.error("Error getting credentials: " + e.getMessage());
        }

        // Set options from config file
        options.setProject(config.getString("project"));
        options.setBucket(config.getString("bucket"));
        options.setInputType(config.getString("inputType"));
        options.setInputGcsPath(config.getString("inputGcsPath"));
        options.setInputPubsubSubscription(
            "projects/" + config.getString("project")
            + "/subscriptions/" + config.getString("inputPubsubSubscription"));
        options.setOutputType(config.getString("outputType"));
        options.setOutputGcsPath(config.getString("outputGcsPath"));
        options.setOutputPubsubTopic(
            "projects/" + config.getString("project")
            + "/topics/" + config.getString("outputPubsubTopic"));
        options.setProfileSideInputPrefix(config.getString("profileSideInputPrefix"));
        options.setCustomerInfoSideInputPrefix(config.getString("custInfoSideInputPrefix"));
        options.setModelPath(config.getString("modelPath"));
        options.setRunner(DataflowRunner.class);
        options.setAutoscalingAlgorithm(AutoscalingAlgorithmType.THROUGHPUT_BASED);
        options.setNumWorkers(config.getInt("numWorkers"));
        options.setMaxNumWorkers(config.getInt("maxNumWorkers"));
        options.setGcpCredential(credentials);
        options.setWorkerMachineType(config.getString("machineType"));
        options.setServiceAccount(config.getString("serviceAccountEmail"));
        options.setStreaming(config.getBoolean("isStreaming"));
        options.setRegion(config.getString("region"));
        options.setSdkContainerImage(config.getString("sdkContainerImage"));
        options.setJobName("fraud-detection-" + System.currentTimeMillis());

        // Need use_runner_v2 for docker containers for workers (need docker for xgboost dependencies)
        if (options.getSdkContainerImage() != null) {
            List<String> experiments;
            if (options.getExperiments() == null) {
                experiments = new ArrayList<>();
            } else {
                experiments = new ArrayList<>(options.getExperiments());
            }
            if (!experiments.contains("use_runner_v2")) {
                experiments.add("use_runner_v2");
                options.setExperiments(ImmutableList.copyOf(experiments));
            }
        }

        return options;
    }
}
