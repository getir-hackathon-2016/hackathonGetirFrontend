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
public class User implements Serializable {
    public String id, name, address, phone, info;
    public ArrayList<Address> addresses;
    public static User instance;

    public User(){}

    public static User getInstance() {
        if (instance == null) {
            synchronized (User.class) {
                if (instance == null) {
                    instance = new User();
                }
            }
        }
        return instance;
    }

    public static void initObjectFromJson(JSONObject jObject){
        try{
            instance.id = jObject.getString("_id");
            instance.name = jObject.getString("name");
            instance.address = jObject.getString("name");
            instance.name = jObject.getString("name");
            if(jObject.has("addresses") && jObject.getJSONArray("addresses") != null){
                JSONArray jsonAddresses = jObject.getJSONArray("addresses");
                instance.addresses = new ArrayList<>(jsonAddresses.length());
                for(int i = 0; i<jsonAddresses.length(); i++){
                    JSONObject jsonAddress = jsonAddresses.getJSONObject(i);
                    instance.addresses.add(Address.objectFromJson(jsonAddress));
                }
            }
        }catch (JSONException e) {
            Log.e("User", e.getLocalizedMessage());
            Crashlytics.log("User - try/catch");
        }
    }
}
