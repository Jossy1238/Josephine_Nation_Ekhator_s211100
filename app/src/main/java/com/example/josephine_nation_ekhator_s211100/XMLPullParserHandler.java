package com.example.josephine_nation_ekhator_s211100;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XMLPullParserHandler {

    private static final String ITEM_TAG = "item";
    private static final String TITLE_TAG = "title";
    private static final String DESCRIPTION_TAG = "description";
    private static final String PUB_DATE_TAG = "pubDate";
    private static final String GEO_RSS_POINT_TAG = "georss:point";

    public static List<Weather> parseWeatherData(InputStream inputStream) throws XmlPullParserException, IOException {
        XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
        xmlPullParserFactory.setNamespaceAware(true);
        XmlPullParser parser = xmlPullParserFactory.newPullParser();
        parser.setInput(inputStream, null);

        int eventType = parser.getEventType();
        Weather weather = null;
        List<Weather> weatherList = new ArrayList<>();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String tagName = null;
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    tagName = parser.getName();
                    if (tagName.equalsIgnoreCase(ITEM_TAG)) {
                        weather = new Weather();
                    }
                    break;
                case XmlPullParser.TEXT:
                    String text = parser.getText();
                    if (weather != null) {
                        switch (tagName) {
                            case TITLE_TAG:
                                String[] titleParts = text.split(":");
                                if (titleParts.length == 2) {
                                    weather.setCity_name(titleParts[0].trim());
                                    String[] temperatureParts = titleParts[1].split("Maximum Temperature:|Minimum Temperature:");
                                    if (temperatureParts.length == 3) {
                                        weather.setTemp_max(temperatureParts[1].trim());
                                        weather.setTemp_min(temperatureParts[2].trim());
                                    }
                                }
                                break;
                            case DESCRIPTION_TAG:
                                String[] descriptionParts = text.split(",");
                                for (String part : descriptionParts) {
                                    String[] keyValue = part.trim().split(":");
                                    if (keyValue.length == 2) {
                                        switch (keyValue[0].trim()) {
                                            case "Wind Speed":
                                                weather.setText_wind(keyValue[1].trim());
                                                break;
                                            case "Visibility":
                                                weather.setVisibility(keyValue[1].trim());
                                                break;
                                            case "Pressure":
                                                weather.setText_pressure(keyValue[1].trim());
                                                break;
                                            case "Humidity":
                                                weather.setText_humidity(keyValue[1].trim());
                                                break;
                                            case "UV Risk":
                                                weather.setUv_risk(keyValue[1].trim());
                                                break;
                                            case "Sunrise":
                                                weather.setText_sunrise(keyValue[1].trim());
                                                break;
                                            case "Sunset":
                                                weather.setText_sunset(keyValue[1].trim());
                                                break;
                                        }
                                    }
                                }
                                break;
                            case PUB_DATE_TAG:
                                weather.setText_date_time(text.trim());
                                break;

                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    tagName = parser.getName();
                    if (tagName.equalsIgnoreCase(ITEM_TAG) && weather != null) {
                        weatherList.add(weather);
                    }
                    break;
                default:
                    break;
            }
            eventType = parser.next();
        }

        return weatherList;
    }
}
