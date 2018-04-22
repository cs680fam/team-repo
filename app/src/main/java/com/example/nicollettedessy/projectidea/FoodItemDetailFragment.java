package com.example.nicollettedessy.projectidea;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.nicollettedessy.projectidea.data.entities.FoodReportResponse;
import com.example.nicollettedessy.projectidea.data.repositories.USDAFoodCompositionRepository;

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
    public static final String ARG_NDBMO = "ndbno";

    private final USDAFoodCompositionRepository repository = new USDAFoodCompositionRepository();
    private FragmentActivity _activity;
    private View _rootView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FoodItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_NDBMO)) {
            String ndbno = getArguments().getString(ARG_NDBMO);

            getFoodBy(ndbno);
        }

        _activity = this.getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _rootView = inflater.inflate(R.layout.fooditem_detail, container, false);

        return _rootView;
    }

    private void getFoodBy(String ndbno) {
        repository.GetFoodBy(ndbno, getContext(), this.getListener(), this.getErrorListener());
    }

    private Response.Listener<FoodReportResponse> getListener() {
        return new Response.Listener<FoodReportResponse>() {

            @Override
            public void onResponse(FoodReportResponse response) {
                Activity activity = _activity;
                CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
                if (appBarLayout != null) {
                    appBarLayout.setTitle((CharSequence) response.foods.get(0).food.desc.sd);
                }

                if (response != null) {
                    ListView lvNutrient = (ListView) activity.findViewById(R.id.fooditem_detail_nutrients);

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.nutrient_list_item, response.foods.get(0).food.getNutrientsAsStringArrayList()) {
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            View view = super.getView(position, convertView, parent);
                            view.setMinimumHeight(100);
                            return view;
                        }
                    };
                    lvNutrient.setAdapter(adapter);

                    ViewGroup.LayoutParams params = lvNutrient.getLayoutParams();
                    params.height = 100 * response.foods.get(0).food.nutrients.size();

                    lvNutrient.setLayoutParams(params);
                    lvNutrient.requestLayout();

                    ((TextView) _rootView.findViewById(R.id.fooditem_detail_food_group)).setText(response.foods.get(0).food.desc.fg);
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
}
