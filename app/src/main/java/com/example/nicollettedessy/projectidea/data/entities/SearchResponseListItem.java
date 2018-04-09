package com.example.nicollettedessy.projectidea.data.entities;

/**
 * Created by pierrethelusma on 3/28/18.
 */
public class SearchResponseListItem {
    public int offset;
    public String group;
    public String name;
    public String ndbno;
    public String ds;

    public String getText()
    {
        return name;
    }
}
