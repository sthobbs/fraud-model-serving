package com.example.config;

// import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.runners.dataflow.options.DataflowPipelineOptions;
import org.apache.beam.sdk.options.Default;



public interface ModelPipelineOptions extends DataflowPipelineOptions {

    @Description("input path to disk or pubsub subscription")
    String getInputPath();
    void setInputPath(String input);

    @Description("type of input source (e.g. 'disk', 'pubsub')")
    @Default.String("disk")
    String getInputType();
    void setInputType(String input);

    @Description("model path")
    String getModelPath();
    void setModelPath(String input);

    @Description("prefix of output path")
    String getOutputPath();
    void setOutputPath(String output);

    @Description("type of input destination (e.g. 'disk', 'pubsub')")
    String getOutputType();
    void setOutputType(String output);

    // @Description("project id")
    // @Default.String("analog-arbor-367702")
    // String getProjectId();
    // void setProjectId(String input);

    @Description("bucket id")
    String getBucket();
    void setBucket(String input);


    @Description("profile side input path")
    String getProfileSideInputPrefix();
    void setProfileSideInputPrefix(String input);

    @Description("customer info side input path")
    String getCustomerInfoSideInputPrefix();
    void setCustomerInfoSideInputPrefix(String input);
}