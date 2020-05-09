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

    TextView current_sol;
    TextView current_min_temp;
    TextView current_max_temp;
    TextView current_season;

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

        current_sol = findViewById(R.id.current_sol);
        current_min_temp = findViewById(R.id.current_min_temp);
        current_max_temp = findViewById(R.id.current_max_temp);
        current_season = findViewById(R.id.current_season);

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
                    String day = sol_days.get(i);

                    String season = response.getJSONObject(day).getString("Season");
                    String sol = day;
                    Double avgTemp = response.getJSONObject(day).getJSONObject("AT").getDouble("av");
                    Double minTemp = response.getJSONObject(day).getJSONObject("AT").getDouble("mn");
                    Double maxTemp = response.getJSONObject(day).getJSONObject("AT").getDouble("mx");
                    String windDir;

                    try {
                        windDir = response.getJSONObject(sol_days.get(i)).getJSONObject("WD").getJSONObject("most_common").getString("compass_point");
                    }catch(JSONException e) {
                        windDir = "NA";
                    }

                    weather.add(new WeatherObject(season, sol, avgTemp, minTemp, maxTemp, windDir));

                    if(day == sol_days.get(sol_days.size() - 1)){
                        int current_min = (int) response.getJSONObject(day).getJSONObject("AT").getDouble("mn");
                        int current_max = (int) response.getJSONObject(day).getJSONObject("AT").getDouble("mx");
                        current_sol.setText("Sol: " + day);
                        current_min_temp.setText("Min: " + current_min + " °C");
                        current_max_temp.setText("Max: " + current_max + " °C");
                        current_season.setText("Season: " + response.getJSONObject(day).getString("Season"));
                    }
                }

                if (weather.size() > 0) {
                    mprocess.updateAdapter(weather);
                }else {
                    Log.d(TAG, "No weather data found");
                }

            } catch (JSONException e) {
                Toast.makeText(MainActivity.this, "Could not get a response from NASA: ", Toast.LENGTH_LONG).show();
            }
        }
    }
}
