package com.example.storage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import lombok.Data;


/**
 * This class models a session of actions
 */
@Data
public class Session implements Serializable {

    private String customerId;
    private String sessionId;
    // TODO: may add min/max timestamp later.
    private Double longitude;
    private Double latitude;
    private boolean hasTxn = false; // true if the session has at least 1 transaction
    private ArrayList<Action> actions = new ArrayList<Action>(); // TODO: might change datatype later

    // return a (shallow) copy of actions sorted by timestamp
    public ArrayList<Action> sortedActions() {

        // Copy list
        ArrayList<Action> copy = new ArrayList<Action>();
        copy.addAll(getActions()); // Note: can't modify the action elements, they still reference the originals

        // Sort list
        Collections.sort(copy, (Action a1, Action a2) ->{
            return a1.getTimestamp().compareTo(a2.getTimestamp());
        });

        return copy;
    }
}
