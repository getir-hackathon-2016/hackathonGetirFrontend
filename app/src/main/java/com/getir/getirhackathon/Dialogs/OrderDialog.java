package com.getir.getirhackathon.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Chronometer;

import com.crashlytics.android.Crashlytics;
import com.getir.getirhackathon.Objects.User;
import com.getir.getirhackathon.R;
import com.getir.getirhackathon.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by Emre on 21.02.2016.
 */
public class OrderDialog extends Dialog {

    Context context;
    Chronometer timer;
    FancyButton button_finish;
    String courierId;

    public OrderDialog(final Context context, final String courierId) {
        super(context);
        this.context = context;
        setContentView(R.layout.order_dialog_layout);
        setCanceledOnTouchOutside(false);
        timer = (Chronometer) findViewById(R.id.chronometer1);
        timer.setTextColor(context.getResources().getColor(R.color.blue_light));
        timer.setTextSize(30);
        button_finish = (FancyButton) findViewById(R.id.button_finish);
        this.courierId = courierId;

        timer.start();

        button_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Util.socket != null){
                    final JSONObject jObject = new JSONObject();
                    try {
                        jObject.put("courierId", courierId);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("newOrder", e.getLocalizedMessage());
                        Crashlytics.log("newOrder" + "-" + e.getLocalizedMessage());
                    }
                    if (Util.socket != null) {
                        Util.socket.emit("JobAccomplished", jObject);
                }
                dismiss();
            }
        }});

        button_finish.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    button_finish.setGhost(false);
                    button_finish.setBackgroundColor(context.getResources().getColor(R.color.blue_light));
                    button_finish.setTextColor(context.getResources().getColor(R.color.white));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    button_finish.setGhost(true);
                    button_finish.setTextColor(context.getResources().getColor(R.color.blue_light));
                }
                return false;
            }
        });



    }
}
