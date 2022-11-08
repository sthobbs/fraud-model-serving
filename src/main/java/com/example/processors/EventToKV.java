package com.example.processors;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;
import com.example.storage.Event;
// import org.apache.beam.sdk.transforms.SerializableFunction;

// Get sessionId (to use as a key) for an Event
public class EventToKV extends DoFn<Event, KV<String, Event>> {

    @ProcessElement
    public void processElement(@Element Event event, OutputReceiver<KV<String, Event>> receiver) {
        String sessionId = event.getSessionId();
        receiver.output(KV.of(sessionId, event));
    }
    
}



