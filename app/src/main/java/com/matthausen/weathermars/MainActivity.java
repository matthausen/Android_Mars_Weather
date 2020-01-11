package com.matthausen.weathermars;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.androdocs.httprequest.HttpRequest;
import com.darwindeveloper.horizontalscrollmenulibrary.custom_views.HorizontalScrollMenuView;
import com.darwindeveloper.horizontalscrollmenulibrary.extras.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    String API_ENDPOINT = "https://api.nasa.gov/insight_weather/?api_key=DEMO_KEY&feedtype=json&ver=1.0";

    HorizontalScrollMenuView forecast;

    TextView avgtemperatureTxt;
    TextView mintemperatureTxt;
    TextView maxtemperatureTxt;
    TextView solTxt;
    TextView seasonTxt;


    Double avgTemp;
    Double minTemp;
    Double maxTemp;
    Double minTemp_1_ago;
    Double maxTemp_1_ago;
    Double minTemp_2_ago;
    Double maxTemp_2_ago;
    Double minTemp_3_ago;
    Double maxTemp_3_ago;
    Double minTemp_4_ago;
    Double maxTemp_4_ago;
    Double minTemp_5_ago;
    Double maxTemp_5_ago;
    Double minTemp_6_ago;
    Double maxTemp_6_ago;

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
        forecast = findViewById(R.id.forecast);
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
                String sol_key_1_ago = jsonObj.getJSONArray("sol_keys")
                        .getString(5);
                String sol_key_2_ago = jsonObj.getJSONArray("sol_keys")
                        .getString(4);
                String sol_key_3_ago = jsonObj.getJSONArray("sol_keys")
                        .getString(3);
                String sol_key_4_ago = jsonObj.getJSONArray("sol_keys")
                        .getString(2);
                String sol_key_5_ago = jsonObj.getJSONArray("sol_keys")
                        .getString(1);
                String sol_key_6_ago = jsonObj.getJSONArray("sol_keys")
                        .getString(0);

                String season = jsonObj.getJSONObject(sol_key)
                        .getString("Season");

                avgTemp = jsonObj.getJSONObject(sol_key)
                        .getJSONObject("AT")
                        .getDouble("av");

                minTemp = jsonObj.getJSONObject(sol_key)
                        .getJSONObject("AT")
                        .getDouble("mn");

                maxTemp = jsonObj.getJSONObject(sol_key)
                        .getJSONObject("AT")
                        .getDouble("mx");

                minTemp_1_ago = jsonObj.getJSONObject(sol_key_1_ago)
                        .getJSONObject("AT")
                        .getDouble("mn");

                maxTemp_1_ago = jsonObj.getJSONObject(sol_key_1_ago)
                        .getJSONObject("AT")
                        .getDouble("mx");
                minTemp_2_ago = jsonObj.getJSONObject(sol_key_2_ago)
                        .getJSONObject("AT")
                        .getDouble("mn");
                maxTemp_2_ago = jsonObj.getJSONObject(sol_key_2_ago)
                        .getJSONObject("AT")
                        .getDouble("mx");
                minTemp_3_ago = jsonObj.getJSONObject(sol_key_3_ago)
                        .getJSONObject("AT")
                        .getDouble("mn");
                maxTemp_3_ago = jsonObj.getJSONObject(sol_key_3_ago)
                        .getJSONObject("AT")
                        .getDouble("mx");
                minTemp_4_ago = jsonObj.getJSONObject(sol_key_4_ago)
                        .getJSONObject("AT")
                        .getDouble("mn");
                maxTemp_4_ago = jsonObj.getJSONObject(sol_key_4_ago)
                        .getJSONObject("AT")
                        .getDouble("mx");
                minTemp_5_ago = jsonObj.getJSONObject(sol_key_5_ago)
                        .getJSONObject("AT")
                        .getDouble("mn");
                maxTemp_5_ago = jsonObj.getJSONObject(sol_key_5_ago)
                        .getJSONObject("AT")
                        .getDouble("mx");
                minTemp_6_ago = jsonObj.getJSONObject(sol_key_6_ago)
                        .getJSONObject("AT")
                        .getDouble("mn");
                maxTemp_6_ago = jsonObj.getJSONObject(sol_key_6_ago)
                        .getJSONObject("AT")
                        .getDouble("mx");

                avgtemperatureTxt.setText(Math.round(avgTemp) + " °C");
                mintemperatureTxt.setText("Min: " + Math.round(minTemp)+ " °C");
                maxtemperatureTxt.setText("Max: " + Math.round(maxTemp)+ " °C");
                solTxt.setText("Sol: " + sol_key);
                seasonTxt.setText("Season: " + season);

                initForecast();

            } catch (JSONException e) {
                Toast.makeText(MainActivity.this, "Could not get a response from NASA: " + e, Toast.LENGTH_LONG).show();
            }
        }

        private void initForecast() {

            forecast.addItem("Min: " + Math.round(minTemp_1_ago) + " | Max: " + Math.round(maxTemp_1_ago), R.drawable.ic_sun);
            forecast.addItem("Min: " + Math.round(minTemp_2_ago) + " | Max: " + Math.round(maxTemp_2_ago), R.drawable.ic_sun);
            forecast.addItem("Min: " + Math.round(minTemp_3_ago) + " | Max: " + Math.round(maxTemp_3_ago), R.drawable.ic_sun);
            forecast.addItem("Min: " + Math.round(minTemp_4_ago) + " | Max: " + Math.round(maxTemp_4_ago), R.drawable.ic_sun);
            forecast.addItem("Min: " + Math.round(minTemp_5_ago) + " | Max: " + Math.round(maxTemp_5_ago), R.drawable.ic_sun);
            forecast.addItem("Min: " + Math.round(minTemp_6_ago) + " | Max: " + Math.round(maxTemp_6_ago), R.drawable.ic_sun);

            forecast.showItems();

            forecast.setOnHSMenuClickListener(new HorizontalScrollMenuView.OnHSMenuClickListener() {
                @Override
                public void onHSMClick(MenuItem menuItem, int position) {
                    Toast.makeText(MainActivity.this, "Weather available", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
