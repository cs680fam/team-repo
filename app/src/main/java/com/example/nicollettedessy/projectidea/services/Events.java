package com.example.nicollettedessy.projectidea.services;

/**
 * Created by pierrethelusma on 4/20/18.
 */

public enum Events {
    FoodEntityInsertSucceeded("Food Entity Insert Succeeded"),
    FoodEntityGetSucceeded("Food Entity Get Succeeded"),
    FoodEntityGetFailed("Food Entity Get Failed");

    private final String _msg;

    Events(String msg) {
        _msg = msg;
    }

    public String msg() { return _msg; }
}
