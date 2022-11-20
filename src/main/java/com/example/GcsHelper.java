package com.example;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.Serializable;
import java.nio.file.Paths;
import java.util.ArrayList;


/**
 * This class provides helper methods for interacting with Google Cloud Storage
 */
public class GcsHelper implements Serializable {

    private String projectId;
    private String bucketName;

    public GcsHelper(String projectId, String bucketName) {
        // projectId: The ID of your GCP project (e.g. "your-project-id")      
        // bucketName: The ID of your GCS bucket (e.g. "your-unique-bucket-name")

        this.projectId = projectId;
        this.bucketName = bucketName;
    }

    // Get last object (alphabetically) in gcs, for a given prefix
    public String lastObjectWithPrefix(String directoryPrefix) {
        // directoryPrefix: The directory prefix to search for (e.g. "myDirectory/")

        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        Page<Blob> blobs = storage.list(bucketName, Storage.BlobListOption.prefix(directoryPrefix));
        ArrayList<String> blobNames = new ArrayList<>();
        for (Blob blob : blobs.iterateAll()) {
            blobNames.add(blob.getName());
        }

        return blobNames.get(blobNames.size() - 1);
    }

    // Copy object from GCS to local file on the dataflow worker
    public void downloadObject(String objectName, String destFilePath) {
        // objectName: The ID of your GCS object (e.g. "your-object-name")
        // destFilePath: The path to which the file should be downloaded
            // (e.g. "/local/path/to/file.txt")

        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        Blob blob = storage.get(BlobId.of(bucketName, objectName));
        blob.downloadTo(Paths.get(destFilePath));
        System.out.println("Downloaded object " + objectName
                + " from bucket name " + bucketName + " to " + destFilePath);
    }
}
