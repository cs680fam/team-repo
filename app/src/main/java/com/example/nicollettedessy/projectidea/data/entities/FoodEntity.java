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
    private int id;

    @ColumnInfo(name = "NDBNO")
    private String ndbno;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNdbno() {
        return ndbno;
    }

    public void setNdbno(String ndbno) {
        this.ndbno = ndbno;
    }
}
