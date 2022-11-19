package com.example.storage;

import java.io.Serializable;
import lombok.Data;


@Data
public class FeaturesTxn implements Serializable {

    // non-feature fields
    private int fraudLabel;
    private String uniqueId;
    private String customerId;
    private String sessionId;
    private String timestamp;
    private String action;

    // Location features
    private double longitude;
    private double latitude;

    // Amount features
    private double amount;
    private double amountMod1;
    private double amountMod100;
    private double amountMod250;
    private double amountMod500;
    private double amountMod1000;

    // Transaction time features
    private int hour;
    private int dayOfWeek;
    private int dayOfMonth;

    // Account type features
    private int accountTypeChecking;
    private int accountTypeSavings;
    private int accountTypeCreditCard;

    // Count of each type of action
    private int transactionCount;
    private int action0Count;
    private int action1Count;
    private int action2Count;
    private int action3Count;
    private int action4Count;
    private int action5Count;
    private int action6Count;
    private int action7Count;
    private int action8Count;
    private int action9Count;
    private int actionCount;

    // Total duration and average duration per action
    private long secondsToTransaction;
    private double avgActionDuration;

    // Sum/avg/min/max amounts for transactions in session
    private double amountSum;
    private double amountAvg;
    private double amountMin;
    private double amountMax;

    // Count transactions to the current recipient in session
    private int recipientTransactionCount;

    // Number of distinct recipients
    private int distinctRecipientCount;

    // Number of repeated recipients (# txns - # distinct recipients)
    private int repeatedRecipientCount;
}
