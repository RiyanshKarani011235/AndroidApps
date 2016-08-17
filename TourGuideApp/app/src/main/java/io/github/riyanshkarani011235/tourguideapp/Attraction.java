package io.github.riyanshkarani011235.tourguideapp;

import android.location.Location;

/**
 * Created by ironstein on 30/06/16.
 */
public class Attraction {

    private String mName;
    private int mImageResourceId;
    private String mLocation;
    private String mShortDescription;
    private String mDescription;
    private String mTimings;
    public AttractionViewHolder attractionViewHolder;

    // constructor
    public Attraction(String name, int imageResourceId, String location,
                      String shortDescription, String description, String timings) {
        mName = name;
        mImageResourceId = imageResourceId;
        mLocation = location;
        mShortDescription = shortDescription;
        mDescription = description;
        mTimings = timings;
    }

    // ---------- getter methods ------------

    public String getName() {
        return mName;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public String getLocation() {
        return mLocation;
    }

    public String getmShortDescription() {
        return mShortDescription;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getTimings() {
        return mTimings;
    }

    // -------------------------------------

    // toString method
    public String toString() {
        String returnString = "";
        returnString += "Name : " + mName;
        returnString += "ImageResourceId : " + mImageResourceId;
        returnString += "Location : " + mLocation.toString();
        returnString += "ShortDescription : " + mShortDescription;
        returnString += "Description : " + mDescription;
        returnString += "Timings : " + mTimings;
        return returnString;
    }
}
