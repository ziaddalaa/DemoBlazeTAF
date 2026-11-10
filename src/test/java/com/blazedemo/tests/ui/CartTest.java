package com.blazedemo.tests.ui;

import com.blazedemo.drivers.GUIDriver;
import com.blazedemo.pages.components.NavigationBarComponent;
import com.blazedemo.pages.components.ProductsPage;
import com.blazedemo.tests.BaseTest;
import com.blazedemo.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Automation Exercises - Cart Tests")
@Feature("UI Cart Management - Cart Tests")
@Story("Cart Management")
@Severity(SeverityLevel.CRITICAL)
@Owner("Ziad Aladin")
public class CartTest extends BaseTest {

    @Test
    public void verifyProductDetailsInCartWithoutLoginTC(){

        new ProductsPage(driver)
                .navigate()
                .clickAddToCart(testData.getJsonData("product.name"))
                .validateItemAddedToCart(testData.getJsonData("messages.addedToCart"))
                .clickViewCart()
                .verifyProductInCart(
                        testData.getJsonData("product.name"),
                        testData.getJsonData("product.price"),
                        testData.getJsonData("product.quantity"),
                        testData.getJsonData("product.total")
                );
    }

    @Test
    public void removeProductFromCart(){

        new ProductsPage(driver)
                .navigate()
                .clickAddToCart(testData.getJsonData("product.name"))
                .validateItemAddedToCart(testData.getJsonData("messages.addedToCart"))
                .clickViewCart()
                .removeProductFromCart(
                        testData.getJsonData("product.name")
                );
    }

    //Configurations
    @BeforeClass
    public void preCondition(){
        testData = new JsonReader("cart-data");

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
