package com.example.josephine_nation_ekhator_s211100;

public class Weather {
    private String title;
    private String description;
    private String pubDate;
    private String geoRssPoint;

    public Weather() {
        // Default constructor
    }

    // Getters and setters for the WeatherItem fields
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getGeoRssPoint() {
        return geoRssPoint;
    }

    public void setGeoRssPoint(String geoRssPoint) {
        this.geoRssPoint = geoRssPoint;
    }
}
