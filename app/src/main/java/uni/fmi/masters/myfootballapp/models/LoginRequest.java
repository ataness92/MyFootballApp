package uni.fmi.masters.myfootballapp.models;

public class LoginRequest {

    private String grant_type;

    public LoginRequest(String grant_type) {
        this.grant_type = grant_type;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }
}
