package com.blazedemo.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FrameActions {

    private final WebDriver driver;
    private final WaitManager waitManager;

    public FrameActions(WebDriver driver){
        this.driver = driver;
        this.waitManager = new WaitManager(driver);
    }

    public void switchToFrameByIndex(int index){
        waitManager.fluentWait().until(d -> {
                    try {
                        d.switchTo().frame(index);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
        );
    }

    public void switchToFrameByNameOrId(String nameOrId){
        waitManager.fluentWait().until(d -> {
                    try {
                        d.switchTo().frame(nameOrId);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
        );
    }

    public void switchToFrameByNameOrId(By frameLocator){
        waitManager.fluentWait().until(d -> {
                    try {
                        d.switchTo().frame(d.findElement(frameLocator));
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
        );
    }

    public void switchToDefaultContent(){
        waitManager.fluentWait().until(d -> {
                    try {
                        d.switchTo().defaultContent();
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
        );
    }
}
