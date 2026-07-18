package com.blazedemo.utils;

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
        return driver.getCurrentUrl();
    }

    public void navigateTo(String url){
        driver.get(url);
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
