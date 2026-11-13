package com.blazedemo.tests.ui;

import com.blazedemo.apis.UserManagementAPI;
import com.blazedemo.drivers.GUIDriver;
import com.blazedemo.drivers.UITest;
import com.blazedemo.pages.components.*;
import com.blazedemo.tests.BaseTest;
import com.blazedemo.utils.TimeManager;
import com.blazedemo.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Automation Exercises - Invoice Tests")
@Feature("UI Invoice Management - Invoice Tests")
@Story("Invoice Management")
@Severity(SeverityLevel.CRITICAL)
@Owner("Ziad Aladin")
@UITest
public class InvoiceTest extends BaseTest {

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
                .verifyCalculatedSum();

    }

    @Test(dependsOnMethods = {"checkout", "addProductToCart", "loginToAccount", "registerNewAccount"})
    public void paymentTest(){
        new CheckoutPage(driver)
                .clickCheckoutButton()
                .fillPaymentDetails(
                        testData.getJsonData("cardDetails.cardName"),
                        testData.getJsonData("cardDetails.cardNumber"),
                        testData.getJsonData("cardDetails.cvv"),
                        testData.getJsonData("cardDetails.expirationMonth"),
                        testData.getJsonData("cardDetails.expirationYear"))
                .verifyPaymentSuccessMsg(testData.getJsonData("messages.paymentSuccess"));
    }

    @Test(dependsOnMethods = {"paymentTest", "checkout", "addProductToCart", "loginToAccount", "registerNewAccount"})
    public void downloadInvoice(){
        new PaymentPage(driver)
                .clickOnDownloadInvoiceButton()
                .verifyDownloadedFile(testData.getJsonData("invoiceName"));
    }

    @Test(dependsOnMethods = {"paymentTest", "checkout", "addProductToCart", "loginToAccount", "registerNewAccount"})
    public void deleteAccountAsPostCondition(){
        new UserManagementAPI().deleteAccount(
                        testData.getJsonData("email") + timestamp + "@gmail.com",
                        testData.getJsonData("password")
                )
                .verifyUserDeletedSuccessfully();
    }

    //Configurations
    @BeforeClass
    public void setUp(){
        testData = new JsonReader("checkout-data");
        driver = new GUIDriver();
//        driver.browserActions().closeExtensionTab();
        new NavigationBarComponent(driver).navigate();

    }
    @AfterClass
    public void tearDown(){
        driver.quitDriver();
    }
}
