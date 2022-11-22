package com.example.storage;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import lombok.Data;


/**
 * This class models scored transaction records
 */
@Data
public class ScoreEvent implements Serializable {

    private Integer fraudLabel;
    private String uniqueId;
    private String customerId;
    private String sessionId;
    private String timestamp;
    private String scoreTimestamp;
    private String action;
    private Double amount;
    private String modelId;
    private Float score;
    private String featureNamesStr;
    private String featureValuesStr;

    // Construct empty ScoreEvent
    public ScoreEvent() {
    }

    // Construct full ScoreEvent
    public ScoreEvent(Features feats, Float score, String featureValuesStr) {

        this.fraudLabel = feats.getFraudLabel();
        this.uniqueId = feats.getUniqueId();
        this.customerId = feats.getCustomerId();
        this.sessionId = feats.getSessionId();
        this.timestamp = feats.getTimestamp();
        this.action = feats.getAction();
        this.amount = feats.getAmount();
        this.modelId = "txn_model.v1"; // TODO: put in config
        this.score = score;
        this.featureNamesStr = Features.getFeatureNamesStr();
        this.featureValuesStr = featureValuesStr;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
                                                       .withZone(ZoneId.systemDefault());
        this.scoreTimestamp = formatter.format(Instant.now());
    }
}
