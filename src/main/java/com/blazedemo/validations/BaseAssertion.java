package com.blazedemo.validations;

import com.blazedemo.utils.WaitManager;
import com.blazedemo.utils.actions.ElementActions;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public abstract class BaseAssertion {

    protected WebDriver driver;
    protected WaitManager waitManager;
    protected ElementActions elementActions;

    protected BaseAssertion(){

    }

    protected BaseAssertion(WebDriver driver){
        this.driver = driver;
        this.waitManager = new WaitManager(driver);
        this.elementActions = new ElementActions(driver);
    }

    protected abstract void assertTrue(boolean condition, String message);
    protected abstract void assertFalse(boolean condition, String message);
    protected abstract void assertEquals(String actual, String expected, String message);


    public BaseAssertion Equals(String actual, String expected, String message) {
        assertEquals(actual, expected, message);
        return this;
    }

    public void isElementVisible(By locator){
        boolean flag = waitManager.fluentWait().until(driver -> {
            try {
                driver.findElement(locator).isDisplayed();
                return true;
            }
            catch (Exception e){
                return false;
            }
        });
        assertTrue(flag, "Element is not visible: " + locator);
    }

    public void assertPageURL(String expectedURL){
        String actualURL = driver.getCurrentUrl();
        assertEquals(actualURL, expectedURL, "Page URL does not match. Expected: " + expectedURL + ", Actual: " + actualURL);
    }

    public void assertPageTitle(String expectedTitle){
        String actualTitle = driver.getTitle();
        assertEquals(actualTitle, expectedTitle, "Page title does not match. Expected: " + expectedTitle + ", Actual: " + actualTitle);
    }

    // Verify that file exists
    public void assertFileExists(String fileName, String message) {
//        boolean fileExists = com.blazedemo.FileUtils.isFileExist(fileName,3);
        waitManager.fluentWait().until(driver -> {
            return com.blazedemo.FileUtils.isFileExists(fileName);
        });
        assertTrue(com.blazedemo.FileUtils.isFileExists(fileName), message);
    }
}
