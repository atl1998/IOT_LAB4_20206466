package com.example.lab4_20206466;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LocationFragment extends Fragment {

    private EditText locationSearchEditText;
    private RecyclerView recyclerView;
    private LocationAdapter locationAdapter;
    private List<Location> locationList;

    public LocationFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);

        locationSearchEditText = view.findViewById(R.id.etLocationSearch);
        recyclerView = view.findViewById(R.id.recyclerViewLocations);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        locationList = new ArrayList<>();

        locationAdapter = new LocationAdapter(getContext(), locationList, location -> {
            ForecastFragment forecastFragment = new ForecastFragment();

            Bundle bundle = new Bundle();
            bundle.putString("locationId", location.getIdLocation());
            forecastFragment.setArguments(bundle);
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, forecastFragment)
                    .addToBackStack(null)
                    .commit();
        });
        recyclerView.setAdapter(locationAdapter);

        Button searchButton = view.findViewById(R.id.btnSearchLocation);
        searchButton.setOnClickListener(v ->
                searchLocation(locationSearchEditText.getText().toString())
        );

        return view;
    }

    private void searchLocation(String query) {
        if (query.isEmpty()) {
            Toast.makeText(getContext(), "Please enter a location", Toast.LENGTH_SHORT).show();
            return;
        }

        WeatherApi.getLocations(query, new WeatherApi.LocationCallback() {
            @Override
            public void onSuccess(List<Location> locations) {
                requireActivity().runOnUiThread(() -> locationAdapter.setData(locations));
            }

            @Override
            public void onError(String message) {
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show()
                );
            }
        });
    }
}