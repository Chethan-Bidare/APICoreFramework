package models.request;

public class ForgotPasswordRequest {
    String email;

    @Override
    public String toString() {
        return "ForgotPassword{" +
                "email='" + email + '\'' +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ForgotPasswordRequest(String email) {
        this.email = email;
    }
}
