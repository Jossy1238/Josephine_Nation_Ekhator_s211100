/*  Starter project for Mobile Platform Development in main diet 2023/2024
   You should use this project as the starting point for your assignment.
   This project simply reads the data from the required URL and displays the
   raw data in a TextField
*/
//
// Name                 Josephine Nation Ekhator
// Student ID           s211100
// Programme of Study   Computing
//
package com.example.josephine_nation_ekhator_s211100;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;

    HomeFragement homeFragement = new HomeFragement();
    SearchFragment searchFragment = new SearchFragment();
    MapFragment mapFragment = new MapFragment();
    SettingsFragment settingsFragment = new SettingsFragment();

    TopLeftSettings topLeftSettings = new TopLeftSettings();

    Share share = new Share();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        bottomNavigationView = findViewById(R.id.buttom_navigation);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("JNWeather");

        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragement).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment selectedFragment = null;
                String title = "";

                if (item.getItemId() == R.id.home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,
                            new HomeFragement()).commit();
                    title = "Home";

                } else if (item.getItemId() == R.id.search) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,
                            new SearchFragment()).commit();
                    title = "Search";

                } else if (item.getItemId() == R.id.map) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,
                            new MapFragment()).commit();
                    title = "Search";

                } else if (item.getItemId() == R.id.settings) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,
                            new SettingsFragment()).commit();
                    title = "Settings";
                }
                getSupportActionBar().setTitle(title);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_navigation_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle menu item clicks
        if (item.getItemId() == R.id.topLeftSettings){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new TopLeftSettings()).addToBackStack(null).commit();
            return true;
        } else if (item.getItemId() == R.id.share) {
            // Replace the container with the HelpFragment
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new Share()).addToBackStack(null).commit();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}