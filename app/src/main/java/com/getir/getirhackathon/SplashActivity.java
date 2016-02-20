package com.getir.getirhackathon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.getir.getirhackathon.Dialogs.ServiceLoginDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import io.fabric.sdk.android.Fabric;
import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by Emre on 20.02.2016.
 */
public class SplashActivity extends Activity {

    private Context mContext;
    private ImageButton logo;
    private FancyButton button_login;
    private EditText username_text, password_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Run Fabric for notify crashes.
        Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());
        mContext = this;
        setContentView(R.layout.splash_layout);

        logo = (ImageButton) findViewById(R.id.logo);

//        logo.setOnTouchListener(new View.OnTouchListener() {
//            Handler handler = new Handler();
//
//            int numberOfTaps = 0;
//            long lastTapTimeMs = 0;
//            long touchDownMs = 0;
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        touchDownMs = System.currentTimeMillis();
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        handler.removeCallbacksAndMessages(null);
//                        lastTapTimeMs = System.currentTimeMillis();
//                        if ((System.currentTimeMillis() - touchDownMs) > ViewConfiguration.getTapTimeout()) {
//                            //it was not a tap
//                            numberOfTaps = 0;
//                            lastTapTimeMs = 0;
//                            break;
//                        } else if (numberOfTaps > 0 && (System.currentTimeMillis() - lastTapTimeMs) < ViewConfiguration.getDoubleTapTimeout()) {
//                            numberOfTaps += 1;
//                        } else {
//                            numberOfTaps = 1;
//                        }
//
//                        if (numberOfTaps == 3) {
//                            ServiceLoginDialog dialog = new ServiceLoginDialog(mContext);
//                            dialog.show();
//                        }
//                }
//                return true;
//            }
//        });

        logo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ServiceLoginDialog dialog = new ServiceLoginDialog(mContext);
                dialog.show();
                return false;
            }
        });


        username_text = (EditText) findViewById(R.id.username_text);
        password_text = (EditText) findViewById(R.id.password_text);

        button_login = (FancyButton) findViewById(R.id.button_login);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = password_text.getText().toString();
                String username = username_text.getText().toString();
                RequestParams params = new RequestParams();
                //params.put("password", pass);
                try {
                    RestClient.get("/api/couriers/password/" + pass, new JsonHttpResponseHandler() {
                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        }

                        @Override
                        public void onStart() {
                            Log.i("get", "Started");
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            super.onSuccess(statusCode, headers, response);
                            Log.i("get", response.toString());
                            Util.parseServiceJson(response);
                            Intent i = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(i);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("RestClient.get()", e.getLocalizedMessage());
                }
            }
        });

        button_login.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    button_login.setGhost(false);
                    button_login.setBackgroundColor(mContext.getResources().getColor(R.color.blue_light));
                    button_login.setTextColor(mContext.getResources().getColor(R.color.white));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    button_login.setGhost(true);
                    button_login.setTextColor(mContext.getResources().getColor(R.color.blue_light));
                }
                return false;
            }
        });
    }

}