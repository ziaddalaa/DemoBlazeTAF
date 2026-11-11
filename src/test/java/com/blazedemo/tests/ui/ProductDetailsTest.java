package com.blazedemo.tests.ui;

import com.blazedemo.drivers.GUIDriver;
import com.blazedemo.drivers.UITest;
import com.blazedemo.pages.components.NavigationBarComponent;
import com.blazedemo.pages.components.ProductsPage;
import com.blazedemo.tests.BaseTest;
import com.blazedemo.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Automation Exercises - Product Details Tests")
@Feature("UI Product Details Management - Product Details Tests")
@Story("Product Details Management")
@Severity(SeverityLevel.CRITICAL)
@Owner("Ziad Aladin")
@UITest
public class ProductDetailsTest extends BaseTest {

    @Test
    public void verifyProductDetailsWithoutLoginTC(){
        new ProductsPage(driver)
                .navigate()
                .clickViewProduct(testData.getJsonData("product.name"))
                .verifyProductDetails(testData.getJsonData("product.name"), testData.getJsonData("product.price"));

    }

    @Test
    public void verifyReviewMsgWithoutLoginTC(){
        new ProductsPage(driver)
                .navigate()
                .clickViewProduct(testData.getJsonData("product.name"))
                .addReview(
                        testData.getJsonData("review.name"),
                        testData.getJsonData("review.email"),
                        testData.getJsonData("review.reviewTxt")
                )
                .verifyReviewSuccessMessage(testData.getJsonData("messages.reviewSuccessMsg"));

    }



    //Configurations
    @BeforeClass
    public void preCondition(){
        testData = new JsonReader("product-details-data");

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
