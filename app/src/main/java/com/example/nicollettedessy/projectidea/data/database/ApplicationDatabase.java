package com.example.nicollettedessy.projectidea.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.nicollettedessy.projectidea.data.entities.FoodEntity;
import com.example.nicollettedessy.projectidea.data.repositories.IFoodEntityRepository;

/**
 * Created by pierrethelusma on 4/12/18.
 */

@Database(entities = { FoodEntity.class }, version = 1)

public abstract class ApplicationDatabase extends RoomDatabase {
    public abstract IFoodEntityRepository repository();
}
