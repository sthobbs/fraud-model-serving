package com.example.storage;

import java.io.Serializable;
import lombok.Data;


@Data
public class FeaturesProfile implements Serializable {

    /*
    By convention, all profile features begin with "profile", and features that only
    use the profile (i.e. not current transaction data) are prefixed with "profileRaw". 
    */

    // Indicator of whether or not we have a profle for this customer
    int profileRawInd;

    // Amount features
    double profileRawAmountMin;
    double profileRawAmountMax;
    double profileRawAmountAvg;
    double profileRawAmountStd;
    double profileRawAmountPercentile10;
    double profileRawAmountPercentile25;
    double profileRawAmountPercentile50;
    double profileRawAmountPercentile75;
    double profileRawAmountPercentile90;
    double profileAmountZScore;

    // Time between start of session and first transaction
    double profileRawMeanSecondsToTransaction;
    double profileRawStdSecondsToTransaction;
    double profileSecondsToTransactionZScore;

    // Number of sessions with transactions
    int profileRawSessionCount;

    // Number of transactions
    int profileRawTransactionCount;

    // Session action count averages
    double profileRawMeanSessionActionCount;
    double profileRawMeanSessionAction0Count;
    double profileRawMeanSessionAction1Count;
    double profileRawMeanSessionAction2Count;
    double profileRawMeanSessionAction3Count;
    double profileRawMeanSessionAction4Count;
    double profileRawMeanSessionAction5Count;
    double profileRawMeanSessionAction6Count;
    double profileRawMeanSessionAction7Count;
    double profileRawMeanSessionAction8Count;
    double profileRawMeanSessionAction9Count;
    
    // Session action count standard deviations
    double profileRawStdSessionActionCount;
    double profileRawStdSessionAction0Count;
    double profileRawStdSessionAction1Count;
    double profileRawStdSessionAction2Count;
    double profileRawStdSessionAction3Count;
    double profileRawStdSessionAction4Count;
    double profileRawStdSessionAction5Count;
    double profileRawStdSessionAction6Count;
    double profileRawStdSessionAction7Count;
    double profileRawStdSessionAction8Count;
    double profileRawStdSessionAction9Count;

    // Session action count z-scores
    double profileSessionActionCountZScore;
    double profileSessionAction0CountZScore;
    double profileSessionAction1CountZScore;
    double profileSessionAction2CountZScore;
    double profileSessionAction3CountZScore;
    double profileSessionAction4CountZScore;
    double profileSessionAction5CountZScore;
    double profileSessionAction6CountZScore;
    double profileSessionAction7CountZScore;
    double profileSessionAction8CountZScore;
    double profileSessionAction9CountZScore;

    // Session transaction count averages
    double profileRawMeanSessionTransactionCount;
    double profileRawMeanSessionTransactionFromCheckingCount;
    double profileRawMeanSessionTransactionFromSavingsCount;
    double profileRawMeanSessionTransactionFromCreditCardCount;

    // Session transaction count standard deviations
    double profileRawStdSessionTransactionCount;
    double profileRawStdSessionTransactionFromCheckingCount;
    double profileRawStdSessionTransactionFromSavingsCount;
    double profileRawStdSessionTransactionFromCreditCardCount;

    // Session transaction count z-score
    double profileSessionTransactionCountZScore;
    double profileSessionTransactionFromCheckingCountZScore;
    double profileSessionTransactionFromSavingsCountZScore;
    double profileSessionTransactionFromCreditCardCountZScore;

    // Number of times they previously sent money to this recipient
    int profileRecipientTxnCount;

    // Number of distinct recipients they previously sent money to
    long profileDistinctRecipientCount;
}
