package com.blazedemo.tests.ui;

import com.blazedemo.drivers.GUIDriver;
import com.blazedemo.pages.components.NavigationBarComponent;
import com.blazedemo.pages.components.ProductsPage;
import com.blazedemo.tests.BaseTest;
import com.blazedemo.utils.TimeManager;
import com.blazedemo.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Automation Exercises - Products Tests")
@Feature("UI Products Management - Products Tests")
@Story("Products Management")
@Severity(SeverityLevel.CRITICAL)
@Owner("Ziad Aladin")
public class ProductsTest extends BaseTest {

    @Test
    @Description("Verify that user is able to search for a product successfully")
    public void searchForProductTC(){
        new ProductsPage(driver)
                .navigate()
                .searchProduct(testData.getJsonData("searchedProduct.name"))
                .validateProductDetails(
                        testData.getJsonData("searchedProduct.name"),
                        testData.getJsonData("searchedProduct.price")
                );
    }

    @Test
    @Description("Verify that user is able to add a product successfully without login")
    public void addProductWithoutLoginTC(){
        new ProductsPage(driver)
                .navigate()
                .clickAddToCart(testData.getJsonData("product1.name"))
                .validateItemAddedToCart(testData.getJsonData("messages.addedToCart"));
    }

    //Configurations
    @BeforeClass
    public void preCondition(){
        testData = new JsonReader("products-data");

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
