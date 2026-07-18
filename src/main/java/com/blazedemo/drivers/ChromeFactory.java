package com.blazedemo.drivers;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeFactory extends AbstractDriver{

    private ChromeOptions getOptions(){
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.setAcceptInsecureCerts(true);
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
        return chromeOptions;

    }

    @Override
    public WebDriver createDriver() {
        return new ChromeDriver(getOptions());
    }
}
