package com.example.nicollettedessy.projectidea.data.database;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.nicollettedessy.projectidea.data.database.ApplicationDatabase;

/**
 * Created by pierrethelusma on 4/12/18.
 */

public class ApplicationDatabaseProvider {

    private static ApplicationDatabase instance;

    public static ApplicationDatabase getApplicationDatabase(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ApplicationDatabase.class, "application-database").build();
        }
        return instance;
    }

    public static void destroy()
    {
        instance = null;
    }
}
