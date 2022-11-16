package com.example.storage;

import java.io.Serializable;
import lombok.Data;


@Data
public class FeaturesCustInfo implements Serializable {

    int age;
    int genderMale;
    int maritalStatusSingle;
    int maritalStatusMarried;
    int maritalStatusDivorced;
    double homeLongitude;
    double homeLatitude;
    double distanceFromHome;
    
}
