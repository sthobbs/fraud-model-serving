package com.example.storage;

import com.example.storage.Action.ActionBuilder;
import java.io.Serializable;
import lombok.Data;


/**
 * This class models the raw input data
 */
@Data
public class Event implements Serializable {
    private Integer fraudLabel;
    private String uniqueId;
    private String customerId;
    private String sessionId;
    private String timestamp;
    private String action;
    private Double longitude;
    private Double latitude;
    private Double amount;
    private String accountType;
    private String recipient;
 
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
