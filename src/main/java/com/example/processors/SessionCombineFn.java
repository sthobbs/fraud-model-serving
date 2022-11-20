package com.example.processors;

import com.example.storage.Action;
import com.example.storage.Event;
import com.example.storage.Session;
import java.io.Serializable;
import lombok.EqualsAndHashCode;
import org.apache.beam.sdk.transforms.Combine.CombineFn;


/**
 * CombineFn to combine events into sessions.
 */
public class SessionCombineFn extends CombineFn<Event, SessionCombineFn.Accum, Session> {
    
    // Accumulator for CombineFn
    @EqualsAndHashCode
    public static class Accum implements Serializable {
        Session session = new Session();
    }

    // Create a new accumulator
    @Override
    public Accum createAccumulator() { return new Accum(); }

    // Add an event to the accumulator
    @Override
    public Accum addInput(Accum accum, Event input) {

        // Set customerId (use max value so output is deterministic)
        if (input.getCustomerId() != null
        && (accum.session.getCustomerId() == null
        || input.getCustomerId().compareTo(accum.session.getCustomerId()) > 0)) {
            accum.session.setCustomerId(input.getCustomerId());
        }
        // Set sessionId
        if (input.getSessionId() != null
        && (accum.session.getSessionId() == null
        || input.getSessionId().compareTo(accum.session.getSessionId()) > 0)) {
            accum.session.setSessionId(input.getSessionId());
        }
        // Set longitude
        if (input.getLongitude() != null
        && (accum.session.getLongitude() == null
        || input.getLongitude() > accum.session.getLongitude())) {
            accum.session.setLongitude(input.getLongitude());
        }
        // Set latitude
        if (input.getLatitude() != null
        && (accum.session.getLatitude() == null
        || input.getLatitude() > accum.session.getLatitude())) {
            accum.session.setLatitude(input.getLatitude());
        }
        // Set hasTxn
        if (input.getAction() != null && input.getAction() == "transaction"){ // ToDO: parameterize this
            accum.session.setHasTxn(true);
        }
        // Append action to session
        Action action = input.makeAction();
        accum.session.getActions().add(action);
        return accum;
    }

    // Merge accumulators
    @Override
    public Accum mergeAccumulators(Iterable<Accum> accums) {
        Accum merged = createAccumulator();
        for (Accum accum : accums) {

            // Set customerId (use max value so output is deterministic)
            if (accum.session.getCustomerId() != null
            && (merged.session.getCustomerId() == null
            || accum.session.getCustomerId().compareTo(merged.session.getCustomerId()) > 0)) {
                merged.session.setCustomerId(accum.session.getCustomerId());
            }
            // Set sessionId
            if (accum.session.getSessionId() != null
            && (merged.session.getSessionId() == null
            || accum.session.getSessionId().compareTo(merged.session.getSessionId()) > 0)) {
                merged.session.setSessionId(accum.session.getSessionId());
            }
            // Set longitude
            if (accum.session.getLongitude() != null
            && (merged.session.getLongitude() == null
            || accum.session.getLongitude() > merged.session.getLongitude())) {
                merged.session.setLongitude(accum.session.getLongitude());
            }
            // Set latitude
            if (accum.session.getLatitude() != null
            && (merged.session.getLatitude() == null
            || accum.session.getLatitude() > merged.session.getLatitude())) {
                merged.session.setLatitude(accum.session.getLatitude());
            }
            // Set hasTxn
            if (accum.session.isHasTxn() || merged.session.isHasTxn()) {
                merged.session.setHasTxn(true);
            }
            // Merge actions in sessions
            merged.session.getActions().addAll(accum.session.getActions());
            // ToDO(?): May want to remove duplicates (from pubsub)
        }
        return merged;
    }

    // Extract the output from the accumulator
    @Override
    public Session extractOutput(Accum accum) {
        return accum.session;
    }
}
