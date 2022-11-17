package com.example.processors;

import org.apache.beam.sdk.transforms.DoFn;
// import org.apache.beam.sdk.transforms.DoFn.Setup;
// import import java.lang.Float;

import com.example.storage.Features;
import com.example.storage.ScoreEvent;

import ml.dmlc.xgboost4j.java.Booster;
import ml.dmlc.xgboost4j.java.DMatrix;
import ml.dmlc.xgboost4j.java.XGBoost;
import ml.dmlc.xgboost4j.java.XGBoostError;


public class FeaturesToScoreEvent extends DoFn<Features, ScoreEvent> {
    

    private Booster booster;


    @Setup
    public void setupDoFn(){
        // Load XGBoost model
        // TODO: put model path in config
        try {
            Booster booster = XGBoost.loadModel("model.bin");
        }
        catch (XGBoostError e) {
            //...
        }
    }


    @ProcessElement
    public void processElement(@Element Features feats, OutputReceiver<ScoreEvent> receiver) {

        // Create Dmatrix with (one row of) feature data
        String[] featureNames = Features.getFeatureNames();
        float[] data = new float[featureNames.length];
        for (int i = 0; i < featureNames.length; i++) {
            // String s = feats.getProperty(featureNames[i]).toString();
            // Float v = Float.parseFloat(s);
            // Object s = feats.getProperty(featureNames[i]);
            // float s = (float) feats.getProperty(featureNames[i]);
            // Float v = Float.parseFloat(s);
            data[i] = Float.parseFloat(feats.getProperty(featureNames[i]).toString());
        }
        int nrow = 1;
        int ncol = data.length;
        float missing = Float.NaN;
        DMatrix dmat;
        Float score = null;
        try {
            dmat = new DMatrix(data, nrow, ncol, missing);
            // get prediction
            score = booster.predict(dmat)[0][0];
        }
        catch (XGBoostError e) {
            //...
        }


        // put feature values in String for ScoreEvent
        String[] featureValues = new String[data.length];
        for (int i = 0; i < featureNames.length; i++) {
            featureValues[i] = Float.toString(data[i]);
        }
        String featureValuesStr = String.join(", ", featureValues);
        // make ScoreEvent
        ScoreEvent scoreEvent = new ScoreEvent(feats, score, featureValuesStr);
        receiver.output(scoreEvent);
    }
        
}
