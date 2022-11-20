package com.example.transforms;

import com.example.storage.CustInfoRecord;
import com.example.storage.FeaturesCustInfo;
import com.example.storage.Transaction;
import java.io.Serializable;
import java.lang.Math;


/**
 * This class generates features that depend on the Transaction and the Customer Info table
 */
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
        feats.setHomeLongitude(cust.getHomeLongitude() == null ? 0 : cust.getHomeLongitude());
        feats.setHomeLatitude(cust.getHomeLatitude() == null ? 0 : cust.getHomeLatitude());
        if (cust.getHomeLongitude() == null
                || cust.getHomeLatitude() == null
                || txn.getLongitude() == null
                ||txn.getLatitude() == null) {
            // Set distance from home to be -1 if we don't have a customer profile
            feats.setDistanceFromHome(-1);
        }
        else {
            feats.setDistanceFromHome(
                Math.pow(
                    Math.pow(txn.getLongitude() - cust.getHomeLongitude(), 2)
                    + Math.pow(txn.getLatitude() - cust.getHomeLatitude(), 2),
                    0.5
                )
            );
        }
        return feats;
    }
}
