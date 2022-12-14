package com.example.transforms;

import com.example.storage.Action;
import com.example.storage.FeaturesTxn;
import com.example.storage.Transaction;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class generates features for a transaction
 */
public class GenFeaturesTxn implements Serializable {

    private final static Logger logger = LoggerFactory.getLogger(GenFeaturesTxn.class);

    public static FeaturesTxn process(Transaction txn) {

        FeaturesTxn feats = new FeaturesTxn();

        // Non-feature fields
        feats.setFraudLabel(txn.getFraudLabel());
        feats.setUniqueId(txn.getUniqueId());
        feats.setCustomerId(txn.getCustomerId());
        feats.setSessionId(txn.getSessionId());
        feats.setTimestamp(txn.getTimestamp());
        feats.setAction(txn.getAction());

        // Location features
        Double longitude = txn.getLongitude();
        Double latitude = txn.getLatitude();
        feats.setLongitude(longitude == null ? 0 : longitude);
        feats.setLatitude(latitude == null ? 0 : latitude);

        // Amount features
        double amount = txn.getAmount();
        feats.setAmount(amount);
        if (amount == -1) {
            feats.setAmountMod1(-1);
            feats.setAmountMod100(-1);
            feats.setAmountMod250(-1);
            feats.setAmountMod500(-1);
            feats.setAmountMod1000(-1);
        } else {
            // round 100 * amount to nearest integer
            long amount100 = new BigDecimal(100 * amount)
                .setScale(0, RoundingMode.HALF_UP).longValue();
            feats.setAmountMod1(    (float) (amount100 % (100 * 1))    / 100);
            feats.setAmountMod100(  (float) (amount100 % (100 * 100))  / 100);
            feats.setAmountMod250(  (float) (amount100 % (100 * 250))  / 100);
            feats.setAmountMod500(  (float) (amount100 % (100 * 500))  / 100);
            feats.setAmountMod1000( (float) (amount100 % (100 * 1000)) / 100);
        }

        // Transaction time features
        String dateFormat = "yyyy-MM-dd HH:mm:ss"; // TODO: put in different file
        if (txn.getTimestamp() == null) {
            feats.setHour(-1);
            feats.setDayOfWeek(-1);
            feats.setDayOfMonth(-1);
        } else {
            // Hour
            feats.setHour(Integer.parseInt(txn.getTimestamp().substring(11,13)));
            // Day of week
            try {
                String txnTimeStr = txn.getTimestamp().substring(0, 19);
                Date txnTime = new SimpleDateFormat(dateFormat).parse(txnTimeStr);
                Calendar cal = Calendar.getInstance();
                cal.setTime(txnTime);
                feats.setDayOfWeek(cal.get(Calendar.DAY_OF_WEEK));
            }
            catch (ParseException e) {
                feats.setDayOfWeek(-1);
                logger.warn("Error generating day of week feature: " + e.getMessage());
            }
            // Day of month
            feats.setDayOfMonth(Integer.parseInt(txn.getTimestamp().substring(8,10)));    
        }

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
            String txnTimeStr = txn.getTimestamp().substring(0, 19);
            Date txnTime = new SimpleDateFormat(dateFormat).parse(txnTimeStr);
            String loginTimeStr = actions.get(0).getTimestamp().substring(0, 19);
            Date loginTime = new SimpleDateFormat(dateFormat).parse(loginTimeStr);
            long secondsDiff = (txnTime.getTime() - loginTime.getTime()) / 1000;
            feats.setSecondsToTransaction(secondsDiff);
            feats.setAvgActionDuration((double) secondsDiff / actions.size());
        }
        catch (ParseException e) {
            feats.setSecondsToTransaction(-1);
            feats.setAvgActionDuration(-1);
            logger.warn("Error generating session time duration features: " + e.getMessage());
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
            .reduce(amount, (a, b) -> (a < b) ? a : b));
        feats.setAmountMax(actions.stream()
            .filter(s -> s.getAction().equals("transaction"))
            .map(s -> s.getAmount())
            .reduce((double) 0, (a, b) -> (a > b) ? a : b));

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
