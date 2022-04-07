package com.github.miyohide.azureblobwithenc;

import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.storage.blob.specialized.cryptography.EncryptedBlobClient;
import com.azure.storage.blob.specialized.cryptography.EncryptedBlobClientBuilder;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class EncryptedBlobHelper {
    private KeyVaultHelper keyVaultHelper;
    private EncryptedBlobClient encryptedBlobClient;

    public EncryptedBlobHelper() {
        this.keyVaultHelper = null;
        this.encryptedBlobClient = null;
    }

    public void createEncryptedBlobClient(KeyVaultInfo keyVaultInfo, String storageAccountURL, String containerName, String blobName) {
        this.keyVaultHelper = new KeyVaultHelper();
        this.keyVaultHelper.createKeyVaultClient(keyVaultInfo.getKeyVaultName());

        this.encryptedBlobClient = new EncryptedBlobClientBuilder()
                .key(this.keyVaultHelper.createAsyncKeyEncryptionKey(keyVaultInfo.getKeyName()), keyVaultInfo.getKeyWrapAlgorithm())
                .credential(new DefaultAzureCredentialBuilder().build())
                .endpoint(storageAccountURL)
                .containerName(containerName)
                .blobName(blobName)
                .buildEncryptedBlobClient();
    }

    public void updateStringData(String updateString) {
        this.encryptedBlobClient.upload(new ByteArrayInputStream(updateString.getBytes(StandardCharsets.UTF_8)), updateString.length());
    }
}
