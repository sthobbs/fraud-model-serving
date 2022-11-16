package com.example.transforms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
// import java.util.NoSuchElementException;
import java.util.Date;

import com.example.storage.Action;
import com.example.storage.FeaturesTxn;
import com.example.storage.Transaction;
import java.util.Calendar;
import java.io.Serializable;

public class GenFeaturesTxn implements Serializable {



    public static FeaturesTxn process(Transaction txn) {// throws ParseException {

        FeaturesTxn feats = new FeaturesTxn();

        // non-feature fields
        feats.setFraudLabel(txn.getFraudLabel());
        feats.setUniqueId(txn.getUniqueId());
        feats.setCustomerId(txn.getCustomerId());
        feats.setSessionId(txn.getSessionId());
        feats.setTimestamp(txn.getTimestamp());

        // Location features
        feats.setLongitude(txn.getLongitude());
        feats.setLatitude(txn.getLongitude());

        // Amount features
        double amount = txn.getAmount();
        feats.setAmount(amount);
        int amount100 = (int) (100 * amount);
        feats.setAmountMod1(    (float) (amount100 % (100 * 1))    / 100);
        feats.setAmountMod100(  (float) (amount100 % (100 * 100))  / 100);
        feats.setAmountMod250(  (float) (amount100 % (100 * 250))  / 100);
        feats.setAmountMod500(  (float) (amount100 % (100 * 500))  / 100);
        feats.setAmountMod1000( (float) (amount100 % (100 * 1000)) / 100);

        // Transaction time features
        feats.setHour(Integer.parseInt(txn.getTimestamp().substring(11,13)));

        String dateFormat = "yyyy-MM-dd HH:mm:ss.SSS"; // TODO: put in different file
        try {
            String txnTimeStr = txn.getTimestamp().substring(0, 23);
            Date txnTime = new SimpleDateFormat(dateFormat).parse(txnTimeStr);
            Calendar cal = Calendar.getInstance();
            cal.setTime(txnTime);
            feats.setDayOfWeek(cal.get(Calendar.DAY_OF_WEEK));
        }
        catch (ParseException e) {
            // setDayOfWeek(null)
        }

        feats.setDayOfMonth(Integer.parseInt(txn.getTimestamp().substring(8,10)));    

        // Account type features
        String accountType = txn.getAccountType();
        feats.setAccountTypeChecking(  accountType.equals("checking")    ? 1 : 0);
        feats.setAccountTypeSavings(   accountType.equals("savings")     ? 1 : 0);
        feats.setAccountTypeCreditCard(accountType.equals("credit_card") ? 1 : 0);
        
        // Count of each type of action
        ArrayList<Action> actions = txn.getActions();
        feats.setTransactionCount((int) actions.stream().filter(s -> s.getAction().equals("transaction")).count());
        feats.setAction0Count((int) actions.stream().filter(s -> s.getAction().equals("action_0")).count());
        feats.setAction1Count((int) actions.stream().filter(s -> s.getAction().equals("action_1")).count());
        feats.setAction2Count((int) actions.stream().filter(s -> s.getAction().equals("action_2")).count());
        feats.setAction3Count((int) actions.stream().filter(s -> s.getAction().equals("action_3")).count());
        feats.setAction4Count((int) actions.stream().filter(s -> s.getAction().equals("action_4")).count());
        feats.setAction5Count((int) actions.stream().filter(s -> s.getAction().equals("action_5")).count());
        feats.setAction6Count((int) actions.stream().filter(s -> s.getAction().equals("action_6")).count());
        feats.setAction7Count((int) actions.stream().filter(s -> s.getAction().equals("action_7")).count());
        feats.setAction8Count((int) actions.stream().filter(s -> s.getAction().equals("action_8")).count());
        feats.setAction9Count((int) actions.stream().filter(s -> s.getAction().equals("action_9")).count());
        feats.setActionCount(actions.size());

        // Total duration and average duration per action
        try {
            String txnTimeStr = txn.getTimestamp().substring(0, 23);
            Date txnTime = new SimpleDateFormat(dateFormat).parse(txnTimeStr);
            String loginTimeStr = actions.get(0).getTimestamp().substring(0, 23);
            Date loginTime = new SimpleDateFormat(dateFormat).parse(loginTimeStr);
            Long secondsDiff = (txnTime.getTime() - loginTime.getTime()) / 1000;
            feats.setSecondsToTransaction(secondsDiff);
            feats.setAvgActionDuration((double) secondsDiff / actions.size());
        }
        catch (ParseException e) {
            feats.setSecondsToTransaction(null);
            feats.setAvgActionDuration(null);
        }

        // Sum/avg/min/max amounts for transactions in session
        feats.setAmountSum(actions.stream()
            .filter(s -> s.getAction().equals("transaction"))
            .map(s -> s.getAmount())
            .reduce((double) 0, (a, b) -> a + b));
        feats.setAmountAvg(feats.getAmountSum() / feats.getTransactionCount());
        feats.setAmountMin(actions.stream()
            .filter(s -> s.getAction().equals("transaction"))
            .map(s -> s.getAmount())
            .reduce((double) 0, (a, b) -> (a < b) ? a : b));
        feats.setAmountMax(actions.stream()
            .filter(s -> s.getAction().equals("transaction"))
            .map(s -> s.getAmount())
            .reduce((double) 0, (a, b) -> (a > b) ? a : b));
        // feats.setAmountMin((float) actions.stream()
        //     .filter(s -> s.getAction().equals("transaction"))
        //     .mapToDouble(s -> s.getAmount()).min().orElseThrow(NoSuchElementException::new));
        // feats.setAmountMax((float) actions.stream()
        //     .filter(s -> s.getAction().equals("transaction"))
        //     .mapToDouble(s -> s.getAmount()).max().orElseThrow(NoSuchElementException::new));


        // Count transactions to the current recipient in session
        String recipient = txn.getRecipient();
        feats.setRecipientTransactionCount((int) actions.stream()
            .filter(s -> s.getAction().equals("transaction")
                && s.getRecipient().equals(recipient))
            .count());
        
        // Number of distinct recipients
        feats.setDistinctRecipientCount((int) actions.stream()
            .filter(s -> s.getAction().equals("transaction"))
            .map(s -> s.getRecipient())
            .distinct()
            .count());

        // Number of repeated recipients (# txns - # distinct recipients)
        feats.setRepeatedRecipientCount(feats.getTransactionCount() - feats.getDistinctRecipientCount());

        return feats;

    }
    
}
