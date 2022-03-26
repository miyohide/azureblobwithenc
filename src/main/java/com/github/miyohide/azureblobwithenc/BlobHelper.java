package com.github.miyohide.azureblobwithenc;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobHttpHeaders;
import com.azure.storage.blob.specialized.BlockBlobClient;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Map;

public class BlobHelper {
    private BlobServiceClient blobServiceClient;
    private BlobContainerClient blobContainerClient;

    public BlobHelper() {
    }

    public void createBlobServiceClient(String connectionString) {
        // blobに接続するためのクライアントを作成する
        this.blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient();
    }

    public void createBlobContainerClient(String containerName) {
        // コンテナーにアクセスするクライアントを作成する
        this.blobContainerClient = this.blobServiceClient
                .getBlobContainerClient(containerName);
        if (!this.blobContainerClient.exists()) {
            this.blobContainerClient.create();
        }
    }

    void createBlobWithData(String blobName, String blobContents) throws NoSuchAlgorithmException {
        // Blobを作成する
        BlockBlobClient blobClient = blobContainerClient.getBlobClient(blobName).getBlockBlobClient();
        // メタデータの設定
        Map<String, String> blobMetadata = Collections.singletonMap("myblobmetadata", "sample");
        // 各種ヘッダーの設定
        BlobHttpHeaders blobHttpHeaders = new BlobHttpHeaders().setContentDisposition("attachment")
                .setContentType("text/plain");
        byte[] md5 = MessageDigest.getInstance("MD5").digest(blobContents.getBytes(StandardCharsets.UTF_8));
        InputStream dataStream = new ByteArrayInputStream(blobContents.getBytes(StandardCharsets.UTF_8));
        blobClient.uploadWithResponse(dataStream, blobContents.length(), blobHttpHeaders, blobMetadata, null, md5, null, null, null);
    }
}