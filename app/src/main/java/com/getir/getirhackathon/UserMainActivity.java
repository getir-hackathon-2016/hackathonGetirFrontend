package com.getir.getirhackathon;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.getir.getirhackathon.Adapters.RecyclerViewAdapter;
import com.getir.getirhackathon.Dialogs.SendAddressDialog;
import com.getir.getirhackathon.Dialogs.UserProfileDialog;
import com.getir.getirhackathon.Objects.ServiceUser;
import com.getir.getirhackathon.Objects.User;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;

public class UserMainActivity extends Activity{

    private TextView settings_button, list_button, language_icon, user_icon_text, cart_down_icon, logout_icon, back_button_left_drawer, name, languages_text;
    private TextView versionBuild, address_icon;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Spinner spinner;
    private LinearLayout profileLayout, prevCartLayout, logoutLayout, adresses_layout;
    private ImageButton en_button, tr_button;
    private DrawerLayout drawerLayout;
    private RelativeLayout rightDrawerLayout, leftDrawerLayout;
    private Context mContext;
    private Socket socket;
    private List<ServiceUser> serviceUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Run Fabric for notify crashes.
        Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());
        mContext = this;

        setContentView(R.layout.user_main_layout);

        SendAddressDialog dialog = new SendAddressDialog(mContext);
        dialog.show();

        //To initiliaze view.
        initView();

        //Start socketIO
        startSocket();
    }

    private void initView() {
        settings_button = (TextView) findViewById(R.id.settings_button);
        settings_button.setTypeface(Util.getFontAwesome(mContext));
        settings_button.setTextSize(30);
        settings_button.setTextColor(getResources().getColor(R.color.black));

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

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

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

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

        address_icon = (TextView) findViewById(R.id.adress_icon);
        address_icon.setTypeface(Util.getFontAwesome(mContext));
        address_icon.setTextColor(mContext.getResources().getColor(R.color.blue_light));

        profileLayout = (LinearLayout) findViewById(R.id.profile_layout);
        prevCartLayout = (LinearLayout) findViewById(R.id.prev_cart_layout);
        logoutLayout = (LinearLayout) findViewById(R.id.logout_layout);
        adresses_layout = (LinearLayout) findViewById(R.id.adresses_layout);

        adresses_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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
                UserProfileDialog profile = new UserProfileDialog(mContext);
                drawerLayout.closeDrawer(leftDrawerLayout);
                profile.show();
            }
        });

        logoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(leftDrawerLayout);
                Util.socket.disconnect();
                finish();
            }
        });
        spinner = (Spinner) findViewById(R.id.spinner);

        List<String> categories = new ArrayList<String>();
        categories.add("Automobile");
        categories.add("Business Services");
        categories.add("Computers");
        categories.add("Education");
        categories.add("Personal");
        categories.add("Travel");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        spinner.setAdapter(dataAdapter);
        //gridView.setAdapter();
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

    private void startSocket() {
        IO.Options opts = new IO.Options();
        opts.forceNew = true;

        try {
            Util.socket = IO.socket(Util.SERVER_URL, opts);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        final JSONObject jObject = new JSONObject();
        try {
            jObject.put("id", User.getInstance().getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Util.socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("response", "connected");
                    }
                });
            }

        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Util.socket.emit("userLogout", jObject);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("response", "disconnected");
                    }
                });

            }

        }).on("sortedCouriersList", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("response", args[0].toString());
                        serviceUsers = new ArrayList<ServiceUser>();
                        JSONArray serviceUsersJson = (JSONArray) args[0];
                        for(int i = 0; i<serviceUsersJson.length(); i++){
                            try {
                                serviceUsers.add(ServiceUser.objectFromJson(serviceUsersJson.getJSONObject(i)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Crashlytics.log("UserMainActivity" + "-" + e.getLocalizedMessage());
                            }
                        }
                        Log.i("couriers", serviceUsers.toString());
                        fillRecyclerView();
                    }
                });
            }

        });
        Util.socket.connect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Util.socket != null) {
            Util.socket.disconnect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Util.socket.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (Util.socket != null) {
            Util.socket.disconnect();
        }
    }

    private void fillRecyclerView(){
        Util.sortServiceUsers(serviceUsers);
        // specify an adapter (see also next example)
        mAdapter = new RecyclerViewAdapter(serviceUsers, mContext);
        recyclerView.setAdapter(mAdapter);
    }
}
