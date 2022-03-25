package com.github.miyohide.azureblobwithenc;

import com.azure.core.cryptography.AsyncKeyEncryptionKey;
import com.azure.core.cryptography.AsyncKeyEncryptionKeyResolver;
import com.azure.storage.blob.specialized.cryptography.EncryptedBlobClient;
import com.azure.storage.blob.specialized.cryptography.EncryptedBlobClientBuilder;
import reactor.core.publisher.Mono;

// see. https://github.com/Azure/azure-sdk-for-java/tree/azure-storage-blob-cryptography_12.14.4/sdk/storage/azure-storage-blob-cryptography
public class EncryptionHelper {
    public static EncryptedBlobClient getEncryptedBlobClient(String blobName, String containerName) {
        return new EncryptedBlobClientBuilder()
                .key(EncryptionHelper.getKey(), "keyWrapAlgorithm")
                .keyResolver(EncryptionHelper.getKeyResolver())
                .blobName(blobName)
                .containerName(containerName)
                .buildEncryptedBlobClient();
    }

    public static AsyncKeyEncryptionKey getKey() {
        return new AsyncKeyEncryptionKey() {
            @Override
            public Mono<String> getKeyId() {
                return null;
            }

            @Override
            public Mono<byte[]> wrapKey(String algorithm, byte[] key) {
                return null;
            }

            @Override
            public Mono<byte[]> unwrapKey(String algorithm, byte[] encryptedKey) {
                return null;
            }
        };
    }

    public static AsyncKeyEncryptionKeyResolver getKeyResolver() {
        return new AsyncKeyEncryptionKeyResolver() {
            @Override
            public Mono<? extends AsyncKeyEncryptionKey> buildAsyncKeyEncryptionKey(String keyId) {
                return null;
            }
        };
    }
}
