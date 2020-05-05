package com.example.uscensusfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    private TextView estimatedPopulation;
    private RequestQueue mQueue;
    private String year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.go);
        mQueue = Volley.newRequestQueue(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonParse();
            }
        });
    }
    private void jsonParse() {
        EditText entry = findViewById(R.id.yearEntry);
        estimatedPopulation = findViewById(R.id.population);
        this.year = entry.getText().toString();
        System.out.println(this.year);
        String url = "api.census.gov/data/2014/pep/projpop?get=POP,YEAR&for=us:1&key=2c993c25d8af778233084ccd91edf018f42ded83";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < 48; i++) {
                            try {
                                if (response.getJSONArray(i).getString(1) == year) {
                                    estimatedPopulation.setText(response.getJSONArray(i).getString(0));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                estimatedPopulation.setText("Invalid Year");
            }
        });
        mQueue.add(request);
    }
}
