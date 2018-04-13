package com.example.nicollettedessy.projectidea.services;

/**
 * Created by pierrethelusma on 4/12/18.
 */

public interface IAsyncDatabaseListener<T> {
    /** Called when a response is received. */
    void onDatabaseResponse(T response);
}
