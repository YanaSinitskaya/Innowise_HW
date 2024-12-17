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

public class AuthorsAPITest extends BaseTest {
    private int id = 1;
    private int idBook = 5;
    SoftAssert softAssert = new SoftAssert();

    @Test
    @Description("Test 'Get' method, get list of all authors")
    public void getAllAuthors() {
        Response response = RestAssured.get("/api/v1/Authors");
        int statusCode = response.getStatusCode();
        softAssert.assertEquals(statusCode, SUCCESS_STATUS_CODE, "Actual status code is not as expected");
        try {
            response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(GET_ALL_AUTHORS_JSON));
        } catch (AssertionError error) {
            softAssert.fail("JSON Schema validation failed:" + error.getMessage());
        }
        softAssert.assertAll();
    }

    @Test
    @Description("Test 'Get' method, get one author with valid id")
    public void getOneAuthor() {
        Response response = RestAssured
                .given()
                .pathParams("id", id)
                .when()
                .get("/api/v1/Authors/{id}");
        int statusCode = response.getStatusCode();
        softAssert.assertEquals(statusCode, SUCCESS_STATUS_CODE, "Actual status code is not as expected");
        try {
            response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(GET_ONE_AUTHOR_JSON));
        } catch (AssertionError error) {
            softAssert.fail("JSON Schema validation failed:" + error.getMessage());
        }
        softAssert.assertAll();
    }

    @Test
    @Description("Test 'Get' method, get authors using valid book id")
    public void getAuthorsByBookId() {
        Response response = RestAssured
                .given()
                .pathParams("idBook", idBook)
                .when()
                .get("api/v1/Authors/authors/books/{idBook}");
        int statusCode = response.getStatusCode();
        softAssert.assertEquals(statusCode, SUCCESS_STATUS_CODE, "Actual status code is not as expected");
        try {
            response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(GET_ALL_AUTHORS_JSON));
        } catch (AssertionError error) {
            softAssert.fail("JSON Schema validation failed:" + error.getMessage());
        }
        softAssert.assertAll();
    }

    @Test
    @Description("Test 'Post' method, create author with valid data")
    public void createAuthorWithValidData() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("id", id);
        requestBody.put("idBook", idBook);
        requestBody.put("firstName", "Ivan");
        requestBody.put("lastName", "Ivanov");
        Response response = RestAssured
                .given()
                .header("accept", "text/plain")
                .header("Content-Type", "application/json")
                .body(requestBody.toString())
                .when()
                .post("/api/v1/Authors");
        int statusCode = response.getStatusCode();
        softAssert.assertEquals(statusCode, SUCCESS_STATUS_CODE, "Actual status code is not as expected");
        try {
            response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(GET_ONE_AUTHOR_JSON));
        } catch (AssertionError error) {
            softAssert.fail("JSON Schema validation failed:" + error.getMessage());
        }
        softAssert.assertAll();
    }

    @Test
    @Description("Test 'Post' method, create author with valid data")
    public void createAuthorWithEmptyRequestBody() {
        Response response = RestAssured
                .given()
                .header("accept", "text/plain")
                .header("Content-Type", "application/json")
                .body("")
                .when()
                .post("/api/v1/Authors");
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
    @Description("Test 'Put' method, update author information with valid data")
    public void updateAuthorWithValidData() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("id", id);
        requestBody.put("idBook", idBook);
        requestBody.put("firstName", "Ivan");
        requestBody.put("lastName", "Ivanov");
        Response response = RestAssured
                .given()
                .header("accept", "text/plain")
                .header("Content-Type", "application/json")
                .pathParams("id", id)
                .body(requestBody.toString())
                .when()
                .put("/api/v1/Authors/{id}");
        int statusCode = response.getStatusCode();
        softAssert.assertEquals(statusCode, SUCCESS_STATUS_CODE, "Actual status code is not as expected");
        try {
            response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(GET_ONE_AUTHOR_JSON));
        } catch (AssertionError error) {
            softAssert.fail("JSON Schema validation failed:" + error.getMessage());
        }
        softAssert.assertAll();
    }

    @Test
    @Description("Test 'Put' method, update author with empty request body")
    public void updateAuthorWithEmptyBody() {
        Response response = RestAssured
                .given()
                .header("accept", "text/plain")
                .header("Content-Type", "application/json")
                .pathParams("id", id)
                .body("")
                .when()
                .put("/api/v1/Authors/{id}");
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
    @Description("Test 'Delete' method, delete author with valid id")
    public void deleteAuthorByUsingValidId() {
        Response response = RestAssured
                .given()
                .header("accept", "*/*")
                .pathParams("id", id)
                .when()
                .delete("/api/v1/Authors/{id}");
        int statusCode = response.getStatusCode();
        assertEquals(statusCode, SUCCESS_STATUS_CODE, "Actual status code is not as expected");
    }
}
