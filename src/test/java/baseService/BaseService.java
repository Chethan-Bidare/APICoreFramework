package baseService;

import filters.LoggingFilter;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.request.LoginRequest;

import java.util.HashMap;
import java.util.List;

public class BaseService {
    private static final String BASE_URI = "http://64.227.160.186:8080";
    private final RequestSpecification requestSpecification;

    public BaseService(){
        requestSpecification = RestAssured.given().baseUri(BASE_URI);
    }

    static {
        RestAssured.filters(new LoggingFilter());
    }

    public void setAuthToken(String token){
        requestSpecification.header("Authorization","Bearer " + token);
    }

    protected Response postRequest(Object payload, String endPoint){
        return requestSpecification.contentType(ContentType.JSON).body(payload).post(endPoint);
    }

    protected Response getRequest(String endpoint){
        return requestSpecification.get(endpoint);
    }

    protected Response putRequest(Object payload, String endpoint){
        return requestSpecification.contentType(ContentType.JSON).body(payload).put(endpoint);
    }

    protected Response getRequest(HashMap<String, String> queryParam, String endPoint){
        return requestSpecification.queryParams(queryParam).get(endPoint);
    }
}
