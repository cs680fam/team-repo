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

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "NBDNO")
    private String nbdno;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNbdno() {
        return nbdno;
    }

    public void setNbdno(String nbdno) {
        this.nbdno = nbdno;
    }
}
