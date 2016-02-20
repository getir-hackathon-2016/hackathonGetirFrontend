package com.getir.getirhackathon.Objects;

import android.util.Log;

import com.crashlytics.android.Crashlytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Emre on 20.02.2016.
 */
public class Category implements Serializable {

    String name;

    public Category(){}

    public static Category objectFromJson(JSONObject jsonObject){
        Category category = new Category();
        try{
            category.name = jsonObject.getString("name");
        }catch (JSONException e){
            e.printStackTrace();
            Log.e("Category", e.getLocalizedMessage());
            Crashlytics.log("Category" + "-" + e.getLocalizedMessage());
        }
        return category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
