package com.example.processors;

import java.util.HashMap;
import org.apache.beam.sdk.transforms.DoFn;

import com.example.GcsHelper;
import com.example.config.ModelPipelineOptions;
import com.example.storage.ProfileRecord;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;



public class LoadProfileSideInput extends DoFn<Long, HashMap<String, ProfileRecord>> {

    private String localFilePath = "/side_input_profile.json";
    private GcsHelper gcsHelper;

    public LoadProfileSideInput(ModelPipelineOptions options) {
        this.gcsHelper = new GcsHelper(options.getProject(), options.getBucket());
    }

    @ProcessElement
    public void process(ProcessContext c) {

        ModelPipelineOptions options = c.getPipelineOptions().as(ModelPipelineOptions.class);

        // get last object (alphabetically) in gcs, for a given prefix
        String lastBlobName = gcsHelper.lastObjectWithPrefix(options.getProfileSideInputPrefix());
            
        // copy side input from GCS to local file on the worker
        gcsHelper.downloadObject(lastBlobName, localFilePath);

        // load new side input into memory   
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
        }

        // output profile table
        c.output(profileMap);
    }
}
