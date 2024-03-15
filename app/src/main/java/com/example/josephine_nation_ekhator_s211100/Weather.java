package com.example.josephine_nation_ekhator_s211100;

public class Weather {
    private String city_name;
    private String text_date_time;
    private String text_current_temp;
    private String text_sunrise;
    private String text_sunset;
    private String uv_risk;
    private String temp_min;
    private String temp_max;
    private String visibility;
    private String text_pressure;
    private String text_humidity;
    private String text_wind;

    public Weather() {
        // Default constructor
    }

    // Getters and setters for the Weather fields

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getText_date_time() {
        return text_date_time;
    }

    public void setText_date_time(String text_date_time) {
        this.text_date_time = text_date_time;
    }

    public String getText_current_temp() {
        return text_current_temp;
    }

    public void setText_current_temp(String text_current_temp) {
        this.text_current_temp = text_current_temp;
    }

    public String getText_sunrise() {
        return text_sunrise;
    }

    public void setText_sunrise(String text_sunrise) {
        this.text_sunrise = text_sunrise;
    }

    public String getText_sunset() {
        return text_sunset;
    }

    public void setText_sunset(String text_sunset) {
        this.text_sunset = text_sunset;
    }

    public String getUv_risk() {
        return uv_risk;
    }

    public void setUv_risk(String uv_risk) {
        this.uv_risk = uv_risk;
    }

    public String getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(String temp_min) {
        this.temp_min = temp_min;
    }

    public String getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(String temp_max) {
        this.temp_max = temp_max;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getText_pressure() {
        return text_pressure;
    }

    public void setText_pressure(String text_pressure) {
        this.text_pressure = text_pressure;
    }

    public String getText_humidity() {
        return text_humidity;
    }

    public void setText_humidity(String text_humidity) {
        this.text_humidity = text_humidity;
    }

    public String getText_wind() {
        return text_wind;
    }

    public void setText_wind(String text_wind) {
        this.text_wind = text_wind;
    }
}
