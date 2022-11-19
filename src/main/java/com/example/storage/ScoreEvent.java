package com.example.storage;

import java.io.Serializable;
import lombok.Data;


@Data
public class ScoreEvent implements Serializable {

    private Integer fraudLabel;
    private String uniqueId;
    private String customerId;
    private String sessionId;
    private String timestamp;
    private String action;
    private Double amount;
    private String modelId;
    private Float score;
    private String featureNamesStr;
    private String featureValuesStr;

    public ScoreEvent() {
    }

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
    }
}
