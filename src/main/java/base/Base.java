package base;

public class Base {

    private static final String userKey ="1ab31d03d37e3f491b68549ca26a2343";
    private static String baseURI = "https://developers.zomato.com/api/v2.1";

    public static String getUserKey() {
        return userKey;
    }

    public static String getBaseURI(){
        return baseURI;
    }


}
