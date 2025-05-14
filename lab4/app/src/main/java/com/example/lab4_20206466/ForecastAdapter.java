package com.example.lab4_20206466;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {

    private Context context;
    private List<Forecast> forecastList;

    public ForecastAdapter(Context context, List<Forecast> forecastList) {
        this.context = context;
        this.forecastList = forecastList;
    }

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_forecast, parent, false);
        return new ForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        Forecast forecast = forecastList.get(position);

        holder.dateTextView.setText("Fecha: " + forecast.getDate());
        holder.conditionTextView.setText("Condición: " + forecast.getConditionWeather());
        holder.tempTextView.setText("T. Max/Min/Avg: " +
                forecast.getMaxTempC() + "°C / " +
                forecast.getMinTempC() + "°C / " +
                forecast.getAvgTempC() + "°C");
        holder.windTextView.setText("Viento máx: " + forecast.getMaxWindSpeed() + " km/h");
        holder.rainTextView.setText("Lluvia: " + forecast.getChanceOfRain() + "%");

        // Cargamos el ícono con la libreria Glide
        Glide.with(context)
                .load("https:" + forecast.getUrlIcon())
                .into(holder.weatherImageView);
    }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }

    public static class ForecastViewHolder extends RecyclerView.ViewHolder {
        TextView dateTextView, conditionTextView, tempTextView, windTextView, rainTextView;
        ImageView weatherImageView;

        public ForecastViewHolder(View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.tvDate);
            conditionTextView = itemView.findViewById(R.id.tvCondition);
            tempTextView = itemView.findViewById(R.id.tvTemperatures);
            windTextView = itemView.findViewById(R.id.tvWind);
            rainTextView = itemView.findViewById(R.id.tvRain);
            weatherImageView = itemView.findViewById(R.id.ivWeatherIcon);
        }
    }

    public void setData(List<Forecast> newList) {
        forecastList.clear();
        forecastList.addAll(newList);
        notifyDataSetChanged();
    }
}
