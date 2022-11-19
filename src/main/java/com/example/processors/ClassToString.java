package com.example.processors;

import org.apache.beam.sdk.transforms.DoFn;

public class ClassToString<T> extends DoFn<T, String> {
    
    @ProcessElement
    public void processElement(@Element T element, OutputReceiver<String> receiver) {
        String s = element.toString();
        receiver.output(s);
    }
}