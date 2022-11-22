package com.example.transforms;

import com.example.storage.Action;
import com.example.storage.FeaturesProfile;
import com.example.storage.ProfileRecord;
import com.example.storage.Transaction;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class generates features that depend on the Transaction and the Profile table
 */
public class GenFeaturesProfile implements Serializable {

    private final static Logger logger = LoggerFactory.getLogger(GenFeaturesProfile.class);

    public static FeaturesProfile process(Transaction txn, ProfileRecord profile) {

        FeaturesProfile feats = new FeaturesProfile();

        // get actions
        ArrayList<Action> actions = txn.getActions();

        // Indicator of whether or not we have a profle for this customer
        feats.setProfileRawInd(profile.getCustomerId() == null ? 0 : 1);

        // Amount features
        feats.setProfileRawAmountMin(profile.getAmountMin());
        feats.setProfileRawAmountMax(profile.getAmountMax());
        feats.setProfileRawAmountAvg(profile.getAmountAvg());
        feats.setProfileRawAmountStd(profile.getAmountStd());
        feats.setProfileRawAmountPercentile10(profile.getAmountPercentile10());
        feats.setProfileRawAmountPercentile25(profile.getAmountPercentile25());
        feats.setProfileRawAmountPercentile50(profile.getAmountPercentile50());
        feats.setProfileRawAmountPercentile75(profile.getAmountPercentile75());
        feats.setProfileRawAmountPercentile90(profile.getAmountPercentile90());
        if (profile.getAmountStd() > 0) {
            feats.setProfileAmountZScore((txn.getAmount() - profile.getAmountAvg()) / profile.getAmountStd());
        } else {
            feats.setProfileAmountZScore(-1);
        }

        // Time between start of session and first transaction
        feats.setProfileRawMeanSecondsToTransaction(profile.getMeanSecondsToTransaction());
        feats.setProfileRawStdSecondsToTransaction(profile.getStdSecondsToTransaction());
        String dateFormat = "yyyy-MM-dd HH:mm:ss"; // TODO: put in different file
        if (profile.getStdSecondsToTransaction() > 0) {
            try {
                String txnTimeStr = txn.getTimestamp().substring(0, 19);
                Date txnTime = new SimpleDateFormat(dateFormat).parse(txnTimeStr);
                String loginTimeStr = actions.get(0).getTimestamp().substring(0, 19);
                Date loginTime = new SimpleDateFormat(dateFormat).parse(loginTimeStr);
                Long secondsDiff = (txnTime.getTime() - loginTime.getTime()) / 1000;
                feats.setProfileSecondsToTransactionZScore(
                    (secondsDiff - profile.getMeanSecondsToTransaction())
                    / profile.getStdSecondsToTransaction());
            }
            catch (ParseException | ArithmeticException e) {
                feats.setProfileSecondsToTransactionZScore(-1);
                logger.warn("Error generating profile time features: " + e.getMessage());
            }
        } else {
            feats.setProfileSecondsToTransactionZScore(-1);
        }

        // Number of sessions with transactions
        feats.setProfileRawSessionCount(profile.getSessionCount());

        // Number of transactions
        feats.setProfileRawTransactionCount(profile.getTransactionCount());

        // Session action count averages
        feats.setProfileRawMeanSessionActionCount(profile.getMeanSessionActionCount());
        feats.setProfileRawMeanSessionAction0Count(profile.getMeanSessionAction0Count());
        feats.setProfileRawMeanSessionAction1Count(profile.getMeanSessionAction1Count());
        feats.setProfileRawMeanSessionAction2Count(profile.getMeanSessionAction2Count());
        feats.setProfileRawMeanSessionAction3Count(profile.getMeanSessionAction3Count());
        feats.setProfileRawMeanSessionAction4Count(profile.getMeanSessionAction4Count());
        feats.setProfileRawMeanSessionAction5Count(profile.getMeanSessionAction5Count());
        feats.setProfileRawMeanSessionAction6Count(profile.getMeanSessionAction6Count());
        feats.setProfileRawMeanSessionAction7Count(profile.getMeanSessionAction7Count());
        feats.setProfileRawMeanSessionAction8Count(profile.getMeanSessionAction8Count());
        feats.setProfileRawMeanSessionAction9Count(profile.getMeanSessionAction9Count());

        // Session action count standard deviations
        feats.setProfileRawStdSessionActionCount(profile.getStdSessionActionCount());
        feats.setProfileRawStdSessionAction0Count(profile.getStdSessionAction0Count());
        feats.setProfileRawStdSessionAction1Count(profile.getStdSessionAction1Count());
        feats.setProfileRawStdSessionAction2Count(profile.getStdSessionAction2Count());
        feats.setProfileRawStdSessionAction3Count(profile.getStdSessionAction3Count());
        feats.setProfileRawStdSessionAction4Count(profile.getStdSessionAction4Count());
        feats.setProfileRawStdSessionAction5Count(profile.getStdSessionAction5Count());
        feats.setProfileRawStdSessionAction6Count(profile.getStdSessionAction6Count());
        feats.setProfileRawStdSessionAction7Count(profile.getStdSessionAction7Count());
        feats.setProfileRawStdSessionAction8Count(profile.getStdSessionAction8Count());
        feats.setProfileRawStdSessionAction9Count(profile.getStdSessionAction9Count());

        // Session action count z-scores
        if (profile.getStdSessionActionCount() > 0) {
            feats.setProfileSessionActionCountZScore(
                (actions.size() - profile.getMeanSessionActionCount())
                / profile.getStdSessionActionCount());
        } else {
            feats.setProfileSessionActionCountZScore(-1);
        }

        if (profile.getStdSessionAction0Count() > 0) {
            feats.setProfileSessionAction0CountZScore(
                (actions.stream().filter(s -> s.getAction().equals("action_0")).count()
                    - profile.getMeanSessionAction0Count())
                / profile.getStdSessionAction0Count());
        } else {
            feats.setProfileSessionAction0CountZScore(-1);
        }

        if (profile.getStdSessionAction1Count() > 0) {
            feats.setProfileSessionAction1CountZScore(
                (actions.stream().filter(s -> s.getAction().equals("action_1")).count()
                    - profile.getMeanSessionAction1Count())
                / profile.getStdSessionAction1Count());
        } else {
            feats.setProfileSessionAction1CountZScore(-1);
        }

        if (profile.getStdSessionAction2Count() > 0) {
            feats.setProfileSessionAction2CountZScore(
                (actions.stream().filter(s -> s.getAction().equals("action_2")).count()
                    - profile.getMeanSessionAction2Count())
                / profile.getStdSessionAction2Count());
        } else {
            feats.setProfileSessionAction2CountZScore(-1);
        }

        if (profile.getStdSessionAction3Count() > 0) {
            feats.setProfileSessionAction3CountZScore(
                (actions.stream().filter(s -> s.getAction().equals("action_3")).count()
                    - profile.getMeanSessionAction3Count())
                / profile.getStdSessionAction3Count());
        } else {
            feats.setProfileSessionAction3CountZScore(-1);
        }

        if (profile.getStdSessionAction4Count() > 0) {
            feats.setProfileSessionAction4CountZScore(
                (actions.stream().filter(s -> s.getAction().equals("action_4")).count()
                    - profile.getMeanSessionAction4Count())
                / profile.getStdSessionAction4Count());
        } else {
            feats.setProfileSessionAction4CountZScore(-1);
        }

        if (profile.getStdSessionAction5Count() > 0) {
            feats.setProfileSessionAction5CountZScore(
                (actions.stream().filter(s -> s.getAction().equals("action_5")).count()
                    - profile.getMeanSessionAction5Count())
                / profile.getStdSessionAction5Count());
        } else {
            feats.setProfileSessionAction5CountZScore(-1);
        }

        if (profile.getStdSessionAction6Count() > 0) {
            feats.setProfileSessionAction6CountZScore(
                (actions.stream().filter(s -> s.getAction().equals("action_6")).count()
                    - profile.getMeanSessionAction6Count())
                / profile.getStdSessionAction6Count());
        } else {
            feats.setProfileSessionAction6CountZScore(-1);
        }

        if (profile.getStdSessionAction7Count() > 0) {
            feats.setProfileSessionAction7CountZScore(
                (actions.stream().filter(s -> s.getAction().equals("action_7")).count()
                    - profile.getMeanSessionAction7Count())
                / profile.getStdSessionAction7Count());
        } else {
            feats.setProfileSessionAction7CountZScore(-1);
        }

        if (profile.getStdSessionAction8Count() > 0) {
            feats.setProfileSessionAction8CountZScore(
                (actions.stream().filter(s -> s.getAction().equals("action_8")).count()
                    - profile.getMeanSessionAction8Count())
                / profile.getStdSessionAction8Count());
        } else {
            feats.setProfileSessionAction8CountZScore(-1);
        }

        if (profile.getStdSessionAction9Count() > 0) {
            feats.setProfileSessionAction9CountZScore(
                (actions.stream().filter(s -> s.getAction().equals("action_9")).count()
                    - profile.getMeanSessionAction9Count())
                / profile.getStdSessionAction9Count());
        } else {
            feats.setProfileSessionAction9CountZScore(-1);
        }

        // Session transaction count averages
        feats.setProfileRawMeanSessionTransactionCount(profile.getMeanSessionTransactionCount());
        feats.setProfileRawMeanSessionTransactionFromCheckingCount(profile.getMeanSessionTransactionFromCheckingCount());
        feats.setProfileRawMeanSessionTransactionFromSavingsCount(profile.getMeanSessionTransactionFromSavingsCount());
        feats.setProfileRawMeanSessionTransactionFromCreditCardCount(profile.getMeanSessionTransactionFromCreditCardCount());

        // Session transaction count standard deviations
        feats.setProfileRawStdSessionTransactionCount(profile.getStdSessionTransactionCount());
        feats.setProfileRawStdSessionTransactionFromCheckingCount(profile.getStdSessionTransactionFromCheckingCount());
        feats.setProfileRawStdSessionTransactionFromSavingsCount(profile.getStdSessionTransactionFromSavingsCount());
        feats.setProfileRawStdSessionTransactionFromCreditCardCount(profile.getStdSessionTransactionFromCreditCardCount());

        // Session transaction count z-score
        if (profile.getStdSessionTransactionCount() > 0) {
            feats.setProfileSessionTransactionCountZScore(
                (actions.stream().filter(s -> s.getAction().equals("transaction")).count()
                    - profile.getMeanSessionTransactionCount())
                / profile.getStdSessionTransactionCount());
        } else {
            feats.setProfileSessionTransactionCountZScore(-1);
        }

        if (profile.getStdSessionTransactionFromCheckingCount() > 0) {
            feats.setProfileSessionTransactionFromCheckingCountZScore(
                (actions.stream().filter(s -> s.getAction().equals("transaction")
                        && s.getAccountType().equals("checking")).count()
                    - profile.getMeanSessionTransactionFromCheckingCount())
                / profile.getStdSessionTransactionFromCheckingCount());
        } else {
            feats.setProfileSessionTransactionFromCheckingCountZScore(-1);
        }

        if (profile.getStdSessionTransactionFromSavingsCount() > 0) {
            feats.setProfileSessionTransactionFromSavingsCountZScore(
                (actions.stream().filter(s -> s.getAction().equals("transaction")
                        && s.getAccountType().equals("savings")).count()
                    - profile.getMeanSessionTransactionFromSavingsCount())
                / profile.getStdSessionTransactionFromSavingsCount());
        } else {
            feats.setProfileSessionTransactionFromSavingsCountZScore(-1);
        }

        if (profile.getStdSessionTransactionFromCreditCardCount() > 0) {
            feats.setProfileSessionTransactionFromCreditCardCountZScore(
                (actions.stream().filter(s -> s.getAction().equals("transaction")
                        && s.getAccountType().equals("credit_card")).count()
                    - profile.getMeanSessionTransactionFromCreditCardCount())
                / profile.getStdSessionTransactionFromCreditCardCount());
        } else {
            feats.setProfileSessionTransactionFromCreditCardCountZScore(-1);
        }

        // Number of times they previously sent money to this recipient
        String recipient = txn.getRecipient();
        if (profile.getRecipients() == null){
            feats.setProfileRecipientTxnCount(0);
        } else {
            feats.setProfileRecipientTxnCount(profile.getRecipients().stream()
                .filter(s -> s.getRecipient().equals(recipient))
                .map(s -> s.getTxnCnt())
                .findFirst().orElse(0));
        }

        // Number of distinct recipients they previously sent money to
        if (profile.getRecipients() == null){
            feats.setProfileDistinctRecipientCount(0);
        } else {
        feats.setProfileDistinctRecipientCount(profile.getRecipients().stream()
            .map(s -> s.getRecipient())
            .distinct()
            .count());
        }

        return feats;
    }
}
