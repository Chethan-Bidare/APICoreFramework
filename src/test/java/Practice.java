import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class Practice {

    public static void main(String[] args) throws IOException {

        //upload a file in Rest Assured
        File file = new File(System.getProperty("user.dir")+"Statement.jpg");
        Response response = given().baseUri("https://testurl")
                .when().multiPart(file)
                .header("Content-Type","multipart/form-data")
                .post("endPoint")
                .then().statusCode(200).extract().response();

        //Download a file

        response = given().baseUri("baseUrl")
                .when().get("endPont")
                .then().statusCode(200).extract().response();
        File file1 = new File("downloadFile.jpg");
        FileOutputStream fos = new FileOutputStream(file1);
        fos.write(response.asByteArray());
        if (file1.exists()){
            System.out.println("downloaded Successfully "+file1.getAbsolutePath());
        }else System.out.println("download failed");

        //Query Param
        Map<String, Object> parameters = new HashMap<>();
        given().baseUri("base url")
                .when().queryParams("testId", 1)
                .queryParams(parameters)
                .get("endPoint")
                .then().statusCode(200).extract().response();

        //Validate Response time of an API

        given().baseUri("base url")
                .when().header("Authorization", "Bearer token")
                .get("endPoint")
                .then().time(lessThan(3000L));

        //Chaining APi requests - nothing but feeding 1 api's input to another Api

        //Path Parameter - used to identify specific resource within url path
        given().baseUri("base Url")
                .pathParam("userId", "324")
                .when().get("/user/userId")
                .then().extract().response();


        //JsonSchemaValidation
        response.then().assertThat().body(matchesJsonSchemaInClasspath("user-schema.json"));

        //OAuth2 - The formParam() method is a synchronous method,
        // which means that it will block until the request has been sent and the response has been received
        String token  = given().baseUri("base url")
                .formParam("client_Id", "provide client id")
                .formParam("client_secret_key", "secret key")
                .formParam("grant_type","grant")
                .when().post("/oauth/token")
                .then().statusCode(200).extract().jsonPath().getString("access_token");

        //Security Test - malicious sql code
        String maliciousPayload = "1' OR '1'='1";  // SQL Injection attempt

        given()
                .baseUri("https://api.example.com")
                .queryParam("userId", maliciousPayload)
                .when()
                .get("/users")
                .then()
                .statusCode(400)  // API should reject invalid inputs
                .log().all();

        //Pass user1 token to user2 and test Authorization broken - 403 Forbidden
        //Authentication failure occurs when without passing token - 401

        //Rate Limiting test
        for (int i=1; i<= 20; i++){
            given().baseUri("base url")
                    .when()
                    .get("end point")
                    .then()
                    .statusCode(anyOf(equalTo(200), equalTo(429)));  // Expect either success or rate limit error
        }

        //Use Expired token and verify 401 unauthorized
        //Use a refresh token and verify refresh mechanism


        //Testing API Security Headers
        given().baseUri("base url")
                .when().get("end point")
                .then().header("Content-Security-Policy", notNullValue())
                .header("X-Frame-Options", equalTo("DENY"))
                .header("Strict-Transport-Security",containsString("max-age")).log().all();

        //Testing for Sensitive Data Exposure
        given().baseUri("base url")
                .when().get("end point")
                .then()
                .body("$",not(hasKey("password")))
                .body("$", not(hasKey("api_key"))).log().all();

        //Testing HTTP methods misconfiguration
        given()
                .baseUri("https://api.example.com")
                .when()
                .delete("/users/1")  // Unauthorized DELETE request
                .then()
                .statusCode(403)  // API should return Forbidden
                .log().all();
    }
}
