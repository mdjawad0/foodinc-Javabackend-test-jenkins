package com.foodinc.RestAssuredValidation.admin;

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

public class AddRestaurantTest {
	
	@Test
	public void tesAddDishToRestaurant() {
		
		RestAssured.baseURI = "http://localhost:8080/restaurants"; 
				
		Response response = given()
	            .queryParam("name", "Cafe Coffee Day")
	            .queryParam("description", "A chain of Coffee Restaurants")
	            .queryParam("address", "ABC Street")
	            .queryParam("contact", "9876598765")
	            .queryParam("email", "info@ccd.cafe")
	            .queryParam("images", "null")
	            .queryParam("thumbnailImage", "0")
	            .queryParam("rating", "5")
	            .queryParam("status", "1")
	            .queryParam("addedOn", "2022/2/2")
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
        String email = (String)data.get(0).get("email");
        Assert.assertEquals(email, "info@ccd.cafe");

	}
	
	@Test
	public void validateAddDishToCartWithRestAssured() {
		
		RestAssured.baseURI = "http://localhost:8080/restaurants"; 
				
		given()
				.queryParam("name", "Cafe Coffee Day")
		        .queryParam("description", "A chain of Coffee Restaurants")
		        .queryParam("address", "ABC Street")
		        .queryParam("contact", "9876598765")
		        .queryParam("email", "info@ccd.cafe")
		        .queryParam("images", "null")
		        .queryParam("thumbnailImage", "0")
		        .queryParam("rating", "5")
		        .queryParam("status", "1")
		        .queryParam("addedOn", "2022/2/2")
                .contentType(ContentType.JSON)
                .when()
                .post("/add")
                .then()
                .body(
                		"code", equalTo(101),  
                		"message", containsString("Success"),
                		"data[0].email", equalTo("info@ccd.cafe")
                );
        
        
	}
	
	@Test
	public void deserializeAndValidateResponse() {
		
		RestAssured.baseURI = "http://localhost:8080/restaurants"; 
				
		Response response = given()
				.queryParam("name", "Cafe Coffee Day")
		        .queryParam("description", "A chain of Coffee Restaurants")
		        .queryParam("address", "ABC Street")
		        .queryParam("contact", "9876598765")
		        .queryParam("email", "info@ccd.cafe")
		        .queryParam("images", "null")
		        .queryParam("thumbnailImage", "0")
		        .queryParam("rating", "5")
		        .queryParam("status", "1")
		        .queryParam("addedOn", "2022/2/2")
                .contentType(ContentType.JSON)
                .when()
                .post("/add")
                .then()
                .extract().response();
		 
		FoodDeliveryResponse adminResponse = response.getBody().as(FoodDeliveryResponse.class); 
		
		System.out.println(adminResponse);
		
		// Check JSON Response to have code 101
        Assert.assertEquals(adminResponse.code, 101);
        
        // Check Success Message from JSON Response
        Assert.assertTrue(adminResponse.message.contains("Success"));
        
	}
        
}