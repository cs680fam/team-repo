package com.example.nicollettedessy.projectidea;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.nicollettedessy.projectidea.data.entities.FoodResponse;
import com.example.nicollettedessy.projectidea.data.entities.SearchResponse;
import com.example.nicollettedessy.projectidea.data.entities.SearchResponseListItem;
import com.example.nicollettedessy.projectidea.data.repositories.USDAFoodCompositionRepository;
import com.example.nicollettedessy.projectidea.services.FoodItemCollectionProvider;

/**
 * A fragment representing a single FoodItem detail screen.
 * This fragment is either contained in a {@link FoodItemListActivity}
 * in two-pane mode (on tablets) or a {@link FoodItemDetailActivity}
 * on handsets.
 */
public class FoodItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "ndbno";

    /**
     * The dummy content this fragment is presenting.
     */
    private SearchResponseListItem mItem;

    private final USDAFoodCompositionRepository repository = new USDAFoodCompositionRepository();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FoodItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = FoodItemCollectionProvider.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            getFoodBy(mItem.ndbno);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.name);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fooditem_detail, container, false);

        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.fooditem_detail)).setText(mItem.group);
        }

        return rootView;
    }

    private void getFoodBy(String ndbno) {
        repository.GetFoodBy(ndbno, getContext(), this.getListener(), this.getErrorListener());
    }

    private Response.Listener<FoodResponse> getListener() {
        return new Response.Listener<FoodResponse>() {

            @Override
            public void onResponse(FoodResponse response) {
                System.out.println(response);
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
