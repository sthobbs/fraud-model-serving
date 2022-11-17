package com.example.storage;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.Data;

@Data
public class ProfileRecord implements Serializable {

    private String profileDate;
    private String customerId;

    // Amounts of transactions
    private double amountMin = -1;
    private double amountMax = -1;
    private double amountAvg = -1;
    private double amountStd = -1;
    private double amountPercentile10 = -1;
    private double amountPercentile25 = -1;
    private double amountPercentile50 = -1;
    private double amountPercentile75 = -1;
    private double amountPercentile90 = -1;

    // Time between start of the session and the transaction
    private double meanSecondsToTransaction = -1;
    private double stdSecondsToTransaction = -1;

    // Number of sessions with transactions
    private int sessionCount = -1;
    
    // Number of transactions
    private int transactionCount = -1;

    // Session action count averages (before the transaction)
    private double meanSessionActionCount = -1;
    private double meanSessionAction0Count = -1;
    private double meanSessionAction1Count = -1;
    private double meanSessionAction2Count = -1;
    private double meanSessionAction3Count = -1;
    private double meanSessionAction4Count = -1;
    private double meanSessionAction5Count = -1;
    private double meanSessionAction6Count = -1;
    private double meanSessionAction7Count = -1;
    private double meanSessionAction8Count = -1;
    private double meanSessionAction9Count = -1;

    // Session action count standard deviations (before the transaction)
    private double stdSessionActionCount = -1;
    private double stdSessionAction0Count = -1;
    private double stdSessionAction1Count = -1;
    private double stdSessionAction2Count = -1;
    private double stdSessionAction3Count = -1;
    private double stdSessionAction4Count = -1;
    private double stdSessionAction5Count = -1;
    private double stdSessionAction6Count = -1;
    private double stdSessionAction7Count = -1;
    private double stdSessionAction8Count = -1;
    private double stdSessionAction9Count = -1;

    // Session transaction count averages (before and including the current transaction)
    private double meanSessionTransactionCount = -1;
    private double meanSessionTransactionFromCheckingCount = -1;
    private double meanSessionTransactionFromSavingsCount = -1;
    private double meanSessionTransactionFromCreditCardCount = -1;

    // Session transaction count averages (before and including the current transaction)
    private double stdSessionTransactionCount = -1;
    private double stdSessionTransactionFromCheckingCount = -1;
    private double stdSessionTransactionFromSavingsCount = -1;
    private double stdSessionTransactionFromCreditCardCount = -1;
    
    // Recipient array
    private ArrayList<Recipient> recipients;

    @Data
    public class Recipient implements Serializable {
        private String recipient;
        private int txnCnt;
        private String minTimestamp;
    }

}
