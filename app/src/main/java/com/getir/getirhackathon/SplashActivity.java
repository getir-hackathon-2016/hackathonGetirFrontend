package com.getir.getirhackathon;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.getir.getirhackathon.Dialogs.ServiceLoginDialog;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Emre on 20.02.2016.
 */
public class SplashActivity extends Activity {

    private Context mContext;
    private ImageButton logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Run Fabric for notify crashes.
        Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());
        mContext = this;
        setContentView(R.layout.splash_layout);

        logo = (ImageButton) findViewById(R.id.logo);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceLoginDialog dialog = new ServiceLoginDialog(mContext);
                dialog.show();

            }
        });
    }
}
