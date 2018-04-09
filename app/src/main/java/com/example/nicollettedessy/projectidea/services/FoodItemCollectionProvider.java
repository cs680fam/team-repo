package com.example.nicollettedessy.projectidea.services;

import com.example.nicollettedessy.projectidea.data.entities.SearchResponseListItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pierrethelusma on 4/8/18.
 */

public class FoodItemCollectionProvider {

    public static final ArrayList<SearchResponseListItem> ITEMS = new ArrayList<SearchResponseListItem>();

    public static final Map<String, SearchResponseListItem> ITEM_MAP = new HashMap<String, SearchResponseListItem>();

    public static void addItem(SearchResponseListItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.ndbno, item);
    }
}
