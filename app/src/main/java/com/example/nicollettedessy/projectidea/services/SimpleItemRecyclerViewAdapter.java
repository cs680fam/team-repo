package com.example.nicollettedessy.projectidea.services;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nicollettedessy.projectidea.FoodItemDetailActivity;
import com.example.nicollettedessy.projectidea.FoodItemDetailFragment;
import com.example.nicollettedessy.projectidea.FoodItemListActivity;
import com.example.nicollettedessy.projectidea.R;
import com.example.nicollettedessy.projectidea.data.entities.SearchResponseListItem;

import java.util.List;

/**
 * Created by pierrethelusma on 4/11/18.
 */

public class SimpleItemRecyclerViewAdapter
        extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

    private final FoodItemListActivity parent;
    private final List<SearchResponseListItem> items;
    private final boolean isTwoPane;
    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SearchResponseListItem item = (SearchResponseListItem) view.getTag();
            if (isTwoPane) {
                Bundle arguments = new Bundle();
                arguments.putString(FoodItemDetailFragment.ARG_ITEM_ID, item.ndbno);
                FoodItemDetailFragment fragment = new FoodItemDetailFragment();
                fragment.setArguments(arguments);
                parent.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fooditem_detail_container, fragment)
                        .commit();
            } else {
                Context context = view.getContext();
                Intent intent = new Intent(context, FoodItemDetailActivity.class);
                intent.putExtra(FoodItemDetailFragment.ARG_ITEM_ID, item.ndbno);

                context.startActivity(intent);
            }
        }
    };

    public SimpleItemRecyclerViewAdapter(FoodItemListActivity parent,
                                  List<SearchResponseListItem> items,
                                  boolean isTwoPane) {
        this.items = items;
        this.parent = parent;
        this.isTwoPane = isTwoPane;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fooditem_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tvId.setText(items.get(position).ndbno);
        holder.tvContent.setText(items.get(position).name);

        holder.itemView.setTag(items.get(position));
        holder.itemView.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvId;
        final TextView tvContent;

        ViewHolder(View view) {
            super(view);
            tvId = (TextView) view.findViewById(R.id.id_text);
            tvContent = (TextView) view.findViewById(R.id.content);
        }
    }
}