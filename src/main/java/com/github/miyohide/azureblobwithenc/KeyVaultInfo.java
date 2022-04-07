package com.github.miyohide.azureblobwithenc;

public class KeyVaultInfo {
    private String keyVaultName;
    private String keyName;
    private String keyWrapAlgorithm;

    public KeyVaultInfo(String keyVaultName, String keyName, String keyWrapAlgorithm) {
        this.keyVaultName = keyVaultName;
        this.keyName = keyName;
        this.keyWrapAlgorithm = keyWrapAlgorithm;
    }

    public String getKeyVaultName() {
        return keyVaultName;
    }

    public void setKeyVaultName(String keyVaultName) {
        this.keyVaultName = keyVaultName;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getKeyWrapAlgorithm() {
        return keyWrapAlgorithm;
    }

    public void setKeyWrapAlgorithm(String keyWrapAlgorithm) {
        this.keyWrapAlgorithm = keyWrapAlgorithm;
    }
}
