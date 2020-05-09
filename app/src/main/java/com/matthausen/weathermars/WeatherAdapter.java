package com.matthausen.weathermars;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {
    private static final String TAG = "Weather Adapter";
    private Context mContext;
    private ArrayList<WeatherObject> mDataset;

    public static class WeatherViewHolder extends RecyclerView.ViewHolder {
        public ImageView solImage;
        public TextView sol;
        public TextView minTemp;
        public TextView maxTemp;
        public ImageView wind;
        public TextView windDirection;
        public ConstraintLayout parentLayout;

        public WeatherViewHolder(View v) {
            super(v);
            parentLayout = v.findViewById(R.id.parent_layout);
            sol = v.findViewById(R.id.sol);
            solImage = v.findViewById(R.id.weather_icon);
            minTemp = v.findViewById(R.id.min);
            maxTemp = v.findViewById(R.id.max);
            wind = v.findViewById(R.id.weather_wind);
            windDirection = v.findViewById(R.id.wind);
        }
    }

    public WeatherAdapter(Context context, ArrayList<WeatherObject> productList) {
        mContext = context;
        mDataset = productList;
    }

    @Override
    public WeatherAdapter.WeatherViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_weather_view, parent, false);

        WeatherViewHolder vh = new WeatherViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherAdapter.WeatherViewHolder holder, int position) {
        final WeatherObject currentWeather = mDataset.get(position);

        int minTemp = (int) Math.round(currentWeather.getMinTemp());
        int maxTemp = (int) Math.round(currentWeather.getMaxTemp());

        holder.sol.setText(currentWeather.getSol());
        holder.minTemp.setText(minTemp + " °C");
        holder.maxTemp.setText(maxTemp + " °C");
        holder.windDirection.setText(currentWeather.getWindDir());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
