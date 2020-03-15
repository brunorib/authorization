package com.bribeiro.auth.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class KeyStoreLoader {

    private KeyStore ks;

    private RootAppConfig config = RootAppConfig.getInstance();

    public KeyStore load() throws IOException, KeyStoreException,
            CertificateException, NoSuchAlgorithmException {
        if (ks != null) {
            throw new IllegalArgumentException("KeyStore already loaded");
        }

        try (FileInputStream is = new FileInputStream(config.getKeyStorePath().toFile())) {
            ks = KeyStore.getInstance(config.getKeystoreAlg());
            ks.load(is, config.getKeyStorePassword());
        }

        return ks;
    }

}
