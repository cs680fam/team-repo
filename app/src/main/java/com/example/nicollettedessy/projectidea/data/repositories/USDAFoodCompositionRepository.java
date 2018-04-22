package com.example.nicollettedessy.projectidea.data.repositories;

import android.content.Context;
import android.net.Uri;

import com.android.volley.Response;
import com.example.nicollettedessy.projectidea.data.entities.FoodReportResponse;
import com.example.nicollettedessy.projectidea.services.GsonRequest;
import com.example.nicollettedessy.projectidea.services.HttpRequestQueue;
import com.example.nicollettedessy.projectidea.data.entities.SearchResponse;

/**
 * Created by pierrethelusma on 3/28/18.
 */

public class USDAFoodCompositionRepository {
    private final String SCHEME = "https";
    private final String AUTHORITY = "api.nal.usda.gov";
    private final String NDB_PATH = "ndb";
    private final String SEARCH_PATH = "search";
    private final String V2_PATH = "V2";
    private final String FOOD_REPORT_PATH = "reports";
    private final String USDA_FOOD_COMPOSITION_API_KEY = "8NmKGTM0wRyd6nY7jGfOS9gD2GRJpvcpsGypgY3M";

    public USDAFoodCompositionRepository() {}

    public void GetFoodsBy(String searchCriteria, Context applicationContext, Response.Listener<SearchResponse> listener, Response.ErrorListener errorListener) {

        Uri uri = new Uri.Builder()
            .scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(NDB_PATH)
            .appendPath(SEARCH_PATH)
            .appendQueryParameter("format", "json")
            .appendQueryParameter("ds", "Standard Reference")
            .appendQueryParameter("q", searchCriteria)
            .appendQueryParameter("sort", "r")
            .appendQueryParameter("max", "25")
            .appendQueryParameter("offset", "0")
            .appendQueryParameter("api_key", USDA_FOOD_COMPOSITION_API_KEY)
            .build();

        GsonRequest request = new GsonRequest(uri.toString(), SearchResponse.class, listener, errorListener);

        HttpRequestQueue.getInstance(applicationContext).addRequest(request);
    }

    public void GetFoodBy(String ndbno, Context applicationContext, Response.Listener<FoodReportResponse> listener, Response.ErrorListener errorListener)
    {
        Uri uri = new Uri.Builder()
                .scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(NDB_PATH)
                .appendPath(V2_PATH)
                .appendPath(FOOD_REPORT_PATH)
                .appendQueryParameter("format", "json")
                .appendQueryParameter("ndbno", ndbno)
                .appendQueryParameter("type", "f")
                .appendQueryParameter("api_key", USDA_FOOD_COMPOSITION_API_KEY)
                .build();

        GsonRequest request = new GsonRequest(uri.toString(), FoodReportResponse.class, listener, errorListener);

        HttpRequestQueue.getInstance(applicationContext).addRequest(request);
    }
}
