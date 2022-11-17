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
    int fraudLabel;
    String uniqueId;
    String customerId;
    String sessionId;
    String timestamp;

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
    

    // -------------------------------------------------------------------------
    // Profile Features
    // -------------------------------------------------------------------------

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


    // -------------------------------------------------------------------------
    // Customer Info Features
    // -------------------------------------------------------------------------

    int age;
    int genderMale;
    int maritalStatusSingle;
    int maritalStatusMarried;
    int maritalStatusDivorced;
    double homeLongitude;
    double homeLatitude;
    double distanceFromHome;

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
