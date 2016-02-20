package com.getir.getirhackathon.Objects;

import android.util.Log;

import com.crashlytics.android.Crashlytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Emre on 21.02.2016.
 */
public class Distance implements Serializable {

    public String distance;
    public int distanceInCentimeters;
    public String duration;
    public int durationInSeconds;

    Distance(){}

    public static Distance objectFromJson(JSONObject jsonObject){
        Distance distance = new Distance();
        try{
            JSONObject distanceJson = jsonObject.getJSONObject("distance");
            distance.distance = distanceJson.getString("text");
            distance.distanceInCentimeters = distanceJson.getInt("value");

            JSONObject durationJson = jsonObject.getJSONObject("duration");
            distance.duration = durationJson.getString("text");
            distance.durationInSeconds = durationJson.getInt("value");
        }
        catch (JSONException e){
            Log.e("Distance", e.getLocalizedMessage());
            Crashlytics.log("Distance" + "-" + e.getLocalizedMessage());
        }

        return distance;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getDistanceInCentimeters() {
        return distanceInCentimeters;
    }

    public void setDistanceInCentimeters(int distanceInCentimeters) {
        this.distanceInCentimeters = distanceInCentimeters;
    }

    public int getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(int durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }
}
