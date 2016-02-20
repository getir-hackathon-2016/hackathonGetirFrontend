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
    public String id, name, info, username, password;
    public int phone;
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
            instance.username = jObject.getString("username");
            instance.password = jObject.getString("password");
            instance.phone = jObject.getInt("phone");
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

    public ArrayList<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(ArrayList<Address> addresses) {
        this.addresses = addresses;
    }

    public static void setInstance(User instance) {
        User.instance = instance;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
