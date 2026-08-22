package com.blazedemo.utils.dataReader;

import com.blazedemo.utils.logs.LogsManager;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Collection;
import java.util.Properties;

public class PropertyReader {


    // 1. Create a static variable to hold your loaded properties
    private static final Properties properties = loadProperties();

    public static Properties loadProperties() {
        try {
            Properties props = new Properties(); // renamed to avoid confusion
            Collection<File> propertiesFiles;
            propertiesFiles = FileUtils.listFiles(new File("src/main/resources"), new String[]{"properties"}, true);

            propertiesFiles.forEach(file -> {
                try {
                    props.load(FileUtils.openInputStream(file));
                } catch (Exception e) {
                    LogsManager.error("Error loading properties file:", file.getName(), e.getMessage());
                }
            });

            return props;
        } catch (Exception e) {
            LogsManager.error("Error loading properties file:", e.getMessage());
            return null;
        }
    }

    // 2. Update this method to read from your static 'properties' object
    public static String getProperty(String key) {
        try {
            if (properties != null) {
                return properties.getProperty(key);
            }
            return null;
        } catch (Exception e) {
            LogsManager.error("Error retrieving property for key:", key, e.getMessage());
            return null;
        }
    }
}
