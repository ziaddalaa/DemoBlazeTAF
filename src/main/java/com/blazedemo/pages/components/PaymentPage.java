package com.blazedemo.pages.components;

import com.blazedemo.drivers.GUIDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class PaymentPage {
    private GUIDriver driver;
    public PaymentPage(GUIDriver driver) {
        this.driver = driver;
    }

    //vars
    private String paymentEndpoint = "/payment";

    //locators
    private final By nameOnCard = By.name("name_on_card");
    private final By cardNumber = By.name("card_number");
    private final By cvvCard = By.name("cvc");
    private final By expirationMonth = By.name("expiry_month");
    private final By expirationYear = By.name("expiry_year");
    private final By payAndConfirmButton = By.id("submit");
    private final By successMessage = By.cssSelector("h2 > b");
    private final By downloadInvoiceButton = By.xpath("//a[.='Download Invoice']");


    //actions
    @Step("Fill Payment Details")
    public PaymentPage fillPaymentDetails(String name, String cardNum, String cvv, String expMonth, String expYear){
        driver.elementActions().type(nameOnCard, name)
        .type(cardNumber, cardNum)
        .type(cvvCard, cvv)
        .type(expirationMonth, expMonth)
        .type(expirationYear, expYear)
        .clickElement(payAndConfirmButton);
        return this;
    }

    //validations
    @Step("Verify Payment Success Message")
    public PaymentPage verifyPaymentSuccessMsg(String expectedMsg){
        driver.verification().Equals(driver.elementActions().getText(successMessage), expectedMsg, "Payment Success Message");
        return this;
    }
}
