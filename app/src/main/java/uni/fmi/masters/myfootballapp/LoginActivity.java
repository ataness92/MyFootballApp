package uni.fmi.masters.myfootballapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

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