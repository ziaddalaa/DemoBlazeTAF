package com.blazedemo.utils;

import com.blazedemo.utils.dataReader.PropertyReader;

public class OSUtils {
    public enum OS {
        WINDOWS,
        MAC,
        LINUX,
        OTHER
    }

    public static OS getCurrentOS() {
        String osName = PropertyReader.getProperty("os.name").toLowerCase();
        if (osName.contains("win")) {
            return OS.WINDOWS;
        } else if (osName.contains("mac")) {
            return OS.MAC;
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
            return OS.LINUX;
        } else {
            return OS.OTHER;
        }
    }
}
