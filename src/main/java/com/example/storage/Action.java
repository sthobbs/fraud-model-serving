package com.example.storage;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

// This class models an action taken in a session
@Builder
@Data
public class Action implements Serializable {
    private Integer fraudLabel;
    private String timestamp;
    private String action;
    private Float longitude;
    private Float latitude;
    private Float amount;
    private String accountType;
    private String recipient;

    public Action clone() {
        return new ActionBuilder().fraudLabel(fraudLabel)
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
