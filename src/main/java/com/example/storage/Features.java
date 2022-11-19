package com.example.storage;

import java.io.Serializable;
import java.lang.reflect.Field;
import lombok.Data;
import lombok.Getter;


@Data
public class Features implements Serializable {
    
    // -------------------------------------------------------------------------
    // Transaction Features
    // -------------------------------------------------------------------------

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
    
    // -------------------------------------------------------------------------
    // Profile Features
    // -------------------------------------------------------------------------

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

    // -------------------------------------------------------------------------
    // Customer Info Features
    // -------------------------------------------------------------------------

    private int age;
    private int genderMale;
    private int maritalStatusSingle;
    private int maritalStatusMarried;
    private int maritalStatusDivorced;
    private double homeLongitude;
    private double homeLatitude;
    private double distanceFromHome;

    // -------------------------------------------------------------------------
    // Feature Names
    // -------------------------------------------------------------------------

    @Getter
    private static final String[] featureNames = {
        "longitude",
        "latitude",
        "amount",
        "amountMod1",
        "amountMod100",
        "amountMod250",
        "amountMod500",
        "amountMod1000",
        "hour",
        "dayOfWeek",
        "dayOfMonth",
        "accountTypeChecking",
        "accountTypeSavings",
        "accountTypeCreditCard",
        "transactionCount",
        "action0Count",
        "action1Count",
        "action2Count",
        "action3Count",
        "action4Count",
        "action5Count",
        "action6Count",
        "action7Count",
        "action8Count",
        "action9Count",
        "actionCount",
        "secondsToTransaction",
        "avgActionDuration",
        "amountSum",
        "amountAvg",
        "amountMin",
        "amountMax",
        "recipientTransactionCount",
        "distinctRecipientCount",
        "repeatedRecipientCount",
        "profileRawInd",
        "profileRawAmountMin",
        "profileRawAmountMax",
        "profileRawAmountAvg",
        "profileRawAmountStd",
        "profileRawAmountPercentile10",
        "profileRawAmountPercentile25",
        "profileRawAmountPercentile50",
        "profileRawAmountPercentile75",
        "profileRawAmountPercentile90",
        "profileAmountZScore",
        "profileRawMeanSecondsToTransaction",
        "profileRawStdSecondsToTransaction",
        "profileSecondsToTransactionZScore",
        "profileRawSessionCount",
        "profileRawTransactionCount",
        "profileRawMeanSessionActionCount",
        "profileRawMeanSessionAction0Count",
        "profileRawMeanSessionAction1Count",
        "profileRawMeanSessionAction2Count",
        "profileRawMeanSessionAction3Count",
        "profileRawMeanSessionAction4Count",
        "profileRawMeanSessionAction5Count",
        "profileRawMeanSessionAction6Count",
        "profileRawMeanSessionAction7Count",
        "profileRawMeanSessionAction8Count",
        "profileRawMeanSessionAction9Count",
        "profileRawStdSessionActionCount",
        "profileRawStdSessionAction0Count",
        "profileRawStdSessionAction1Count",
        "profileRawStdSessionAction2Count",
        "profileRawStdSessionAction3Count",
        "profileRawStdSessionAction4Count",
        "profileRawStdSessionAction5Count",
        "profileRawStdSessionAction6Count",
        "profileRawStdSessionAction7Count",
        "profileRawStdSessionAction8Count",
        "profileRawStdSessionAction9Count",
        "profileSessionActionCountZScore",
        "profileSessionAction0CountZScore",
        "profileSessionAction1CountZScore",
        "profileSessionAction2CountZScore",
        "profileSessionAction3CountZScore",
        "profileSessionAction4CountZScore",
        "profileSessionAction5CountZScore",
        "profileSessionAction6CountZScore",
        "profileSessionAction7CountZScore",
        "profileSessionAction8CountZScore",
        "profileSessionAction9CountZScore",
        "profileRawMeanSessionTransactionCount",
        "profileRawMeanSessionTransactionFromCheckingCount",
        "profileRawMeanSessionTransactionFromSavingsCount",
        "profileRawMeanSessionTransactionFromCreditCardCount",
        "profileRawStdSessionTransactionCount",
        "profileRawStdSessionTransactionFromCheckingCount",
        "profileRawStdSessionTransactionFromSavingsCount",
        "profileRawStdSessionTransactionFromCreditCardCount",
        "profileSessionTransactionCountZScore",
        "profileSessionTransactionFromCheckingCountZScore",
        "profileSessionTransactionFromSavingsCountZScore",
        "profileSessionTransactionFromCreditCardCountZScore",
        "profileRecipientTxnCount",
        "profileDistinctRecipientCount",
        "age",
        "genderMale",
        "maritalStatusSingle",
        "maritalStatusMarried",
        "maritalStatusDivorced",
        "homeLongitude",
        "homeLatitude",
        "distanceFromHome"
    };

    @Getter
    private static final String featureNamesStr = String.join(", ", featureNames);

    public Features(){
    }

    public Features(FeaturesTxn txnFeats,
                    FeaturesProfile profileFeats,
                    FeaturesCustInfo custInfoFeats) {
        
        // Transaction Features
        this.fraudLabel = txnFeats.getFraudLabel();
        this.uniqueId = txnFeats.getUniqueId();
        this.customerId = txnFeats.getCustomerId();
        this.sessionId = txnFeats.getSessionId();
        this.timestamp = txnFeats.getTimestamp();
        this.action = txnFeats.getAction();
        this.longitude = txnFeats.getLongitude();
        this.latitude = txnFeats.getLatitude();
        this.amount = txnFeats.getAmount();
        this.amountMod1 = txnFeats.getAmountMod1();
        this.amountMod100 = txnFeats.getAmountMod100();
        this.amountMod250 = txnFeats.getAmountMod250();
        this.amountMod500 = txnFeats.getAmountMod500();
        this.amountMod1000 = txnFeats.getAmountMod1000();
        this.hour = txnFeats.getHour();
        this.dayOfWeek = txnFeats.getDayOfWeek();
        this.dayOfMonth = txnFeats.getDayOfMonth();
        this.accountTypeChecking = txnFeats.getAccountTypeChecking();
        this.accountTypeSavings = txnFeats.getAccountTypeSavings();
        this.accountTypeCreditCard = txnFeats.getAccountTypeCreditCard();
        this.transactionCount = txnFeats.getTransactionCount();
        this.action0Count = txnFeats.getAction0Count();
        this.action1Count = txnFeats.getAction1Count();
        this.action2Count = txnFeats.getAction2Count();
        this.action3Count = txnFeats.getAction3Count();
        this.action4Count = txnFeats.getAction4Count();
        this.action5Count = txnFeats.getAction5Count();
        this.action6Count = txnFeats.getAction6Count();
        this.action7Count = txnFeats.getAction7Count();
        this.action8Count = txnFeats.getAction8Count();
        this.action9Count = txnFeats.getAction9Count();
        this.actionCount = txnFeats.getActionCount();
        this.secondsToTransaction = txnFeats.getSecondsToTransaction();
        this.avgActionDuration = txnFeats.getAvgActionDuration();
        this.amountSum = txnFeats.getAmountSum();
        this.amountAvg = txnFeats.getAmountAvg();
        this.amountMin = txnFeats.getAmountMin();
        this.amountMax = txnFeats.getAmountMax();
        this.recipientTransactionCount = txnFeats.getRecipientTransactionCount();
        this.distinctRecipientCount = txnFeats.getDistinctRecipientCount();
        this.repeatedRecipientCount = txnFeats.getRepeatedRecipientCount();
        
        // Profile Features
        this.profileRawInd = profileFeats.getProfileRawInd();
        this.profileRawAmountMin = profileFeats.getProfileRawAmountMin();
        this.profileRawAmountMax = profileFeats.getProfileRawAmountMax();
        this.profileRawAmountAvg = profileFeats.getProfileRawAmountAvg();
        this.profileRawAmountStd = profileFeats.getProfileRawAmountStd();
        this.profileRawAmountPercentile10 = profileFeats.getProfileRawAmountPercentile10();
        this.profileRawAmountPercentile25 = profileFeats.getProfileRawAmountPercentile25();
        this.profileRawAmountPercentile50 = profileFeats.getProfileRawAmountPercentile50();
        this.profileRawAmountPercentile75 = profileFeats.getProfileRawAmountPercentile75();
        this.profileRawAmountPercentile90 = profileFeats.getProfileRawAmountPercentile90();
        this.profileAmountZScore = profileFeats.getProfileAmountZScore();
        this.profileRawMeanSecondsToTransaction = profileFeats.getProfileRawMeanSecondsToTransaction();
        this.profileRawStdSecondsToTransaction = profileFeats.getProfileRawStdSecondsToTransaction();
        this.profileSecondsToTransactionZScore = profileFeats.getProfileSecondsToTransactionZScore();
        this.profileRawSessionCount = profileFeats.getProfileRawSessionCount();
        this.profileRawTransactionCount = profileFeats.getProfileRawTransactionCount();
        this.profileRawMeanSessionActionCount = profileFeats.getProfileRawMeanSessionActionCount();
        this.profileRawMeanSessionAction0Count = profileFeats.getProfileRawMeanSessionAction0Count();
        this.profileRawMeanSessionAction1Count = profileFeats.getProfileRawMeanSessionAction1Count();
        this.profileRawMeanSessionAction2Count = profileFeats.getProfileRawMeanSessionAction2Count();
        this.profileRawMeanSessionAction3Count = profileFeats.getProfileRawMeanSessionAction3Count();
        this.profileRawMeanSessionAction4Count = profileFeats.getProfileRawMeanSessionAction4Count();
        this.profileRawMeanSessionAction5Count = profileFeats.getProfileRawMeanSessionAction5Count();
        this.profileRawMeanSessionAction6Count = profileFeats.getProfileRawMeanSessionAction6Count();
        this.profileRawMeanSessionAction7Count = profileFeats.getProfileRawMeanSessionAction7Count();
        this.profileRawMeanSessionAction8Count = profileFeats.getProfileRawMeanSessionAction8Count();
        this.profileRawMeanSessionAction9Count = profileFeats.getProfileRawMeanSessionAction9Count();
        this.profileRawStdSessionActionCount = profileFeats.getProfileRawStdSessionActionCount();
        this.profileRawStdSessionAction0Count = profileFeats.getProfileRawStdSessionAction0Count();
        this.profileRawStdSessionAction1Count = profileFeats.getProfileRawStdSessionAction1Count();
        this.profileRawStdSessionAction2Count = profileFeats.getProfileRawStdSessionAction2Count();
        this.profileRawStdSessionAction3Count = profileFeats.getProfileRawStdSessionAction3Count();
        this.profileRawStdSessionAction4Count = profileFeats.getProfileRawStdSessionAction4Count();
        this.profileRawStdSessionAction5Count = profileFeats.getProfileRawStdSessionAction5Count();
        this.profileRawStdSessionAction6Count = profileFeats.getProfileRawStdSessionAction6Count();
        this.profileRawStdSessionAction7Count = profileFeats.getProfileRawStdSessionAction7Count();
        this.profileRawStdSessionAction8Count = profileFeats.getProfileRawStdSessionAction8Count();
        this.profileRawStdSessionAction9Count = profileFeats.getProfileRawStdSessionAction9Count();
        this.profileSessionActionCountZScore = profileFeats.getProfileSessionActionCountZScore();
        this.profileSessionAction0CountZScore = profileFeats.getProfileSessionAction0CountZScore();
        this.profileSessionAction1CountZScore = profileFeats.getProfileSessionAction1CountZScore();
        this.profileSessionAction2CountZScore = profileFeats.getProfileSessionAction2CountZScore();
        this.profileSessionAction3CountZScore = profileFeats.getProfileSessionAction3CountZScore();
        this.profileSessionAction4CountZScore = profileFeats.getProfileSessionAction4CountZScore();
        this.profileSessionAction5CountZScore = profileFeats.getProfileSessionAction5CountZScore();
        this.profileSessionAction6CountZScore = profileFeats.getProfileSessionAction6CountZScore();
        this.profileSessionAction7CountZScore = profileFeats.getProfileSessionAction7CountZScore();
        this.profileSessionAction8CountZScore = profileFeats.getProfileSessionAction8CountZScore();
        this.profileSessionAction9CountZScore = profileFeats.getProfileSessionAction9CountZScore();
        this.profileRawMeanSessionTransactionCount = profileFeats.getProfileRawMeanSessionTransactionCount();
        this.profileRawMeanSessionTransactionFromCheckingCount = profileFeats.getProfileRawMeanSessionTransactionFromCheckingCount();
        this.profileRawMeanSessionTransactionFromSavingsCount = profileFeats.getProfileRawMeanSessionTransactionFromSavingsCount();
        this.profileRawMeanSessionTransactionFromCreditCardCount = profileFeats.getProfileRawMeanSessionTransactionFromCreditCardCount();
        this.profileRawStdSessionTransactionCount = profileFeats.getProfileRawStdSessionTransactionCount();
        this.profileRawStdSessionTransactionFromCheckingCount = profileFeats.getProfileRawStdSessionTransactionFromCheckingCount();
        this.profileRawStdSessionTransactionFromSavingsCount = profileFeats.getProfileRawStdSessionTransactionFromSavingsCount();
        this.profileRawStdSessionTransactionFromCreditCardCount = profileFeats.getProfileRawStdSessionTransactionFromCreditCardCount();
        this.profileSessionTransactionCountZScore = profileFeats.getProfileSessionTransactionCountZScore();
        this.profileSessionTransactionFromCheckingCountZScore = profileFeats.getProfileSessionTransactionFromCheckingCountZScore();
        this.profileSessionTransactionFromSavingsCountZScore = profileFeats.getProfileSessionTransactionFromSavingsCountZScore();
        this.profileSessionTransactionFromCreditCardCountZScore = profileFeats.getProfileSessionTransactionFromCreditCardCountZScore();
        this.profileRecipientTxnCount = profileFeats.getProfileRecipientTxnCount();
        this.profileDistinctRecipientCount = profileFeats.getProfileDistinctRecipientCount();
        
        // Customer Info Features
        this.age = custInfoFeats.getAge();
        this.genderMale = custInfoFeats.getGenderMale();
        this.maritalStatusSingle = custInfoFeats.getMaritalStatusSingle();
        this.maritalStatusMarried = custInfoFeats.getMaritalStatusMarried();
        this.maritalStatusDivorced = custInfoFeats.getMaritalStatusDivorced();
        this.homeLongitude = custInfoFeats.getHomeLongitude();
        this.homeLatitude = custInfoFeats.getHomeLatitude();
        this.distanceFromHome = custInfoFeats.getDistanceFromHome();   
    }

    // Dynamically get fields
    public Object getProperty(String fieldName) {
        try {
            Field field = this.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(this);
        }
        catch (IllegalAccessException | NoSuchFieldException e) {
            // TODO: add log
            return null;
        }
    }
}
