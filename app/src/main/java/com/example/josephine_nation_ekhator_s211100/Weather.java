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
