package com.example.nicollettedessy.projectidea.data.entities;

import java.util.ArrayList;

/**
 * Created by pierrethelusma on 4/11/18.
 */

public class GooglePlacesSearchResponse {
    public ArrayList<GooglePlacesSearchResponseResult> results;

    public class GooglePlacesSearchResponseResult {
        public String name;
        public Geometry geometry;

        public class Geometry {

            public Location location;

            public class Location {
                public double lat;
                public double lng;
            }
        }
    }
}
