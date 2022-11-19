package com.example.processors;

import com.example.storage.Session;
import org.apache.beam.sdk.transforms.SerializableFunction;


// Filter Session on those with transactions
public class SessionFilter implements SerializableFunction<Session, Boolean> {

    @Override
    public Boolean apply(Session session) {
        return !session.isHasTxn();
    }
    
}



