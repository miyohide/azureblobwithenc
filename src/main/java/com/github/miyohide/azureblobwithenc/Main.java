package com.github.miyohide.azureblobwithenc;

import com.azure.security.keyvault.keys.models.KeyVaultKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;

public class Main {
    public Main() {
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        Logger logger = LoggerFactory.getLogger("com.github.miyohide.azureblobwithenc");
        logger.info("********** Start Application **********");
        Main m = new Main();
        m.createBlob();
        KeyVaultHelper keyVaultHelper = new KeyVaultHelper();
        keyVaultHelper.createKeyVaultClient("miyohidekeyvault");
        KeyVaultKey keyVaultKey = keyVaultHelper.getKeyVaultKey("testkey1");
        logger.info("----- [" + keyVaultKey.getId() + "] -----");
        logger.info("********** End Application **********");
    }

    private void createBlob() throws NoSuchAlgorithmException {
        BlobHelper blobHelper = new BlobHelper();
        //blobHelper.createBlobServiceClientWithConnectionString(System.getenv("BLOB_CONNECTION_STRING"));
        blobHelper.createBlobServiceClientWithAzureID(System.getenv("STORAGE_ENDPOINT_URL"));
        blobHelper.createBlobContainerClient(System.getenv("CONTAINER_NAME"));
        blobHelper.createBlobWithData("myblob2.txt", "This is a blob contents2");
    }
}
