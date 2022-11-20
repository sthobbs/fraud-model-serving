package com.example.storage;

import java.io.Serializable;
import lombok.Data;


/**
 * This class contains features that depend on the Transaction and the Profile table
 */
@Data
public class FeaturesProfile implements Serializable {

    /*
    By convention, all profile features begin with "profile", and features that only
    use the profile (i.e. not current transaction data) are prefixed with "profileRaw". 
    */

    // Indicator of whether or not we have a profle for this customer
    private int profileRawInd;

    // Amount features
    private double profileRawAmountMin;
    private double profileRawAmountMax;
    private double profileRawAmountAvg;
    private double profileRawAmountStd;
    private double profileRawAmountPercentile10;
    private double profileRawAmountPercentile25;
    private double profileRawAmountPercentile50;
    private double profileRawAmountPercentile75;
    private double profileRawAmountPercentile90;
    private double profileAmountZScore;

    // Time between start of session and first transaction
    private double profileRawMeanSecondsToTransaction;
    private double profileRawStdSecondsToTransaction;
    private double profileSecondsToTransactionZScore;

    // Number of sessions with transactions
    private int profileRawSessionCount;

    // Number of transactions
    private int profileRawTransactionCount;

    // Session action count averages
    private double profileRawMeanSessionActionCount;
    private double profileRawMeanSessionAction0Count;
    private double profileRawMeanSessionAction1Count;
    private double profileRawMeanSessionAction2Count;
    private double profileRawMeanSessionAction3Count;
    private double profileRawMeanSessionAction4Count;
    private double profileRawMeanSessionAction5Count;
    private double profileRawMeanSessionAction6Count;
    private double profileRawMeanSessionAction7Count;
    private double profileRawMeanSessionAction8Count;
    private double profileRawMeanSessionAction9Count;

    // Session action count standard deviations
    private double profileRawStdSessionActionCount;
    private double profileRawStdSessionAction0Count;
    private double profileRawStdSessionAction1Count;
    private double profileRawStdSessionAction2Count;
    private double profileRawStdSessionAction3Count;
    private double profileRawStdSessionAction4Count;
    private double profileRawStdSessionAction5Count;
    private double profileRawStdSessionAction6Count;
    private double profileRawStdSessionAction7Count;
    private double profileRawStdSessionAction8Count;
    private double profileRawStdSessionAction9Count;

    // Session action count z-scores
    private double profileSessionActionCountZScore;
    private double profileSessionAction0CountZScore;
    private double profileSessionAction1CountZScore;
    private double profileSessionAction2CountZScore;
    private double profileSessionAction3CountZScore;
    private double profileSessionAction4CountZScore;
    private double profileSessionAction5CountZScore;
    private double profileSessionAction6CountZScore;
    private double profileSessionAction7CountZScore;
    private double profileSessionAction8CountZScore;
    private double profileSessionAction9CountZScore;

    // Session transaction count averages
    private double profileRawMeanSessionTransactionCount;
    private double profileRawMeanSessionTransactionFromCheckingCount;
    private double profileRawMeanSessionTransactionFromSavingsCount;
    private double profileRawMeanSessionTransactionFromCreditCardCount;

    // Session transaction count standard deviations
    private double profileRawStdSessionTransactionCount;
    private double profileRawStdSessionTransactionFromCheckingCount;
    private double profileRawStdSessionTransactionFromSavingsCount;
    private double profileRawStdSessionTransactionFromCreditCardCount;

    // Session transaction count z-score
    private double profileSessionTransactionCountZScore;
    private double profileSessionTransactionFromCheckingCountZScore;
    private double profileSessionTransactionFromSavingsCountZScore;
    private double profileSessionTransactionFromCreditCardCountZScore;

    // Number of times they previously sent money to this recipient
    private int profileRecipientTxnCount;

    // Number of distinct recipients they previously sent money to
    private long profileDistinctRecipientCount;
}
