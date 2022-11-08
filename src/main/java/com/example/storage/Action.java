package com.example.storage;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Action implements Serializable {
    private Integer fraudLabel;
    private String timestamp;
    private String action;
    private Float amount;
    private String accountType;
    private String recipient;
}
