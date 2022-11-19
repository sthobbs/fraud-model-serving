package com.example.storage;

import java.io.Serializable;
import java.util.ArrayList;
import lombok.Data;


// This class models a transaction and it's session before the transaction
@Data
public class Transaction implements Serializable {
    private int fraudLabel = 0;
    private String uniqueId;
    private String customerId;
    private String sessionId;
    private String timestamp;
    private String action;
    private Double longitude;
    private Double latitude;
    private double amount = -1;
    private String accountType = "";
    private String recipient;
    private ArrayList<Action> actions; 
}
