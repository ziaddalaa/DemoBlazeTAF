package com.blazedemo.apis;

import com.blazedemo.utils.logs.LogsManager;
import com.blazedemo.validations.Verification;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class UserManagementAPI {
    RequestSpecification requestSpecification;
    Response response;
    Verification verification;

    public UserManagementAPI(){
        requestSpecification = RestAssured.given();
        verification = new Verification();
    }

    //endpoints
    private static final String createAccount_endpoint = "/createAccount";
    private static final String deleteAccount_endpoint = "/deleteAccount";


    //api methods
    @Step("Create user account with full details")
    public UserManagementAPI createAccount(String name, String email, String password, String title, String birth_date, String birth_month, String birth_year, String firstname, String lastname, String company, String address1, String address2, String country, String zipcode, String state, String city, String mobile_number){
        Map<String, String> formParams = new HashMap<>();
                formParams.put("name", name);
                formParams.put("email", email);
                formParams.put("password", password);
                formParams.put("title", title);
                formParams.put("birth_date", birth_date);
                formParams.put("birth_month", birth_month);
                formParams.put("birth_year", birth_year);
                formParams.put("firstname", firstname);
                formParams.put("lastname", lastname);
                formParams.put("company", company);
                formParams.put("address1", address1);
                formParams.put("address2", address2);
                formParams.put("country", country);
                formParams.put("zipcode", zipcode);
                formParams.put("state", state);
                formParams.put("city", city);
                formParams.put("mobile_number", mobile_number);
                response = requestSpecification.spec(Builder.getUserManagementRequestSpecification(formParams))
                        .post(createAccount_endpoint);
        LogsManager.info("Create account response: " + response.asPrettyString());
        return this;
    }

    @Step("Delete user account with email: {email}")
    public UserManagementAPI deleteAccount(String email, String password){
        Map<String, String> formParams = new HashMap<>();
        formParams.put("email", email);
        formParams.put("password", password);
        response = requestSpecification.spec(Builder.getUserManagementRequestSpecification(formParams))
                .delete(deleteAccount_endpoint);
        LogsManager.info("Delete account response: " + response.asPrettyString());
        return this;

    }
    //Validations
    @Step("Verify user is created successfully")
    public UserManagementAPI verifyUserCreatedSuccessfully(){
        verification.Equals(response.jsonPath().get("message"), "User created!", "User creation failed");
        return this;
    }

    @Step("Verify user is deleted successfully")
    public UserManagementAPI verifyUserDeletedSuccessfully(){
        verification.Equals(response.jsonPath().get("message"), "Account deleted!", "User deletion failed");
        return this;
    }
}
