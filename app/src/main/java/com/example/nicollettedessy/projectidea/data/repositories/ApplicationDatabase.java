package com.example.nicollettedessy.projectidea.data.repositories;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.nicollettedessy.projectidea.data.entities.FoodEntity;

/**
 * Created by pierrethelusma on 4/12/18.
 */

@Database(entities = { FoodEntity.class }, version = 1)

public abstract class ApplicationDatabase extends RoomDatabase {
    public abstract IFoodEntityRepository repository();
}
