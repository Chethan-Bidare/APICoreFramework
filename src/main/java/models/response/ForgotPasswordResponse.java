package models.response;

public class ForgotPasswordResponse {
    String message;

    @Override
    public String toString() {
        return "ForgotPasswordResponse{" +
                "message='" + message + '\'' +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ForgotPasswordResponse(String message) {
        this.message = message;
    }

    public ForgotPasswordResponse(){

    }
}
