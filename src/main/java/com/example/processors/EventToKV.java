package com.example.processors;

import com.example.storage.Event;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;


/**
 * Convert Event to (sessiondId, Event) key value pairs.
 */
public class EventToKV extends DoFn<Event, KV<String, Event>> {

    @ProcessElement
    public void processElement(@Element Event event, OutputReceiver<KV<String, Event>> receiver) {
        String sessionId = event.getSessionId();
        receiver.output(KV.of(sessionId, event));
    }   
}
