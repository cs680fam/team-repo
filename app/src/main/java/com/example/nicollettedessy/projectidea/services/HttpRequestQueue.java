package com.example.nicollettedessy.projectidea.services;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by pierrethelusma on 3/29/18.
 */

public class HttpRequestQueue {
    private static HttpRequestQueue instance;
    private static Context applicationContext;
    private RequestQueue requestQueue;

    private HttpRequestQueue(Context context) {
        applicationContext = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized HttpRequestQueue getInstance(Context context) {
        if (instance == null) {
            instance = new HttpRequestQueue(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(applicationContext.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addRequest(Request<T> request) {
        getRequestQueue().add(request);
    }
}
