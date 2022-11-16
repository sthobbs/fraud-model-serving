package com.example.storage;

import java.io.Serializable;

import com.example.storage.Action.ActionBuilder;

import lombok.Data;

// This class models the raw input data
@Data
public class Event implements Serializable {
    private Integer fraudLabel = null;
    private String uniqueId = null;
    private String customerId = null;
    private String sessionId = null;
    private String timestamp = null;
    private String action = null;
    private Double longitude = null;
    private Double latitude = null;
    private Double amount = null;
    private String accountType = null;
    private String recipient = null;
 
    public Action makeAction() {
        return new ActionBuilder().fraudLabel(fraudLabel)
                                  .uniqueId(uniqueId)
                                  .timestamp(timestamp)
                                  .action(action)
                                  .longitude(longitude)
                                  .latitude(latitude)
                                  .amount(amount)
                                  .accountType(accountType)
                                  .recipient(recipient)
                                  .build();
    }

}