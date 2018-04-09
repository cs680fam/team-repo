package com.example.nicollettedessy.projectidea.data.entities;

import java.util.ArrayList;

/**
 * Created by pierrethelusma on 4/8/18.
 */

public class FoodResponse {
    public ArrayList<FoodContainer> foods;

    public class FoodContainer {

        public Food food;

        public class Food {

            public Description desc;

            public class Description {
                public String name;
                public String fg;
            }
        }
    }
}
