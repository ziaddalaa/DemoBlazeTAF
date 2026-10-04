package com.blazedemo.pages.components;

import com.blazedemo.drivers.GUIDriver;
import com.blazedemo.utils.dataReader.PropertyReader;
import com.blazedemo.utils.logs.LogsManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class NavigationBarComponent {
    private final GUIDriver driver;

    public NavigationBarComponent(GUIDriver driver) {
        this.driver = driver;
    }

    //locators
    private final By homeButton =By.xpath("//a[.=' Home']");
    private final By productsButton = By.cssSelector("a[href='/products']");
    private final By cartButton = By.xpath("//a[.=' Cart']");
    private final By signupOrLoginButton = By.cssSelector("a[href='/login']");
    private final By logoutButton = By.cssSelector("a[href='/logout']");
    private final By testCasesButton = By.xpath("//a[.=' Test Cases']");
    private final By deleteAccountButton = By.cssSelector("a[href='/delete_account']");
    private final By apiTestingButton = By.xpath("//a[.=' API Testing']");
    private final By videoTutorialsButton = By.xpath("//a[.=' Video Tutorials']");
    private final By contactUsButton = By.cssSelector("a[href='/contact_us']");
    private final By homePageLabel = By.cssSelector("h1 > span");
    private final By loggedInAsLabel = By.tagName("b");



    //actions
    @Step("Navigate to base URL")
    public NavigationBarComponent navigate(){
        driver.browserActions().navigateTo(PropertyReader.getProperty("baseUrl"));
        return this;
    }

    @Step("Click on Home button")
    public NavigationBarComponent clickHomeButton(){
        driver.elementActions().clickElement(homeButton);
        return this;
    }

    @Step("Click on Products Button")
    public ProductsPage clickOnProductsButton()
    {
        driver.elementActions().clickElement(productsButton);
        return new ProductsPage(driver);
    }
    @Step("Click on Cart Button")
    public CartPage clickOnCartButton()
    {
        driver.elementActions().clickElement(cartButton);
        return new CartPage(driver);
    }
    @Step("Click on Logout Button")
    public LogoutPage clickOnLogoutButton()
    {
        driver.elementActions().clickElement(logoutButton);
        return new LogoutPage(driver);
    }
    @Step("Click on Signup/Login Button")
    public SignupLoginPage clickOnSignupLoginButton()
    {
        driver.elementActions().clickElement(signupOrLoginButton);
        return new SignupLoginPage(driver);
    }
    @Step("Click on Test Cases Button")
    public TestCasesPage clickOnTestCasesButton()
    {
        driver.elementActions().clickElement(testCasesButton);
        return new TestCasesPage(driver);
    }
    @Step("Click on Delete Account Button")
    public DeleteAccountPage clickOnDeleteAccountButton()
    {
        driver.elementActions().clickElement(deleteAccountButton);
        return new DeleteAccountPage(driver);
    }
    @Step("Click on ContactUs Button Button")
    public ContactUsPage clickOnContactUsButton()
    {
        driver.elementActions().clickElement(contactUsButton);
        return new ContactUsPage(driver);
    }

    //validations
    @Step("Validate Home Page Label is displayed")
    public NavigationBarComponent verifyHomePageLabelIsDisplayed(){
        driver.verification().isElementVisible(homePageLabel);
        return this;
    }

    @Step("Validate Logged In As Label is displayed")
    public NavigationBarComponent verifyLoggedInAsLabelIsDisplayed(String expectedName){
        String actualName = driver.elementActions().getText(loggedInAsLabel);
        LogsManager.info("Validating Logged In As Label. Expected: " + expectedName + ", Actual: " + actualName);
        driver.verification().Equals(actualName, expectedName, "Logged In As Label does not match");
        return this;
    }
}
