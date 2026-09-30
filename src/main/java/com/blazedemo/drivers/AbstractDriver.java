package com.blazedemo.drivers;

import com.blazedemo.utils.dataReader.PropertyReader;
import org.openqa.selenium.WebDriver;

public abstract class AbstractDriver {

    protected final String remoteHost = PropertyReader.getProperty("remoteHost");
    protected final String remotePort = PropertyReader.getProperty("remotePort");

    public abstract WebDriver createDriver();
}
