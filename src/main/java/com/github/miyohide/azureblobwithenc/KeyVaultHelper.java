package com.github.miyohide.azureblobwithenc;

import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.security.keyvault.keys.KeyClient;
import com.azure.security.keyvault.keys.KeyClientBuilder;
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
}
