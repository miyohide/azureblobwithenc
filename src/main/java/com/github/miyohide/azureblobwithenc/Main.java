package com.github.miyohide.azureblobwithenc;

import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;

public class Main {
    public static void main(String[] args) {
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .endpoint(System.getenv("BLOB_ENDPOINT"))
                .sasToken(System.getenv("BLOB_SAS_TOKEN"))
                .buildClient();

        BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient(System.getenv("CONTAINER_NAME"));

        BlobClient blobClient = blobContainerClient.getBlobClient("myblockBlob");
        blobClient.upload(BinaryData.fromString("This is Super Secret Data"));
    }
}
