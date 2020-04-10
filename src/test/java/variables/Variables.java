package variables;

import org.testng.ITestContext;
import org.testng.annotations.BeforeSuite;

public class Variables {

     public static final int categoriesCount =13;
     public static final String invalidCityIdMessage ="Invalid coordinates / city_id";
     public static final String invalidOrMissingPramsMessage = "Invalid or missing parameter \"city_id\"";
     public static final String invalidUSerKey="Invalid API Key";
     public static final String noDailyMenu ="No Daily Menu Available";

     @BeforeSuite(alwaysRun = true)
     public void setPreParameters(ITestContext context){
          context.setAttribute("user-key","1ab31d03d37e3f491b68549ca26a2343");
          context.setAttribute("baseUri","");
     }
}
