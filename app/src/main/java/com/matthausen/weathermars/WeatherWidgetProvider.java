package com.matthausen.weathermars;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;
import com.androdocs.httprequest.HttpRequest;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class WeatherWidgetProvider extends AppWidgetProvider {

    private static final String TAG = "Hello Widget";
    String API_ENDPOINT = "https://api.nasa.gov/insight_weather/?api_key=DEMO_KEY&feedtype=json&ver=1.0";
    String temperature = new weatherTask().execute().get();
    Integer widgetId;

    public WeatherWidgetProvider() throws ExecutionException, InterruptedException {
    }

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        new weatherTask().execute();
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final int N = appWidgetIds.length;

        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];

            widgetId = appWidgetId;

            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.mars_appwidget);
            views.setOnClickPendingIntent(R.id.weather_station, pendingIntent);

            views.setTextViewText(R.id.temp, temperature);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    class weatherTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
        }

        protected String doInBackground(String... strings) {
            String response = HttpRequest.excuteGet(API_ENDPOINT);
            try {
                JSONObject jsonObj = new JSONObject(response);
                ArrayList<WeatherObject> weather = new ArrayList<>();

                List<String> sol_days = new ArrayList<>();
                for(int i = 0; i < jsonObj.getJSONArray("sol_keys").length(); i++){
                    sol_days.add(jsonObj.getJSONArray("sol_keys").getString(i));
                }

                for(int i = 0; i < sol_days.size(); i++){
                    String day = sol_days.get(i);

                    String season = jsonObj.getJSONObject(day).getString("Season");
                    String sol = day;
                    Double avgTemp = jsonObj.getJSONObject(day).getJSONObject("AT").getDouble("av");
                    Double minTemp = jsonObj.getJSONObject(day).getJSONObject("AT").getDouble("mn");
                    Double maxTemp = jsonObj.getJSONObject(day).getJSONObject("AT").getDouble("mx");
                    String windDir;

                    try {
                        windDir = jsonObj.getJSONObject(sol_days.get(i)).getJSONObject("WD").getJSONObject("most_common").getString("compass_point");
                    }catch(JSONException e) {
                        windDir = "NA";
                    }

                    weather.add(new WeatherObject(season, sol, avgTemp, minTemp, maxTemp, windDir));

                    if(day == sol_days.get(sol_days.size() - 1)){
                        int current_min = (int) jsonObj.getJSONObject(day).getJSONObject("AT").getDouble("mn");
                        int current_max = (int) jsonObj.getJSONObject(day).getJSONObject("AT").getDouble("mx");
                        temperature = current_max + " °C | " + current_min + " °C";
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return temperature;
        }

        @Override
        protected void onPostExecute(String result) {
        }
    }
}