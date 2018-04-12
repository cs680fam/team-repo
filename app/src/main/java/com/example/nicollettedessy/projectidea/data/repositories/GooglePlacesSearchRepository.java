package com.example.nicollettedessy.projectidea.data.repositories;

import android.content.Context;
import android.net.Uri;

import com.android.volley.Response;
import com.example.nicollettedessy.projectidea.data.entities.GooglePlacesSearchResponse;
import com.example.nicollettedessy.projectidea.data.entities.SearchResponse;
import com.example.nicollettedessy.projectidea.services.GsonRequest;
import com.example.nicollettedessy.projectidea.services.HttpRequestQueue;

/**
 * Created by pierrethelusma on 4/11/18.
 */

public class GooglePlacesSearchRepository {

    private final String SCHEME = "https";
    private final String AUTHORITY = "maps.googleapis.com";
    private final String MAPS_PATH = "maps";
    private final String API_PATH = "api";
    private final String PLACE_PATH = "place";
    private final String NEAR_BY_SEARCH_PATH = "nearbysearch";
    private final String JSON_PATH = "json";
    private final String GOOGLE_MAPS_API_KEY = "AIzaSyCb4CpZeU6e_hOhXJHqT1Qy1zzeMGQi4Z8";

    public GooglePlacesSearchRepository() {}

    public void GetNearByEntitiesBy(Context applicationContext, String type, double latitude, double longitude, int radiusInMeters, Response.Listener<GooglePlacesSearchResponse> listener, Response.ErrorListener errorListener)
    {
        Uri uri = new Uri.Builder()
                .scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(MAPS_PATH)
                .appendPath(API_PATH)
                .appendPath(PLACE_PATH)
                .appendPath(NEAR_BY_SEARCH_PATH)
                .appendPath(JSON_PATH)
                .appendQueryParameter("location", String.format("%f,%f", latitude, longitude))
                .appendQueryParameter("radius", String.valueOf(radiusInMeters))
                .appendQueryParameter("type", type)
                .appendQueryParameter("sensor", "true")
                .appendQueryParameter("key", GOOGLE_MAPS_API_KEY)
                .build();

        GsonRequest request = new GsonRequest(uri.toString(), GooglePlacesSearchResponse.class, listener, errorListener);

        HttpRequestQueue.getInstance(applicationContext).addRequest(request);
    }
}
