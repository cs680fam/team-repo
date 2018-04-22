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

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            SearchResponseListItem item = (SearchResponseListItem) view.getTag();

            Context context = view.getContext();
            Intent intent = new Intent(context, FoodItemDetailActivity.class);
            intent.putExtra(FoodItemDetailFragment.ARG_NDBMO, item.ndbno);

            context.startActivity(intent);
        }
    };

    public SimpleItemRecyclerViewAdapter(FoodItemListActivity parent,
                                  List<SearchResponseListItem> items,
                                  boolean isTwoPane) {
        this.items = items;
        this.parent = parent;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fooditem_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tvContent.setText(items.get(position).name);

        holder.itemView.setTag(items.get(position));
        holder.itemView.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvContent;

        ViewHolder(View view) {
            super(view);
            tvContent = (TextView) view.findViewById(R.id.content);
        }
    }
}