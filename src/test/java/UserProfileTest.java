import io.restassured.response.Response;
import models.request.LoginRequest;
import models.request.UserProfileRequest;
import models.response.LoginResponse;
import models.response.UserProfileResponse;
import org.testng.Assert;
import org.testng.annotations.Test;
import services.AuthService;
import services.UserProfileService;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class UserProfileTest {

    @Test
    public void getUserProfile(){
        AuthService authService = new AuthService();
        LoginRequest loginRequest = new LoginRequest("chethan", "chethan1234");
        Response response = authService.login(loginRequest);
        LoginResponse loginResponse = response.as(LoginResponse.class);
        UserProfileService userProfileService = new UserProfileService();
        response = userProfileService.getProfile(loginResponse.getToken());
        System.out.println(response.asPrettyString());
        UserProfileResponse userProfileResponse = response.as(UserProfileResponse.class);
        Assert.assertEquals(userProfileResponse.getId(),"537");
        Assert.assertEquals(userProfileResponse.getUsername(),"Chethan");

        //JsonSchemaValidation
        response.then().assertThat().body(matchesJsonSchemaInClasspath("user-schema.json"));
    }

    @Test
    public void putUserProfile(){
        AuthService authService = new AuthService();
        LoginRequest loginRequest = new LoginRequest("chethan", "chethan1234");
        Response response = authService.login(loginRequest);
        LoginResponse loginResponse = response.as(LoginResponse.class);
        UserProfileService userProfileService = new UserProfileService();

        UserProfileRequest userProfileRequest = new UserProfileRequest.Builder()
                .firstName("Chethan")
                .lastName("Bidare")
                .email("chethan.bidare12@gmail.com")
                .mobileNumber("9110642779").build();
        response = userProfileService.putProfile(userProfileRequest, loginResponse.getToken());
        System.out.println(response.asPrettyString());
        UserProfileResponse userProfileResponse = response.as(UserProfileResponse.class);
        Assert.assertEquals(userProfileResponse.getId(),"537");
        Assert.assertEquals(userProfileResponse.getMobileNumber(),"9110642779");

    }
}
