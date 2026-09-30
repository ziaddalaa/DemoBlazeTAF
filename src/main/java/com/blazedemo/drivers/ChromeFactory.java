package com.blazedemo.drivers;

import com.blazedemo.utils.dataReader.PropertyReader;
import com.blazedemo.utils.logs.LogsManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URI;

public class ChromeFactory extends AbstractDriver{


    private ChromeOptions getOptions(){
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.addArguments("--start-maximized");
        if(PropertyReader.getProperty("executionType").equalsIgnoreCase("LocalHeadless")
                || PropertyReader.getProperty("executionType").equalsIgnoreCase("Remote")){
            chromeOptions.addArguments("--headless");
        }
        chromeOptions.setAcceptInsecureCerts(true);
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
        return chromeOptions;

    }

    @Override
    public WebDriver createDriver() {
        if(PropertyReader.getProperty("executionType").equalsIgnoreCase("Local") ||
        PropertyReader.getProperty("executionType").equalsIgnoreCase("LocalHeadless")){
            return new ChromeDriver(getOptions());
        }
        else if (PropertyReader.getProperty("executionType").equalsIgnoreCase("Remote")){
            try {
                return new RemoteWebDriver(
                        new URI("http://"+ remoteHost + ":" + remotePort + "/wd/hub").toURL(),
                        getOptions()
                );
            }
            catch (Exception e){
                LogsManager.error("Error while creating RemoteWebDriver: " + e.getMessage());
                throw new RuntimeException("Error while creating RemoteWebDriver: " + e.getMessage(), e);
            }
        }
        else {
            LogsManager.error("Invalid execution type: " + PropertyReader.getProperty("executionType"));
            throw new IllegalArgumentException("Invalid execution type: " + PropertyReader.getProperty("executionType"));
        }
    }
}
