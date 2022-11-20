package com.example.storage;

import java.io.Serializable;
import lombok.Data;


/**
 * This class contains features that depend on the Transaction and the Customer Info table
 */
@Data
public class FeaturesCustInfo implements Serializable {

    private int age;
    private int genderMale;
    private int maritalStatusSingle;
    private int maritalStatusMarried;
    private int maritalStatusDivorced;
    private double homeLongitude;
    private double homeLatitude;
    private double distanceFromHome;
}
