package com.blazedemo.pages.components;

import com.blazedemo.drivers.GUIDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class SignupPage {
    private GUIDriver driver;
    private final String signUpLoginEndPoint = "/signup";
    public SignupPage(GUIDriver driver) {
        this.driver = driver;
    }

    //locators
    private final By name = By.cssSelector("[data-qa=\"name\"]");
    private final By email = By.cssSelector("[data-qa=\"email\"]");
    private final By password = By.cssSelector("[data-qa=\"password\"]");
    private final By day = By.id("days");
    private final By month = By.id("months");
    private final By year = By.id("years");
    private final By newsletterCheckbox = By.id("newsletter");
    private final By offersCheckbox = By.id("optin");
    private final By firstName = By.cssSelector("[data-qa=\"first_name\"]");
    private final By lastName = By.cssSelector("[data-qa=\"last_name\"]");
    private final By company = By.cssSelector("[data-qa=\"company\"]");
    private final By address1 = By.cssSelector("[data-qa=\"address\"]");
    private final By address2 = By.cssSelector("[data-qa=\"address2\"]");
    private final By country = By.cssSelector("[data-qa=\"country\"]");
    private final By state = By.cssSelector("[data-qa=\"state\"]");
    private final By city = By.cssSelector("[data-qa=\"city\"]");
    private final By zipcode = By.cssSelector("[data-qa=\"zipcode\"]");
    private final By mobileNumber = By.cssSelector("[data-qa=\"mobile_number\"]");
    private final By createAccountButton = By.cssSelector("[data-qa=\"create-account\"]");
    private final By accountCreatedMessage = By.tagName("p");
    private final By continueButton = By.cssSelector("[data-qa=\"continue-button\"]");

    //actions
    @Step("Choosing title: {title}") //Mr - Mrs
    private SignupPage chooseTitle(String title){
        By titleLocator = By.xpath("//input [@value='" + title + "']");
        driver.elementActions().clickElement(titleLocator);
        return this;
    }

    @Step("Fill registration form")
    public SignupPage fillRegistrationForm(String title, String password, String day, String month, String year,
                                           String firstName, String lastName, String company,
                                           String address1, String address2, String country, String state, String city,
                                           String zipcode, String mobileNumber) {
        chooseTitle(title);
        driver.elementActions().type(this.password, password);
        driver.elementActions().selectFromDropdown(this.day, day);
        driver.elementActions().selectFromDropdown(this.month, month);
        driver.elementActions().selectFromDropdown(this.year, year);
        driver.elementActions().clickElement(newsletterCheckbox);
        driver.elementActions().clickElement(offersCheckbox);
        driver.elementActions().type(this.firstName, firstName);
        driver.elementActions().type(this.lastName, lastName);
        driver.elementActions().type(this.company, company);
        driver.elementActions().type(this.address1, address1);
        driver.elementActions().type(this.address2, address2);
        driver.elementActions().selectFromDropdown(this.country, country);
        driver.elementActions().type(this.state, state);
        driver.elementActions().type(this.city, city);
        driver.elementActions().type(this.zipcode, zipcode);
        driver.elementActions().type(this.mobileNumber, mobileNumber);

        return this;
    }

    @Step("Clicking on create account button")
    public SignupPage clickCreateAccountButton() {
        driver.elementActions().clickElement(createAccountButton);
        return this;
    }

    @Step("Clicking on continue button")
    public NavigationBarComponent clickContinueButton() {
        driver.elementActions().clickElement(continueButton);
        return new NavigationBarComponent(driver);
    }


    //validations
    @Step("Vefying account created message is displayed")
    public SignupPage isAccountCreatedMessageDisplayed() {
        driver.verification().isElementVisible(accountCreatedMessage);
        return this;
    }
}
