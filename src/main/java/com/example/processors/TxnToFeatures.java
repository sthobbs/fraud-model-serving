package com.example.processors;

import com.example.storage.CustInfoRecord;
import com.example.storage.Features;
import com.example.storage.FeaturesCustInfo;
import com.example.storage.FeaturesProfile;
import com.example.storage.FeaturesTxn;
import com.example.storage.ProfileRecord;
import com.example.storage.Transaction;
import com.example.transforms.GenFeaturesCustInfo;
import com.example.transforms.GenFeaturesProfile;
import com.example.transforms.GenFeaturesTxn;
import java.util.HashMap;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.PCollectionView;


/**
 * Convert Transaction to Features
 */
public class TxnToFeatures extends DoFn<Transaction, Features> {

    // Side input hash maps
    private PCollectionView<HashMap<String, ProfileRecord>> profileMap;
    private PCollectionView<HashMap<String, CustInfoRecord>> custInfoMap;

    // Constructor
    public TxnToFeatures(PCollectionView<HashMap<String, ProfileRecord>> profileMap,
                         PCollectionView<HashMap<String, CustInfoRecord>> custInfoMap) {
        this.profileMap = profileMap;
        this.custInfoMap = custInfoMap;
    }

    @ProcessElement
    public void process(ProcessContext c) {
        
        // Get transaction
        Transaction txn = c.element();
        
        // Get profile and customer info side inputs
        String custId = txn.getCustomerId();
        ProfileRecord profile = c.sideInput(profileMap).get(custId);
        CustInfoRecord custInfo = c.sideInput(custInfoMap).get(custId);

        // If we don't have a profile (or cust info) on a customer, then make an empty record
        if (profile == null) {
            profile = new ProfileRecord();
        }
        if (custInfo == null) {
            custInfo = new CustInfoRecord();
        }

        // Compute features that depend solely on the Transaction
        FeaturesTxn txnFeats = GenFeaturesTxn.process(txn);

        // Compute features that depend on the Transaction and the Profile table
        FeaturesProfile profileFeats = GenFeaturesProfile.process(txn, profile);

        // Compute features that depend on the Transaction and the Customer Info table
        FeaturesCustInfo custInfoFeats = GenFeaturesCustInfo.process(txn, custInfo);

        // Combine features
        Features feats = new Features(txnFeats, profileFeats, custInfoFeats);

        c.output(feats); 
    }
}
