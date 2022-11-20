package com.example.storage;

import java.io.Serializable;
import lombok.Data;


/**
 * This class models a record from the customer information table
 */
@Data
public class CustInfoRecord implements Serializable {

    private String customerId;
    private int age = -1;
    private String gender = "";
    private String maritalStatus = "";
    private Double homeLongitude;
    private Double homeLatitude;
}
