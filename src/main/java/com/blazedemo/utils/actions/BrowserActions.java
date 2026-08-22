package com.blazedemo.utils.actions;

import com.blazedemo.utils.logs.LogsManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;

public class BrowserActions {

    private final WebDriver driver;

    public BrowserActions(WebDriver driver){
        this.driver = driver;
    }

    public void maximizeWindow(){
        driver.manage().window().maximize();
    }

    public String getCurrentURL(){
        LogsManager.info("Current URL: " + driver.getCurrentUrl());
        return driver.getCurrentUrl();
    }

    public void navigateTo(String url){
        driver.get(url);
        LogsManager.info("Navigated to URL: " + url);
    }

    public void refreshPage(){
        driver.navigate().refresh();
    }

    public void closeCurrentWindow(){
        driver.close();
    }

    public void openNewWindow(){
        driver.switchTo().newWindow(WindowType.WINDOW);
    }
}
