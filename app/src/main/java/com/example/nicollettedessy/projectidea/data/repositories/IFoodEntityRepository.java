package com.example.nicollettedessy.projectidea.data.repositories;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.nicollettedessy.projectidea.data.entities.FoodEntity;

import java.util.List;

/**
 * Created by pierrethelusma on 4/12/18.
 */

@Dao
public interface IFoodEntityRepository {

    @Query("SELECT * FROM MyFood")
    List<FoodEntity> getFood();

    @Insert
    void add(FoodEntity foodEntity);

    @Delete
    void delete(FoodEntity foodEntity);

    @Query("SELECT * FROM MyFood WHERE NDBNO == :ndbno")
    FoodEntity getFood(String ndbno);
}