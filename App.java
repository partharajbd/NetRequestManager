package com.partharaj;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import java.net.CookieHandler;
import java.net.CookieManager;

public class App extends Application {

    public static final int PROJECT_CODE = 1;
    public static String PROJECT_NAME = null;

    private static App app;
    private RequestQueue mRequestQueue;
    public final String TAG = this.getClass().getSimpleName();
    private static App context;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        context = this;
        CookieHandler.setDefault(new CookieManager());
    }

    public static App getApp(){
        return context;
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context);
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

}
