package com.blazedemo.tests;

import com.blazedemo.drivers.GUIDriver;
import com.blazedemo.drivers.WebDriverProvider;
import com.blazedemo.utils.dataReader.JsonReader;
import org.openqa.selenium.WebDriver;

public class BaseTest implements WebDriverProvider{

    protected GUIDriver driver;
    protected JsonReader testData;

    public void beforeClass(){
        testData = new JsonReader("");

    }
    @Override
    public WebDriver getWebDriver() {
        return driver.get();
    }

}
