package variables;

import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class DataProviderTest {

    @DataProvider(name="Iterator return type")
    public Iterator<Object[]> getDataP(){
        Object[][] t = {{5,2,3},{7,3,4}};
        List<Object[][]> ls = new ArrayList<>();
        ls.addAll(new Object[][]{});
        return Arrays.asList(t).iterator();
    }
}
