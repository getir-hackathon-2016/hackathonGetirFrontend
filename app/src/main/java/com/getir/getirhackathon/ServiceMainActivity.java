package com.getir.getirhackathon;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.getir.getirhackathon.Dialogs.ProfileDialog;
import com.getir.getirhackathon.Objects.ServiceUser;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.fabric.sdk.android.Fabric;

public class ServiceMainActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private TextView settings_button, list_button, language_icon, user_icon_text, cart_down_icon, logout_icon, back_button_left_drawer, name, languages_text;
    private TextView versionBuild;
    private LinearLayout fragment_container;
    private LinearLayout profileLayout, prevCartLayout, logoutLayout;
    private ImageButton en_button, tr_button;
    private DrawerLayout drawerLayout;
    private RelativeLayout rightDrawerLayout, leftDrawerLayout;
    private Context mContext;

    private LocationManager mLocationManager;
    private Location mLastLocation;
    public static boolean isGPSEnabled = false;
    public static boolean isNetworkEnabled = false;

    private GoogleApiClient mGoogleApiClient;
    private double latitude;
    private double longitude;
    private Socket socket;

    private ServiceUser currentServiceUser;

    private GoogleMap mMap;

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            //location_text.setText(String.valueOf(location.getLatitude()) + "-" + String.valueOf(location.getLongitude()));

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

        currentServiceUser = ServiceUser.getInstance();

        setContentView(R.layout.service_main_layout);

        createMap();

        //To initiliaze view.
        initView();

        //check if location services are enabled.
        checkLocationService();

        //To initiliaze location listener.
        initLocationListener();

        //Start socketIO
        startSocket();
    }

    private void createMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    private void initView() {
        settings_button = (TextView) findViewById(R.id.settings_button);
        settings_button.setTypeface(Util.getFontAwesome(mContext));
        settings_button.setTextSize(30);
        settings_button.setTextColor(getResources().getColor(R.color.black));

        settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(rightDrawerLayout);
            }
        });

        list_button = (TextView) findViewById(R.id.left_drawer_button);
        list_button.setTypeface(Util.getFontAwesome(mContext));
        list_button.setTextColor(getResources().getColor(R.color.black));

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

    private void initLeftDrawerView() {
        leftDrawerLayout = (RelativeLayout) findViewById(R.id.left_drawer);
        user_icon_text = (TextView) findViewById(R.id.user_icon_text);
        user_icon_text.setTypeface(Util.getFontAwesome(mContext));
        user_icon_text.setTextColor(mContext.getResources().getColor(R.color.blue_light));

        cart_down_icon = (TextView) findViewById(R.id.cart_down_icon);
        cart_down_icon.setTypeface(Util.getFontAwesome(mContext));
        cart_down_icon.setTextColor(mContext.getResources().getColor(R.color.blue_light));

        logout_icon = (TextView) findViewById(R.id.logout_icon);
        logout_icon.setTypeface(Util.getFontAwesome(mContext));
        logout_icon.setTextColor(mContext.getResources().getColor(R.color.blue_light));

        profileLayout = (LinearLayout) findViewById(R.id.profile_layout);
        prevCartLayout = (LinearLayout) findViewById(R.id.prev_cart_layout);
        logoutLayout = (LinearLayout) findViewById(R.id.logout_layout);

        //back button
        back_button_left_drawer = (TextView) findViewById(R.id.back_button_left_drawer);
        back_button_left_drawer.setTypeface(Util.getFontAwesome(mContext));
        back_button_left_drawer.setTextColor(mContext.getResources().getColor(R.color.blue_light));
        back_button_left_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(leftDrawerLayout);
            }
        });

        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileDialog profile = new ProfileDialog(mContext);
                profile.show();
            }
        });

        logoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socket.disconnect();
                finish();
            }
        });
    }

    private void initRightDrawerView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        rightDrawerLayout = (RelativeLayout) findViewById(R.id.right_drawer);
        rightDrawerLayout.setBackgroundColor(getResources().getColor(R.color.gray));
        language_icon = (TextView) findViewById(R.id.language_icon);
        language_icon.setTypeface(Util.getFontAwesome(mContext));
        language_icon.setTextColor(getResources().getColor(R.color.blue_light));

        en_button = (ImageButton) findViewById(R.id.en_button);
        tr_button = (ImageButton) findViewById(R.id.tr_button);

        en_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(rightDrawerLayout);

                //
            }
        });

        tr_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(rightDrawerLayout);

                //
            }
        });

        versionBuild = (TextView) findViewById(R.id.versionBuild);
        try {
            versionBuild.setText(mContext.getPackageManager()
                    .getPackageInfo(mContext.getPackageName(), 0).versionName + " (build " + mContext.getPackageManager()
                    .getPackageInfo(mContext.getPackageName(), 0).versionCode + ")");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Crashlytics.log("ServiceMainActivity" + "-" + e.getLocalizedMessage());
        }
    }

    private void initLocationListener() {

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, Util.LOCATION_REFRESH_TIME,
                Util.LOCATION_REFRESH_DISTANCE, mLocationListener);

        mLastLocation = getLocation(mContext);
    }

    private void checkLocationService() {
        if (!Util.isLocationEnabled(mContext)) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
            dialog.setMessage(mContext.getResources().getString(R.string.location_service_not_enabled));
            dialog.setPositiveButton(mContext.getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
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

    private void startSocket() {
        IO.Options opts = new IO.Options();
        opts.forceNew = true;

        try {
            socket = IO.socket(Util.SERVER_URL, opts);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        final JSONObject jObject = new JSONObject();
        try {
            jObject.put("courierId", ServiceUser.getInstance().getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        socket.emit("courierLogin", jObject);
                        Log.i("response", "connected");
                    }
                });
            }

        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                socket.emit("courierLogout", jObject);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("response", "disconnected");
                    }
                });

            }

        });
        socket.connect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (socket != null) {
            socket.disconnect();
        }
    }

    @Override
    protected void onResume() {
        mGoogleApiClient.connect();
        super.onResume();
        if (socket != null) {
            socket.connect();
        } else {
            startSocket();
        }
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mLastLocation = getLocation(mContext);
        latitude = mLastLocation.getLatitude();
        longitude = mLastLocation.getLongitude();
        mMap = googleMap;
        // Add a marker in Sydney, Australia, and move the camera.
        LatLng currentLoc = new LatLng(latitude, longitude);
        emitCurrentLocation();
        mMap.addMarker(new MarkerOptions().position(currentLoc).title("Current Location"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLoc));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(currentLoc)      // Sets the center of the map to location user
                .zoom(14)                   // Sets the zoom
                //.bearing(90)                // Sets the orientation of the camera to east
                //.tilt(40)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private Location getLocation(Context mContext) {
        try {
            mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            Log.v("locationServices", "isGPSEnabled =" + isGPSEnabled);

            // getting network status
            isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            Log.v("locationServices", "isNetworkEnabled =" + isNetworkEnabled);

            //this.canGetLocation = true;
            if (isNetworkEnabled) {
                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, Util.LOCATION_REFRESH_TIME, Util.LOCATION_REFRESH_DISTANCE, (LocationListener) mContext);
                Log.d("getLocation", "Network");
                if (mLocationManager != null) {
                    if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    }
                    mLastLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (mLastLocation != null) {
                        latitude = mLastLocation.getLatitude();
                        longitude = mLastLocation.getLongitude();
                    }
                }
            }
            // if GPS Enabled get lat/long using GPS Services
            if (isGPSEnabled && mLastLocation == null) {
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, Util.LOCATION_REFRESH_TIME, Util.LOCATION_REFRESH_DISTANCE, (LocationListener) mContext);
                Log.d("getLocation", "GPS Enabled");
                if (mLocationManager != null) {
                    mLastLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (mLastLocation != null) {
                        latitude = mLastLocation.getLatitude();
                        longitude = mLastLocation.getLongitude();
                    }
                }
            }
        } catch (Exception e) {
            Log.e("getLocation", "Location Not Found");
        }
        return mLastLocation;
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        emitCurrentLocation();
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

    private void emitCurrentLocation(){
        if(socket != null && socket.connected()){
            final JSONObject jObject = new JSONObject();
            try {
                jObject.put("courierId", ServiceUser.getInstance().getId());
                jObject.put("longitude", longitude);
                jObject.put("latitude", latitude);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            socket.emit("courierLocation", jObject);
            Log.i("courierLocation", jObject.toString());
        }
    }
}
