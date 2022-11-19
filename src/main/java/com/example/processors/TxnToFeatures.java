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


public class TxnToFeatures extends DoFn<Transaction, Features> {

    private PCollectionView<HashMap<String, ProfileRecord>> profileMap;
    private PCollectionView<HashMap<String, CustInfoRecord>> custInfoMap;    

    public TxnToFeatures(PCollectionView<HashMap<String, ProfileRecord>> profileMap,
                         PCollectionView<HashMap<String, CustInfoRecord>> custInfoMap) {
        this.profileMap = profileMap;
        this.custInfoMap = custInfoMap;
    }

    @ProcessElement
    public void process(ProcessContext c) {
        
        Transaction txn = c.element();
        
        // get profile and customer info side inputs
        String custId = txn.getCustomerId();
        ProfileRecord profile = c.sideInput(profileMap).get(custId);
        CustInfoRecord custInfo = c.sideInput(custInfoMap).get(custId);

        // if we don't have a profile / cust info on a customer, then make an empty object
        if (profile == null) {
            profile = new ProfileRecord();
        }
        if (custInfo == null) {
            custInfo = new CustInfoRecord();
        }

        // compute features
        FeaturesTxn txnFeats = GenFeaturesTxn.process(txn);
        FeaturesProfile profileFeats = GenFeaturesProfile.process(txn, profile);
        FeaturesCustInfo custInfoFeats = GenFeaturesCustInfo.process(txn, custInfo);

        // combine features
        Features feats = new Features(txnFeats, profileFeats, custInfoFeats);

        c.output(feats); 
    } 
}
