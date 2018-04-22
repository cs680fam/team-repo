package com.example.nicollettedessy.projectidea.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.example.nicollettedessy.projectidea.data.entities.EntityTypePropertyConverter;
import com.example.nicollettedessy.projectidea.data.entities.FoodEntity;
import com.example.nicollettedessy.projectidea.data.repositories.IFoodEntityRepository;

/**
 * Created by pierrethelusma on 4/12/18.
 */

@Database(entities = { FoodEntity.class }, version = 1)
@TypeConverters(EntityTypePropertyConverter.class)
public abstract class ApplicationDatabase extends RoomDatabase {
    public abstract IFoodEntityRepository repository();
}
