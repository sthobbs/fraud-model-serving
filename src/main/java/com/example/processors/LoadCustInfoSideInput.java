package com.example.processors;

import java.util.HashMap;
import org.apache.beam.sdk.transforms.DoFn;

import com.example.config.ModelPipelineOptions;
import com.example.storage.CustInfoRecord;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;



public class LoadCustInfoSideInput extends DoFn<Long, HashMap<String, CustInfoRecord>> {


    private String sideInputPath;

    public LoadCustInfoSideInput(ModelPipelineOptions options) {
        this.sideInputPath = options.getCustomerInfoSideInputPath();
    }

    @ProcessElement
    public void processElement(@Element Long i,
                               OutputReceiver<HashMap<String, CustInfoRecord>> receiver) {

        BufferedReader reader;
        Gson gson = new Gson();
        HashMap<String, CustInfoRecord> custInfoMap = new HashMap<String, CustInfoRecord>();
        try {
            reader = new BufferedReader(new FileReader(sideInputPath));
            String line = reader.readLine();
            while (line != null) {
                CustInfoRecord record = gson.fromJson(line, CustInfoRecord.class);
                custInfoMap.put(record.getCustomerId(), record);
                line = reader.readLine(); // read next line
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // output customer info table
        receiver.output(custInfoMap);
    }

}
