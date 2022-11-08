package com.example.storage;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.Data;

// This class models a transaction and it's session before the transaction
@Data
public class Transaction implements Serializable {
    private Integer fraudLabel;
    private String customerId;
    private String sessionId;
    private String timestamp;
    private String action;
    private Float longitude;
    private Float latitude;
    private Float amount;
    private String accountType;
    private String recipient;
    private ArrayList<Action> actions; 

    // public String toString() {
    //     return "amount: " + amount;
    // }


}
