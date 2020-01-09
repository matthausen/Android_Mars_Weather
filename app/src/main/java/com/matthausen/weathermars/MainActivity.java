package com.matthausen.weathermars;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.androdocs.httprequest.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    String API_ENDPOINT = "https://api.nasa.gov/insight_weather/?api_key=DEMO_KEY&feedtype=json&ver=1.0";

    TextView avgtemperatureTxt;
    TextView mintemperatureTxt;
    TextView maxtemperatureTxt;
    TextView solTxt;
    TextView seasonTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        avgtemperatureTxt = findViewById(R.id.average);
        mintemperatureTxt = findViewById(R.id.minimum);
        maxtemperatureTxt = findViewById(R.id.maximum);
        solTxt = findViewById(R.id.sol);
        seasonTxt = findViewById(R.id.season);

        new weatherTask().execute();
    }

    class weatherTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
        }

        protected String doInBackground(String... strings) {
            String response = HttpRequest.excuteGet(API_ENDPOINT);
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObj = new JSONObject(result);

                 String sol_key = jsonObj.getJSONArray("sol_keys")
                         .getString(6);


                //for(int i = 0; i<jsonObj.length(); i++){
                  //  Toast.makeText(MainActivity.this, "this" + jsonObj.names().getString(i), Toast.LENGTH_SHORT).show();
               // }

                String season = jsonObj.getJSONObject(sol_key)
                        .getString("Season");

                Double avgTemp = jsonObj.getJSONObject(sol_key)
                        .getJSONObject("AT")
                        .getDouble("av");

                Double minTemp = jsonObj.getJSONObject(sol_key)
                        .getJSONObject("AT")
                        .getDouble("mn");

                Double maxTemp = jsonObj.getJSONObject(sol_key)
                        .getJSONObject("AT")
                        .getDouble("mx");

                avgtemperatureTxt.setText(String.valueOf(avgTemp)+ " °C");
                solTxt.setText(sol_key);
                seasonTxt.setText(season);
                mintemperatureTxt.setText("Min: " + String.valueOf(Math.round(minTemp))+ " °C");
                maxtemperatureTxt.setText("Max: " + String.valueOf(Math.round(maxTemp))+ " °C");

            } catch (JSONException e) {
                Toast.makeText(MainActivity.this, "Could not get a response from NASA: " + e, Toast.LENGTH_LONG).show();
            }
        }
    }
}
