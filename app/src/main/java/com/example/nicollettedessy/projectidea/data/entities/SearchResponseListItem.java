package com.example.nicollettedessy.projectidea.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pierrethelusma on 3/28/18.
 */
public class SearchResponseListItem implements Parcelable {
    public int offset;
    public String group;
    public String name;
    public String ndbno;
    public String ds;

    protected SearchResponseListItem(Parcel in) {
        offset = in.readInt();
        group = in.readString();
        name = in.readString();
        ndbno = in.readString();
        ds = in.readString();
    }

    public static final Creator<SearchResponseListItem> CREATOR = new Creator<SearchResponseListItem>() {
        @Override
        public SearchResponseListItem createFromParcel(Parcel in) {
            return new SearchResponseListItem(in);
        }

        @Override
        public SearchResponseListItem[] newArray(int size) {
            return new SearchResponseListItem[size];
        }
    };

    public String getText()
    {
        return name;
    }

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
        dest.writeInt(offset);
        dest.writeString(group);
        dest.writeString(name);
        dest.writeString(ndbno);
        dest.writeString(ds);
    }
}
