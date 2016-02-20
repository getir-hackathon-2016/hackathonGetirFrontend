package com.getir.getirhackathon;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends Activity {

    private TextView settings_button, list_button, location_text;
    private DrawerLayout drawerLayout;
    private RelativeLayout rightDrawerLayout, leftDrawerLayout;
    private Context mContext;
    private LocationManager mLocationManager;

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            location_text.setText(String.valueOf(location.getLatitude()) + "-" + String.valueOf(location.getLongitude()));
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Run Fabric for notify crashes.
        Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());
        mContext = this;

        setContentView(R.layout.activity_main);

        //To initiliaze view.
        initView();

        //check if location services are enabled.
        checkLocationService();

        //To initiliaze location listener.
        initLocationListener();
    }

    private void initView() {
        settings_button = (TextView) findViewById(R.id.settings_button);
        settings_button.setTypeface(Util.getFontAwesome(mContext));
        settings_button.setTextSize(30);
        settings_button.setTextColor(getResources().getColor(R.color.blue_light));

        settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(rightDrawerLayout);
            }
        });

//        settings_button.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    settings_button.setTextSize(40);
//                } else if (event.getAction() == MotionEvent.ACTION_UP) {
//                    settings_button.setTextSize(30);
//                }
//                return true;
//            }
//        });

        location_text = (TextView) findViewById(R.id.location_text);
        location_text.setTextColor(Color.BLACK);

        list_button = (TextView) findViewById(R.id.left_drawer_button);
        list_button.setTypeface(Util.getFontAwesome(mContext));
        list_button.setTextColor(getResources().getColor(R.color.blue_light));

        list_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(leftDrawerLayout);
            }
        });


        //Right Drawer View
        initRightDrawerView();

        //Left Drawer View
        initLeftDrawerView();
    }
    private void initLeftDrawerView(){
        leftDrawerLayout = (RelativeLayout) findViewById(R.id.left_drawer);
    }

    private void initRightDrawerView(){
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        rightDrawerLayout = (RelativeLayout) findViewById(R.id.right_drawer);

        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    private void initLocationListener(){
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, Util.LOCATION_REFRESH_TIME,
                Util.LOCATION_REFRESH_DISTANCE, mLocationListener);
    }

    private void checkLocationService(){
        if(!Util.isLocationEnabled(mContext)){
            AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
            dialog.setMessage(mContext.getResources().getString(R.string.location_service_not_enabled));
            dialog.setPositiveButton(mContext.getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    mContext.startActivity(myIntent);
                    //get gps
                }
            });
            dialog.setNegativeButton(mContext.getString(R.string.cancel), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub

                }
            });
            dialog.show();
        }
    }
}
