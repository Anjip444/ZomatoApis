package common;

import base.Base;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.util.HashMap;

public class CommonApis {

    public Response getListOfCategories(){

        Response response  = RestAssured.given().header("user-key",Base.getUserKey())
                .contentType("application/json")
                .baseUri(Base.getBaseURI())
                .when().get("/categories");

        return response;
    }

    public Response getCityDetials(HashMap<String, String> stringParams,HashMap<String, Double> doubleParam,HashMap<String, Integer> intParamss){
                return RestAssured.given().header("user-key",Base.getUserKey())
                        .contentType("application/json")
                        .params(stringParams)
                        .params(doubleParam)
                        .params(intParamss)
                        .baseUri(Base.getBaseURI())
                        .when().get("/cities");
    }

    public Response getCollectionsOfACity(Integer cityId, double latitude, double longitude, int count ){
        return RestAssured.given().header("user-key",Base.getUserKey())
                .contentType("application/json")
                .params("city_id",cityId)
                .params("lat",latitude)
                .params("lon",longitude)
                .param("count",count)
                .baseUri(Base.getBaseURI())
                .when().get("/collections");
    }

    public Response getCusinesOfACity(Integer cityId, double latitude, double longitude){
        return RestAssured.given().header("user-key",Base.getUserKey())
                .contentType("application/json")
                .params("city_id",cityId)
                .params("lat",latitude)
                .params("lon",longitude)
                .baseUri(Base.getBaseURI())
                .when().get("/cuisines");
    }

    public Response getListOfRestaurantsOfACity(Integer cityId, double latitude, double longitude){
        return RestAssured.given().header("user-key",Base.getUserKey())
                .contentType("application/json")
                .params("city_id",cityId)
                .params("lat",latitude)
                .params("lon",longitude)
                .baseUri(Base.getBaseURI())
                .when().get("/establishments");
    }

    public Response getByGeoCodes(double latitude, double longitude){
        return RestAssured.given().header("user-key",Base.getUserKey())
                .contentType("application/json")
                .params("lat",latitude)
                .params("lon",longitude)
                .baseUri(Base.getBaseURI())
                .when().get("/geocode");
    }

    public static void main(String[] args) {
        CommonApis as = new CommonApis();
        HashMap<String, String> stringParams = new HashMap<>();
        //stringParams.put("q","Bengaluru");
        //as.getCityDetials(stringParams,new HashMap<>(),new HashMap<>()).then().statusCode(200).extract().body().jsonPath().prettyPrint();
        //as.getCollectionsOfACity(4,0,0,0).then().statusCode(200).extract().body().jsonPath().prettyPrint();
    }

}
