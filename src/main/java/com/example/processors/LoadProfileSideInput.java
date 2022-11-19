package com.example.processors;

import java.util.HashMap;
import org.apache.beam.sdk.transforms.DoFn;

import com.example.config.ModelPipelineOptions;
import com.example.storage.ProfileRecord;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;



public class LoadProfileSideInput extends DoFn<Long, HashMap<String, ProfileRecord>> {

    private String sideInputPath;

    public LoadProfileSideInput(ModelPipelineOptions options) {
        this.sideInputPath = options.getProfileSideInputPath();
    }

    @ProcessElement
    public void processElement(@Element Long i,
                               OutputReceiver<HashMap<String, ProfileRecord>> receiver) {

        BufferedReader reader;
        Gson gson = new Gson();
        HashMap<String, ProfileRecord> profileMap = new HashMap<String, ProfileRecord>();
        try {
            reader = new BufferedReader(new FileReader(sideInputPath));
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
        receiver.output(profileMap);
    }

}
