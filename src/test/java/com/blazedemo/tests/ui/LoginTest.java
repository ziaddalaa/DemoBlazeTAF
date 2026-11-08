package com.blazedemo.tests.ui;

import com.blazedemo.apis.UserManagementAPI;
import com.blazedemo.drivers.GUIDriver;
import com.blazedemo.pages.components.NavigationBarComponent;
import com.blazedemo.pages.components.SignupLoginPage;
import com.blazedemo.tests.BaseTest;
import com.blazedemo.utils.TimeManager;
import com.blazedemo.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Automation Exercises - Login Tests")
@Feature("UI User Management - Login Tests")
@Story("User Login")
@Severity(SeverityLevel.CRITICAL)
@Owner("Ziad Aladin")
public class LoginTest extends BaseTest {

    String timestamp = TimeManager.getSimpleTimeStamp();

    @Description("Verify that user is able to login successfully")
    @Test
    public void validLoginTC(){
        new UserManagementAPI().createAccount(
                testData.getJsonData("name"),
                testData.getJsonData("email") + timestamp + "@test.com",
                testData.getJsonData("password"),
                testData.getJsonData("firstName"),
                testData.getJsonData("lastName"))
                .verifyUserCreatedSuccessfully();

        new SignupLoginPage(driver).navigate()
                .enterLoginEmail(testData.getJsonData("email") + timestamp + "@test.com")
                .enterLoginPassword(testData.getJsonData("password"))
                .clickLoginButton()
                .navigationBarComponent
                .verifyLoggedInAsLabelIsDisplayed(testData.getJsonData("firstName") + " " + testData.getJsonData("lastName"));

        new UserManagementAPI().deleteAccount(
                testData.getJsonData("email") + timestamp + "@test.com",
                testData.getJsonData("password"))
                .verifyUserDeletedSuccessfully();
    }

    @Description("Verify error message for invalid email")
    @Test
    public void invalidLoginUsingInvalidEmailTC(){
        new UserManagementAPI().createAccount(
                        testData.getJsonData("name"),
                        testData.getJsonData("email") + timestamp + "@test.com",
                        testData.getJsonData("password"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"))
                .verifyUserCreatedSuccessfully();

        new SignupLoginPage(driver).navigate()
                .enterLoginEmail(testData.getJsonData("email") + "@test.com")
                .enterLoginPassword(testData.getJsonData("password"))
                .clickLoginButton()
                .verifyLoginErrorMessage(testData.getJsonData("messages.errorMessage"));

        new UserManagementAPI().deleteAccount(
                        testData.getJsonData("email") + timestamp + "@test.com",
                        testData.getJsonData("password"))
                .verifyUserDeletedSuccessfully();
    }

    @Description("Verify error message for invalid password")
    @Test
    public void invalidLoginUsingInvalidPasswordTC(){
        new UserManagementAPI().createAccount(
                        testData.getJsonData("name"),
                        testData.getJsonData("email") + timestamp + "@test.com",
                        testData.getJsonData("password"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"))
                .verifyUserCreatedSuccessfully();

        new SignupLoginPage(driver).navigate()
                .enterLoginEmail(testData.getJsonData("email") + timestamp + "@test.com")
                .enterLoginPassword(testData.getJsonData("password") + timestamp)
                .clickLoginButton()
                .verifyLoginErrorMessage(testData.getJsonData("messages.errorMessage"));

        new UserManagementAPI().deleteAccount(
                        testData.getJsonData("email") + timestamp + "@test.com",
                        testData.getJsonData("password"))
                .verifyUserDeletedSuccessfully();
    }

    //Configurations
    @BeforeClass
    public void preCondition(){
        testData = new JsonReader("login-data");

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
