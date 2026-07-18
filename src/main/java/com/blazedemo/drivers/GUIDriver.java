package com.blazedemo.drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ThreadGuard;
import utils.PropertyReader;


public class GUIDriver {

    private final String browser = PropertyReader.getProperty("browserType");

    private ThreadLocal<WebDriver> driverThreadLocal= new ThreadLocal<>();

    public GUIDriver(){
        Browser browserType = Browser.valueOf(browser.toUpperCase());
        AbstractDriver abstractDriver = browserType.getDriverFactory();
        WebDriver driver = ThreadGuard.protect(abstractDriver.createDriver());
        driverThreadLocal.set(driver);
    }

    public WebDriver get() {
        return driverThreadLocal.get();
    }

    public void quitDriver(){
        driverThreadLocal.get().quit();
    }
}
