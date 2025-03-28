package services;

import baseService.BaseService;
import io.restassured.response.Response;
import models.request.ForgotPasswordRequest;
import models.request.LoginRequest;
import models.request.SignUpRequest;

public class AuthService extends BaseService {
    private static final String BASE_PATH = "/api/auth/";

    public Response login(LoginRequest payload){
        return postRequest(payload, BASE_PATH + "login");
    }

    public Response forgotPassword(ForgotPasswordRequest payload){
        return postRequest(payload, BASE_PATH + "forgot-password");
    }

    public Response signUp(SignUpRequest payload){
        return postRequest(payload, BASE_PATH + "signup");
    }
}
