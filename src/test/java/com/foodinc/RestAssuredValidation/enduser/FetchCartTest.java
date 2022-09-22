package com.foodinc.RestAssuredValidation.enduser;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.foodinc.RestAssuredValidation.FoodDeliveryResponse;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class FetchCartTest {

	@Test
	public void testFetchCart() {
		
		RestAssured.baseURI = "http://localhost:8080/cart"; 
				
		Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/get")
                .then()
                .extract().response();
		 
		System.out.println(response.asPrettyString());
		JsonPath json = response.jsonPath();
        
		int statusCode = response.getStatusCode();
		long responseTime = response.getTime();
		
        int code = json.getInt("code");
		String message = json.getString("message");
		List data = json.getList("data");
		
		// Check if response is OK
		Assert.assertEquals(statusCode, 200);
		
		// Check if Response has a valid body
		Assert.assertNotNull(response.getBody());	
		
		// Check response must be available in less than 300 ms
		Assert.assertTrue(responseTime < 300);
		
		// Check JSON Response to have code 101
        Assert.assertEquals(code, 101);
        
        // Check Success Message from JSON Response
        Assert.assertTrue(message.contains("Success"));
        
        // Check if database has more than 10 carts
        Assert.assertTrue(data.size() > 10);
        
	}
	
	@Test
	public void validateFetchCartWithRestAssured() {
		
		RestAssured.baseURI = "http://localhost:8080/cart"; 
				
		given()
                .contentType(ContentType.JSON)
                .when()
                .get("/get")
                .then()
                .body(
                		"code", equalTo(101),  
                		"message", containsString("Success"),
                		"data[0].quantity", equalTo(1)
                );
        
	}
	
	@Test
	public void deserializeAndValidateResponse() {
		
		RestAssured.baseURI = "http://localhost:8080/cart"; 
				
		Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/get")
                .then()
                .extract().response();
		 
		FoodDeliveryResponse userResponse = response.getBody().as(FoodDeliveryResponse.class); 
		
		System.out.println(userResponse);
		
		// Check JSON Response to have code 101
        Assert.assertEquals(userResponse.code, 101);
        
        // Check Success Message from JSON Response
        Assert.assertTrue(userResponse.message.contains("Success"));
        
	}
	
}
