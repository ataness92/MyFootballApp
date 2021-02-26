package uni.fmi.masters.myfootballapp.models;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("access_token")
    private String token;
    @SerializedName("expires_in")
    private int expires;
    @SerializedName("token_type")
    private String type;

    public LoginResponse(String token, int expires, String type) {
        this.token = token;
        this.expires = expires;
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getExpires() {
        return expires;
    }

    public void setExpires(int expires) {
        this.expires = expires;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
