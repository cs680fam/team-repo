package com.example.nicollettedessy.projectidea.data.entities;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by pierrethelusma on 4/8/18.
 */

public class FoodReportResponse {
    public ArrayList<FoodContainer> foods;

    public class FoodContainer {

        public Food food;

        public class Food {

            public Description desc;
            public Ingredient ing;
            public ArrayList<Nutrient> nutrients;

            public ArrayList<String> getNutrientsAsStringArrayList() {
                return nutrients
                        .stream()
                        .map(n -> n.getText())
                        .collect(Collectors.toCollection(ArrayList::new));
            }

            public class Ingredient {
                public String desc;
            }

            public class Nutrient {
                public int nutrient_id;
                public String name;
                public String derivation;
                public String group;
                public String unit;
                public String value;

                public class Measure {
                    public String label;
                    public double eqv;
                    public String eunit;
                    public double qty;
                    public String value;
                }

                public String getText() {
                    return String.format("%s%s of %s", value, unit, name);
                }
            }

            public class Description {
                public String ndbno;
                public String name;
                public String manu;
                public String sd;
                public String group;
                public String sn;
                public String cn;
                public String ru;
                public String fg;
            }
        }
    }
}
