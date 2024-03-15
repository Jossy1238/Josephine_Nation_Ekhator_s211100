package com.example.josephine_nation_ekhator_s211100;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class TheHomeFragment extends Fragment {
    private TextView city_name;
    private TextView text_date_time;
    private TextView text_current_temp;
    private ImageView sun_icon;
    private TextView text_sunrise;
    private TextView text_sunset;
    private TextView uv_risk;
    private TextView temp_min;
    private TextView temp_max;
    private TextView visibility;
    private TextView text_pressure;
    private TextView text_humidity;
    private TextView text_wind;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_the_home, container, false);

        city_name = view.findViewById(R.id.city_name);
        text_date_time = view.findViewById(R.id.text_date_time);
        text_current_temp = view.findViewById(R.id.text_current_temp);
        sun_icon = view.findViewById(R.id.sun_icon);
        text_sunrise = view.findViewById(R.id.text_sunrise);
        text_sunset = view.findViewById(R.id.text_sunset);
        uv_risk = view.findViewById(R.id.uv_risk);
        temp_min = view.findViewById(R.id.temp_min);
        temp_max = view.findViewById(R.id.temp_max);
        visibility = view.findViewById(R.id.visibility);
        text_pressure = view.findViewById(R.id.text_pressure);
        text_humidity = view.findViewById(R.id.text_humidity);
        text_wind = view.findViewById(R.id.text_wind);

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
                        city_name.setText(weather.getCity_name());
                        text_date_time.setText(weather.getText_date_time());
                        text_current_temp.setText(weather.getText_current_temp());
                        text_sunrise.setText(weather.getText_sunrise());
                        text_sunset.setText(weather.getText_sunset());
                        uv_risk.setText(weather.getUv_risk());
                        temp_min.setText(weather.getTemp_min());
                        temp_max.setText(weather.getTemp_max());
                        visibility.setText(weather.getVisibility());
                        text_pressure.setText(weather.getText_pressure());
                        text_humidity.setText(weather.getText_humidity());
                        text_wind.setText(weather.getText_wind());
                    } else {
                        city_name.setText("Error fetching data");
                    }
                });
            } catch (XmlPullParserException | IOException e) {
                e.printStackTrace();
                // Update UI on the main thread to show error
                getActivity().runOnUiThread(() -> {
                    city_name.setText("Error fetching data");
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