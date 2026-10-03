package com.blazedemo.media;

import com.blazedemo.utils.TimeManager;
import com.blazedemo.utils.logs.LogsManager;
import com.blazedemo.utils.report.AllureAttachmentManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;

public class ScreenshotsManager {

    public static final String SCREENSHOTS_PATH = "test-output/screenshots/";

    public static void takeFullPageScreenshot(WebDriver driver, String screenShotName){
        try{
            File screenshotFileSrc = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            File screenshotFileDest = new File(SCREENSHOTS_PATH + screenShotName + TimeManager.getTimeStamp() + ".png");

            FileUtils.copyFile(screenshotFileSrc, screenshotFileDest);


            AllureAttachmentManager.attachScreenshot(screenShotName, screenshotFileDest.getAbsolutePath());

            LogsManager.info("Screenshot taken: " + screenshotFileDest.getAbsolutePath());
        }
        catch (Exception e){
            LogsManager.error("Failed to take screenshot:", e.getMessage());
    }
    }

    public static void takeElementScreenshot(WebDriver driver, By element) {
        try {
            String ariaName = driver.findElement(element).getAccessibleName();
            File screenshotFileSrc = driver.findElement(element).getScreenshotAs(OutputType.FILE);
            File screenshotFileDest = new File(SCREENSHOTS_PATH + ariaName + TimeManager.getTimeStamp() + ".png");
            FileUtils.copyFile(screenshotFileSrc, screenshotFileDest);

            AllureAttachmentManager.attachScreenshot(ariaName, screenshotFileDest.getAbsolutePath());

            LogsManager.info("Screenshot taken: " + screenshotFileDest.getAbsolutePath());
        } catch (Exception e) {
            LogsManager.error("Failed to take element screenshot:", e.getMessage());
        }
    }
}
