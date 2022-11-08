package com.example.processors;
import java.io.Serializable;

import org.apache.beam.sdk.transforms.Combine.CombineFn;
// import java.util.ArrayList;

import com.example.storage.Action;
import com.example.storage.Event;
import com.example.storage.Session;

// import lombok.Data;
import lombok.EqualsAndHashCode;




// ToDO: can I replace Accum with Session?
public class SessionCombineFn extends CombineFn<Event, SessionCombineFn.Accum, Session> {
    @EqualsAndHashCode
    public static class Accum implements Serializable {
        Session session = new Session();
    }

    @Override
    public Accum createAccumulator() { return new Accum(); }

    @Override
    public Accum addInput(Accum accum, Event input) {
        // set customerId (use max value so output is deterministic)
        if (input.getCustomerId() != null
        && (accum.session.getCustomerId() == null
        || input.getCustomerId().compareTo(accum.session.getCustomerId()) > 0)) {
            accum.session.setCustomerId(input.getCustomerId());
        }
        // set sessionId
        if (input.getSessionId() != null
        && (accum.session.getSessionId() == null
        || input.getSessionId().compareTo(accum.session.getSessionId()) > 0)) {
            accum.session.setSessionId(input.getSessionId());
        }
        // set longitude
        if (input.getLongitude() != null
        && (accum.session.getLongitude() == null
        || input.getLongitude() > accum.session.getLongitude())) {
            accum.session.setLongitude(input.getLongitude());
        }
        // set latitude
        if (input.getLatitude() != null
        && (accum.session.getLatitude() == null
        || input.getLatitude() > accum.session.getLatitude())) {
            accum.session.setLatitude(input.getLatitude());
        }
        // set hasTxn
        if (input.getAction() != null && input.getAction() == "transaction"){ // ToDO: parameterize this
            accum.session.setHasTxn(true);
        }
        // append action to session
        Action action = input.makeAction();
        accum.session.getActions().add(action);
        return accum;
  }

    @Override
    public Accum mergeAccumulators(Iterable<Accum> accums) {
        Accum merged = createAccumulator();
        for (Accum accum : accums) {
            // set customerId (use max value so output is deterministic)
            if (accum.session.getCustomerId() != null
            && (merged.session.getCustomerId() == null
            || accum.session.getCustomerId().compareTo(merged.session.getCustomerId()) > 0)) {
                merged.session.setCustomerId(accum.session.getCustomerId());
            }
            // set sessionId
            if (accum.session.getSessionId() != null
            && (merged.session.getSessionId() == null
            || accum.session.getSessionId().compareTo(merged.session.getSessionId()) > 0)) {
                merged.session.setSessionId(accum.session.getSessionId());
            }
            // set longitude
            if (accum.session.getLongitude() != null
            && (merged.session.getLongitude() == null
            || accum.session.getLongitude() > merged.session.getLongitude())) {
                merged.session.setLongitude(accum.session.getLongitude());
            }
            // set latitude
            if (accum.session.getLatitude() != null
            && (merged.session.getLatitude() == null
            || accum.session.getLatitude() > merged.session.getLatitude())) {
                merged.session.setLatitude(accum.session.getLatitude());
            }
            // set hasTxn
            if (accum.session.isHasTxn() || merged.session.isHasTxn()) {
                merged.session.setHasTxn(true);
            }
            // merge actions in sessions
            merged.session.getActions().addAll(accum.session.getActions());
            // ToDO address possible duplicates at somepoint, in which I should probably change the datatype
        }
        return merged;
    }

  @Override
  public Session extractOutput(Accum accum) {
    return accum.session;
  }
}



// // ToDO: can I replace Accum with Session?
// public class SessionCombineFn extends CombineFn<Event, SessionCombineFn.Accum, Session> {

//     @Override
//     public Session createAccumulator() { return new Session(); }

//     @Override
//     public Session addInput(Session session, Event input) {
//         // set customerId (use max value so output is deterministic)
//         if (input.getCustomerId() != null
//         && (session.getCustomerId() == null
//         || input.getCustomerId().compareTo(session.getCustomerId()) > 0)) {
//             session.setCustomerId(input.getCustomerId());
//         }
//         // set sessionId
//         if (input.getSessionId() != null
//         && (session.getSessionId() == null
//         || input.getSessionId().compareTo(session.getSessionId()) > 0)) {
//             session.setSessionId(input.getSessionId());
//         }
//         // set longitude
//         if (input.getLongitude() != null
//         && (session.getLongitude() == null
//         || input.getLongitude() > session.getLongitude())) {
//             session.setLongitude(input.getLongitude());
//         }
//         // set latitude
//         if (input.getLatitude() != null
//         && (session.getLatitude() == null
//         || input.getLatitude() > session.getLatitude())) {
//             session.setLatitude(input.getLatitude());
//         }
//         // set hasTxn
//         if (input.getAction() != null && input.getAction() == "transaction"){ // ToDO: parameterize this
//             session.setHasTxn(true);
//         }
//         // append action to session
//         Action action = input.makeAction();
//         session.getActions().add(action);
//         return session;
//   }

//     @Override
//     public Session mergeAccumulators(Iterable<Accum> accums) {
//         Session merged = createAccumulator();
//         for (Session session : accums) {
//             // set customerId (use max value so output is deterministic)
//             if (session.getCustomerId() != null
//             && (merged.getCustomerId() == null
//             || session.getCustomerId().compareTo(merged.getCustomerId()) > 0)) {
//                 merged.setCustomerId(session.getCustomerId());
//             }
//             // set sessionId
//             if (session.getSessionId() != null
//             && (merged.getSessionId() == null
//             || session.getSessionId().compareTo(merged.getSessionId()) > 0)) {
//                 merged.setSessionId(session.getSessionId());
//             }
//             // set longitude
//             if (session.getLongitude() != null
//             && (merged.getLongitude() == null
//             || session.getLongitude() > merged.getLongitude())) {
//                 merged.setLongitude(session.getLongitude());
//             }
//             // set latitude
//             if (session.getLatitude() != null
//             && (merged.getLatitude() == null
//             || session.getLatitude() > merged.getLatitude())) {
//                 merged.setLatitude(session.getLatitude());
//             }
//             // set hasTxn
//             if (session.isHasTxn() || merged.isHasTxn()) {
//                 merged.setHasTxn(true);
//             }
//             // merge actions in sessions
//             merged.getActions().addAll(session.getActions());
//             // ToDO address possible duplicates at somepoint, in which I should probably change the datatype
//         }
//         return merged;
//     }

//   @Override
//   public Session extractOutput(Session session) {
//     return session;
//   }
// }