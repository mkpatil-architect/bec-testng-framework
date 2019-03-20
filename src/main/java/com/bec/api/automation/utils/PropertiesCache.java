package com.bec.api.automation.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

public class PropertiesCache {
    private static Log LOGGER = LogFactory.getLog(PropertiesCache.class);

    private final Properties properties = new Properties();

    private PropertiesCache(Object o) {
        String[] baseEnvDetails = FileUtil.baseEnvDetail();
        String[] envDetails = FileUtil.envDetailList();
        File baseFile = null;
        File envFile = null;

        for (int i = 0; i < baseEnvDetails.length; i++) {
            baseFile = new File(baseEnvDetails[i] + ".configuration.properties");
            if (baseFile.exists()) {
                try {
                    this.properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(baseEnvDetails[i] + ".configuration.properties"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            for (int i = 0; i < envDetails.length; i++) {
                envFile = new File(envDetails[i] + ".configuration.properties");
                if (envFile.exists()) {
                    this.properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(envDetails[i] + ".configuration.properties"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PropertiesCache getInstance() {
        return PropertyHolder.INSTANCE;
    }

    public String getProperty(String key) {
        return this.properties.getProperty(key);
    }

    public Set<String> getAllPropertyNames() {

        return this.properties.stringPropertyNames();

    }

    public boolean containsKey(String key) {

        return this.properties.containsKey(key);

    }

    public void setProperty(String key, String value) {

        this.properties.setProperty(key, value);

    }

    private static class PropertyHolder {
        private static final PropertiesCache INSTANCE = new PropertiesCache(null);
    }
}


