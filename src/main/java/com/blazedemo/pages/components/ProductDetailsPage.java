package com.blazedemo.pages.components;

import com.blazedemo.drivers.GUIDriver;
import com.blazedemo.utils.dataReader.PropertyReader;
import com.blazedemo.utils.logs.LogsManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class ProductDetailsPage {
    private final GUIDriver driver;

    public ProductDetailsPage(GUIDriver driver) {
        this.driver = driver;
    }

    //vars
    private String productDetailsPageEndpoint = "/product_details/2";
    //locators
    private final By productName = By.cssSelector(".product-information > h2");
    private final By productPrice = By.cssSelector(".product-information > span > span");
    private final By reviewName = By.id("name");
    private final By reviewEmail = By.id("email");
    private final By reviewTextArea = By.cssSelector("[name='review']");
    private final By submitButton = By.id("button-review");
    private final By reviewSuccessMessage = By.cssSelector(".alert-success > span");


    //actions
    @Step("Navigate to Product Details Page")
    public ProductDetailsPage navigate(){
        driver.browserActions().navigateTo(PropertyReader.getProperty("baseURLWeb") + productDetailsPageEndpoint);
        return this;
    }

    @Step("Write a review with name: {name}, email: {email}, and review: {review}")
    public ProductDetailsPage addReview(String name, String email, String review){
        driver.elementActions().type(reviewName, name)
                .type(reviewEmail, email)
                .type(reviewTextArea, review)
                .clickElement(submitButton);
        return this;
    }

    //validations
    @Step("Verify product details with name: {name} and price: {price}")
    public ProductDetailsPage verifyProductDetails(String name, String price){
        String actualProductName = driver.elementActions().getText(productName);
        String actualProductPrice = driver.elementActions().getText(productPrice);
        LogsManager.info("Verifying product details: Expected Name: " + name + ", Actual Name: " + actualProductName +
                ", Expected Price: " + price + ", Actual Price: " + actualProductPrice);
        driver.validation().Equals(actualProductName, name, "Product name does not match");
        driver.validation().Equals(actualProductPrice, price, "Product price does not match");
        return this;
    }

    @Step("Verify review success message: {msg}")
    public ProductDetailsPage verifyReviewSuccessMessage(String msg){
        String actualMsg = driver.elementActions().getText(reviewSuccessMessage);
        LogsManager.info("Verifying review success message: Expected: " + msg + ", Actual: " + actualMsg);
        driver.verification().Equals(actualMsg, msg, "Review success message does not match");

        return this;
    }

}
