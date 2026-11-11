package com.blazedemo.tests.ui;

import com.blazedemo.apis.UserManagementAPI;
import com.blazedemo.drivers.GUIDriver;
import com.blazedemo.drivers.UITest;
import com.blazedemo.pages.components.NavigationBarComponent;
import com.blazedemo.pages.components.SignupLoginPage;
import com.blazedemo.tests.BaseTest;
import com.blazedemo.utils.TimeManager;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Automation Exercises - Login Tests")
@Feature("UI Login Management - Login Tests")
@Story("Login Management")
@Severity(SeverityLevel.CRITICAL)
@Owner("Ziad Aladin")
@UITest
public class LoginTest extends BaseTest {

    String timestamp = TimeManager.getSimpleTimeStamp();
    @Description("Verify user can login with valid credentials")
    @Test
    public void validLoginTC()
    {
        new UserManagementAPI().createAccount(
                        testData.getJsonData("name"),
                        testData.getJsonData("email") + timestamp + "@gmail.com",
                        testData.getJsonData("password"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"))
                .verifyUserCreatedSuccessfully();

        new SignupLoginPage(driver).navigate()
                .enterLoginEmail(testData.getJsonData("email") + timestamp + "@gmail.com")
                .enterLoginPassword(testData.getJsonData("password"))
                .clickLoginButton()
                .navigationBarComponent
                .verifyLoggedInAsLabelIsDisplayed(testData.getJsonData("name"));

        new UserManagementAPI().deleteAccount(
                        testData.getJsonData("email") + timestamp + "@gmail.com",
                        testData.getJsonData("password"))
                .verifyUserDeletedSuccessfully();
    }

    @Description("Verify user cannot login with invalid email")
    @Test
    public void inValidLoginUsingInvalidEmailTC()
    {
        new UserManagementAPI().createAccount(
                        testData.getJsonData("name"),
                        testData.getJsonData("email") + timestamp + "@gmail.com",
                        testData.getJsonData("password"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"))
                .verifyUserCreatedSuccessfully();

        new SignupLoginPage(driver).navigate()
                .enterLoginEmail(testData.getJsonData("email")  + "@gmail.com")
                .enterLoginPassword(testData.getJsonData("password"))
                .clickLoginButton()
                .verifyLoginErrorMessage(testData.getJsonData("messages.error"));

        new UserManagementAPI().deleteAccount(
                        testData.getJsonData("email") + timestamp + "@gmail.com",
                        testData.getJsonData("password"))
                .verifyUserDeletedSuccessfully();
    }

    @Description("Verify user cannot login with invalid password")
    @Test
    public void inValidLoginUsingInvalidPasswordTC()
    {
        new UserManagementAPI().createAccount(
                        testData.getJsonData("name"),
                        testData.getJsonData("email") + timestamp + "@gmail.com",
                        testData.getJsonData("password"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"))
                .verifyUserCreatedSuccessfully();

        new SignupLoginPage(driver).navigate()
                .enterLoginEmail(testData.getJsonData("email") + timestamp + "@gmail.com")
                .enterLoginPassword(testData.getJsonData("password")+timestamp)
                .clickLoginButton()
                .verifyLoginErrorMessage(testData.getJsonData("messages.error"));

        new UserManagementAPI().deleteAccount(
                        testData.getJsonData("email") + timestamp + "@gmail.com",
                        testData.getJsonData("password"))
                .verifyUserDeletedSuccessfully();
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
