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
    private EditText entry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.go);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonParse();
            }
        });
    }
    private void jsonParse() {
        entry = findViewById(R.id.yearEntry);
        estimatedPopulation = findViewById(R.id.population);
        this.year = entry.getText().toString();
        System.out.println(this.year);
        String url = "https://api.census.gov/data/2014/pep/projpop?get=POP,YEAR&for=us:1&key=2c993c25d8af778233084ccd91edf018f42ded83";
        JsonArrayRequest request = new JsonArrayRequest(url,
        new Response.Listener<org.json.JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try  {
                    if (year == null)  {
                        estimatedPopulation.setText("Invalid Input");
                    }
                    if (year.length() != 4)  {
                        estimatedPopulation.setText("Invalid Input");
                    }
                    boolean check = false;
                    for (int n = 2014; n < 2061; n++)  {
                        String date = String.valueOf(n);
                        if (year.equals(date))  {
                            check = true;
                        }
                    }
                    if (!check)  {
                        estimatedPopulation.setText("Invalid Input");
                    }  else  {
                        for (int i = 0; i < 47; i++)  {
                            JSONArray arr = response.getJSONArray(i);
                            JSONObject obj = arr.getJSONObject(1);
                            String comp = obj.toString();
                            if (comp.equals(year))  {
                                String resp = response.getJSONArray(i).getJSONObject(0).toString();
                                estimatedPopulation.setText(resp);
                            }
                        }
                    }
                }  catch(JSONException e)  {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                estimatedPopulation.setText("Unknown problem. Restart the app and try again");

            }
        });
        mQueue = Volley.newRequestQueue(this);
        mQueue.add(request);
    }
}
