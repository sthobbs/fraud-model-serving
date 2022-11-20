package com.example.processors;

import com.example.storage.Action;
import com.example.storage.Session;
import com.example.storage.Transaction;
import java.util.ArrayList;
import org.apache.beam.sdk.transforms.DoFn;


/**
 * Convert a Session to Transactions
 */
public class SessionToTxn extends DoFn<Session, Transaction> {

    @ProcessElement
    public void processElement(@Element Session session, OutputReceiver<Transaction> receiver) {

        // Get shallow copy of sorted actions
        ArrayList<Action> actions = session.sortedActions();

        // Get indices of transactions
        ArrayList<Integer> txnIndices = new ArrayList<Integer>();
        for (int i = 0; i < actions.size(); i++) {
            if (actions.get(i).getAction().equals("transaction")) {
                txnIndices.add(i);
            }
        }

        // Make session object for each transaction
        for (int i : txnIndices) {

            // Create new session object
            Transaction txn = new Transaction();

            // Copy immutable fields (based on the txn action or the session)
            txn.setFraudLabel(actions.get(i).getFraudLabel());
            txn.setUniqueId(actions.get(i).getUniqueId());
            txn.setCustomerId(session.getCustomerId());
            txn.setSessionId(session.getSessionId());
            txn.setTimestamp(actions.get(i).getTimestamp());
            txn.setAction(actions.get(i).getAction());
            txn.setAmount(actions.get(i).getAmount());
            txn.setAccountType(actions.get(i).getAccountType());
            txn.setRecipient(actions.get(i).getRecipient());

            // Copy mutable objects and objects based on partial sessions
            ArrayList<Action> newActions = new ArrayList<Action>();
            for (int j = 0; j <= i; j++){

                // Append action clone
                Action action = actions.get(j).clone();
                newActions.add(action);

                // Longitude
                if (action.getLongitude() != null
                && (txn.getLongitude() == null
                || action.getLongitude() > txn.getLongitude())) {
                    txn.setLongitude(action.getLongitude());
                }

                // Latitude
                if (action.getLatitude() != null
                && (txn.getLatitude() == null
                || action.getLatitude() > txn.getLatitude())) {
                    txn.setLatitude(action.getLatitude());
                }
            }

            // Append all actions
            txn.setActions(newActions);

            // Output Transaction
            receiver.output(txn);
        }
    }
}
