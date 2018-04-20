package com.example.nicollettedessy.projectidea.data.database;

import android.content.Context;
import android.os.AsyncTask;

import com.example.nicollettedessy.projectidea.data.entities.FoodEntity;
import com.example.nicollettedessy.projectidea.services.IAsyncDatabaseListener;

/**
 * Created by pierrethelusma on 4/12/18.
 */

public class AddFoodAsyncDatabaseTask<T, V> extends AsyncTask<T, Void, V> {

    private final Context context;
    private final IAsyncDatabaseListener<V> listener;

    public AddFoodAsyncDatabaseTask(Context context, IAsyncDatabaseListener<V> listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected V doInBackground(T... params) {

        FoodEntity entity = new FoodEntity();

        entity.setNdbno((String) params[0]);
        ApplicationDatabaseProvider.getApplicationDatabase(this.context).repository().add(entity);

        return null;
    }

    @Override
    protected void onPostExecute(V param)
    {
        listener.onDatabaseResponse(param);
    }
}
