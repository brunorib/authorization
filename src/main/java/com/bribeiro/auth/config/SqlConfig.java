package com.bribeiro.auth.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class SqlConfig {

    private static final String JDBC_URL = "javax.persistence.jdbc.url";

    private static final String DB_USER = "javax.persistence.jdbc.user";

    private static final String DB_PASS = "javax.persistence.jdbc.password";

    private Map<String, Object> configOverrides;

    private static SqlConfig instance = new SqlConfig();

    public static SqlConfig getInstance() {
        return instance;
    }

    private SqlConfig() {
        Map<String, String> vars = System.getenv();

        configOverrides = new HashMap<>();
        for (String envName : vars.keySet()) {
            if (envName.contains("JDBC_URL")) {
                configOverrides.put(JDBC_URL, vars.get(envName));
            }
            if (envName.contains("DB_PASS")) {
                configOverrides.put(DB_PASS, vars.get(envName));
            }
            if (envName.contains("DB_USER")) {
                configOverrides.put(DB_USER, vars.get(envName));
            }
        }

        if (configOverrides.size() != 3) {
            configOverrides = null;
        }

    }

    public Map<String, Object> getConfigMap() {
        return configOverrides;
    }

}
