package uni.fmi.masters.myfootballapp.http;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import uni.fmi.masters.myfootballapp.models.CountriesResponse;
import uni.fmi.masters.myfootballapp.models.LoginResponse;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("oauth2/token")
    Call<LoginResponse> getToken(@Header("Authorization") String token, @Field("grant_type") String type);

    @GET("/v2/countries")
    Call<CountriesResponse> getCountries();

}
