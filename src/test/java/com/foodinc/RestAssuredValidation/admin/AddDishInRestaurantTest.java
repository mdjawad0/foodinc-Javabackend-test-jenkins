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


public class AddDishInRestaurantTest {
	
	@Test
	public void tesAddDishToRestaurant() {
		
		RestAssured.baseURI = "http://localhost:8080/dishes"; 
				
		Response response = given()
				.queryParam("restaurantId", "202")
	            .queryParam("restaurantName", "Cafe Coffee Day")
	            .queryParam("restaurantAddress", "ABC Street")
	            .queryParam("name", "Cafe Frappe")
	            .queryParam("description", "Cold Coffee with Chocolate")
	            .queryParam("price", "150")
	            .queryParam("rating", "4")
	            .queryParam("ingredients", "Coffee and Chocholate")
	            .queryParam("images", "0")
	            .queryParam("thumbnailImage", "0")
	            .queryParam("status", "1")
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
		// Check response must be available in less than 200 ms
		Assert.assertTrue(responseTime < 200);
		// Check JSON Response to have code 101
        Assert.assertEquals(code, 101);
        // Check Success Message from JSON Response
        Assert.assertTrue(message.contains("Success"));
        
        // Check Success Message from JSON Response
        String name = (String)data.get(0).get("name");
        Assert.assertEquals(name, "Cafe Frappe");
        
        int price = (Integer)data.get(0).get("price");
        Assert.assertEquals(price, 150);
	}

	
	@Test
	public void validateAddDishToRestaurantWithRestAssured() {
		
		RestAssured.baseURI = "http://localhost:8080/dishes"; 
				
		given()
				.queryParam("restaurantId", "202")
		        .queryParam("restaurantName", "Cafe Coffee Day")
		        .queryParam("restaurantAddress", "ABC Street")
		        .queryParam("name", "Cafe Frappe")
		        .queryParam("description", "Cold Coffee with Chocolate")
		        .queryParam("price", "150")
		        .queryParam("rating", "4")
		        .queryParam("ingredients", "Coffee and Chocholate")
		        .queryParam("images", "0")
		        .queryParam("thumbnailImage", "0")
		        .queryParam("status", "1")
                .contentType(ContentType.JSON)
                .when()
                .post("/add")
                .then()
                .body(
                		"code", equalTo(101),  
                		"message", containsString("Success"),
                		"data[0].name", equalTo("Cafe Frappe")
                );
        
	}
	
	@Test
	public void deserializeAndValidateResponse() {
		
		RestAssured.baseURI = "http://localhost:8080/dishes"; 
				
		Response response = given()
				.queryParam("restaurantId", "202")
		        .queryParam("restaurantName", "Cafe Coffee Day")
		        .queryParam("restaurantAddress", "ABC Street")
		        .queryParam("name", "Cafe Frappe")
		        .queryParam("description", "Cold Coffee with Chocolate")
		        .queryParam("price", "150")
		        .queryParam("rating", "4")
		        .queryParam("ingredients", "Coffee and Chocholate")
		        .queryParam("images", "0")
		        .queryParam("thumbnailImage", "0")
		        .queryParam("status", "1")
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
