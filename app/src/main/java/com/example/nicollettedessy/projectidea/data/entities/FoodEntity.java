package com.example.nicollettedessy.projectidea.data.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.ArrayList;

/**
 * Created by pierrethelusma on 4/12/18.
 */

@Entity(tableName = "MyFood")
public class FoodEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "NDBNO")
    public String ndbno;

    @ColumnInfo(name = "Name")
    public String name;

    @ColumnInfo(name = "FoodGroup")
    public String fg;

    @TypeConverters(EntityTypePropertyConverter.class)
    @ColumnInfo(name = "Nutrients")
    public ArrayList<String> nutrients;
}
