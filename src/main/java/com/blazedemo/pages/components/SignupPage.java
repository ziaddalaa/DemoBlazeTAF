package com.blazedemo.pages.components;

import com.blazedemo.drivers.GUIDriver;

public class SignupPage {
    private GUIDriver driver;
    private final String signUpLoginEndPoint = "/signup";
    public SignupPage(GUIDriver driver) {
        this.driver = driver;
    }
}
