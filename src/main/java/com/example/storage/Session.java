package com.example.storage;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

// This class models a session of actions
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
    
    // return a (shallow) copy of actions sorted by timestamp
    public ArrayList<Action> sortedActions() {
        // copy list
        ArrayList<Action> copy = new ArrayList<Action>();
        copy.addAll(getActions()); // Note: can't modify these actions, they still reference the originals
        // sort list
        Collections.sort(copy, (Action a1, Action a2) ->{
            return a1.getTimestamp().compareTo(a2.getTimestamp());
        });
        return copy;
    }
}
