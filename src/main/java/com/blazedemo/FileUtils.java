package com.blazedemo;

import com.blazedemo.utils.dataReader.PropertyReader;
import com.blazedemo.utils.logs.LogsManager;

import java.io.File;

public class FileUtils {

    private static final String USER_DIR = PropertyReader.getProperty("user.dir") + File.separator;

    private FileUtils(){
        // Prevent instantiation
    }

    //Renaming
    public static void renameFile(String oldName, String newName){
        try{
            File oldFile = new File(USER_DIR + oldName);
            File newFile = new File(USER_DIR + newName);
            if (oldFile.exists()){
                if (oldFile.renameTo(newFile)){
                    LogsManager.info("File renamed from " + oldName + " to " + newName);
                } else {
                    LogsManager.error("Failed to rename file from " + oldName + " to " + newName);
                }
            } else {
                LogsManager.error("File not found: " + oldName);
            }
        }
        catch (Exception e){
            LogsManager.error("Error renaming file from " + oldName + " to " + newName, e.getMessage());
        }
    }

    //Creating Dir
    public static void createDir(String path){
        try {
            File file = new File(USER_DIR + path);
            if (!file.exists()){
                file.mkdirs();
                LogsManager.info("Dir created: " + path);
            }
        }
        catch (Exception e){
            LogsManager.error("Error creating directory: " + path, e.getMessage());
        }

    }

    //Cleaning Dir
    public static void cleanDir(File file){
        try {
            org.apache.commons.io.FileUtils.deleteQuietly(file);
        }
        catch (Exception e){
            LogsManager.error("Error cleaning directory: " + file.getAbsolutePath(), e.getMessage());
        }

    }
}
