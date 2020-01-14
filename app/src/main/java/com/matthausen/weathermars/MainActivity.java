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
    TextView minMaxTxt;
    TextView solTxt;
    TextView seasonTxt;
    TextView windTxt;

    String sol_key;
    String sol_key_1_ago;
    String sol_key_2_ago;
    String sol_key_3_ago;
    String sol_key_4_ago;
    String sol_key_5_ago;
    String sol_key_6_ago;

    String season;
    String wind;


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
        minMaxTxt = findViewById(R.id.minMaxTemp);
        solTxt = findViewById(R.id.sol);
        seasonTxt = findViewById(R.id.season);
        windTxt = findViewById(R.id.wind);

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

                sol_key = jsonObj.getJSONArray("sol_keys")
                        .getString(6);
                sol_key_1_ago = jsonObj.getJSONArray("sol_keys")
                        .getString(5);
                sol_key_2_ago = jsonObj.getJSONArray("sol_keys")
                        .getString(4);
                sol_key_3_ago = jsonObj.getJSONArray("sol_keys")
                        .getString(3);
                sol_key_4_ago = jsonObj.getJSONArray("sol_keys")
                        .getString(2);
                sol_key_5_ago = jsonObj.getJSONArray("sol_keys")
                        .getString(1);
                sol_key_6_ago = jsonObj.getJSONArray("sol_keys")
                        .getString(0);

                season = jsonObj.getJSONObject(sol_key)
                        .getString("Season");
                try {
                    wind = jsonObj.getJSONObject(sol_key)
                            .getJSONObject("WD")
                            .getJSONObject("most_common")
                            .getString("compass_point");
                } catch(JSONException e){
                    Toast.makeText(MainActivity.this, "No wind data available yet for today", Toast.LENGTH_LONG).show();
                }


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
                minMaxTxt.setText("Min: " + Math.round(minTemp)+ " °C\nMax: " + Math.round(maxTemp)+ " °C");
                solTxt.setText("Solar day: " + sol_key);
                seasonTxt.setText("Season: " + season);

                if(wind != null) {
                    windTxt.setText("Wind direction: " + wind);
                }

                initForecast();

            } catch (JSONException e) {
                Toast.makeText(MainActivity.this, "Could not get a response from NASA: " + e, Toast.LENGTH_LONG).show();
            }
        }

        private void initForecast() {

            forecast.addItem("Solar Day: " + sol_key_1_ago + "\n\n Min: " + Math.round(minTemp_1_ago) + "°C\n Max: " + Math.round(maxTemp_1_ago) + "°C", R.drawable.ic_solar_day);
            forecast.addItem("Solar Day: " + sol_key_2_ago + "\n\n Min: " + Math.round(minTemp_2_ago) + "°C\n Max: " + Math.round(maxTemp_2_ago) + "°C", R.drawable.ic_solar_day);
            forecast.addItem("Solar Day: " + sol_key_3_ago + "\n\n Min: " + Math.round(minTemp_3_ago) + "°C\n Max: " + Math.round(maxTemp_3_ago) + "°C", R.drawable.ic_solar_day);
            forecast.addItem("Solar Day: " + sol_key_4_ago + "\n\n Min: " + Math.round(minTemp_4_ago) + "°C\n Max: " + Math.round(maxTemp_4_ago) + "°C", R.drawable.ic_solar_day);
            forecast.addItem("Solar Day: " + sol_key_5_ago + "\n\n Min: " + Math.round(minTemp_5_ago) + "°C\n Max: " + Math.round(maxTemp_5_ago) + "°C", R.drawable.ic_solar_day);
            forecast.addItem("Solar Day: " + sol_key_6_ago + "\n\n Min: " + Math.round(minTemp_6_ago) + "°C\n Max: " + Math.round(maxTemp_6_ago) + "°C", R.drawable.ic_solar_day);

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
