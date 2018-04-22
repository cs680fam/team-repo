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
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.nicollettedessy.projectidea.data.database.AddFoodAsyncDatabaseTask;
import com.example.nicollettedessy.projectidea.data.database.GetFoodByNdbnoAsyncDatabaseTask;
import com.example.nicollettedessy.projectidea.data.entities.FoodEntity;
import com.example.nicollettedessy.projectidea.data.entities.FoodReportResponse;
import com.example.nicollettedessy.projectidea.data.entities.SearchResponseListItem;
import com.example.nicollettedessy.projectidea.data.repositories.USDAFoodCompositionRepository;
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

    private final USDAFoodCompositionRepository repository = new USDAFoodCompositionRepository();
    private FloatingActionButton _fab;
    private FoodReportResponse _item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fooditem_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        final String ndbno = getIntent().getStringExtra(FoodItemDetailFragment.ARG_NDBMO);

        getFoodBy(ndbno);

        _fab = (FloatingActionButton) findViewById(R.id.fab);
        _fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddFoodAsyncDatabaseTask<FoodReportResponse, Object>(getApplicationContext(), new IAsyncDatabaseListener<Object>() {
                    @Override
                    public void onDatabaseResponse(Object response) {
                        Log.d(Events.FoodEntityInsertSucceeded.name()  , Events.FoodEntityInsertSucceeded.msg());
                        Toast.makeText(getApplicationContext(), "Food has been added", Toast.LENGTH_LONG).show();
                    }
                }).execute(_item);
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void getFoodBy(String ndbno) {
        repository.GetFoodBy(ndbno, getApplicationContext(), this.getListener(), this.getErrorListener());
    }

    private Response.Listener<FoodReportResponse> getListener() {
        return new Response.Listener<FoodReportResponse>() {

            @Override
            public void onResponse(FoodReportResponse response) {

                _item = response;

                Bundle arguments = new Bundle();
                arguments.putParcelable(FoodItemDetailFragment.ARG_FOOD_REPORT_RESPONSE,
                        response);

                FoodItemDetailFragment fragment = new FoodItemDetailFragment();
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fooditem_detail_container, fragment)
                        .commit();
            }
        };
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

    private Response.ErrorListener getErrorListener() {
        return new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        };
    }
}
