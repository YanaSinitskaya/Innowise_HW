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

public class CoverPhotosAPITest extends BaseTest {
    SoftAssert softAssert = new SoftAssert();

    private int id = 5;
    private int idBook = 11;

    @Test
    @Description("Test 'Get' method, get all photo links")
    public void getAllPhotoLinksOfBooks() {
        Response response = RestAssured.get("/api/v1/CoverPhotos");
        int statusCode = response.getStatusCode();
        softAssert.assertEquals(statusCode, SUCCESS_STATUS_CODE, "Actual status code is not as expected");
        try {
            response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(GET_ALL_BOOKS_PHOTO_LINKS_JSON));
        } catch (AssertionError error) {
            softAssert.fail("JSON Schema validation failed:" + error.getMessage());
        }
        softAssert.assertAll();
    }

    @Test
    @Description("Test 'Get' method, get one photo link for one book using valid book id")
    public void getOnePhotoLinkOfBookWithBookId() {
        Response response = RestAssured
                .given()
                .pathParams("idBook", idBook)
                .when()
                .get("/api/v1/CoverPhotos/books/covers/{idBook}");
        int statusCode = response.getStatusCode();
        softAssert.assertEquals(statusCode, SUCCESS_STATUS_CODE, "Actual status code is not as expected");
        try {
            response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(GET_ALL_BOOKS_PHOTO_LINKS_JSON));
        } catch (AssertionError error) {
            softAssert.fail("JSON Schema validation failed:" + error.getMessage());
        }
        softAssert.assertAll();
    }

    @Test
    @Description("Test 'Get' method, get one photo link for one book using valid id")
    public void getOnePhotoLinkOfBookWithId() {
        Response response = RestAssured
                .given()
                .pathParams("id", id)
                .when()
                .get("/api/v1/CoverPhotos/{id}");
        int statusCode = response.getStatusCode();
        softAssert.assertEquals(statusCode, SUCCESS_STATUS_CODE, "Actual status code is not as expected");
        try {
            response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(GET_ONE_BOOK_PHOTO_LINK_JSON));
        } catch (AssertionError error) {
            softAssert.fail("JSON Schema validation failed:" + error.getMessage());
        }
        softAssert.assertAll();
    }

    @Test
    @Description("Test 'Post' method, create photo link for one book with valid data")
    public void createAuthorWithValidData() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("id", id);
        requestBody.put("idBook", idBook);
        requestBody.put("url", "https://i1.sndcdn.com/artworks-XJdVplPCbvDvJlH7-jF9c4A-t500x500.jpg");
        Response response = RestAssured
                .given()
                .header("accept", "text/plain")
                .header("Content-Type", "application/json")
                .body(requestBody.toString())
                .when()
                .post("/api/v1/CoverPhotos");
        int statusCode = response.getStatusCode();
        softAssert.assertEquals(statusCode, SUCCESS_STATUS_CODE, "Actual status code is not as expected");
        try {
            response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(GET_ONE_BOOK_PHOTO_LINK_JSON));
        } catch (AssertionError error) {
            softAssert.fail("JSON Schema validation failed:" + error.getMessage());
        }
        softAssert.assertAll();
    }

    @Test
    @Description("Test 'Put' method, update photo link for book information using valid data")
    public void updateBookInfoWithValidData() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("id", id);
        requestBody.put("idBook", idBook);
        requestBody.put("url", "https://i1.sndcdn.com/artworks-XJdVplPCbvDvJlH7-jF9c4A-t500x500.jpg");
        Response response = RestAssured
                .given()
                .header("accept", "text/plain")
                .header("Content-Type", "application/json")
                .pathParams("id", id)
                .body(requestBody.toString())
                .when()
                .put("/api/v1/CoverPhotos/{id}");
        int statusCode = response.getStatusCode();
        softAssert.assertEquals(statusCode, SUCCESS_STATUS_CODE, "Actual status code is not as expected");
        try {
            response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(GET_ONE_BOOK_PHOTO_LINK_JSON));
        } catch (AssertionError error) {
            softAssert.fail("JSON Schema validation failed:" + error.getMessage());
        }
        softAssert.assertAll();
    }

    @Test
    @Description("Test 'Put' method, update photo link for book info using empty request body")
    public void updateBookInfoWithEmptyBody() {
        Response response = RestAssured
                .given()
                .header("accept", "text/plain")
                .header("Content-Type", "application/json")
                .pathParams("id", id)
                .body("")
                .when()
                .put("/api/v1/CoverPhotos/{id}");
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
    @Description("Test 'Delete' method, delete photo link for book using valid id")
    public void deleteBookByUsingValidId() {
        Response response = RestAssured
                .given()
                .header("accept", "*/*")
                .pathParams("id", id)
                .when()
                .delete("/api/v1/CoverPhotos/{id}");
        int statusCode = response.getStatusCode();
        assertEquals(statusCode, SUCCESS_STATUS_CODE, "Actual status code is not as expected");
    }
}
