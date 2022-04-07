package com.github.miyohide.azureblobwithenc;

import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.security.keyvault.keys.models.KeyVaultKey;
import com.azure.storage.blob.specialized.BlockBlobClient;
import com.azure.storage.blob.specialized.cryptography.EncryptedBlobClient;
import com.azure.storage.blob.specialized.cryptography.EncryptedBlobClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

public class Main {
    public Main() {
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        Logger logger = LoggerFactory.getLogger("com.github.miyohide.azureblobwithenc");
        logger.info("********** Start Application **********");
        Main m = new Main();
//        m.createBlob();
        m.createEncBlob();
        logger.info("********** End Application **********");
    }

    private void createBlob() throws NoSuchAlgorithmException {
        BlobHelper blobHelper = new BlobHelper();
        //blobHelper.createBlobServiceClientWithConnectionString(System.getenv("BLOB_CONNECTION_STRING"));
        blobHelper.createBlobServiceClientWithAzureID(System.getenv("STORAGE_ENDPOINT_URL"));
        blobHelper.createBlobContainerClient(System.getenv("CONTAINER_NAME"));
        blobHelper.createBlobWithData("myblob2.txt", "This is a blob contents2");
    }

    private void createEncBlob() {
        KeyVaultInfo keyVaultInfo = new KeyVaultInfo(
                System.getenv("KEY_VAULT_NAME"),
                System.getenv("KEY_NAME"),
                "RSA-OAEP"
        );
        EncryptedBlobHelper encryptedBlobHelper = new EncryptedBlobHelper();
        encryptedBlobHelper.createEncryptedBlobClient(
                keyVaultInfo,
                System.getenv("STORAGE_ENDPOINT_URL"),
                "enc",
                "test4.txt");
        encryptedBlobHelper.updateStringData("This is a sample string2");
    }
}
