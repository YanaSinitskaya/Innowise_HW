package FakeRESTApi;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static FakeRESTApi.ConstantsStatusCode.BAD_REQUEST_STATUS_CODE;
import static FakeRESTApi.ConstantsStatusCode.SUCCESS_STATUS_CODE;
import static FakeRESTApi.PathsToJSONSchemas.*;
import static org.testng.Assert.assertEquals;

public class UsersAPITest extends BaseTest {
    SoftAssert softAssert = new SoftAssert();

    private int id = 7;

    @Test
    @Description("Test 'Get' method, get all users")
    public void getAllPhotoLinksOfBooks() {
        Response response = RestAssured.get("/api/v1/Users");
        int statusCode = response.getStatusCode();
        softAssert.assertEquals(statusCode, SUCCESS_STATUS_CODE, "Actual status code is not as expected");
        try {
            response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(GET_ALL_USERS_JSON));
        } catch (AssertionError error) {
            softAssert.fail("JSON Schema validation failed:" + error.getMessage());
        }
        softAssert.assertAll();
    }

    @Test
    @Description("Test 'Get' method, get one user using valid id")
    public void getOnePhotoLinkOfBookWithId() {
        Response response = RestAssured
                .given()
                .pathParams("id", id)
                .when()
                .get("/api/v1/Users/{id}");
        int statusCode = response.getStatusCode();
        softAssert.assertEquals(statusCode, SUCCESS_STATUS_CODE, "Actual status code is not as expected");
        try {
            response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(GET_ONE_USER_JSON));
        } catch (AssertionError error) {
            softAssert.fail("JSON Schema validation failed:" + error.getMessage());
        }
        softAssert.assertAll();
    }

    @Test
    @Description("Test 'Post' method, create user with valid data")
    public void createAuthorWithValidData() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("id", id);
        requestBody.put("userName", "userName");
        requestBody.put("password", "Pass123");
        Response response = RestAssured
                .given()
                .header("accept", "*/*")
                .header("Content-Type", "application/json")
                .body(requestBody.toString())
                .when()
                .post("/api/v1/Users");
        int statusCode = response.getStatusCode();
        softAssert.assertEquals(statusCode, SUCCESS_STATUS_CODE, "Actual status code is not as expected");
        try {
            response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(GET_ONE_USER_JSON));
        } catch (AssertionError error) {
            softAssert.fail("JSON Schema validation failed:" + error.getMessage());
        }
        softAssert.assertAll();
    }

    @Test
    @Description("Test 'Put' method, update user information using valid data")
    public void updateBookInfoWithValidData() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("id", id);
        requestBody.put("userName", "userName588");
        requestBody.put("password", "Pass321");
        Response response = RestAssured
                .given()
                .header("accept", "*/*")
                .header("Content-Type", "application/json")
                .pathParams("id", id)
                .body(requestBody.toString())
                .when()
                .put("/api/v1/Users/{id}");
        int statusCode = response.getStatusCode();
        softAssert.assertEquals(statusCode, SUCCESS_STATUS_CODE, "Actual status code is not as expected");
        try {
            response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(GET_ONE_USER_JSON));
        } catch (AssertionError error) {
            softAssert.fail("JSON Schema validation failed:" + error.getMessage());
        }
        softAssert.assertAll();
    }

    @Test
    @Description("Test 'Put' method, update user info using empty request body")
    public void updateBookInfoWithEmptyBody() {
        Response response = RestAssured
                .given()
                .header("accept", "*/*")
                .header("Content-Type", "application/json")
                .pathParams("id", id)
                .body("")
                .when()
                .put("/api/v1/Users/{id}");
        int statusCode = response.getStatusCode();
        softAssert.assertEquals(statusCode, BAD_REQUEST_STATUS_CODE, "Actual status code is not as expected");
        try {
            response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(VALIDATION_ERROR_JSON));
        } catch (AssertionError error) {
            softAssert.fail("JSON Schema validation failed:" + error.getMessage());
        }
        softAssert.assertAll();
    }

    @Test
    @Description("Test 'Delete' method, delete user with valid id")
    public void deleteBookByUsingValidId() {
        Response response = RestAssured
                .given()
                .header("accept", "*/*")
                .pathParams("id", id)
                .when()
                .delete("/api/v1/Users/{id}");
        int statusCode = response.getStatusCode();
        assertEquals(statusCode, SUCCESS_STATUS_CODE, "Actual status code is not as expected");
    }
}

