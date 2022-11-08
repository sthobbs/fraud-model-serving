package com.example.storage;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

@Data
public class Session implements Serializable {
    private String customerId = null;
    private String sessionId = null;
    // may need min/max timestamp
    //private String timestamp = null;
    private Float longitude = null;
    private Float latitude = null;
    private boolean hasTxn = false; // true if the session has at least 1 transaction
    private ArrayList<Action> actions = new ArrayList<Action>(); // TODO: might change datatype later
    
    // public String toString() {
    //     return "customerId: " + customerId;
    // }
}
