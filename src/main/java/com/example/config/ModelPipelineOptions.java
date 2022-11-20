package com.example.config;

import org.apache.beam.runners.dataflow.options.DataflowPipelineOptions;
import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;


/**
 * Options that can be used to configure the pipeline.
 */
public interface ModelPipelineOptions extends DataflowPipelineOptions {

    // Input source type
    @Description("type of input source (e.g. 'gcs', 'pubsub')")
    @Default.String("gcs")
    String getInputType();
    void setInputType(String input);
    
    // GCS Input path
    @Description("GCS input path")
    String getInputGcsPath();
    void setInputGcsPath(String input);

    // Input Pub/Sub subscription
    @Description("Pub/Sub subscription")
    String getInputPubsubSubscription();
    void setInputPubsubSubscription(String input);

    // Output destination type
    @Description("type of out destination (e.g. 'gcs', 'pubsub')")
    String getOutputType();
    void setOutputType(String output);

    // GCS Output path
    @Description("GCS output path prefix (before sharding)")
    String getOutputGcsPath();
    void setOutputGcsPath(String output);

    // Output Pub/Sub topic
    @Description("Pub/Sub topic")
    String getOutputPubsubTopic();
    void setOutputPubsubTopic(String output);
    
    // Model path
    @Description("model path")
    String getModelPath();
    void setModelPath(String input);

    // Bucket name (where side inputs are stored)
    @Description("bucket name")
    String getBucket();
    void setBucket(String input);

    // Path to profile table side input
    @Description("profile side input path")
    String getProfileSideInputPrefix();
    void setProfileSideInputPrefix(String input);

    // Path to customer info table side input
    @Description("customer info side input path")
    String getCustomerInfoSideInputPrefix();
    void setCustomerInfoSideInputPrefix(String input);


}
