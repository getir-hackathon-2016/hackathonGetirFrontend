package com.getir.getirhackathon;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;

import com.getir.getirhackathon.Objects.ServiceUser;
import com.getir.getirhackathon.Objects.User;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static com.google.android.gms.internal.zzir.runOnUiThread;

/**
 * Created by Emre on 19.02.2016.
 */
public class Util {

    public static long LOCATION_REFRESH_TIME = 100;
    public static float LOCATION_REFRESH_DISTANCE = 0.001f;
    public static LocationManager locationManager;
    public static Location location;
    public static boolean isGPSEnabled = false;
    public static boolean isNetworkEnabled = false;
    public static double longitude = 0;
    public static double latitude = 0;
    public static double userCurrentLatitude;
    public static double userCurrentLongitude;
    public static Socket socket;

    public static final String BASE_URL = "https://fixitt.herokuapp.com";
    public static String BASE_URL1 = "http://192.168.1.56:9000";

    public static Typeface getFontAwesome(Context context) {
        Typeface fontAwesome = Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf");
        return fontAwesome;
    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    public static void parseServiceJson(JSONObject jsonObject) {
        ServiceUser.getInstance().initObjectFromJson(jsonObject);
    }

    public static void parseUserJson(JSONObject jsonObject) {
        User.getInstance().initObjectFromJson(jsonObject);
    }

    //sortServiceUsers is a function that takes an ServiceUser List and sorts it ascending.
    public static void sortServiceUsers(List<ServiceUser> array){
        Collections.sort(array, new Comparator<ServiceUser>() {
            @Override
            public int compare(ServiceUser lhs, ServiceUser rhs) {
                int leftHandSideDuration = lhs.getDistance().getDurationInSeconds();
                int rightHandSideDuration = rhs.getDistance().getDurationInSeconds();
                if(leftHandSideDuration < rightHandSideDuration){
                    return -1;
                }else if(leftHandSideDuration == rightHandSideDuration){
                    return 0;
                }else{
                    return 1;
                }
            }
        });
    }


}
