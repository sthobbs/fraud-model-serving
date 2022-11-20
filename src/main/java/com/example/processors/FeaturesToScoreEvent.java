package com.example.processors;

import com.example.config.ModelPipelineOptions;
import com.example.storage.Features;
import com.example.storage.ScoreEvent;
import ml.dmlc.xgboost4j.java.Booster;
import ml.dmlc.xgboost4j.java.DMatrix;
import ml.dmlc.xgboost4j.java.XGBoost;
import ml.dmlc.xgboost4j.java.XGBoostError;
import org.apache.beam.sdk.transforms.DoFn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Score Features using XGBoost model and output ScoreEvents.
 */
public class FeaturesToScoreEvent extends DoFn<Features, ScoreEvent> {

    private final static Logger logger = LoggerFactory.getLogger(FeaturesToScoreEvent.class);

    private Booster booster;
    private String[] featureNames;
    private int nrow;
    private int ncol;
    private float missing = Float.NaN;

    public FeaturesToScoreEvent(ModelPipelineOptions options) {
        
        // Load model from GCS
        String modelPath = options.getModelPath();
        try {
            this.booster = XGBoost.loadModel(modelPath);
        }
        catch (XGBoostError e) {
            e.printStackTrace();
            logger.error("Error loading model: " + e.getMessage());
        }

        // Get feature names and DMatrix shape
        featureNames = Features.getFeatureNames();
        nrow = 1;
        ncol = featureNames.length;
    }

    @ProcessElement
    public void processElement(@Element Features feats, OutputReceiver<ScoreEvent> receiver) {

        // Create DMatrix of feature data (with one row)
        float[] data = new float[featureNames.length];
        for (int i = 0; i < featureNames.length; i++) {
            data[i] = Float.parseFloat(feats.getProperty(featureNames[i]).toString());
        }
        DMatrix dmat;
        Float score = null;

        // Score the DMatrix (i.e. get model prediction)
        try {
            dmat = new DMatrix(data, nrow, ncol, missing);
            score = booster.predict(dmat)[0][0];
        }
        catch (XGBoostError e) {
            e.printStackTrace();
            logger.error("Error making DMAtrix or predicting score: " + e.getMessage());
        }

        // Put feature values in String for ScoreEvent
        String[] featureValues = new String[data.length];
        for (int i = 0; i < featureNames.length; i++) {
            featureValues[i] = Float.toString(data[i]);
        }
        String featureValuesStr = String.join(", ", featureValues);

        // Make ScoreEvent
        ScoreEvent scoreEvent = new ScoreEvent(feats, score, featureValuesStr);
        receiver.output(scoreEvent);
    }
}
