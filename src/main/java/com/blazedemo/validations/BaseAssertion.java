package com.blazedemo.validations;

import com.blazedemo.utils.WaitManager;
import com.blazedemo.utils.actions.ElementActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public abstract class BaseAssertion {

    protected final WebDriver driver;
    protected final WaitManager waitManager;
    protected ElementActions elementActions;

    protected BaseAssertion(WebDriver driver){
        this.driver = driver;
        this.waitManager = new WaitManager(driver);
        this.elementActions = new ElementActions(driver);
    }

    protected abstract void assertTrue(boolean condition, String message);
    protected abstract void assertFalse(boolean condition, String message);
    protected abstract void assertEquals(String actual, String expected, String message);


    public void Equals(String actual, String expected, String message) {
        assertEquals(actual, expected, message);
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
}
