package com.getir.getirhackathon.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.getir.getirhackathon.R;
import com.getir.getirhackathon.RestClient;
import com.getir.getirhackathon.ServiceMainActivity;
import com.getir.getirhackathon.Util;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Emre on 20.02.2016.
 */
public class ServiceLoginDialog extends Dialog {

    private EditText password_text;
    private Button button;
    private Context context;
    public ServiceLoginDialog(final Context context) {
        super(context);
        this.context = context;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.service_login_dialog);

        password_text = (EditText) findViewById(R.id.password_text);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = password_text.getText().toString();

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
                            Intent i = new Intent(context, ServiceMainActivity.class);
                            dismiss();
                            context.startActivity(i);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("RestClient.get()", e.getLocalizedMessage());
                }

            }
        });

    }
}
