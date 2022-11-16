package com.example.storage;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.Data;

@Data
public class ProfileRecord implements Serializable {

    private String profileDate;
    private String customerId;

    // Amounts of transactions
    private double amountMin;
    private double amountMax;
    private double amountAvg;
    private double amountStd;
    private double amountPercentile10;
    private double amountPercentile25;
    private double amountPercentile50;
    private double amountPercentile75;
    private double amountPercentile90;

    // Time between start of the session and the transaction
    private Double meanSecondsToTransaction;
    private Double stdSecondsToTransaction;

    // Number of sessions with transactions
    private int sessionCount;
    
    // Number of transactions
    private int transactionCount;

    // Session action count averages (before the transaction)
    private double meanSessionActionCount;
    private double meanSessionAction0Count;
    private double meanSessionAction1Count;
    private double meanSessionAction2Count;
    private double meanSessionAction3Count;
    private double meanSessionAction4Count;
    private double meanSessionAction5Count;
    private double meanSessionAction6Count;
    private double meanSessionAction7Count;
    private double meanSessionAction8Count;
    private double meanSessionAction9Count;

    // Session action count standard deviations (before the transaction)
    private double stdSessionActionCount;
    private double stdSessionAction0Count;
    private double stdSessionAction1Count;
    private double stdSessionAction2Count;
    private double stdSessionAction3Count;
    private double stdSessionAction4Count;
    private double stdSessionAction5Count;
    private double stdSessionAction6Count;
    private double stdSessionAction7Count;
    private double stdSessionAction8Count;
    private double stdSessionAction9Count;

    // Session transaction count averages (before and including the current transaction)
    private double meanSessionTransactionCount;
    private double meanSessionTransactionFromCheckingCount;
    private double meanSessionTransactionFromSavingsCount;
    private double meanSessionTransactionFromCreditCardCount;

    // Session transaction count averages (before and including the current transaction)
    private double stdSessionTransactionCount;
    private double stdSessionTransactionFromCheckingCount;
    private double stdSessionTransactionFromSavingsCount;
    private double stdSessionTransactionFromCreditCardCount;
    
    // Recipient array
    private ArrayList<Recipient> recipients;

    @Data
    public class Recipient implements Serializable {
        private String recipient;
        private int txnCnt;
        private String minTimestamp;
    }

}
