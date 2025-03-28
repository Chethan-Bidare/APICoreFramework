package services;

import baseService.BaseService;
import io.restassured.response.Response;
import models.request.UserProfileRequest;

public class UserProfileService extends BaseService {

    public static final String BASE_PATH = "/api/users/";

    public Response getProfile(String token){
        setAuthToken(token);
        return getRequest(BASE_PATH + "profile");
    }

    public Response putProfile(UserProfileRequest payload, String token){
        setAuthToken(token);
        return putRequest(payload, BASE_PATH + "profile" );
    }
}
