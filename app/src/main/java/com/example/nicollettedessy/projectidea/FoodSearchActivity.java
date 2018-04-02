package com.example.nicollettedessy.projectidea;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.nicollettedessy.projectidea.data.entities.SearchResponse;
import com.example.nicollettedessy.projectidea.data.repositories.USDAFoodCompositionRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

public class FoodSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        EditText search = (EditText) findViewById(R.id.etSearchCriteria);
        setSupportActionBar(toolbar);

        USDAFoodCompositionRepository repository = new USDAFoodCompositionRepository();

        repository.GetFoodsBy("butter", getApplicationContext(), this.getListener(), this.getErrorListener());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private Response.Listener<SearchResponse> getListener() {
        return new Response.Listener<SearchResponse>() {

            @Override
            public void onResponse(SearchResponse response) {
                System.out.println(response.toString());
            }
        };
    }

    private Response.ErrorListener getErrorListener() {
        return new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        };
    }

}
