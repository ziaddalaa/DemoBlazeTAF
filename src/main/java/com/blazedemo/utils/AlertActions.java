package com.blazedemo.utils;

import org.openqa.selenium.WebDriver;

public class AlertActions {

    private final WebDriver driver;
    private final WaitManager waitManager;

    public AlertActions(WebDriver driver){
        this.driver = driver;
        this.waitManager = new WaitManager(driver);
    }

    public void acceptAlert(){
        waitManager.fluentWait().until(
                d -> {
                    try {
                        d.switchTo().alert().accept();
                        return true;
                    }
                    catch (Exception e){
                        return false;
                    }
                }
        );
    }


    public void dismissAlert(){
        waitManager.fluentWait().until(
                d -> {
                    try {
                        d.switchTo().alert().dismiss();
                        return true;
                    }
                    catch (Exception e){
                        return false;
                    }
                }
        );
    }

    public void getAlertText(){
        waitManager.fluentWait().until(
                d -> {
                    try {
                        String alertText =d.switchTo().alert().getText();
                        return !alertText.isEmpty() ? alertText : null;
                    }
                    catch (Exception e){
                        return null;
                    }
                }
        );
    }

    public void setAlertText(String alertText){
        waitManager.fluentWait().until(
                d -> {
                    try {
                        d.switchTo().alert().sendKeys(alertText);
                        return true;
                    }
                    catch (Exception e){
                        return false;
                    }
                }
        );
    }

}
