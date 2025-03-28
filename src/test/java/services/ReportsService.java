package services;

import baseService.BaseService;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.HashMap;

public class ReportsService extends BaseService {

    public static final String BASE_PATH = "/api/reports/statement/";

    public Response getExcel(String token, HashMap<String, String> queryParams){
        setAuthToken(token);
        return getRequest(queryParams, BASE_PATH + "excel");
    }

}
