package com.example.processors;

import org.apache.beam.sdk.transforms.DoFn;
import com.example.config.ModelPipelineOptions;
import com.example.GcsHelper;
import com.example.storage.CustInfoRecord;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Load customer info from GCS and create a side input.
 */
public class LoadCustInfoSideInput extends DoFn<Long, HashMap<String, CustInfoRecord>> {

    private final static Logger logger = LoggerFactory.getLogger(LoadCustInfoSideInput.class);

    private String localFilePath = "/side_input_customer_info.json";
    private GcsHelper gcsHelper;

    public LoadCustInfoSideInput(ModelPipelineOptions options) {
        this.gcsHelper = new GcsHelper(options.getProject(), options.getBucket());
    }

    @ProcessElement
    public void process(ProcessContext c) {

        logger.info("Generating customer info side input...");

        // Get pipeline options
        ModelPipelineOptions options = c.getPipelineOptions().as(ModelPipelineOptions.class);

        // Get last blob (alphabetically) in gcs, for a given prefix
        String lastBlobName = gcsHelper.lastObjectWithPrefix(options.getCustomerInfoSideInputPrefix());

        // Copy side input from GCS to local file on the worker
        gcsHelper.downloadObject(lastBlobName, localFilePath);

        // Load new side input into a hashmap line-by-line       
        BufferedReader reader;
        Gson gson = new Gson();
        HashMap<String, CustInfoRecord> custInfoMap = new HashMap<String, CustInfoRecord>();
        try {
            reader = new BufferedReader(new FileReader(localFilePath));
            String line = reader.readLine();
            while (line != null) {
                CustInfoRecord record = gson.fromJson(line, CustInfoRecord.class);
                custInfoMap.put(record.getCustomerId(), record);
                line = reader.readLine(); // read next line
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Error customer info side input: " + e.getMessage());
        }

    // Output customer info table   
    c.output(custInfoMap);
    }
}
