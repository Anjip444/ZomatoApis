import io.restassured.response.Response;
import location.LocationApis;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import restaurant.RestaurantApis;
import variables.Variables;

import java.util.HashMap;
import java.util.Map;

public class RestaurantApisTest {

    RestaurantApis restaurantApis = new RestaurantApis();
    LocationApis locationApis = new LocationApis();

    String rest_id;

    @BeforeClass
    public void getRestaurantId(){
        Response response = locationApis.getLocationDetails(Integer.valueOf(4),"city");
        response.then().statusCode(HttpStatus.SC_OK);
        response.then().body("location.city_name",Matchers.equalTo("Bengaluru"));
        response.then().body("popularity",Matchers.notNullValue());
        response.then().body("nearby_res",Matchers.notNullValue());
        rest_id = response.then().extract().body().jsonPath().getJsonObject("nearby_res[2]").toString();
        System.out.println(rest_id);
    }


    @Test
    public void getDailyMenuOfARestaurantTest(){
        Response response = restaurantApis.getDailyMenuOfARestaurant(Integer.valueOf(rest_id));
        response.then().statusCode(HttpStatus.SC_BAD_REQUEST);
        response.then().body("message",Matchers.equalTo(Variables.noDailyMenu));
    }

    @Test
    public void getRestaurantDetailsTest(){
        Response response = restaurantApis.getRestaurantDetails(Integer.valueOf(rest_id));
        response.then().statusCode(HttpStatus.SC_OK);
        response.then().body("location.city_id",Matchers.equalTo(4));
    }

    @Test
    public void getRestaurantReviewsDefaultCountTest(){
        Response response = restaurantApis.getRestaurantReviews(Integer.valueOf(rest_id));
        response.then().statusCode(HttpStatus.SC_OK);
        response.then().body("reviews_shown",Matchers.equalTo(5));
    }


    @Test
    public void getRestaurantReviewsSpecifiedOffsetAndCountTest(){
        Response response = restaurantApis.getRestaurantReviews(Integer.valueOf(rest_id),1,1);
        response.then().statusCode(HttpStatus.SC_OK);
        response.then().body("user_reviews",Matchers.notNullValue());
        response.then().body("reviews_shown",Matchers.equalTo(1));
        response.then().body("user_reviews",Matchers.hasSize(1));
    }

    @Test
    public void searchForRestaurantsTest(){
        HashMap<String, String> stringParams = new HashMap<>();
        HashMap<String, Double> doubleParams = new HashMap<>();
        HashMap<String, Integer> intParams = new HashMap<>();
        stringParams.put("entity_type","landmark");
        stringParams.put("q","Beng");
        stringParams.put("establishment_type",String.valueOf(CommonApisTest.establishmentId));
        stringParams.put("category",String.valueOf(CommonApisTest.categoryId));
        stringParams.put("collection_id",String.valueOf(CommonApisTest.collectionId));
        intParams.put("start",1);
        intParams.put("count",5);
        Response response = restaurantApis.searchForRestaurants(stringParams,doubleParams,intParams);
        response.then().statusCode(HttpStatus.SC_OK);
        response.then().body("restaurants",Matchers.notNullValue());
        response.then().body("results_shown",Matchers.equalTo(5));
    }

}
