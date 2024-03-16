package com.example.josephine_nation_ekhator_s211100;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.util.Log;

public class XMLPullParserHandler {
    private static final String TAG_ITEM = "item";
    private static final String TAG_TITLE = "title";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_PUB_DATE = "pubDate";

    public static Weather parseWeatherData (InputStream inputStream) throws XmlPullParserException, IOException {
        Weather firstFoundItem = null;
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(inputStream, null);

            int eventType = parser.getEventType();
            Weather currentItem = null;
            boolean isFirstItem = true; // Flag to track the first item

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = null;
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        tagName = parser.getName();
                        if (TAG_ITEM.equals(tagName) && isFirstItem) {
                            currentItem = new Weather();
                            Log.d("XmlPullParserHelper", "Found first item tag");
                            isFirstItem = false; // Set to false to ensure we only process the first item
                        } else if (TAG_TITLE.equals(tagName) && currentItem != null) {
                            String title = parser.nextText();
                            parseTitle(title, currentItem);
                            Log.d("XmlPullParserHelper", "Title: " + title);
                        } else if (TAG_DESCRIPTION.equals(tagName) && currentItem != null) {
                            String description = parser.nextText();
                            parseDescription(description, currentItem);
                            Log.d("XmlPullParserHelper", "Description: " + description);
                        } else if (TAG_PUB_DATE.equals(tagName) && currentItem != null) {
                            String rawDate = parser.nextText();
                            // Trim the date to only show the day and date
                            String trimmedDate = trimDate(rawDate);
                            currentItem.setText_date_time(trimmedDate);
                            Log.d("XmlPullParserHelper", "Trimmed Pub Date: " + trimmedDate);
                        }

                        break;
                    case XmlPullParser.END_TAG:
                        if (TAG_ITEM.equals(parser.getName()) && currentItem != null) {
                            firstFoundItem = currentItem; // Assign the first item to the return variable
                            currentItem = null; // Reset the current item
                        }
                        break;
                    default:
                        // Do nothing
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException | IOException e){
            e.printStackTrace();
        }
     return  firstFoundItem;
    }


    private static void parseDescription(String description, Weather currentItem) {
        // Adjusted to match key terms in any order
        Pattern pattern = Pattern.compile("Wind Direction: (\\w+),?|Wind Speed: (\\d+mph),?|Pressure: (\\w+mb),?|Humidity: (\\d+%),?|Visibility: (\\w+),?|UV Risk: (\\d+),?|Sunrise: (\\d{2}:\\d{2}GMT),?");
        Matcher matcher = pattern.matcher(description);
        String wind = "", pressure = "",  humidity = "", visibility = "", risk = "", sunrise = "";

        while (matcher.find()) {
            if (matcher.group(1) != null) { // Wind Direction
                wind = matcher.group(1);
            } else if (matcher.group(2) != null) { // Wind Speed
                wind += " " + matcher.group(2);
            } else if (matcher.group(3) != null) { // pressure
                pressure = matcher.group(3);
            } else if (matcher.group(4) != null) { // Humidity
                humidity = matcher.group(4);
            } else if (matcher.group(5) != null) { // visibility
                visibility = matcher.group(5);
            }else if (matcher.group(6) != null) { // visibility
                risk = matcher.group(6);
            }else if (matcher.group(7) != null) { // Sunrise
                sunrise = matcher.group(7);
            }
        }
        System.out.println("Sunrise: " + sunrise);

        currentItem.setText_wind(wind);
        currentItem.setText_pressure(pressure);
        currentItem.setText_humidity(humidity);
        currentItem.setVisibility(visibility); //
        currentItem.setUv_risk(risk); //
        currentItem.setText_sunrise(sunrise);


    }

    private static String trimDate(String rawDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
        SimpleDateFormat outputFormat = new SimpleDateFormat("EEE, dd MMM yyyy");
        try {
            Date date = inputFormat.parse(rawDate);
            return outputFormat.format(date);
        } catch (
                ParseException e) {
            e.printStackTrace();
            return rawDate; // Return the raw date if parsing fails
        }
    }

    private static void parseTitle(String title, Weather currentItem) {
        //Extract city name from the title
        String cityName = parseCityNameFromTitle(title);
        currentItem.setCity_name("Mauritius");

        Pattern pattern = Pattern.compile("Today: (.*?), Minimum Temperature: (-?\\d+)°C \\(.*?\\) Maximum Temperature: (-?\\d+)°C \\(.*?\\)");
        Matcher matcher = pattern.matcher(title);
        if (matcher.find()) {
            String condition = matcher.group(1).trim();
            String minTemperature = matcher.group(2).trim();
            String maxTemperature = matcher.group(3).trim();
            currentItem.setTemp_min(minTemperature + "°C/ " + maxTemperature + "°C");
            currentItem.setWeather_condition(condition);

            // Additional logging for debugging
            Log.d("XmlPullParserHelper", "Extracted Weather Condition: " + condition);
            Log.d("XmlPullParserHelper", "Extracted Temperature: " + minTemperature + "°C / " + maxTemperature + "°C");
        } else {
            // Log if the pattern does not match
            Log.d("XmlPullParserHelper", "Pattern did not match the title: " + title);
        }
    }

    private static String parseCityNameFromTitle(String title) {
        // Extract the city name from the title
        String cityName = "";
        int startIndex = title.indexOf("Forecast for") + "Forecast for".length();
        int endIndex = title.lastIndexOf(", MU");
        if (startIndex >= 0 && endIndex >= 0 && endIndex > startIndex) {
            cityName = title.substring(startIndex, endIndex).trim();
        }
        return cityName;
    }

}
