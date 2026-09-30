package com.blazedemo.drivers;
import com.blazedemo.utils.dataReader.PropertyReader;
import com.blazedemo.utils.logs.LogsManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URI;

public class EdgeFactory extends AbstractDriver{

    private EdgeOptions getOptions(){
        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.addArguments("--remote-allow-origins=*");
        edgeOptions.addArguments("--start-maximized");
        if(PropertyReader.getProperty("executionType").equalsIgnoreCase("LocalHeadless")
                || PropertyReader.getProperty("executionType").equalsIgnoreCase("Remote")){
            edgeOptions.addArguments("--headless");
        }
        edgeOptions.setAcceptInsecureCerts(true);
        edgeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
        return edgeOptions;

    }

    @Override
    public WebDriver createDriver() {
        if(PropertyReader.getProperty("executionType").equalsIgnoreCase("Local") ||
                PropertyReader.getProperty("executionType").equalsIgnoreCase("LocalHeadless")){
            return new EdgeDriver(getOptions());
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
