package testngTests;

import org.testng.*;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;
import variables.DataProviderTest;

import java.util.List;

public class TestClass1 {

    @BeforeSuite
    public void test1(XmlTest xmlTest){
        System.out.println(xmlTest.getExpression());
        System.out.println("In before suite");
    }

    @BeforeTest
    public void beforeTest(ITestContext context){
        System.out.println(context.getHost());
        System.out.println("In before test method");
    }

    @DataProvider(name = "data")
    public Object[][] getData(){
        Integer[][] intVal = {{1,2},{2,3}};
        return intVal;
    }

    @Test(dataProvider = "data")
    public void getDataTest(Integer a, Integer b){
        System.out.println(a+b);
    }

    @Test(dataProvider = "Iterator return type", dataProviderClass = DataProviderTest.class)
    public void getDataTest2(Integer a, Integer b){
        System.out.println(a+b);
    }
}
