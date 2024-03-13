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
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XMLPullParserHandler {
    private static final String TAG_TITLE = "title";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_PUB_DATE = "pubDate";
    private static final String TAG_GEO_RSS_POINT = "georss:point";

    public static List<Weather> parseWeatherData(InputStream inputStream) throws XmlPullParserException, IOException {
        List<Weather> weatherInfo = new ArrayList<>();
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(inputStream, null);

        int eventType = parser.getEventType();
        Weather currentItem = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String tagName = null;
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    tagName = parser.getName();
                    if (TAG_TITLE.equals(tagName)) {
                        currentItem = new Weather();
                        currentItem.setTitle(parser.nextText());
                    } else if (TAG_DESCRIPTION.equals(tagName)) {
                        if (currentItem != null) {
                            currentItem.setDescription(parser.nextText());
                        }
                    } else if (TAG_PUB_DATE.equals(tagName)) {
                        if (currentItem != null) {
                            currentItem.setPubDate(parser.nextText());
                        }
                    } else if (TAG_GEO_RSS_POINT.equals(tagName)) {
                        if (currentItem != null) {
                            currentItem.setGeoRssPoint(parser.nextText());
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if ("item".equals(parser.getName())) {
                        if (currentItem != null) {
                            weatherInfo.add(currentItem);
                        }
                    }
                    break;
                default:
                    // Do nothing
            }
            eventType = parser.next();
        }

        return weatherInfo;
    }

}
