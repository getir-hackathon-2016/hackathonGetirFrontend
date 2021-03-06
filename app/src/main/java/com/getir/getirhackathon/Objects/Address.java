package com.getir.getirhackathon.Objects;

import com.crashlytics.android.Crashlytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Emre on 20.02.2016.
 */
public class Address implements Serializable {
    String id, name, address, town, country;
    double latitude, longitude;


    public Address(){}

    public static Address objectFromJson(JSONObject jsonObject){
        Address adress = new Address();
        try {
            adress.id = jsonObject.getString("id");
            adress.name = jsonObject.getString("name");
            adress.address = jsonObject.getString("address");
            adress.town = jsonObject.getString("town");
            adress.country = jsonObject.getString("country");
            adress.latitude = jsonObject.getDouble("latitude");
            adress.longitude = jsonObject.getDouble("longitude");
        }catch (JSONException e){
            e.printStackTrace();
            Crashlytics.log("Address - objectFromJson" + "-" + e.getLocalizedMessage());
        }
        return adress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longtitude) {
        this.longitude = longtitude;
    }
}
