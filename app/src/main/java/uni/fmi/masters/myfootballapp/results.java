package uni.fmi.masters.myfootballapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class results extends AppCompatActivity {

    private LinearLayout parentLinearLayout;

    String homeTeam = "Manchester United";
    String awayTeam = "Liverpool";
    String result   = "0:0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        parentLinearLayout=(LinearLayout) findViewById(R.id.parent_linear_layout);

        //createTextView();

        createRequest();

    }

    private void createRequest() {
        //HTTP Post
        //TextView eventText = new TextView(results.this);
        //eventText.setText(homeTeam + " vs " + awayTeam + " " + result);
        //parentLinearLayout.addView(eventText, parentLinearLayout.getChildCount()-1);

        String url = "https://oauth2.elenasport.io/oauth2/token";

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Content-Type", "application/x-www-form-urlencoded");
            jsonObject.put("grant_type", "client_credentials");
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                @Override

                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("access_token");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // do something...
                    Toast.makeText(results.this, "ERRRROR", Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    final Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", "Basic ZHFyZzZvMDM4ajc5aWhnMG1ka3BjbzZtajpscTE1dWQ4czZsYWs3NnJjbW1hdDh1ZG83NG5kYmU4NGcxa2U0bmJib3M4bWhjZTVsc3Y");
                    return headers;
                }
            };
            requestQueue.add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void createTextView() {
            TextView eventText = new TextView(results.this);
            eventText.setText(homeTeam + " vs " + awayTeam + " " + result);
            parentLinearLayout.addView(eventText, parentLinearLayout.getChildCount()-1);
    }

    //Unirest.setTimeouts(0, 0);
    //HttpResponse<String> response = Unirest.post("https://oauth2.elenasport.io/oauth2/token")
     //       .header("Authorization", "Basic ZHFyZzZvMDM4ajc5aWhnMG1ka3BjbzZtajpscTE1dWQ4czZsYWs3NnJjbW1hdDh1ZG83NG5kYmU4NGcxa2U0bmJib3M4bWhjZTVsc3Y=")
     //       .header("Content-Type", "application/x-www-form-urlencoded")
     //       .header("Cookie", "XSRF-TOKEN=46ab1f06-c093-4fd0-acb2-e6b13c6db947")
     //       .field("grant_type", "client_credentials")
     //       .asString();

}