package com.example.storage;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

// This class models an action taken in a session
@Builder
@Data
public class Action implements Serializable {
    private Integer fraudLabel;
    private String uniqueId;
    private String timestamp;
    private String action;
    private Double longitude;
    private Double latitude;
    private Double amount;
    private String accountType;
    private String recipient;

    public Action clone() {
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
