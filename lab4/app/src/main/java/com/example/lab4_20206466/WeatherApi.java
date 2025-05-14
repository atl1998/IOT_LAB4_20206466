package com.example.lab4_20206466;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WeatherApi {

    private static final String API_KEY = "ec24b1c6dd8a4d528c1205500250305";
    private static final String BASE_URL = "https://api.weatherapi.com/v1/";

    // Interfaz interna
    public interface LocationCallback {
        void onSuccess(List<Location> locations);
        void onError(String message);
    }

    public interface ForecastCallback {
        void onSuccess(List<Forecast> forecastList);
        void onError(String errorMessage);
    }

    // Método para obtener ubicaciones
    public static void getLocations(String query, LocationCallback callback) {
        String url = BASE_URL + "search.json?key=" + API_KEY + "&q=" + query;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onError("Error de red: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    callback.onError("Error en la respuesta del servidor");
                    return;
                }

                List<Location> locations = new ArrayList<>();

                try {
                    JSONArray array = new JSONArray(response.body().string());
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);

                        Location location = new Location();
                        location.setIdLocation(obj.optString("id", ""));
                        location.setName(obj.optString("name", ""));
                        location.setRegion(obj.optString("region", ""));
                        location.setCountry(obj.optString("country", ""));

                        locations.add(location);
                    }

                    callback.onSuccess(locations);

                } catch (JSONException e) {
                    callback.onError("Error al parsear JSON: " + e.getMessage());
                }
            }
        });
    }


    // Método para obtener los pronósticos de clima
    public static void getForecast(String locationId, int days, ForecastCallback callback) {
        String url = BASE_URL + "forecast.json?key=" + API_KEY + "&q=id:" + locationId + "&days=" + days;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onError("Error de red: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    callback.onError("Error en la respuesta del servidor");
                    return;
                }

                try {
                    List<Forecast> forecastList = new ArrayList<>();
                    JSONObject root = new JSONObject(response.body().string());

                    JSONArray forecastDays = root
                            .getJSONObject("forecast")
                            .getJSONArray("forecastday");

                    for (int i = 0; i < forecastDays.length(); i++) {
                        JSONObject dayObject = forecastDays.getJSONObject(i);
                        JSONObject day = dayObject.getJSONObject("day");
                        JSONObject condition = day.getJSONObject("condition");

                        Forecast forecast = new Forecast();
                        forecast.setDate(dayObject.getString("date"));
                        forecast.setMaxTempC(day.getDouble("maxtemp_c"));
                        forecast.setMinTempC(day.getDouble("mintemp_c"));
                        forecast.setAvgTempC(day.getDouble("avgtemp_c"));
                        forecast.setConditionWeather(condition.getString("text"));
                        forecast.setUrlIcon(condition.getString("icon"));
                        forecast.setMaxWindSpeed(day.getDouble("maxwind_kph"));
                        forecast.setChanceOfRain(day.optDouble("daily_chance_of_rain", 0));

                        forecastList.add(forecast);
                    }

                    callback.onSuccess(forecastList);

                } catch (JSONException e) {
                    callback.onError("Error al parsear JSON: " + e.getMessage());
                }
            }
        });
    }


}
