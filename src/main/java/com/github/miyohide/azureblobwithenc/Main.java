package com.github.miyohide.azureblobwithenc;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobHttpHeaders;
import com.azure.storage.blob.specialized.BlockBlobClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        Logger logger = LoggerFactory.getLogger("com.github.miyohide.azureblobwithenc");
        logger.info("********** Start Application **********");
        createBlob();
        logger.info("********** End Application **********");
    }

    private static void createBlob() throws NoSuchAlgorithmException {
        // Blobへの接続クライアントを作成する
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(System.getenv("BLOB_CONNECTION_STRING"))
                .buildClient();

        // Blobコンテナーのクライアントを作成する
        BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient(System.getenv("CONTAINER_NAME"));
        if (!blobContainerClient.exists()) {
            blobContainerClient.create();
        }

        String blobContents = "This is a super secret data";
        // Blobを作成する
        BlockBlobClient blobClient = blobContainerClient.getBlobClient("myblob").getBlockBlobClient();
        if (blobClient.exists()) {
            blobClient.delete();
        }
        Map<String, String> blobMetadata = Collections.singletonMap("myblobmetadata", "sample");
        BlobHttpHeaders blobHttpHeaders = new BlobHttpHeaders().setContentDisposition("attachment")
                .setContentType("text/plain");
        byte[] md5 = MessageDigest.getInstance("MD5").digest(blobContents.getBytes(StandardCharsets.UTF_8));
        InputStream dataStream = new ByteArrayInputStream(blobContents.getBytes(StandardCharsets.UTF_8));
        blobClient.uploadWithResponse(dataStream, blobContents.length(), blobHttpHeaders, blobMetadata, null, md5, null, null, null);
    }
}
