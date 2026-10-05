package com.blazedemo.pages.components;

import com.blazedemo.drivers.GUIDriver;
import com.blazedemo.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class SignupLoginPage {
    private GUIDriver driver;
    private final String signUpLoginEndPoint = "/login";
    public SignupLoginPage(GUIDriver driver) {
        this.driver = driver;
    }

    //locators
    private final By loginEmailInput = By.cssSelector("[data-qa='login-email']");
    private final By loginPasswordInput = By.cssSelector("[data-qa='login-password']");
    private final By loginButton = By.cssSelector("[data-qa='login-button']");
    private final By registerNameInput = By.cssSelector("[data-qa='register-name']");
    private final By registerEmailInput = By.cssSelector("[data-qa='register-email']");
    private final By registerButton = By.cssSelector("[data-qa='register-button']");
    private final By signUpLabel = By.cssSelector(".signup-form h2");
    private final By loginError = By.cssSelector(".login-form p");
    private final By signUpError = By.cssSelector(".signup-form p");

    //actions
    @Step("Navigating to Signup/Login page")
    public SignupLoginPage navigate() {
        driver.browserActions().navigateTo(PropertyReader.getProperty("baseURLWeb") + signUpLoginEndPoint);
        return this;
    }
    @Step("Entering login email: {email}")
    public SignupLoginPage enterLoginEmail(String email) {
        driver.elementActions().type(loginEmailInput,email);
        return this;
    }
    @Step("Entering login password: {password}")
    public SignupLoginPage enterLoginPassword(String password) {
        driver.elementActions().type(loginPasswordInput,password);
        return this;
    }
    @Step("Clicking on login button")
    public SignupLoginPage clickLoginButton() {
        driver.elementActions().clickElement(loginButton);
        return this;
    }
    @Step("Entering register name: {name}")
    public SignupLoginPage enterRegisterName(String name) {
        driver.elementActions().type(registerNameInput,name);
        return this;
    }
    @Step("Entering register email: {email}")
    public SignupLoginPage enterRegisterEmail(String email) {
        driver.elementActions().type(registerEmailInput,email);
        return this;
    }
    @Step("Clicking on register button")
    public SignupPage clickRegisterButton() {
        driver.elementActions().clickElement(registerButton);
        return new SignupPage(driver);
    }
    //validations
    @Step("Verify new user signup form is visible")
    public SignupLoginPage isSignupPageDisplayed() {
        driver.verification().isElementVisible(signUpLabel);
        return this;
    }

    @Step("Verify login error message is displayed")
    public SignupLoginPage verifyLoginErrorMessage(String expectedErrorMessage) {
        String actualErrorMessage = driver.elementActions().getText(loginError);
        driver.verification().Equals(actualErrorMessage,expectedErrorMessage,"Login error message validation");
        return this;
    }

    @Step("Verify signup error message is displayed")
    public SignupLoginPage verifySignupErrorMessage(String expectedErrorMessage) {
        String actualErrorMessage = driver.elementActions().getText(signUpError);
        driver.verification().Equals(actualErrorMessage, expectedErrorMessage, "Signup error message validation");
        return this;
    }
}
