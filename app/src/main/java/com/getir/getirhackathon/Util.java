package com.getir.getirhackathon;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

/**
 * Created by Emre on 19.02.2016.
 */
public class Util {

    public static long LOCATION_REFRESH_TIME = 100;
    public static float LOCATION_REFRESH_DISTANCE = 0.1f;

    public static Typeface getFontAwesome(Context context){
        Typeface fontAwesome = Typeface.createFromAsset( context.getAssets(), "fontawesome-webfont.ttf" );
        return fontAwesome;
    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        }else{
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }


    }

}
