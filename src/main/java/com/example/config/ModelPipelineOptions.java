package com.example.config;

import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.Default;



public interface ModelPipelineOptions extends PipelineOptions {

    @Description("input path to disk or pubsub subscription")
    String getInputPath();
    void setInputPath(String input);

    @Description("type of input source (e.g. 'disk', 'pubsub')")
    @Default.String("disk")
    String getInputType();
    void setInputType(String input);

    @Description("prefix of output path")
    String getOutputPath();
    void setOutputPath(String output);

    @Description("type of input destination (e.g. 'disk', 'pubsub')")
    String getOutputType();
    void setOutputType(String output);
}