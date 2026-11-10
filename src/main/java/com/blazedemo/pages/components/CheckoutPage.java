package com.blazedemo.pages.components;

import com.blazedemo.drivers.GUIDriver;
import com.blazedemo.utils.dataReader.PropertyReader;
import com.blazedemo.utils.logs.LogsManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CheckoutPage {
    private GUIDriver driver;
    int calculatedSum = 0;

    public CheckoutPage(GUIDriver driver) {
        this.driver = driver;
        itemTotals = driver.get().findElements(By.cssSelector("td.cart_total p.cart_total_price"));
    }

    //vars
    private String checkoutEndpoint = "/checkout";

    //locators
    //delivery address locators
    private final By deliveryName = By.xpath("//ul[@id='address_delivery'] /li[@class='address_firstname address_lastname']");
    private final By deliveryCompany = By.xpath("(//ul[@id='address_delivery'] /li[@class='address_address1 address_address2'])[1]");
    private final By deliveryAddress1 = By.xpath("(//ul[@id='address_delivery'] /li[@class='address_address1 address_address2'])[2]");
    private final By deliveryAddress2 = By.xpath("(//ul[@id='address_delivery'] /li[@class='address_address1 address_address2'])[3]");
    private final By deliveryCityStateZip = By.xpath("//ul[@id='address_delivery'] /li[@class='address_city address_state_name address_postcode']");
    private final By deliveryCountry = By.xpath("//ul[@id='address_delivery'] /li[@class='address_country_name']");
    private final By deliveryPhone = By.xpath("//ul[@id='address_delivery'] /li[@class='address_phone']");


    //billing address locators
    private final By billingName = By.xpath("//ul[@id='address_invoice'] /li[@class='address_firstname address_lastname']");
    private final By billingCompany = By.xpath("(//ul[@id='address_invoice'] /li[@class='address_address1 address_address2'])[1]");
    private final By billingAddress1 = By.xpath("(//ul[@id='address_invoice'] /li[@class='address_address1 address_address2'])[2]");
    private final By billingAddress2 = By.xpath("(//ul[@id='address_invoice'] /li[@class='address_address1 address_address2'])[3]");
    private final By billingCityStateZip = By.xpath("//ul[@id='address_invoice'] /li[@class='address_city address_state_name address_postcode']");
    private final By billingCountry = By.xpath("//ul[@id='address_invoice'] /li[@class='address_country_name']");
    private final By billingPhone = By.xpath("//ul[@id='address_invoice'] /li[@class='address_phone']");

    private final By checkoutButton = By.cssSelector("a[href='/payment']");

    private final By totalCheckout = By.xpath("(//td /p[@class='cart_total_price'])[last()]");
    List<WebElement> itemTotals;



    //actions
    @Step("Navigate to Checkout Page")
    public CheckoutPage navigate(){
        driver.browserActions().navigateTo(PropertyReader.getProperty("baseURLWeb") + checkoutEndpoint);
        return this;
    }

    @Step("Click Checkout Button")
    public OrderPage clickCheckoutButton(){
        driver.elementActions().clickElement(checkoutButton);
        return new OrderPage(driver);
    }

    //validations
    @Step("Verify Delivery Address Details")
    public CheckoutPage verifyDeliveryAddressDetails(String title, String fName, String lName, String company, String address1, String address2, String city, String state, String zipCode, String country, String phone) {
        driver.validation().Equals(driver.elementActions().getText(deliveryName), (title + ". " + fName + " " + lName), "Delivery Name does not match")
                .Equals(driver.elementActions().getText(deliveryCompany), company, "Delivery Company does not match")
                .Equals(driver.elementActions().getText(deliveryAddress1), address1, "Delivery Address 1 does not match")
                .Equals(driver.elementActions().getText(deliveryAddress2), address2, "Delivery Address 2 does not match")
                .Equals(driver.elementActions().getText(deliveryCityStateZip), (zipCode + " " + city + " " + state), "Delivery City/State/Zip does not match")
                .Equals(driver.elementActions().getText(deliveryCountry), country, "Delivery Country does not match")
                .Equals(driver.elementActions().getText(deliveryPhone), phone, "Delivery Phone does not match");
        return this;
    }

    @Step("Verify Billing Address Details")
    public CheckoutPage verifyBillingAddressDetails(String title, String fName, String lName, String company, String address1, String address2, String city, String state, String zipCode, String country, String phone) {
        driver.validation().Equals(driver.elementActions().getText(billingName), (title + ". " + fName + " " + lName), "Billing Name does not match")
                .Equals(driver.elementActions().getText(billingCompany), company, "Billing Company does not match")
                .Equals(driver.elementActions().getText(billingAddress1), address1, "Billing Address 1 does not match")
                .Equals(driver.elementActions().getText(billingAddress2), address2, "Billing Address 2 does not match")
                .Equals(driver.elementActions().getText(billingCityStateZip), (zipCode + " " + city + " " + state), "Billing City/State/Zip does not match")
                .Equals(driver.elementActions().getText(billingCountry), country, "Billing Country does not match")
                .Equals(driver.elementActions().getText(billingPhone), phone, "Billing Phone does not match");
        return this;
    }

    @Step("Calculate Sum of Item Totals in Checkout Page")
    public CheckoutPage verifyCalculatedSum(){

        // 2. Loop through each item, remove "Rs. ", convert to int, and add to sum
        for (WebElement item : itemTotals) {
            String priceText = item.getText().replace("Rs. ", "").trim(); // e.g., "500"
            calculatedSum += Integer.parseInt(priceText);
        }
        // 3. Locate the final 'Total Amount' (the last p.cart_total_price on the page)
        String finalTotalText = driver.elementActions().getText(totalCheckout)
                .replace("Rs. ", "").trim();
        LogsManager.info("Calculated Sum: " + calculatedSum + ", Final Total Text: " + finalTotalText);
        // 4. Assert that the calculated sum matches the final total amount
        driver.verification().Equals(finalTotalText, String.valueOf(calculatedSum), "Calculated sum does not match the final total amount.");
        return this;
    }

}


