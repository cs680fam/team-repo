package com.example.nicollettedessy.projectidea.data.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pierrethelusma on 3/28/18.
 */
public class SearchResponseList {
    public String q;
    public String sr;
    public String ds;
    public int start;
    public int end;
    public int total;
    public String group;
    public String sort;
    public ArrayList<SearchResponseListItem> item;
}
