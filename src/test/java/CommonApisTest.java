import common.CommonApis;
import io.restassured.response.Response;
import location.LocationApis;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import variables.Variables;

import java.util.HashMap;
import java.util.Map;

public class CommonApisTest {

    CommonApis commonApis = new CommonApis();
    LocationApis locationApis = new LocationApis();
    Map<String, String> locations = new HashMap<>();
    static String establishmentId ;
    static String collectionId;
    static String categoryId;

    @BeforeSuite
    public void testSomething(ITestContext context){
        context.setAttribute("user-key","1ab31d03d37e3f491b68549ca26a2343");
        System.out.println();
    }

    @BeforeClass
    public void getCityDetails(){
        Response response = locationApis.getLocations("Bengaluru",0,0,1);
        response.then().statusCode(HttpStatus.SC_OK);
        locations.put("city",response.then().extract().body().jsonPath().getJsonObject("location_suggestions[0].title").toString());
        locations.put("latitude",response.then().extract().body().jsonPath().getJsonObject("location_suggestions[0].latitude").toString());
        locations.put("longitude",response.then().extract().body().jsonPath().getJsonObject("location_suggestions[0].longitude").toString());
        locations.put("entityId",response.then().extract().body().jsonPath().getJsonObject("location_suggestions[0].entity_id").toString());
        locations.put("cityId",response.then().extract().body().jsonPath().getJsonObject("location_suggestions[0].city_id").toString());
        locations.put("entityType",response.then().extract().body().jsonPath().getJsonObject("location_suggestions[0].entity_type").toString());
    }
    /**
     * this test is to find all the categories
     * to assert the total number of category objects with expected values
     * to assert any one of the category name
     */
    @Test()
    public void getListOfCategoriesTest(ITestContext context){
        Response response = commonApis.getListOfCategories();
        response.then().statusCode(HttpStatus.SC_OK);
        Assert.assertTrue(response.then()
                .extract().body().jsonPath().getList("categories").size()==Variables.categoriesCount);
        response.then().body("categories[12].categories.name",Matchers.equalTo("Clubs & Lounges"));
        System.out.println(context.getAttribute("user-key"));
        categoryId = response.then().extract().body().jsonPath().getJsonObject("categories[12].categories.id").toString();

    }

    @Test
    public void getCitiesTest(){
        HashMap<String, String> stringParams = new HashMap<>();
        HashMap<String, Double> doubleParams = new HashMap<>();
        HashMap<String, Integer> intParams = new HashMap<>();
        stringParams.put("q",locations.get("city"));
        stringParams.put("city_ids",locations.get("cityId"));
        doubleParams.put("lat", Double.valueOf(locations.get("latitude")));
        doubleParams.put("lon", Double.valueOf(locations.get("longitude")));
        intParams.put("count",1);
        Response response = commonApis.getCityDetials(stringParams,doubleParams,intParams);
        response.then().statusCode(HttpStatus.SC_OK);
        response.then().body("location_suggestions[0].name",Matchers.equalTo("Bengaluru"));
    }

    @Test
    public void getCitiesNoSearchCriteriaTest(){
        HashMap<String, String> stringParams = new HashMap<>();
        HashMap<String, Double> doubleParams = new HashMap<>();
        HashMap<String, Integer> intParams = new HashMap<>();
        Response response = commonApis.getCityDetials(stringParams,doubleParams,intParams);
        response.then().statusCode(HttpStatus.SC_OK);
        response.then().body("location_suggestions",Matchers.hasSize(0));
    }

    @Test
    public void getCollectionsTest(){
        Response response = commonApis.getCollectionsOfACity(Integer.valueOf(locations.get("cityId")),Double.valueOf(locations.get("latitude")),
                Double.valueOf(locations.get("longitude")),4);
        response.then().statusCode(HttpStatus.SC_OK);
        response.then().body("collections",Matchers.hasSize(4));
        response.then().body("collections[0].collection.title",Matchers.equalTo("Trending This Week"));
        collectionId = response.then().extract().body().jsonPath().getJsonObject("collections[0].collection.title").toString();
    }

    @Test
    public void getCollectionsInvalidDataTest(){
        Response response = commonApis.getCollectionsOfACity(Integer.valueOf(0),Double.valueOf(locations.get("latitude")),
                Double.valueOf(locations.get("longitude")),4);
        response.then().statusCode(HttpStatus.SC_BAD_REQUEST);
        response.then().body("message",Matchers.equalTo(Variables.invalidOrMissingPramsMessage));
    }

    @Test
    public void getCusinesOfCityTest(){
        Response response = commonApis.getCusinesOfACity(Integer.valueOf(locations.get("cityId")),Double.valueOf(locations.get("latitude")),
                Double.valueOf(locations.get("longitude")));
        response.then().statusCode(HttpStatus.SC_OK);
        response.then().body("cuisines[0].cuisine.cuisine_name",Matchers.equalTo("Afghan"));
    }

    @Test
    public void getCusinesOfCityInvalidDataTest(){
        Response response = commonApis.getCusinesOfACity(0,Double.valueOf(-1),-1);
        response.then().statusCode(HttpStatus.SC_BAD_REQUEST);
        response.then().body("message",Matchers.equalTo(Variables.invalidCityIdMessage));
    }

    @Test
    public void getListOfEstablishmentsTest(){
        Response response = commonApis.getListOfRestaurantsOfACity(Integer.valueOf(locations.get("cityId")),Double.valueOf(locations.get("latitude")),
                Double.valueOf(locations.get("longitude")));
        response.then().statusCode(HttpStatus.SC_OK);
        response.then().body("establishments[0].establishment.id",Matchers.equalTo(16));
        establishmentId = response.then().extract().body().jsonPath().getJsonObject("establishments[0].establishment.id").toString();
        System.out.println(establishmentId);
    }

    @Test
    public void getListOfEstablishmentsInvalidDataTest(){
        Response response = commonApis.getListOfRestaurantsOfACity(Integer.valueOf(0),Double.valueOf(locations.get("latitude")),
                Double.valueOf(locations.get("longitude")));
        response.then().statusCode(HttpStatus.SC_BAD_REQUEST);
        response.then().body("message",Matchers.equalTo(Variables.invalidOrMissingPramsMessage));
    }

    @Test
    public void getByGeoCodesTest(){
        Response response = commonApis.getByGeoCodes(Double.valueOf(locations.get("latitude")),
                Double.valueOf(locations.get("longitude")));
        response.then().statusCode(HttpStatus.SC_OK);
        response.then().body("location.city_name",Matchers.equalTo("Bengaluru"));
        response.then().body("popularity",Matchers.notNullValue());
        response.then().body("popularity.nearby_res",Matchers.hasItem("51912"));
        response.then().body("nearby_restaurants",Matchers.notNullValue());
        response.then().body("nearby_restaurants",Matchers.notNullValue());
    }


}
