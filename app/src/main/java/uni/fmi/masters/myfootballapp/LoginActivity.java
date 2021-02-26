package uni.fmi.masters.myfootballapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uni.fmi.masters.myfootballapp.http.ApiInterface;
import uni.fmi.masters.myfootballapp.http.HttpClient;
import uni.fmi.masters.myfootballapp.http.SessionManager;
import uni.fmi.masters.myfootballapp.models.Countries;
import uni.fmi.masters.myfootballapp.models.CountriesResponse;
import uni.fmi.masters.myfootballapp.models.LoginResponse;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    EditText usernameET;
    EditText passwordET;
    Button   loginB;
    TextView registerTV;

    SharedPreferences pref;
    String username;
    String password;

    SQLiteDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SessionManager sessionManager = new SessionManager(this);
        ApiInterface service = HttpClient.getTokenClient().create(ApiInterface.class);
        Call<LoginResponse> tokenRequest = service.getToken("Basic ZHFyZzZvMDM4ajc5aWhnMG1ka3BjbzZtajpscTE1dWQ4czZsYWs3NnJjbW1hdDh1ZG83NG5kYmU4NGcxa2U0bmJib3M4bWhjZTVsc3Y=","client_credentials");

        tokenRequest.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.e(TAG,response.body().getToken());
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });


        ApiInterface mainService = HttpClient.getClient(this).create(ApiInterface.class);
        Call<CountriesResponse> countriesRequest = mainService.getCountries();

        countriesRequest.enqueue(new Callback<CountriesResponse>() {
            @Override
            public void onResponse(Call<CountriesResponse> call, Response<CountriesResponse> response) {

                Log.e(TAG, response.body().getData().size() + "");

                for (Countries country:response.body().getData()) {
                    Log.e(TAG, country.getName());
                }
            }

            @Override
            public void onFailure(Call<CountriesResponse> call, Throwable t) {

            }
        });



        dbHelper = new SQLiteDatabaseHelper(this);

        usernameET = findViewById(R.id.usernameEditText);
        passwordET = findViewById(R.id.passwordEditText);
        loginB     = findViewById(R.id.loginButton);
        registerTV = findViewById(R.id.registerEditText);

        loginB.setOnClickListener(onClick);
        registerTV.setOnClickListener(onClick);

        //pref = getApplicationContext().getSharedPreferences(RegisterActivity.SHARED_PREF_NAME, MODE_PRIVATE);
        //username = pref.getString(RegisterActivity.SHARED_PREF_USERNAME, "goshko");
        //password = pref.getString(RegisterActivity.SHARED_PREF_PASSWORD, "1111");
    }

    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.loginButton) {
                //goshko // 1111
                String usernameInput = usernameET.getText().toString();
                String passwordInput = passwordET.getText().toString();

                if(dbHelper.login(usernameInput, passwordInput)){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("user", usernameInput);

                    startActivity(intent);

                }else {
                    Toast.makeText(getApplicationContext(), "Incorrect Login Credentials", Toast.LENGTH_LONG).show();

                }
            }else {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        }
    };
}