package com.example.processors;

import java.util.HashMap;
import org.apache.beam.sdk.transforms.DoFn;
import com.example.storage.CustInfoRecord;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.ConfigurationException;



public class LoadCustInfoSideInput extends DoFn<Long, HashMap<String, CustInfoRecord>> {
 

    @ProcessElement
    public void processElement(@Element Long i,
                               OutputReceiver<HashMap<String, CustInfoRecord>> receiver) {

        // Get path to side input data
        String sideInputPath = "";
        try {
            Configuration config = new PropertiesConfiguration("config.properties");
            sideInputPath = config.getString("custInfoPath");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }

        BufferedReader reader;
        Gson gson = new Gson();
        HashMap<String, CustInfoRecord> custInfoMap = new HashMap<String, CustInfoRecord>();
        try {
            reader = new BufferedReader(new FileReader(sideInputPath));
            String line = reader.readLine();
            while (line != null) {
                CustInfoRecord record = gson.fromJson(line, CustInfoRecord.class);
                custInfoMap.put(record.getCustomerId(), record);
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // output Transaction
        receiver.output(custInfoMap);
    }

}
