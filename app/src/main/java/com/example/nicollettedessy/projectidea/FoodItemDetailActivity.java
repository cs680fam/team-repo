package com.example.nicollettedessy.projectidea;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.EventLog;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.example.nicollettedessy.projectidea.data.database.AddFoodAsyncDatabaseTask;
import com.example.nicollettedessy.projectidea.data.database.GetFoodByNdbnoAsyncDatabaseTask;
import com.example.nicollettedessy.projectidea.data.entities.FoodEntity;
import com.example.nicollettedessy.projectidea.services.Events;
import com.example.nicollettedessy.projectidea.services.IAsyncDatabaseListener;

import java.util.List;

/**
 * An activity representing a single FoodItem detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link FoodItemListActivity}.
 */
public class FoodItemDetailActivity extends AppCompatActivity {

    private FoodEntity entity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fooditem_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        final String ndbno = getIntent().getStringExtra(FoodItemDetailFragment.ARG_NDBMO);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(entity == null) {
                new AddFoodAsyncDatabaseTask<String, Object>(getApplicationContext(), new IAsyncDatabaseListener<Object>() {
                    @Override
                    public void onDatabaseResponse(Object response) {
                        Log.d(Events.FoodEntityInsertSucceeded.name()  , Events.FoodEntityInsertSucceeded.msg());
                    }
                }).execute(ndbno);
            }
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putString(FoodItemDetailFragment.ARG_NDBMO,
                    getIntent().getStringExtra(FoodItemDetailFragment.ARG_NDBMO));
            FoodItemDetailFragment fragment = new FoodItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fooditem_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, FoodItemListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
