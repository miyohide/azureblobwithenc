package com.github.miyohide.azureblobwithenc;

import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;

public class Main {
    public static void main(String[] args) {
        // Blobへの接続クライアントを作成する
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .endpoint(System.getenv("BLOB_ENDPOINT"))
                .sasToken(System.getenv("BLOB_SAS_TOKEN"))
                .buildClient();

        // Blobコンテナーのクライアントを作成する
        BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient(System.getenv("CONTAINER_NAME"));
        if (!blobContainerClient.exists()) {
            blobContainerClient.create();
        }

        // Blobを作成する
        BlobClient blobClient = blobContainerClient.getBlobClient("myblockBlob");
        if (blobClient.exists()) {
            blobClient.delete();
        }
        blobClient.upload(BinaryData.fromString("This is Super Secret Data"));
    }
}
