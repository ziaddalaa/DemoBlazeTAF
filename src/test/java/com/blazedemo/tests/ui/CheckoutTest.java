package com.blazedemo.tests.ui;

import com.blazedemo.apis.UserManagementAPI;
import com.blazedemo.drivers.GUIDriver;
import com.blazedemo.pages.components.CartPage;
import com.blazedemo.pages.components.NavigationBarComponent;
import com.blazedemo.pages.components.ProductsPage;
import com.blazedemo.pages.components.SignupLoginPage;
import com.blazedemo.tests.BaseTest;
import com.blazedemo.utils.TimeManager;
import com.blazedemo.utils.dataReader.JsonReader;
import org.testng.annotations.*;

public class CheckoutTest extends BaseTest {

    String timestamp = TimeManager.getSimpleTimeStamp();

    @Test
    public void registerNewAccount(){
        new UserManagementAPI().createAccount(
                        testData.getJsonData("name"),
                        testData.getJsonData("email") + timestamp + "@gmail.com",
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
    }

    @Test(dependsOnMethods = "registerNewAccount")
    public void loginToAccount(){
        new SignupLoginPage(driver).navigate()
                .enterLoginEmail(testData.getJsonData("email") + timestamp + "@gmail.com")
                .enterLoginPassword(testData.getJsonData("password"))
                .clickLoginButton()
                .navigationBarComponent
                .verifyLoggedInAsLabelIsDisplayed(testData.getJsonData("name"));
    }

    @Test(dependsOnMethods = {"loginToAccount", "registerNewAccount"})
    public void addProductToCart(){
        new ProductsPage(driver)
                .navigate()
                .clickAddToCart(testData.getJsonData("product1.name"))
                .validateItemAddedToCart(testData.getJsonData("messages.addedToCart"))
                .clickContinueShopping()
                .clickAddToCart(testData.getJsonData("product2.name"))
                .validateItemAddedToCart(testData.getJsonData("messages.addedToCart"))
                .clickViewCart()
                .verifyProductInCart(
                        testData.getJsonData("product1.name"),
                        testData.getJsonData("product1.price"),
                        testData.getJsonData("product1.quantity"),
                        testData.getJsonData("product1.total")
                )
                .verifyProductInCart(
                        testData.getJsonData("product2.name"),
                        testData.getJsonData("product2.price"),
                        testData.getJsonData("product2.quantity"),
                        testData.getJsonData("product2.total")
                );
    }

    @Test(dependsOnMethods = {"addProductToCart", "loginToAccount", "registerNewAccount"})
    public void checkout(){
        new CartPage(driver)
                .clickProceedToCheckout()
                .verifyDeliveryAddressDetails(
                        testData.getJsonData("titleMale"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"),
                        testData.getJsonData("companyName"),
                        testData.getJsonData("address1"),
                        testData.getJsonData("address2"),
                        testData.getJsonData("city"),
                        testData.getJsonData("state"),
                        testData.getJsonData("zipCode"),
                        testData.getJsonData("country"),
                        testData.getJsonData("mobileNumber")
                )
                .verifyBillingAddressDetails(
                        testData.getJsonData("titleMale"),
                        testData.getJsonData("firstName"),
                        testData.getJsonData("lastName"),
                        testData.getJsonData("companyName"),
                        testData.getJsonData("address1"),
                        testData.getJsonData("address2"),
                        testData.getJsonData("city"),
                        testData.getJsonData("state"),
                        testData.getJsonData("zipCode"),
                        testData.getJsonData("country"),
                        testData.getJsonData("mobileNumber")
                )
                .verifyCalculatedSum()
                .clickCheckoutButton();

    }



    //Configurations
    @BeforeClass
    public void setUp(){
        testData = new JsonReader("checkout-data");
        driver = new GUIDriver();
        new NavigationBarComponent(driver).navigate();

    }
    @AfterClass
    public void tearDown(){
        driver.quitDriver();
    }
}
