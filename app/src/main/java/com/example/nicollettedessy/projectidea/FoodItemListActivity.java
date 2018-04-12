package com.example.nicollettedessy.projectidea;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.nicollettedessy.projectidea.data.entities.SearchResponse;
import com.example.nicollettedessy.projectidea.data.entities.SearchResponseListItem;
import com.example.nicollettedessy.projectidea.data.repositories.USDAFoodCompositionRepository;
import com.example.nicollettedessy.projectidea.services.FoodItemCollectionProvider;
import com.example.nicollettedessy.projectidea.services.SimpleItemRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An activity representing a list of FoodItems. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link FoodItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class FoodItemListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean isTwoPane;
    private final USDAFoodCompositionRepository repository = new USDAFoodCompositionRepository();
    private ArrayList<SearchResponseListItem> foodItems = null;
    private ProgressBar spinner = null;
    private TextView noResults = null;

    private SimpleItemRecyclerViewAdapter simpleItemRecyclerViewAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fooditem_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        spinner = (ProgressBar) findViewById(R.id.progressBar);
        noResults = (TextView) findViewById(R.id.noResults);

        makeNoResultsInvisible();
        makeSpinnerInvisible();

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (findViewById(R.id.fooditem_detail_container) != null) {
            isTwoPane = true;
        }

        foodItems = FoodItemCollectionProvider.ITEMS;

        handleIntent(getIntent());
    }

    private void makeNoResultsInvisible()
    {
        noResults.setVisibility(View.GONE);
    }

    private void makeNoResultsVisible()
    {
        noResults.setVisibility(View.VISIBLE);
    }

    private void makeSpinnerInvisible()
    {
        spinner.setVisibility(View.GONE);
    }

    private void makeSpinnerVisible()
    {
        spinner.setVisibility(View.VISIBLE);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        simpleItemRecyclerViewAdapter = new SimpleItemRecyclerViewAdapter(this, foodItems, isTwoPane);

        recyclerView.setAdapter(simpleItemRecyclerViewAdapter);
    }

    @Override
    protected void onNewIntent(Intent intent) {

        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        View recyclerView = findViewById(R.id.fooditem_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        String intentAction = intent.getAction();

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            makeSpinnerVisible();

            FoodItemCollectionProvider.clear();
            simpleItemRecyclerViewAdapter.notifyDataSetChanged();

            searchBy(query);
        }
    }

    private void searchBy(String query) {
        repository.GetFoodsBy(query, getApplicationContext(), this.getListener(), this.getErrorListener());
    }

    private Response.Listener<SearchResponse> getListener() {
        return new Response.Listener<SearchResponse>() {

            @Override
            public void onResponse(SearchResponse response) {

                makeSpinnerInvisible();

                if(response == null || response.list == null)
                {
                    // Show no results
                    makeNoResultsVisible();
                    return;
                }

                if(response.list.total == 0 || response.list.item.size() == 0)
                {
                    // Show no results
                    makeNoResultsVisible();
                    return;
                }

                for (SearchResponseListItem item : response.list.item) {
                    FoodItemCollectionProvider.addItem(item);
                    simpleItemRecyclerViewAdapter.notifyDataSetChanged();
                }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, EnterMeals.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
