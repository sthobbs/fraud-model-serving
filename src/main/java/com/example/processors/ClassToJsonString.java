package com.example.processors;

import com.google.gson.Gson;
import org.apache.beam.sdk.transforms.DoFn;


/**
 * Convert elements of a PCollection to Strings.
 */
public class ClassToJsonString<T> extends DoFn<T, String> {

    private static Gson gson = new Gson();
    
    @ProcessElement
    public void processElement(@Element T element, OutputReceiver<String> receiver) {
        // String s = element.toString();
        String s = gson.toJson(element);
        receiver.output(s);
    }
}
