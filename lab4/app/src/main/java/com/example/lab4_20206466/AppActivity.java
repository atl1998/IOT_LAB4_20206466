package com.example.lab4_20206466;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;

import com.example.lab4_20206466.R;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AppActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        // Inicializamos el BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // navegar entre fragmentos
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                if (item.getItemId() == R.id.location) {
                    selectedFragment = new LocationFragment();
                } else if (item.getItemId() == R.id.forecast) {
                    selectedFragment = new ForecastFragment();
                } else if (item.getItemId() == R.id.futureForecast) {
                    selectedFragment = new FutureForecastFragment();
                }

                // Cargar el fragmento seleccionado
                return loadFragment(selectedFragment);
            }
        });

        // Fragmento inicial (location fragment por defecto)
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, new LocationFragment())
                    .commit();
        }
    }

    // Método para cargar un fragmento
    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}

