package com.blazedemo.tests;

import com.blazedemo.drivers.GUIDriver;
import com.blazedemo.drivers.WebDriverProvider;
import com.blazedemo.utils.dataReader.JsonReader;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;

public class BaseTest implements WebDriverProvider{

    protected GUIDriver driver;
    protected JsonReader testData;

    @BeforeClass
    public void beforeClass(){
        testData = new JsonReader("register-data");

    }
    @Override
    public WebDriver getWebDriver() {
        return driver.get();
    }

}
