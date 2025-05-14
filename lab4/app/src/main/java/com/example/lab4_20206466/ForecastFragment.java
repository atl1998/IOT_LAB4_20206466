package com.example.lab4_20206466;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ForecastFragment extends Fragment {

    private RecyclerView recyclerView;
    private ForecastAdapter forecastAdapter;
    private List<Forecast> forecastList;
    private EditText locationIdEditText, daysEditText;
    private Button searchButton;

    public ForecastFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);

        // Inicializamos vistas
        locationIdEditText = view.findViewById(R.id.etLocationId);
        daysEditText = view.findViewById(R.id.etDays);
        recyclerView = view.findViewById(R.id.recyclerViewForecast);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inicializamos adaptador
        forecastList = new ArrayList<>();
        forecastAdapter = new ForecastAdapter(getContext(), forecastList);
        recyclerView.setAdapter(forecastAdapter);

        //botón de búsqueda
        searchButton = view.findViewById(R.id.btnSearchForecast);
        searchButton.setOnClickListener(v -> searchForecast());

        if (getArguments() != null && getArguments().containsKey("locationId")) {
            String locationId = getArguments().getString("locationId");
            if (locationId != null && !locationId.isEmpty()) {
                locationIdEditText.setText(locationId);
                daysEditText.setText("14");//14 por defecto
                searchForecast();
            }
        }

        return view;
    }

    private void searchForecast() {
        String locationId = locationIdEditText.getText().toString().trim();
        String daysText = daysEditText.getText().toString().trim();

        if (locationId.isEmpty() || daysText.isEmpty()) {
            Toast.makeText(getContext(), "Por favor completa ambos campos", Toast.LENGTH_SHORT).show();
            return;
        }

        int days;
        try {
            days = Integer.parseInt(daysText);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Días inválidos", Toast.LENGTH_SHORT).show();
            return;
        }

        WeatherApi.getForecast(locationId, days, new WeatherApi.ForecastCallback() {
            @Override
            public void onSuccess(List<Forecast> forecastResult) {
                requireActivity().runOnUiThread(() -> forecastAdapter.setData(forecastResult));
            }

            @Override
            public void onError(String errorMessage) {
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(getContext(), "Error: " + errorMessage, Toast.LENGTH_SHORT).show()
                );
            }
        });
    }
}
