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

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.xmlpull.v1.XmlPullParserException;
import java.util.List;


public class HomeFragement extends Fragment {
    private TextView titleTextView;
    private TextView descriptionTextView;
    private TextView pubDateTextView;
    private TextView georssPointTextView;
    private Button refreshButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home_fragement, container, false);

        titleTextView = view.findViewById(R.id.titleTextView);
        descriptionTextView = view.findViewById(R.id.descriptionTextView);
        pubDateTextView = view.findViewById(R.id.pubDateTextView);
        georssPointTextView = view.findViewById(R.id.georssPointTextView);
        refreshButton = view.findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchData(); // Refresh the data
            }
        });
        fetchData(); // Call method to fetch data when fragment is created
        return view;
    }

    private void fetchData() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                InputStream inputStream = fetchDataFromUrl("https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/2643123");
                List<Weather> weatherInfo = XMLPullParserHandler.parseWeatherData(inputStream);
                Weather weather = weatherInfo != null && !weatherInfo.isEmpty() ? weatherInfo.get(0) : null;

                // Update UI on the main thread
                getActivity().runOnUiThread(() -> {
                    if (weather != null) {
                        titleTextView.setText(weather.getTitle());
                        descriptionTextView.setText(weather.getDescription());
                        pubDateTextView.setText(weather.getPubDate());
                        georssPointTextView.setText(weather.getGeoRssPoint());
                    } else {
                        titleTextView.setText("Error fetching data");
                    }
                });
            } catch (XmlPullParserException | IOException e) {
                e.printStackTrace();
                // Update UI on the main thread to show error
                getActivity().runOnUiThread(() -> {
                    titleTextView.setText("Error fetching data");
                });
            }
        });
    }
    private InputStream fetchDataFromUrl(String urlString) {
        InputStream inputStream = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            inputStream = connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

}