package com.bribeiro.auth.config;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class RootAppConfig {

    private static final String KS_PATH = "keystore.path";
    private static final String KS_PASS = "keystore.password";
    private static final String KS_ALG = "keystore.algorithm";
    private static final String SH_ALG = "hash.algorithm";
    private static final String SESSION_TIME = "session.time";

    private static final RootAppConfig instance = new RootAppConfig();

    private Path keyStorePath;

    private char[] keyStorePassword;

    private String keystoreAlg;

    private String hashAlgorithm;

    private long sessionTime;

    public static RootAppConfig getInstance() {
        return instance;
    }

    private RootAppConfig() {
        Properties props = System.getProperties();
        keyStorePath = props.containsKey(KS_PATH) ?
            Paths.get(props.getProperty(KS_PATH)) : Paths.get(this.getClass().getClassLoader().getResource("auth-sign.p12").getPath());
        keyStorePassword = props.containsKey(KS_PASS) ?
            props.getProperty(KS_PASS).toCharArray() : new char[]{'1', '2', '3', '4'};
        keystoreAlg = props.containsKey(KS_ALG) ?
            props.getProperty(KS_ALG) : "PKCS12";
        hashAlgorithm = props.containsKey(SH_ALG) ?
            props.getProperty(SH_ALG) : "SHA-256";
        sessionTime = props.containsKey(SESSION_TIME) ?
            Long.parseLong(props.getProperty(SESSION_TIME)) : 900000L;
    }

    public Path getKeyStorePath() {
        return keyStorePath;
    }

    public char[] getKeyStorePassword() {
        return keyStorePassword;
    }

    public String getKeystoreAlg() {
        return keystoreAlg;
    }

    public String getHashAlgorithm() {
        return hashAlgorithm;
    }

    public long getSessionTime() {
        return sessionTime;
    }
}
