package com.example.nicollettedessy.projectidea.data.repositories;

import android.content.Context;
import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.Response;
import com.example.nicollettedessy.projectidea.GsonRequest;
import com.example.nicollettedessy.projectidea.HttpRequestQueue;
import com.example.nicollettedessy.projectidea.data.entities.SearchResponse;

/**
 * Created by pierrethelusma on 3/28/18.
 */

public class USDAFoodCompositionRepository {
    private final String SCHEME = "https";
    private final String AUTHORITY = "api.nal.usda.gov";
    private final String NDB_PATH = "ndb";
    private final String SEARCH_PATH = "search";
    private final String USDA_FOOD_COMPOSITION_API_KEY = "8NmKGTM0wRyd6nY7jGfOS9gD2GRJpvcpsGypgY3M";

    public USDAFoodCompositionRepository() {}

    public void GetFoodsBy(String searchCriteria, Context applicationContext, Response.Listener<SearchResponse> listener, Response.ErrorListener errorListener) {

        Uri uri = new Uri.Builder()
            .scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(NDB_PATH)
            .appendPath(SEARCH_PATH)
            .appendQueryParameter("format", "json")
            .appendQueryParameter("q", searchCriteria)
            .appendQueryParameter("sort", "n")
            .appendQueryParameter("max", "25")
            .appendQueryParameter("offset", "0")
            .appendQueryParameter("api_key", USDA_FOOD_COMPOSITION_API_KEY)
            .build();

        GsonRequest request = new GsonRequest(uri.toString(), SearchResponse.class, listener, errorListener);

        HttpRequestQueue.getInstance(applicationContext).addRequest(request);
    }
}
