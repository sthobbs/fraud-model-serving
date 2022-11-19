package com.example;
import java.io.Serializable;
import java.util.ArrayList;

import com.google.api.gax.paging.Page;
// import com.google.cloud.pubsublite.ProjectId;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.nio.file.Paths;


public class GcsHelper implements Serializable {

    private String projectId;
    private String bucketName;

    public GcsHelper(String projectId, String bucketName) {
        // The ID of your GCP project
        // String projectId = "your-project-id";
      
        // The ID of your GCS bucket
        // String bucketName = "your-unique-bucket-name";
        this.projectId = projectId;
        this.bucketName = bucketName;
    }

    // Get last object (alphabetically) in gcs, for a given prefix
    public String lastObjectWithPrefix(String directoryPrefix) {
      
        // The directory prefix to search for
        // String directoryPrefix = "myDirectory/"
      
        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        Page<Blob> blobs = storage.list(bucketName, Storage.BlobListOption.prefix(directoryPrefix));
        ArrayList<String> blobNames = new ArrayList<>();
        for (Blob blob : blobs.iterateAll()) {
            blobNames.add(blob.getName());
        }

        return blobNames.get(blobNames.size() - 1);
    }

    // Copy object from GCS to local file on the worker
    public void downloadObject(String objectName, String destFilePath) {
        // The ID of your GCS object
        // String objectName = "your-object-name";
    
        // The path to which the file should be downloaded
        // String destFilePath = "/local/path/to/file.txt";
    
        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
    
        Blob blob = storage.get(BlobId.of(bucketName, objectName));
        blob.downloadTo(Paths.get(destFilePath));
    
        System.out.println("Downloaded object " + objectName
                + " from bucket name " + bucketName + " to " + destFilePath);
    }
  

}
