package com.example.processors;

import org.apache.beam.sdk.transforms.SerializableFunction;
import com.example.storage.Session;

// Filter Session on those with transactions
public class FilterSession implements SerializableFunction<Session, Boolean> {

    @Override
    public Boolean apply(Session session) {
        return session.isHasTxn();
    }
    
}



