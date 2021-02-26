package uni.fmi.masters.myfootballapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    static final int TAKE_PICTURE = 666;
    static final String SHARED_PREF_USERNAME = "username";
    static final String SHARED_PREF_PASSWORD = "password";
    static final String SHARED_PREF_NAME = "SecretData";

    SQLiteDatabaseHelper dbHelper;


    SharedPreferences pref;
    SharedPreferences.Editor editor;

    ImageView  avatarIM;
    EditText   usernameET;
    EditText   passwordET;
    EditText   repeatPasswordET;
    EditText   firstNameET;
    EditText   lastNameET;
    RadioGroup genderRG;
    EditText   otherET;
    Button     registerB;
    Button     cancelB;
    String     gender;
    private RadioGroup.OnCheckedChangeListener onCheckedChanged = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            if(checkedId == R.id.otherButton) {
                otherET.setEnabled(true);
                gender = null;
            }else {
                RadioButton rb = findViewById(checkedId);
                gender = rb.getText().toString();
                otherET.setText("");
                otherET.setEnabled(false);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new SQLiteDatabaseHelper(this);

        avatarIM = findViewById(R.id.imageView4);
        usernameET = findViewById(R.id.usernameRegisterText);
        passwordET = findViewById(R.id.passwordRegisterText);
        repeatPasswordET = findViewById(R.id.repeatPasswordRegisterText);
        firstNameET = findViewById(R.id.firstNameRegisterText);
        lastNameET = findViewById(R.id.lastNameRegisterText);
        genderRG = findViewById(R.id.radioGroup);
        otherET = findViewById(R.id.genderRegisterText);
        registerB = findViewById(R.id.registerButton);
        cancelB = findViewById(R.id.cancelButton);

        otherET.setEnabled(false);

        avatarIM.setOnLongClickListener(onLongClick);

        pref = getApplicationContext().getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        editor = pref.edit();

        registerB.setOnClickListener(onClick);
        cancelB.setOnClickListener(onClick);

        genderRG.setOnCheckedChangeListener(onCheckedChanged);
    }



    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(v.getId() == R.id.registerButton) {
                if(usernameET.getText().length() > 0
                        && passwordET.getText().length()>0
                        && passwordET.getText().toString().equals(repeatPasswordET.getText().toString())){

                    //editor.putString(SHARED_PREF_USERNAME, usernameET.getText().toString());
                    //editor.putString(SHARED_PREF_PASSWORD, passwordET.getText().toString());
                    //editor.commit();

                    String username = usernameET.getText().toString();
                    String password = passwordET.getText().toString();
                    String firstName= firstNameET.getText().toString();
                    String lastName= firstNameET.getText().toString();


                    if(gender == null && otherET.getText().length() > 0) {
                        gender = otherET.getText().toString();
                    }
                    User user = new User();
                    user.setUsername(username);
                    user.setPassword(password);
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    user.setGender(gender);

                    if(!dbHelper.registerUser(user)) {
                        Toast.makeText(RegisterActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        return;
                    }


                } else {
                    Toast.makeText(RegisterActivity.this, "Please provide all the necessary information!", Toast.LENGTH_LONG).show();
                    return;
                }
            }

            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    };

    View.OnLongClickListener onLongClick = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {

            Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(pictureIntent, TAKE_PICTURE);
            return true;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == TAKE_PICTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap avatarImage = (Bitmap)extras.get("data");

            avatarIM.setImageBitmap(avatarImage);
        }


    }
}