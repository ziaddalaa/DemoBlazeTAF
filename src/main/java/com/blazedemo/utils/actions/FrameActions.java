package com.blazedemo.utils.actions;

import com.blazedemo.utils.WaitManager;
import com.blazedemo.utils.logs.LogsManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.bidi.log.Log;

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
                        LogsManager.info("Switching to frame with index: " + index);
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
                        LogsManager.info("Switching to frame with name or id: " + nameOrId);
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
                        LogsManager.info("Switching to frame with locator: " + frameLocator.toString());
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
                        LogsManager.info("Switching to default content");
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
        );
    }
}
