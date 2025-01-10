package FakeRESTApi;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseTest {
    private final String BASE_URI = "https://fakerestapi.azurewebsites.net";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = BASE_URI;
    }
}
