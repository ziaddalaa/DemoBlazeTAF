package com.blazedemo.utils.actions;

import com.blazedemo.utils.WaitManager;
import com.blazedemo.utils.logs.LogsManager;
import org.openqa.selenium.*;
import org.openqa.selenium.bidi.log.Log;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.io.File;

public class ElementActions {

    private final WebDriver driver;
    private WaitManager waitManager;

    public ElementActions(WebDriver driver){
        this.driver = driver;
        this.waitManager = new WaitManager(driver);
    }

    //Clicking
    public ElementActions clickElement(By locator){
        waitManager.fluentWait().until(
                d -> {
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        //Wait until the element is stable
                        Point initialLocation = element.getLocation();
                        LogsManager.info("Waiting for element with locator:" + locator + " to be stable before clicking");
                        Point finalLocation = element.getLocation();
                        LogsManager.info("Element with locator:" + locator + " is stable. Proceeding to click");
                        if (!initialLocation.equals(finalLocation)){
                            return false;
                        }
                        element.click();
                        LogsManager.info("Clicked on element with locator" + locator);
                        return true;
                    }
                    catch (Exception e){
                        return false;
                    }
                }
        );
        return this;
    }

    //Typing
    public ElementActions type(By locator, String text){
        waitManager.fluentWait().until(
                d -> {
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        element.clear();
                        element.sendKeys(text);
                        LogsManager.info("Typed text:" + text + " into element with locator:" + locator);
                        return true;
                    }
                    catch (Exception e){
                        return false;
                    }
                }
        );
        return this;
    }

    //Hovering
    public ElementActions hoverOnElement(By locator){
        waitManager.fluentWait().until( d->
        {
            try {
                WebElement element = d.findElement(locator);
                scrollToElementJS(locator);
                new Actions(d).moveToElement(element).perform();
                LogsManager.info("Hovered on element with locator:" + locator);
                return true;
            }
            catch (Exception e){
                return false;
            }
        });
        return this;
    }

    //Getting text
    public String getText(By locator) {

        return (String) waitManager.fluentWait().until(
                d -> {
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        String message = element.getText();
                        LogsManager.info("Text retrieved from element with locator:" + locator + " is: " + message);
                        return !message.isEmpty() ? message : null;
                    } catch (Exception e) {
                        return false;
                    }
                }
        );
    }

    //Upload File
    public ElementActions uploadFile(By locator, String filePath){
        String fileAbsolutePath = System.getProperty("user.dir") + File.separator + filePath;
        waitManager.fluentWait().until(
                d -> {
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        element.sendKeys(fileAbsolutePath);
                        LogsManager.info("Uploaded file from path:" + fileAbsolutePath + " into element with locator:" + locator);
                        return true;
                    }
                    catch (Exception e){
                        return false;
                    }
                }
        );
        return this;
    }

    //Find an element
    public WebElement findElement(By locator){
        return driver.findElement(locator);
    }


    //Function to scroll to an element using JS
    public void scrollToElementJS(By locator){
        ((JavascriptExecutor)driver)
                .executeScript("arguments[0].scrollIntoView({behaviour:'auto',block:'center',inline:'center'});", findElement(locator));
    }

    //Select from dropdown
    public ElementActions selectFromDropdown(By locator, String value){
        waitManager.fluentWait().until(
                d -> {
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        Select select = new Select(element);
                        select.selectByVisibleText(value);
                        LogsManager.info("Selected value:" + value + " from dropdown with locator:" + locator);
                        return true;
                    }
                    catch (Exception e){
                        return false;
                }
                }
        );
        return this;
    }
}
