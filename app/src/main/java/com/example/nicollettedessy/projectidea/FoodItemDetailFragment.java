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
import com.example.nicollettedessy.projectidea.data.entities.SearchResponseListItem;
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
    public static final String ARG_FOOD_REPORT_RESPONSE = "food_report_response";

    private final USDAFoodCompositionRepository repository = new USDAFoodCompositionRepository();
    private FragmentActivity _activity;
    private View _rootView;
    private FoodReportResponse _item;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FoodItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_FOOD_REPORT_RESPONSE)) {
            _item = getArguments().getParcelable(ARG_FOOD_REPORT_RESPONSE);
        }

        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle((CharSequence) _item.foods.get(0).food.desc.sd);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _rootView = inflater.inflate(R.layout.fooditem_detail, container, false);

        if (_item != null) {
            ListView lvNutrient = (ListView) _rootView.findViewById(R.id.fooditem_detail_nutrients);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.nutrient_list_item, _item.foods.get(0).food.getNutrientsAsStringArrayList()) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    view.setMinimumHeight(100);
                    return view;
                }
            };

            lvNutrient.setAdapter(adapter);

            ViewGroup.LayoutParams params = lvNutrient.getLayoutParams();
            params.height = 100 * _item.foods.get(0).food.nutrients.size();

            lvNutrient.setLayoutParams(params);
            lvNutrient.requestLayout();

            ((TextView) _rootView.findViewById(R.id.fooditem_detail_food_group)).setText(_item.foods.get(0).food.desc.fg);
        }

        return _rootView;
    }
}
