package com.example.storage;

import java.io.Serializable;

import com.example.storage.Action.ActionBuilder;

import lombok.Data;

@Data
public class Event implements Serializable {
    private Integer fraudLabel = null;
    private String customerId = null;
    private String sessionId = null;
    private String timestamp = null;
    private String action = null;
    private Float longitude = null;
    private Float latitude = null;
    private Float amount = null;
    private String accountType = null;
    private String recipient = null;
 
    public Action makeAction() {
        return new ActionBuilder().fraudLabel(fraudLabel)
                                  .timestamp(timestamp)
                                  .action(action)
                                  .amount(amount)
                                  .accountType(accountType)
                                  .recipient(recipient)
                                  .build();
    }

}