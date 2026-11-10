package com.blazedemo.pages.components;

import com.blazedemo.drivers.GUIDriver;
import com.blazedemo.utils.dataReader.PropertyReader;
import com.blazedemo.utils.logs.LogsManager;
import io.qameta.allure.Step;
import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.By;

public class ProductsPage {
    private final GUIDriver driver;
    public  NavigationBarComponent navigationBarComponent;
    public ProductsPage(GUIDriver driver) {
        this.driver = driver;
        this.navigationBarComponent = new NavigationBarComponent(driver);
    }

    //variables
    private String productPage = "/products";

    //locators
    private final By searchField = By.id("search_product");
    private final By searchButton = By.id("submit_search");
    private final By itemAddedToCartMessage = By.cssSelector(".modal-body > p");
    private final By viewCartButton = By.cssSelector("p > [href=\"/view_cart\"]");
    private final By continueShoppingButton = By.cssSelector(".modal-footer > button");

    //dynamic locators
    private By productName(String productName){
        return By.xpath("//div[@class='productinfo text-center'] /p[.='" + productName + "']");
    }
    private By productPrice(String productName){
        return By.xpath("//div[@class='productinfo text-center'] /p[.='" + productName + "'] //preceding-sibling::h2");
    }

    private By hoverOnProduct(String productName){
        return By.xpath("//div[@class='productinfo text-center'] /p[.='" + productName + "']");
    }

    private By addToCartButton(String productName){
        return By.xpath("//div[@class='productinfo text-center'] /p[.='" + productName + "'] //following-sibling::a");
    }

    private By viewProduct(String productName){
        return By.xpath("//p[.='" + productName + "'] //following::div[@class='choose'][1]");
    }



    //actions
    @Step("Navigate to Products Page")
    public ProductsPage navigate(){
        driver.browserActions().navigateTo(PropertyReader.getProperty("baseURLWeb") + productPage);
        return this;
    }

    @Step("Search for product: {productName}")
    public ProductsPage searchProduct(String productName){
        driver.elementActions().type(searchField, productName)
                .clickElement(searchButton)
                .hoverOnElement(productName(productName));
        return this;
    }

    @Step("Add product: {productName} to cart")
    public ProductsPage clickAddToCart(String productName){
        driver.elementActions().hoverOnElement(hoverOnProduct(productName))
                .clickElement(addToCartButton(productName));
        return this;
    }

    @Step("Click on View Product: {productName}")
    public ProductDetailsPage clickViewProduct(String productName){
        driver.elementActions().clickElement(viewProduct(productName));
        return new ProductDetailsPage(driver);
    }

    @Step("Click on View Cart button")
    public CartPage clickViewCart(){
        driver.elementActions().clickElement(viewCartButton);
        return new CartPage(driver);
    }

    @Step("Click on Continue Shopping")
    public ProductsPage clickContinueShopping(){
        driver.elementActions().clickElement(continueShoppingButton);
        return this;
    }

    //validations
    @Step("Validate product details: {productName} and price: {productPrice}")
    public ProductsPage validateProductDetails(String productName, String productPrice){
        String actualProductName = driver.elementActions().hoverOnElement(productName(productName)).getText(productName(productName));
        String actualProductPrice = driver.elementActions().hoverOnElement(productName(productName)).getText(this.productPrice(productName));
        driver.validation().Equals(actualProductName, productName, "Product name does not match");
        driver.validation().Equals(actualProductPrice, productPrice, "Product price does not match");
        LogsManager.info("Validating product details. Expected: " + productName + " and " + productPrice + ". Actual: " + actualProductName + " and " + actualProductPrice);
        return this;
    }

    @Step("Validate item added to cart message: {expectedMessage}")
    public ProductsPage validateItemAddedToCart(String expectedMessage){
        String actualMsg = driver.elementActions().getText(itemAddedToCartMessage);
        driver.verification().Equals(actualMsg, expectedMessage, "Item added to cart message does not match");
        LogsManager.info("Validating item added to cart message. Expected: " + expectedMessage + ". Actual: " + actualMsg);
        return this;
    }
}
