package com.example.processors;

import java.util.HashMap;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.PCollectionView;

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

public class TxnToFeatures extends DoFn<Transaction, Features> {

    PCollectionView<HashMap<String, ProfileRecord>> profileMap;
    PCollectionView<HashMap<String, CustInfoRecord>> custInfoMap;    

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

        // compute features
        FeaturesTxn txnFeats = GenFeaturesTxn.process(txn);
        FeaturesProfile profileFeats = GenFeaturesProfile.process(txn, profile);
        FeaturesCustInfo custInfoFeats = GenFeaturesCustInfo.process(txn, custInfo);

        // combine features
        Features feats = new Features(txnFeats, profileFeats, custInfoFeats);

        c.output(feats);
        
    } 
}
