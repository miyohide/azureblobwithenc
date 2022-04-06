package com.github.miyohide.azureblobwithenc;

import com.azure.core.cryptography.AsyncKeyEncryptionKey;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.security.keyvault.keys.KeyClient;
import com.azure.security.keyvault.keys.KeyClientBuilder;
import com.azure.security.keyvault.keys.cryptography.KeyEncryptionKeyClientBuilder;
import com.azure.security.keyvault.keys.models.KeyVaultKey;

public class KeyVaultHelper {
    private KeyClient keyClient;
    private String keyVaultUri;

    public KeyVaultHelper() {
        this.keyClient = null;
        this.keyVaultUri = null;
    }

    public void createKeyVaultClient(String keyvaultName) {
        this.keyVaultUri = "https://" + keyvaultName + ".vault.azure.net";
        this.keyClient = new KeyClientBuilder()
                .vaultUrl(this.keyVaultUri)
                .credential(new DefaultAzureCredentialBuilder().build())
                .buildClient();
    }

    public KeyVaultKey getKeyVaultKey(String keyName) {
        return this.keyClient.getKey(keyName);
    }

    public AsyncKeyEncryptionKey createAsyncKeyEncryptionKey(String keyName) {
        KeyVaultKey key = this.getKeyVaultKey(keyName);
        AsyncKeyEncryptionKey asyncKeyEncryptionKey = new KeyEncryptionKeyClientBuilder()
                .credential(new DefaultAzureCredentialBuilder().build())
                .buildAsyncKeyEncryptionKey(key.getId())
                .block();
        return asyncKeyEncryptionKey;
    }
}
