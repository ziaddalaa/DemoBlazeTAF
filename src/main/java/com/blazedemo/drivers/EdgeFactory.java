package com.blazedemo.drivers;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class EdgeFactory extends AbstractDriver{

    private EdgeOptions getOptions(){
        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.addArguments("--remote-allow-origins=*");
        edgeOptions.addArguments("--start-maximized");
        edgeOptions.setAcceptInsecureCerts(true);
        edgeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
        return edgeOptions;

    }

    @Override
    public WebDriver createDriver() {
        return new EdgeDriver(getOptions());
    }

}
