package uni.fmi.masters.myfootballapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class SQLiteDatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "hello.db";
    public static final int DB_VERSION = 1;
    public static final String MY_ERROR = "My_Error";


    //Column Table user
    public static final String TABLE_USER = "user";

    public static final String TABLE_USER_ID = "id";
    public static final String TABLE_USER_USERNAME = "username";
    public static final String TABLE_USER_PASSWORD = "password";
    public static final String TABLE_USER_FIRST_NAME = "firstName";
    public static final String TABLE_USER_LAST_NAME = "lastName";
    public static final String TABLE_USER_GENDER = "gender";
    public static final String TABLE_USER_IMAGE_PATH = "imagePath";

    //Creating table request
    public static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER +
            "('" + TABLE_USER_ID        + "' INTEGER PRIMARY KEY AUTOINCREMENT," +
            "'" + TABLE_USER_USERNAME   + "' varchar(50) NOT NULL UNIQUE," +
            "'" + TABLE_USER_PASSWORD   + "' varchar(50) NOT NULL," +
            "'" + TABLE_USER_FIRST_NAME + "' varchar(50)," +
            "'" + TABLE_USER_LAST_NAME  + "' varchar(50)," +
            "'" + TABLE_USER_GENDER     + "' varchar(30)," +
            "'" + TABLE_USER_IMAGE_PATH + "' varchar(250) DEFAULT 'avatar.jpg')";

    public SQLiteDatabaseHelper(@Nullable Context context) {


        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    public boolean registerUser(User user) {
        SQLiteDatabase db = null;
        try{
            db = getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put(TABLE_USER_USERNAME, user.getUsername());
            cv.put(TABLE_USER_PASSWORD, user.getPassword());
            cv.put(TABLE_USER_FIRST_NAME, user.getFirstName());
            cv.put(TABLE_USER_LAST_NAME, user.getLastName());
            cv.put(TABLE_USER_GENDER, user.getGender());
            cv.put(TABLE_USER_IMAGE_PATH, user.getImagePath());

            if(db.insert(TABLE_USER, null, cv) != -1) {
                return true;
            }
        }catch (SQLException e) {
            Log.wtf(MY_ERROR, e.getMessage());
        }finally {
            if(db != null) {
                db.close();
            }
        }

        return false;

    }

    public boolean login (String username, String password) {
        SQLiteDatabase db = null;
        Cursor c = null;
        try {
            db = getReadableDatabase();
            String sql = "SELECT * FROM " + TABLE_USER
                    + " WHERE " + TABLE_USER_USERNAME + " = '" + username + "'" +
                    " AND " + TABLE_USER_PASSWORD + " = '" + password + "'";

            c = db.rawQuery(sql, null);

            return c.moveToFirst();

        }catch (Exception e) {
            Log.wtf(MY_ERROR, e.getMessage());
        }finally {
            if(c != null)
                c.close();

            if(db != null)
                db.close();
        }

        return false;
    }

    public void deleteAccount(String username) {
        SQLiteDatabase db = null;

        try{
            db = getWritableDatabase();

            String where = TABLE_USER_USERNAME + "=?";
            String[] whereArgs = {username};

            db.delete(TABLE_USER, where, whereArgs);
        }catch(SQLException e) {
            Log.wtf(MY_ERROR, e.getMessage());
        }finally {
            if(db != null)
                db.close();

        }
    }
}
