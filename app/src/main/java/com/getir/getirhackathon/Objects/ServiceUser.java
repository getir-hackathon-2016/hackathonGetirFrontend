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
    public String id, name, info, categoryStr;
    public int phone;
    public double longitude;
    public double latitude;
    public Price price;
    public Category category;
    public Distance distance;
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

    public static ServiceUser objectFromJson(JSONObject jObject){
        ServiceUser serviceUser = new ServiceUser();
        try{
            serviceUser.name = jObject.getString("name");
            serviceUser.id = jObject.getString("_id");
            serviceUser.info = jObject.getString("info");
            serviceUser.phone = jObject.getInt("phone");
            serviceUser.longitude = jObject.getDouble("longitude");
            serviceUser.latitude = jObject.getDouble("latitude");
            //serviceUser.category = Category.objectFromJson(jObject.getJSONObject("category"));
            serviceUser.categoryStr = jObject.getString("category");
            serviceUser.price = Price.objectFromJson(jObject.getJSONObject("price"));
            if(jObject.has("distance")){
                serviceUser.distance = Distance.objectFromJson(jObject.getJSONObject("distance"));
            }
        }catch (JSONException e) {
            Log.e("ServiceUser", e.getLocalizedMessage());
            Crashlytics.log("ServiceUser - try/catch");
        }
        return serviceUser;
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

    public String getCategoryStr() {
        return categoryStr;
    }

    public void setCategoryStr(String categoryStr) {
        this.categoryStr = categoryStr;
    }

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }
}
