import io.restassured.response.Response;
import location.LocationApis;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import variables.Variables;

import java.util.HashMap;
import java.util.Map;

public class LocationApisTest {

    LocationApis locationApis = new LocationApis();
    Map<String, String> locations = new HashMap<>();

    @BeforeClass
    public void getCityDetails(){
        Response response = locationApis.getLocations("Bengaluru",0,0,1);
        response.then().statusCode(HttpStatus.SC_OK);
        response.then().body("location_suggestions[0].title",Matchers.equalTo("Bengaluru"));
        locations.put("city",response.then().extract().body().jsonPath().getJsonObject("location_suggestions[0].title").toString());
        locations.put("latitude",response.then().extract().body().jsonPath().getJsonObject("location_suggestions[0].latitude").toString());
        locations.put("longitude",response.then().extract().body().jsonPath().getJsonObject("location_suggestions[0].longitude").toString());
        locations.put("entityId",response.then().extract().body().jsonPath().getJsonObject("location_suggestions[0].entity_id").toString());
        locations.put("cityId",response.then().extract().body().jsonPath().getJsonObject("location_suggestions[0].city_id").toString());
        locations.put("entityType",response.then().extract().body().jsonPath().getJsonObject("location_suggestions[0].entity_type").toString());
    }

    @Test
    public void getLocationDetailsTest(){
        Response response = locationApis.getLocationDetails(Integer.valueOf(locations.get("entityId")),locations.get("entityType"));
        response.then().statusCode(HttpStatus.SC_OK);
        response.then().body("location.city_name",Matchers.equalTo("Bengaluru"));
        response.then().body("popularity",Matchers.notNullValue());
        response.then().body("nearby_res",Matchers.hasItem("51912"));
        response.then().body("best_rated_restaurant",Matchers.notNullValue());
        response.then().body("best_rated_restaurant[0].restaurant.photos",Matchers.notNullValue());
    }

    @Test
    public void getCityDetailsTest(){
        Response response = locationApis.getLocations("Bengaluru",0,0,1);
        response.then().statusCode(HttpStatus.SC_OK);
        response.then().body("location_suggestions[0].title",Matchers.equalTo("Bengaluru"));
    }

    @Test
    public void getLocationsInvalidUserKeyTest(){
        Response response = locationApis.getLocationDetails(Integer.valueOf(locations.get("entityId")),locations.get("entityType"),"testKey");
        response.then().statusCode(HttpStatus.SC_FORBIDDEN);
        response.then().body("message",Matchers.equalTo(Variables.invalidUSerKey));
    }
}
