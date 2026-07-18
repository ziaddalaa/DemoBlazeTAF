package com.blazedemo.utils;

import org.openqa.selenium.*;

import java.io.File;

public class ElementActions {

    private final WebDriver driver;
    private WaitManager waitManager;

    public ElementActions(WebDriver driver){
        this.driver = driver;
        this.waitManager = new WaitManager(driver);
    }

    //Clicking
    public void clickElement(By locator){
        waitManager.fluentWait().until(
                d -> {
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        element.click();
                        return true;
                    }
                    catch (Exception e){
                        return false;
                    }
                }
        );
    }

    //Typing
    public void type(By locator, String text){
        waitManager.fluentWait().until(
                d -> {
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        element.clear();
                        element.sendKeys(text);
                        return true;
                    }
                    catch (Exception e){
                        return false;
                    }
                }
        );
    }

    //Getting text
    public String getText(By locator) {

        return (String) waitManager.fluentWait().until(
                d -> {
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        String message = element.getText();
                        return !message.isEmpty() ? message : null;
                    } catch (Exception e) {
                        return false;
                    }
                }
        );
    }

    //Upload File
    public void uploadFile(By locator, String filePath){
        String fileAbsolutePath = System.getProperty("user.dir") + File.separator + filePath;
        waitManager.fluentWait().until(
                d -> {
                    try {
                        WebElement element = d.findElement(locator);
                        scrollToElementJS(locator);
                        element.sendKeys(fileAbsolutePath);
                        return true;
                    }
                    catch (Exception e){
                        return false;
                    }
                }
        );
    }

    //Find an element
    public WebElement findElement(By locator){
        return driver.findElement(locator);
    }


    //Function to scroll to an element using JS
    public void scrollToElementJS(By locator){
        ((JavascriptExecutor)driver)
                .executeScript("arguments[0].scrollIntoView({behaviour:auto,block:'center',inline:'center'});", findElement(locator));
    }
}
