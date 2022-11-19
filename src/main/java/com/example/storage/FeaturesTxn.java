package com.example.storage;

import java.io.Serializable;
import lombok.Data;


@Data
public class FeaturesTxn implements Serializable {


    // non-feature fields
    int fraudLabel;
    String uniqueId;
    String customerId;
    String sessionId;
    String timestamp;
    String action;

    // Location features
    double longitude;
    double latitude;

    // Amount features
    double amount;
    double amountMod1;
    double amountMod100;
    double amountMod250;
    double amountMod500;
    double amountMod1000;

    // Transaction time features
    int hour;
    int dayOfWeek;
    int dayOfMonth;

    // Account type features
    int accountTypeChecking;
    int accountTypeSavings;
    int accountTypeCreditCard;

    // Count of each type of action
    int transactionCount;
    int action0Count;
    int action1Count;
    int action2Count;
    int action3Count;
    int action4Count;
    int action5Count;
    int action6Count;
    int action7Count;
    int action8Count;
    int action9Count;
    int actionCount;

    // Total duration and average duration per action
    long secondsToTransaction;
    double avgActionDuration;

    // Sum/avg/min/max amounts for transactions in session
    double amountSum;
    double amountAvg;
    double amountMin;
    double amountMax;

    // Count transactions to the current recipient in session
    int recipientTransactionCount;

    // Number of distinct recipients
    int distinctRecipientCount;

    // Number of repeated recipients (# txns - # distinct recipients)
    int repeatedRecipientCount;


}
