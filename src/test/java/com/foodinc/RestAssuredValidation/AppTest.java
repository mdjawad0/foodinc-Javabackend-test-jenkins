package com.foodinc.RestAssuredValidation;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class AppTest{ 
   
	@Test
	public void testUserLogin() {
		
		RestAssured.baseURI = "http://localhost:8080/users"; 
				
		Response response = given()
				.queryParam("email", "john@example.com")
	            .queryParam("password", "john123")
                .contentType(ContentType.JSON)
                .when()
                .post("/login")
                .then()
                .extract().response();
		 
		System.out.println(response.asPrettyString());
		JsonPath json = response.jsonPath();
		
		int expectedCode = 101;
		int actualCode = json.getInt("code");
		
		String message = json.getString("message");
		List data = json.getList("data");
				
        Assert.assertEquals(actualCode, expectedCode);
        Assert.assertTrue(message.contains("User Logged In Successfully"));
        Assert.assertTrue(data.size()==1);
	}
	
	@Test
	public void testUserLoginRestAssured() {
		
		RestAssured.baseURI = "http://localhost:8080/users"; 
				
		given()
				.queryParam("email", "john@example.com")
	            .queryParam("password", "john123")
                .contentType(ContentType.JSON)
                .when()
                .post("/login")
                .then()
                .body("code", equalTo(101), "data[0].userId", is(1));
		 
        
	}
	
	@Test
	public void testAdminLogin() {
		
		RestAssured.baseURI = "http://localhost:8080/adminauth"; 
				
		Response response = given()
				.queryParam("email", "admin@example.com")
	            .queryParam("password", "admin123")
                .contentType(ContentType.JSON)
                .when()
                .post("/login")
                .then()
                .extract().response();
		 
		System.out.println(response.asPrettyString());
		JsonPath json = response.jsonPath();
		
		int expectedCode = 101;
		int actualCode = json.getInt("code");
		
		String message = json.getString("message");
		List data = json.getList("data");
		
        Assert.assertEquals(actualCode, expectedCode);
        Assert.assertTrue(message.contains("Admin Logged In Successfully"));
        Assert.assertNull(data);
        
        
	}
	
	@Test
	public void testAdminLoginRestAssured() {
		
		RestAssured.baseURI = "http://localhost:8080/adminauth"; 
				
		given()
				.queryParam("email", "admin@example.com")
	            .queryParam("password", "admin123")
                .contentType(ContentType.JSON)
                .when()
                .post("/login")
                .then()
                .body("code", equalTo(101), "message", containsString("Admin Logged In Successfully"));
		 
	}
	
}
