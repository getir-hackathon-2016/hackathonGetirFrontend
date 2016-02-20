package com.getir.getirhackathon;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.json.JSONException;

import cz.msebera.android.httpclient.HttpEntity;

/**
 * Created by Emre on 20.02.2016.
 */
public class RestClient {

    private static final String BASE_URL = "http://192.168.1.95:9000";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url,AsyncHttpResponseHandler responseHandler) throws JSONException {
        client.addHeader("content-type", "application/json");
        client.get(getAbsoluteUrl(url), responseHandler);
    }

    public static void get(String url,RequestParams params, AsyncHttpResponseHandler responseHandler) throws JSONException {
        client.addHeader("content-type", "application/json");
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.addHeader("content-type", "application/json");
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(Context context, String url, HttpEntity entity, String contentType, ResponseHandlerInterface responseHandler){
        //client.addHeader("content-type", "application/json");
        client.post(context,getAbsoluteUrl(url),entity, contentType, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

}
