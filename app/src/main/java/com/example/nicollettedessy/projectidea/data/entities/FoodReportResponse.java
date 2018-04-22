package com.example.nicollettedessy.projectidea.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by pierrethelusma on 4/8/18.
 */

public class FoodReportResponse implements Parcelable {
    public ArrayList<FoodContainer> foods;

    protected FoodReportResponse(Parcel in) {
    }

    public static final Creator<FoodReportResponse> CREATOR = new Creator<FoodReportResponse>() {
        @Override
        public FoodReportResponse createFromParcel(Parcel in) {
            return new FoodReportResponse(in);
        }

        @Override
        public FoodReportResponse[] newArray(int size) {
            return new FoodReportResponse[size];
        }
    };

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

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
