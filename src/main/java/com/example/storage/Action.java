package com.example.storage;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Action {
    private Integer fraudLabel;
    private String timestamp;
    private String action;
    private Float amount;
    private String accountType;
    private String recipient;
}
