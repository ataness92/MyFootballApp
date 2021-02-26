package uni.fmi.masters.myfootballapp.http;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static String TAG = SessionManager.class.getSimpleName();
    private static int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "AppPref";
    private static final String KEY_ACCESS_TOKEN="accessToken";

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();

    }

    public String getAccessToken(){
        return pref.getString(KEY_ACCESS_TOKEN,"");
    }
    public void saveAccessToken(String token){
        editor.putString(KEY_ACCESS_TOKEN,token);
        editor.commit();
    }
}
