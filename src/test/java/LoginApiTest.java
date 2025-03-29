import io.restassured.response.Response;
import models.request.ForgotPasswordRequest;
import models.request.LoginRequest;
import models.request.SignUpRequest;
import models.response.ForgotPasswordResponse;
import models.response.LoginResponse;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import services.AuthService;
@Listeners(listeners.TestListeners.class)
public class LoginApiTest {

    @Test(description = "Verify Login API")
    public void verifyLogin(){
        LoginRequest loginRequest = new LoginRequest("chethan","chethan1234");

        AuthService authService = new AuthService();
        Response response = authService.login(loginRequest);
        LoginResponse loginResponse = response.as(LoginResponse.class);
        System.out.println(loginResponse.getToken());
        //System.out.println(response.asPrettyString());
        Assert.assertNotNull(loginResponse.getToken());
        Assert.assertEquals(loginResponse.getEmail(), "chethan.bidare12@gmail.com");
        Assert.assertTrue(loginResponse.getRoles().contains("ROLE_USER"));

    }

    @Test
    public void verifyForgotPasswordValid(){
        ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest("chethan.bidare123@gmail.com");
        AuthService authService = new AuthService();
        Response response = authService.forgotPassword(forgotPasswordRequest);
        ForgotPasswordResponse forgotPasswordResponse = response.as(ForgotPasswordResponse.class);
        System.out.println(forgotPasswordResponse.getMessage());
        Assert.assertEquals(forgotPasswordResponse.getMessage(), "If your email exists in our system, you will receive reset instructions.");
    }

    @Test
    public void verifySignup(){
        SignUpRequest signUpRequest =new SignUpRequest.Builder().username("chethan")
                .email("chethan.bidare1234@gmail.com")
                .firstName("chethan")
                .lastName("bidara")
                .mobileNumber("9110652799")
                .password("test123")
                .build();

        AuthService authService = new AuthService();
        Response response = authService.signUp(signUpRequest);
        Assert.assertEquals(response.asPrettyString(), "Error: Username is already taken!");
    }
}
