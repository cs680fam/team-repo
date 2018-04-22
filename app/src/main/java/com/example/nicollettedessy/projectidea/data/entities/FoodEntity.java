package com.example.nicollettedessy.projectidea.data.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by pierrethelusma on 4/12/18.
 */

@Entity(tableName = "MyFood")
public class FoodEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "NDBNO")
    public String ndbno;
}
