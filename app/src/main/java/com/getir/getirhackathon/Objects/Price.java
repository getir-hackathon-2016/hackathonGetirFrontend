package com.getir.getirhackathon.Objects;

import android.util.Log;

import com.crashlytics.android.Crashlytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Emre on 20.02.2016.
 */
public class Price implements Serializable {
    double tl, usd;

    Price(){}

    public static Price objectFromJson(JSONObject jsonObject){
        Price price = new Price();
        try{
            price.tl = jsonObject.getDouble("tl");
            price.usd = jsonObject.getDouble("usd");
        }catch (JSONException e){
            Log.e("Price", e.getLocalizedMessage());
            Crashlytics.log("Price - try/catch");
        }
        return price;
    }

    public double getTl() {
        return tl;
    }

    public void setTl(double tl) {
        this.tl = tl;
    }

    public double getUsd() {
        return usd;
    }

    public void setUsd(double usd) {
        this.usd = usd;
    }
}
