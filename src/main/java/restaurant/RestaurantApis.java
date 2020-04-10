package restaurant;

import base.Base;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.HashMap;

public class RestaurantApis {

    public Response getDailyMenuOfARestaurant(Integer restarantId){
        return RestAssured.given().header("user-key",Base.getUserKey())
                .contentType("application/json")
                .params("res_id",restarantId)
                .baseUri(Base.getBaseURI())
                .when().get("/dailymenu");
    }

    public Response getRestaurantDetails(Integer restarantId){
        return RestAssured.given().header("user-key",Base.getUserKey())
                .contentType("application/json")
                .params("res_id",restarantId)
                .baseUri(Base.getBaseURI())
                .when().get("/restaurant");
    }

    public Response getRestaurantReviews(Integer restarantId, int startOffset, int count){
        return RestAssured.given().header("user-key",Base.getUserKey())
                .contentType("application/json")
                .params("res_id",restarantId)
                .params("start",startOffset)
                .params("count",count)
                .baseUri(Base.getBaseURI())
                .when().get("/reviews");
    }

    public Response getRestaurantReviews(Integer restarantId){
        return RestAssured.given().header("user-key",Base.getUserKey())
                .contentType("application/json")
                .params("res_id",restarantId)
                .baseUri(Base.getBaseURI())
                .when().get("/reviews");
    }

    public Response searchForRestaurants(HashMap<String, String> stringParams, HashMap<String, Double> doubleParam, HashMap<String, Integer> intParamss){
        return RestAssured.given().header("user-key",Base.getUserKey())
                .contentType("application/json")
                .params(stringParams)
                .params(doubleParam)
                .params(intParamss)
                .baseUri(Base.getBaseURI())
                .when().get("/search");
    }


}
