package com.example.transforms;

import com.example.storage.CustInfoRecord;
import com.example.storage.FeaturesCustInfo;
import com.example.storage.Transaction;
import java.lang.Math;
import java.io.Serializable;

public class GenFeaturesCustInfo implements Serializable {
    
    public static FeaturesCustInfo process(Transaction txn, CustInfoRecord cust) {

        FeaturesCustInfo feats = new FeaturesCustInfo();

        // Age
        feats.setAge(cust.getAge());
        
        // Gender
        feats.setGenderMale(cust.getGender().equals("M") ? 1 : 0);
        
        // Marital Status
        feats.setMaritalStatusSingle(cust.getMaritalStatus().equals("single") ? 1 : 0);
        feats.setMaritalStatusMarried(cust.getMaritalStatus().equals("married") ? 1 : 0);
        feats.setMaritalStatusDivorced(cust.getMaritalStatus().equals("divorced") ? 1 : 0);

        // Location
        feats.setHomeLongitude(cust.getHomeLongitude());
        feats.setHomeLatitude(cust.getHomeLatitude());
        feats.setDistanceFromHome(
            Math.pow(
                Math.pow(txn.getLongitude() - cust.getHomeLongitude(), 2)
                + Math.pow(txn.getLatitude() - cust.getHomeLatitude(), 2),
                0.5
            )
        );

        return feats;
    }
}
