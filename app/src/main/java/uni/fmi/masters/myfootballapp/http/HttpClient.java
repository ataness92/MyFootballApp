package uni.fmi.masters.myfootballapp.http;

import android.content.Context;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClient {

    private static final String TOKEN_BASE_URL = "https://oauth2.elenasport.io/";
    private static final String BASE_URL = "https://football.elenasport.io/";

    private static Retrofit tokenClient = null;
    private static Retrofit retrofit = null;

    public static Retrofit getTokenClient(){

        if(tokenClient == null ){

            tokenClient = new Retrofit.Builder()
                    .baseUrl(TOKEN_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return tokenClient;
    }

    public static Retrofit getClient(Context context){

        if(retrofit==null){

            final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            TokenInterceptor tokenInterceptor = new TokenInterceptor(context);
            httpClient.addInterceptor(tokenInterceptor);
//            TokenAuthenticator auth = new TokenAuthenticator(context);
//            httpClient.authenticator(auth);
            retrofit=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }

        return retrofit;

    }


}
