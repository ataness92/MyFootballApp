package uni.fmi.masters.myfootballapp.http;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {

    private Context context;

    public TokenInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        SessionManager sessionManager = new SessionManager(context);
        Request req= chain.request();
        String accessToken = sessionManager.getAccessToken();
        Request newRequest = req.newBuilder().header("Authorization","Bearer " + accessToken).build();

        return chain.proceed(newRequest);
    }
}