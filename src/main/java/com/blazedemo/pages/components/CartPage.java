package com.blazedemo.pages.components;

import com.blazedemo.drivers.GUIDriver;
import com.blazedemo.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CartPage {
    private GUIDriver driver;

    public CartPage(GUIDriver driver) {
        this.driver = driver;
    }

    //vars
    private String cartEndpoint = "/view_cart";

    //locators
    private final By proceedToCheckoutButton = By.cssSelector(".col-sm-6 a");

    //dynamic locators
    private By productName(String productName){
        return By.xpath("(//h4 /a[.='" + productName +"'])[1]");
    }
    private By productPrice(String productName){
        return By.xpath("(//a[.='" + productName + "'] //following::td[@class='cart_price']  //p)[1]");
    }
    private By productQuantity(String productName){
        return By.xpath("(//a[.='" + productName + "'] //following::td[@class='cart_quantity']  //button)[1]");
    }
    private By productTotal(String productName){
        return By.xpath("(//a[.='" + productName + "'] //following::td[@class='cart_total']  //p)[1]");
    }
    private By removeProduct(String productName){
        return By.xpath("(//a[.='" + productName + "'] //following::td[@class='cart_delete']  //a)[1]");
    }

    //actions
    @Step("Navigate to Cart Page")
    public CartPage navigate(){
        driver.browserActions().navigateTo(PropertyReader.getProperty("baseURLWeb") + cartEndpoint);
        return this;
    }

    @Step("Click Proceed to Checkout")
    public CheckoutPage clickProceedToCheckout(){
        driver.elementActions().clickElement(proceedToCheckoutButton);
        return new CheckoutPage(driver);
    }

    @Step("Remove Product from Cart")
    public CartPage removeProductFromCart(String productName){
        driver.elementActions().clickElement(removeProduct(productName));
        return this;
    }

    //validations
    @Step("Verify Product Details in Cart")
    public CartPage verifyProductInCart(String productName, String productPrice, String productQuantity, String productTotal){
        String actualProductName = driver.elementActions().getText(productName(productName));
        String actualProductPrice = driver.elementActions().getText(productPrice(productName));
        String actualProductQuantity = driver.elementActions().getText(productQuantity(productName));
        String actualProductTotal = driver.elementActions().getText(productTotal(productName));

        driver.validation().Equals(actualProductName, productName, "Product name in cart is not as expected")
        .Equals(actualProductPrice, productPrice, "Product price in cart is not as expected")
        .Equals(actualProductQuantity, productQuantity, "Product quantity in cart is not as expected")
        .Equals(actualProductTotal, productTotal, "Product total in cart is not as expected");
        return this;
    }
}
