package com.example.processors;

import com.example.config.ModelPipelineOptions;
import com.example.GcsHelper;
import com.example.storage.ProfileRecord;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import org.apache.beam.sdk.transforms.DoFn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Load profile from GCS and create a side input.
 */
public class LoadProfileSideInput extends DoFn<Long, HashMap<String, ProfileRecord>> {

    private final static Logger logger = LoggerFactory.getLogger(LoadProfileSideInput.class);

    private String localFilePath = "/side_input_profile.json";
    private GcsHelper gcsHelper;

    public LoadProfileSideInput(ModelPipelineOptions options) {
        this.gcsHelper = new GcsHelper(options.getProject(), options.getBucket());
    }

    @ProcessElement
    public void process(ProcessContext c) {

        logger.info("Generating profile side input...");

        // Get pipeline options
        ModelPipelineOptions options = c.getPipelineOptions().as(ModelPipelineOptions.class);

        // Get last blob (alphabetically) in gcs, for a given prefix
        String lastBlobName = gcsHelper.lastObjectWithPrefix(options.getProfileSideInputPrefix());

        // Copy side input from GCS to local file on the worker
        gcsHelper.downloadObject(lastBlobName, localFilePath);

        // Load new side input into a hashmap line-by-line       
        BufferedReader reader;
        Gson gson = new Gson();
        HashMap<String, ProfileRecord> profileMap = new HashMap<String, ProfileRecord>();
        try {
            reader = new BufferedReader(new FileReader(localFilePath));
            String line = reader.readLine();
            while (line != null) {
                ProfileRecord record = gson.fromJson(line, ProfileRecord.class);
                profileMap.put(record.getCustomerId(), record);
                line = reader.readLine(); // read next line
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Error customer info side input: " + e.getMessage());
        }

        // Output profile table
        c.output(profileMap);
    }
}
