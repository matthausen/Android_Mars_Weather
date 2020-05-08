package com.matthausen.weathermars;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.androdocs.httprequest.HttpRequest;
import com.darwindeveloper.horizontalscrollmenulibrary.custom_views.HorizontalScrollMenuView;
import com.darwindeveloper.horizontalscrollmenulibrary.extras.MenuItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main Activity";
    private Context context;
    private IProcess mprocess;

    String API_ENDPOINT = "https://api.nasa.gov/insight_weather/?api_key=DEMO_KEY&feedtype=json&ver=1.0";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

//    TextView avgtemperatureTxt;
//    TextView minMaxTxt;
//    TextView solTxt;
//    TextView seasonTxt;
//    TextView windTxt;
//
//    String sol_key;
//    String sol_key_1_ago;
//    String sol_key_2_ago;
//    String sol_key_3_ago;
//    String sol_key_4_ago;
//    String sol_key_5_ago;
//    String sol_key_6_ago;
//
//    String season;
//    String wind;
//
//
//    Double avgTemp;
//    Double minTemp;
//    Double maxTemp;
//    Double minTemp_1_ago;
//    Double maxTemp_1_ago;
//    Double minTemp_2_ago;
//    Double maxTemp_2_ago;
//    Double minTemp_3_ago;
//    Double maxTemp_3_ago;
//    Double minTemp_4_ago;
//    Double maxTemp_4_ago;
//    Double minTemp_5_ago;
//    Double maxTemp_5_ago;
//    Double minTemp_6_ago;
//    Double maxTemp_6_ago;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        context = this;

        ArrayList<WeatherObject> myDataset = new ArrayList<>();
        mprocess = new IProcess() {
            @Override
            public void updateAdapter(ArrayList<WeatherObject> result) {
                recyclerView.setAdapter(new WeatherAdapter(context, result));
                mAdapter.notifyDataSetChanged();
            }
        };

        mAdapter = new WeatherAdapter(this, myDataset);
        recyclerView.setAdapter(mAdapter);

//        avgtemperatureTxt = findViewById(R.id.average);
//        minMaxTxt = findViewById(R.id.minMaxTemp);
//        solTxt = findViewById(R.id.sol);
//        seasonTxt = findViewById(R.id.season);
//        windTxt = findViewById(R.id.wind);

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
                JSONObject response = new JSONObject(result);
                ArrayList<WeatherObject> weather = new ArrayList<>();

                List<String> sol_days = new ArrayList<>();
                for(int i = 0; i < response.getJSONArray("sol_keys").length(); i++){
                    sol_days.add(response.getJSONArray("sol_keys").getString(i));
                }

                for(int i = 0; i < sol_days.size(); i++){
//                    String season = response.getJSONObject(sol_days.get(i)).getString("season");
//                    String sol = sol_days.get(i);
//                    Double avgTemp = response.getJSONObject(sol_days.get(i)).getJSONObject("AT").getDouble("av");
//                    Double minTemp = response.getJSONObject(sol_days.get(i)).getJSONObject("AT").getDouble("mn");
//                    Double maxTemp = response.getJSONObject(sol_days.get(i)).getJSONObject("AT").getDouble("mx");
//                    String windDir;
//
//                    try {
//                        windDir = response.getJSONObject(sol_days.get(i)).getJSONObject("WD").getJSONObject("most_common").getString("compass_point");
//                    }catch(JSONException e) {
//                        windDir = "NA";
//                    }
//
//                    weather.add(new WeatherObject(season, sol, avgTemp, minTemp, maxTemp, windDir));
                }

                if (weather.size() > 0) {
                    mprocess.updateAdapter(weather);
                }else {
                    Log.d(TAG, "No weather data found");
                }


//                sol_key = jsonObj.getJSONArray("sol_keys")
//                        .getString(6);
//                sol_key_1_ago = jsonObj.getJSONArray("sol_keys")
//                        .getString(5);
//                sol_key_2_ago = jsonObj.getJSONArray("sol_keys")
//                        .getString(4);
//                sol_key_3_ago = jsonObj.getJSONArray("sol_keys")
//                        .getString(3);
//                sol_key_4_ago = jsonObj.getJSONArray("sol_keys")
//                        .getString(2);
//                sol_key_5_ago = jsonObj.getJSONArray("sol_keys")
//                        .getString(1);
//                sol_key_6_ago = jsonObj.getJSONArray("sol_keys")
//                        .getString(0);
//
//                season = jsonObj.getJSONObject(sol_key)
//                        .getString("Season");
//                try {
//                    wind = jsonObj.getJSONObject(sol_key)
//                            .getJSONObject("WD")
//                            .getJSONObject("most_common")
//                            .getString("compass_point");
//                } catch(JSONException e){
//                    Toast.makeText(MainActivity.this, "No wind data available yet for today", Toast.LENGTH_LONG).show();
//                }
//
//
//                avgTemp = jsonObj.getJSONObject(sol_key)
//                        .getJSONObject("AT")
//                        .getDouble("av");
//
//                minTemp = jsonObj.getJSONObject(sol_key)
//                        .getJSONObject("AT")
//                        .getDouble("mn");
//
//                maxTemp = jsonObj.getJSONObject(sol_key)
//                        .getJSONObject("AT")
//                        .getDouble("mx");
//
//                minTemp_1_ago = jsonObj.getJSONObject(sol_key_1_ago)
//                        .getJSONObject("AT")
//                        .getDouble("mn");
//
//                maxTemp_1_ago = jsonObj.getJSONObject(sol_key_1_ago)
//                        .getJSONObject("AT")
//                        .getDouble("mx");
//                minTemp_2_ago = jsonObj.getJSONObject(sol_key_2_ago)
//                        .getJSONObject("AT")
//                        .getDouble("mn");
//                maxTemp_2_ago = jsonObj.getJSONObject(sol_key_2_ago)
//                        .getJSONObject("AT")
//                        .getDouble("mx");
//                minTemp_3_ago = jsonObj.getJSONObject(sol_key_3_ago)
//                        .getJSONObject("AT")
//                        .getDouble("mn");
//                maxTemp_3_ago = jsonObj.getJSONObject(sol_key_3_ago)
//                        .getJSONObject("AT")
//                        .getDouble("mx");
//                minTemp_4_ago = jsonObj.getJSONObject(sol_key_4_ago)
//                        .getJSONObject("AT")
//                        .getDouble("mn");
//                maxTemp_4_ago = jsonObj.getJSONObject(sol_key_4_ago)
//                        .getJSONObject("AT")
//                        .getDouble("mx");
//                minTemp_5_ago = jsonObj.getJSONObject(sol_key_5_ago)
//                        .getJSONObject("AT")
//                        .getDouble("mn");
//                maxTemp_5_ago = jsonObj.getJSONObject(sol_key_5_ago)
//                        .getJSONObject("AT")
//                        .getDouble("mx");
//                minTemp_6_ago = jsonObj.getJSONObject(sol_key_6_ago)
//                        .getJSONObject("AT")
//                        .getDouble("mn");
//                maxTemp_6_ago = jsonObj.getJSONObject(sol_key_6_ago)
//                        .getJSONObject("AT")
//                        .getDouble("mx");
//
//                avgtemperatureTxt.setText(Math.round(avgTemp) + " °C");
//                minMaxTxt.setText("Min: " + Math.round(minTemp)+ " °C\nMax: " + Math.round(maxTemp)+ " °C");
//                solTxt.setText("Solar day: " + sol_key);
//                seasonTxt.setText("Season: " + season);
//
//                if(wind != null) {
//                    windTxt.setText("Wind direction: " + wind);
//                }


            } catch (JSONException e) {
                Toast.makeText(MainActivity.this, "Could not get a response from NASA: ", Toast.LENGTH_LONG).show();
            }
        }
    }
}
