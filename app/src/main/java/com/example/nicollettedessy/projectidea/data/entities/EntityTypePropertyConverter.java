package com.example.nicollettedessy.projectidea.data.entities;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by pierrethelusma on 4/22/18.
 */

public class EntityTypePropertyConverter {
    @TypeConverter
    public String fromArrayList(ArrayList<String> value) {
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().toJson(value, type);
    }

    @TypeConverter
    public ArrayList<String> toArrayList(String value) {
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(value, type);
    };

}
