package com.getir.getirhackathon.Objects;

import android.util.Log;

import com.crashlytics.android.Crashlytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Emre on 20.02.2016.
 */
public class ServiceUser implements Serializable{
    String id, name, info;
    int phone;
    double longitude;
    double latitude;
    Price price;
    Category category;
    public static ServiceUser instance;

    public static ServiceUser getInstance() {
        if (instance == null) {
            synchronized (ServiceUser.class) {
                if (instance == null) {
                    instance = new ServiceUser();
                }
            }
        }
        return instance;
    }

    public static void initObjectFromJson(JSONObject jObject){
        try{
            instance.id = jObject.getString("_id");
            instance.name = jObject.getString("name");
            instance.info = jObject.getString("info");
            instance.phone = jObject.getInt("phone");
            instance.longitude = jObject.getDouble("longtitude");
            instance.latitude = jObject.getDouble("latitude");
            instance.category = Category.objectFromJson(jObject.getJSONObject("category"));
            instance.price = Price.objectFromJson(jObject.getJSONObject("price"));
        }catch (JSONException e) {
            Log.e("ServiceUser", e.getLocalizedMessage());
            Crashlytics.log("ServiceUser - try/catch");
        }
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public static void setInstance(ServiceUser instance) {
        ServiceUser.instance = instance;
    }
}
