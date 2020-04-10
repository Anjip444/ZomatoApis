package location;

import base.Base;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class LocationApis {

    public Response getLocationDetails(Integer entityId, String entityType){
        return RestAssured.given().header("user-key",Base.getUserKey())
                .contentType("application/json")
                .params("entity_id",entityId)
                .params("entity_type",entityType)
                .baseUri(Base.getBaseURI())
                .when().get("/location_details");
    }

    public Response getLocationDetails(Integer entityId, String entityType, String userKey){
        return RestAssured.given().header("user-key",userKey)
                .contentType("application/json")
                .params("entity_id",entityId)
                .params("entity_type",entityType)
                .baseUri(Base.getBaseURI())
                .when().get("/location_details");
    }

    public Response getLocations(String locName, double latitude, double longitude,int count){
        return RestAssured.given().header("user-key",Base.getUserKey())
                .contentType("application/json")
                .params("query",locName)
                .params("lat",latitude)
                .params("lon",longitude)
                .params("count",count)
                .baseUri(Base.getBaseURI())
                .when().get("/locations");
    }



}
