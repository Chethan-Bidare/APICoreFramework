import io.restassured.response.Response;
import models.request.LoginRequest;
import models.response.LoginResponse;
import org.testng.Assert;
import org.testng.annotations.Test;
import services.AuthService;
import services.ReportsService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import static org.hamcrest.Matchers.*;

public class ReportsTest {

    @Test
    public void verifyExcelReport() throws IOException {
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("accountNumber","3135072780");

        AuthService authService = new AuthService();
        LoginRequest loginRequest = new LoginRequest("chethan", "chethan1234");
        Response response = authService.login(loginRequest);
        LoginResponse loginResponse = response.as(LoginResponse.class);
        ReportsService reportsService = new ReportsService();
        response = reportsService.getExcel(loginResponse.getToken(), queryParams);
        System.out.println(response.statusCode());
        System.out.println(response.asPrettyString());
        File downloadFile = new File(System.getProperty("user.dir")+ "/src/test/resources/" +"statement.xlsx");
        FileOutputStream fos = new FileOutputStream(downloadFile);
        fos.write(response.asByteArray());

        if (downloadFile.exists()){
            System.out.println("File downloaded successfuly: "+downloadFile.getAbsolutePath());
        }else {
            System.out.println("file download failed");
        }
        Assert.assertTrue(downloadFile.length() > 0);



    }
}
