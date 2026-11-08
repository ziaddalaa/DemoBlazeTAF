package com.blazedemo.tests.ui;

import com.blazedemo.apis.UserManagementAPI;
import com.blazedemo.drivers.GUIDriver;
import com.blazedemo.pages.components.NavigationBarComponent;
import com.blazedemo.pages.components.SignupLoginPage;
import com.blazedemo.pages.components.SignupPage;
import com.blazedemo.tests.BaseTest;
import com.blazedemo.utils.TimeManager;
import com.blazedemo.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Automation Exercises - Register Tests")
@Feature("UI User Management - Register Tests")
@Story("User Register")
@Severity(SeverityLevel.CRITICAL)
@Owner("Ziad Aladin")
public class RegisterTest extends BaseTest {

    String timestamp = TimeManager.getSimpleTimeStamp();

    //Tests
    @Description("Verify that user is able to register successfully")
    @Test
    public void validSignUpTC(){
        new SignupLoginPage(driver).navigate()
                .enterRegisterName(testData.getJsonData("name"))
                .enterRegisterEmail(testData.getJsonData("email") + timestamp + "@test.com")
                .clickRegisterButton();
                new SignupPage(driver)
                .fillRegistrationForm(
                        testData.getJsonData("titleMale"),
                        testData.getJsonData("password"),
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
                        testData.getJsonData("mobileNumber"))
                .clickCreateAccountButton()
                .isAccountCreatedMessageDisplayed();

        new UserManagementAPI().deleteAccount(
                        testData.getJsonData("email") + timestamp + "@test.com",
                        testData.getJsonData("password"))
                .verifyUserDeletedSuccessfully();
    }

    @Description("Verify error message for existing email")
    @Test
    public void verifyErrorMessageForExistingEmailTC(){
        //precondition: create an account
        new UserManagementAPI().createAccount(
                testData.getJsonData("name"),
                testData.getJsonData("email") + timestamp + "@test.com",
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

        new SignupLoginPage(driver).navigate()
                .enterRegisterName(testData.getJsonData("name"))
                .enterRegisterEmail(testData.getJsonData("email") + timestamp + "@test.com")
                .clickRegisterButton()
                .verifySignupErrorMessage(testData.getJsonData("messages.errorMessage"));

        new UserManagementAPI().deleteAccount(
                        testData.getJsonData("email") + timestamp + "@test.com",
                        testData.getJsonData("password"))
                .verifyUserDeletedSuccessfully();

    }



    //Configurations
    @BeforeClass
    public void preCondition(){
        testData = new JsonReader("register-data");

    }

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
