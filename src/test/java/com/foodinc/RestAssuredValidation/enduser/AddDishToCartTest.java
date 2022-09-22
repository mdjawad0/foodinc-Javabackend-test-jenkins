package com.foodinc.RestAssuredValidation.enduser;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;

import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.foodinc.RestAssuredValidation.FoodDeliveryResponse;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class AddDishToCartTest {

	@Test
	public void tesAddDishToCart() {
		
		RestAssured.baseURI = "http://localhost:8080/cart"; 
				
		Response response = given()
				.queryParam("dishId", "1")
	            .queryParam("userId", "150")
                .contentType(ContentType.JSON)
                .when()
                .post("/add")
                .then()
                .extract().response();
		 
		System.out.println(response.asPrettyString());
		JsonPath json = response.jsonPath();
        
		int statusCode = response.getStatusCode();
		long responseTime = response.getTime();
		
        int code = json.getInt("code");
		String message = json.getString("message");
		List<HashMap<String, Object>> data = json.getList("data");
		
		// Check if response is OK
		Assert.assertEquals(statusCode, 200);
		
		// Check if Response has a valid body
		Assert.assertNotNull(response.getBody());	
		
		// Check response must be available in less than 150 ms
		Assert.assertTrue(responseTime < 150);
		
		// Check JSON Response to have code 101
        Assert.assertEquals(code, 101);
        
        // Check Success Message from JSON Response
        Assert.assertTrue(message.contains("Success"));
        
        // Check Success Message from JSON Response
        int cartID = (Integer)data.get(0).get("cartId");
        Assert.assertTrue(cartID > 190);
        
	}
	
	@Test
	public void validateAddDishToCartWithRestAssured() {
		
		RestAssured.baseURI = "http://localhost:8080/cart"; 
				
		given()
				.queryParam("dishId", "1")
	            .queryParam("userId", "150")
                .contentType(ContentType.JSON)
                .when()
                .post("/add")
                .then()
                .body(
                		"code", equalTo(101),  
                		"message", containsString("Success"),
                		"data[0].dishId", equalTo(1)
                );
        
        
	}

	@Test
	public void deserializeAndValidateResponse() {
		
		RestAssured.baseURI = "http://localhost:8080/cart"; 
				
		Response response = given()
				.queryParam("dishId", "1")
	            .queryParam("userId", "150")
                .contentType(ContentType.JSON)
                .when()
                .post("/add")
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
