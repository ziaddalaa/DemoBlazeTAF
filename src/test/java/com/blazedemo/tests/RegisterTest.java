package com.blazedemo.tests;

import com.blazedemo.drivers.GUIDriver;
import com.blazedemo.pages.components.NavigationBarComponent;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RegisterTest extends BaseTest{

    //Tests
    @Test
    public void signUpTC(){

    }



    //Configurations
    @BeforeMethod
    public void setUp(){
        driver = new GUIDriver();
        new NavigationBarComponent(driver).navigate();
    }

    @AfterMethod
    public void tearDown(){
        driver.quitDriver();

    }

}
