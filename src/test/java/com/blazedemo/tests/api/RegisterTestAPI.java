package com.blazedemo.tests.api;

import com.blazedemo.apis.UserManagementAPI;
import com.blazedemo.drivers.GUIDriver;
import com.blazedemo.pages.components.NavigationBarComponent;
import com.blazedemo.tests.BaseTest;
import com.blazedemo.utils.TimeManager;
import com.blazedemo.utils.dataReader.JsonReader;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RegisterTestAPI extends BaseTest {

    String timestamp = TimeManager.getSimpleTimeStamp();


    @Test
    public void registerNewAccount(){
        new UserManagementAPI().createAccount(
                        testData.getJsonData("name"),
                        testData.getJsonData("email") + timestamp + "@gmail.com",
                        testData.getJsonData("password"),
                        testData.getJsonData("titleMale"),
                        testData.getJsonData("day"),
                        testData.getJsonData("month"),
                        testData.getJsonData("year"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"),
                        testData.getJsonData("companyName"),
                        testData.getJsonData("address1"),
                        testData.getJsonData("address2"),
                        testData.getJsonData("country"),
                        testData.getJsonData("state"),
                        testData.getJsonData("city"),
                        testData.getJsonData("zipCode"),
                        testData.getJsonData("mobileNumber")
                )
                .verifyUserCreatedSuccessfully();
    }

    //Configurations
    @BeforeClass
    public void setUp(){
        testData = new JsonReader("register-data");
    }
}
