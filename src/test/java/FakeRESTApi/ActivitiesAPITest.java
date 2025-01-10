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

public class ActivitiesAPITest extends BaseTest {
    private int id = 1;
    SoftAssert softAssert = new SoftAssert();

    @Test
    @Description("Test 'Get' method for getting all activities")
    public void getAllActivities() {
        Response response = RestAssured.get("/api/v1/Activities");
        int statusCode = response.getStatusCode();
        softAssert.assertEquals(statusCode, SUCCESS_STATUS_CODE, "Actual status code is not as expected");
        try {
            response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(GET_ALL_ACTIVITIES_JSON));
        } catch (AssertionError error) {
            softAssert.fail("JSON Schema validation failed:" + error.getMessage());
        }
        softAssert.assertAll();
    }

    @Test
    @Description("Test 'Get' method for getting one activity with valid id")
    public void getOneActivity() {
        Response response = RestAssured
                .given()
                .pathParams("id", id)
                .when()
                .get("/api/v1/Activities/{id}");
        int statusCode = response.getStatusCode();
        softAssert.assertEquals(statusCode, SUCCESS_STATUS_CODE, "Actual status code is not as expected");
        try {
            response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(GET_ONE_ACTIVITY_JSON));
        } catch (AssertionError error) {
            softAssert.fail("JSON Schema validation failed:" + error.getMessage());
        }
        softAssert.assertAll();
    }

    @Test
    @Description("Test 'Post' method, creating new activity with valid request body")
    public void postNewActivityWithValidData() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("id", id);
        requestBody.put("title", "Title");
        requestBody.put("dueDate", "2024-12-17T07:11:22.274Z");
        requestBody.put("completed", true);
        Response response = RestAssured
                .given()
                .header("accept", "text/plain")
                .header("Content-Type", "application/json")
                .body(requestBody.toString())
                .when()
                .post("/api/v1/Activities");
        int statusCode = response.getStatusCode();
        softAssert.assertEquals(statusCode, SUCCESS_STATUS_CODE, "Actual status code isn't as expected");
        try {
            response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(GET_ONE_ACTIVITY_JSON));
        } catch (AssertionError error) {
            softAssert.fail("JSON Schema validation failed: " + error.getMessage());
        }
        softAssert.assertAll();
    }

    @Test
    @Description("Test 'Post' method, create activity with empty request body")
    public void createActivityWithEmptyRequestBody() {
        Response response = RestAssured
                .given()
                .header("accept", "text/plain")
                .header("Content-Type", "application/json")
                .body("")
                .when()
                .post("/api/v1/Activities");
        int statusCode = response.getStatusCode();
        softAssert.assertEquals(statusCode, BAD_REQUEST_STATUS_CODE, "Actual status code isn't as expected");
        try {
            response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(VALIDATION_ERROR_JSON));
        } catch (AssertionError error) {
            softAssert.fail("JSON Schema validation failed: " + error.getMessage());
        }
        softAssert.assertAll();
    }

    @Test
    @Description("Test 'Put' method, update activity with valid id and data")
    public void updateActivityWithValidData() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("id", id);
        requestBody.put("title", "TitleNew");
        requestBody.put("dueDate", "2025-11-18T07:11:22.274Z");
        requestBody.put("completed", true);
        Response response = RestAssured
                .given()
                .header("accept", "text/plain")
                .header("Content-Type", "application/json")
                .pathParams("id", id)
                .body(requestBody.toString())
                .when()
                .put("/api/v1/Activities/{id}");
        int statusCode = response.getStatusCode();
        softAssert.assertEquals(statusCode, SUCCESS_STATUS_CODE, "Actual status code isn't as expected");
        try {
            response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(GET_ONE_ACTIVITY_JSON));
        } catch (AssertionError error) {
            softAssert.fail("JSON Schema validation failed: " + error.getMessage());
        }
        softAssert.assertAll();
    }

    @Test
    @Description("Test 'Put' method, update activity with empty request body")
    public void updateActivityWithEmptyRequestBody() {
        Response response = RestAssured
                .given()
                .header("accept", "text/plain")
                .header("Content-Type", "application/json")
                .pathParams("id", id)
                .body("")
                .when()
                .put("/api/v1/Activities/{id}");
        int statusCode = response.getStatusCode();
        softAssert.assertEquals(statusCode, BAD_REQUEST_STATUS_CODE, "Actual status code isn't as expected");
        try {
            response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(VALIDATION_ERROR_JSON));
        } catch (AssertionError error) {
            softAssert.fail("JSON Schema validation failed: " + error.getMessage());
        }
        softAssert.assertAll();
    }

    @Test
    @Description("Test 'Delete' method, delete activity with valid id")
    public void deleteActivityWithValidId() {
        Response response = RestAssured
                .given()
                .header("accept", "*/*")
                .pathParams("id", id)
                .when()
                .delete("/api/v1/Activities/{id}");
        int statusCode = response.getStatusCode();
        assertEquals(statusCode, SUCCESS_STATUS_CODE, "Actual status code isn't as expected");
    }
}
