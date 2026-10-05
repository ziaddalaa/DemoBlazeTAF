package com.blazedemo.drivers;

import com.blazedemo.utils.actions.AlertActions;
import com.blazedemo.utils.actions.BrowserActions;
import com.blazedemo.utils.actions.ElementActions;
import com.blazedemo.utils.actions.FrameActions;
import com.blazedemo.utils.dataReader.PropertyReader;
import com.blazedemo.utils.logs.LogsManager;
import com.blazedemo.validations.Validation;
import com.blazedemo.validations.Verification;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ThreadGuard;


public class GUIDriver {

    private final String browser = PropertyReader.getProperty("browserType");

    private ThreadLocal<WebDriver> driverThreadLocal= new ThreadLocal<>();

    public GUIDriver(){
        LogsManager.info("Initializing GUIDriver" + browser);
        Browser browserType = Browser.valueOf(browser.toUpperCase());
        LogsManager.info("Initializing WebDriver for browser:" + browserType);
        AbstractDriver abstractDriver = browserType.getDriverFactory();
        WebDriver driver = ThreadGuard.protect(abstractDriver.createDriver());
        driverThreadLocal.set(driver);
    }

    public ElementActions elementActions() {
        return new ElementActions(get());
    }

    public BrowserActions browserActions() {
        return new BrowserActions(get());
    }

    public FrameActions frameActions() {
        return new FrameActions(get());
    }

    public AlertActions alertActions() {
        return new AlertActions(get());
    }

    //soft assertions
    public Validation validation() {
        return new Validation(get());
    }

    //hard assertions
    public Verification verification() {
        return new Verification(get());
    }

    public WebDriver get() {
        return driverThreadLocal.get();
    }

    public void quitDriver(){
        driverThreadLocal.get().quit();
    }
}
