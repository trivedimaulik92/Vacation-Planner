package com.example.maulik.vacation_planner_2.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/*
Place - This class is POJO representing a place/location
 */
@Entity(tableName = "places")
public class Place {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "placeid")
    private final String mId;

    @NonNull
    @ColumnInfo(name = "title")
    private final String title;

    @NonNull
    @ColumnInfo(name = "latitude")
    private final double latitude;


    @NonNull
    @ColumnInfo(name = "longitude")
    double longitude;

    @Nullable
    @ColumnInfo(name = "vicinity")
    String vicinity;

    @Nullable
    @ColumnInfo(name = "reference")
    String reference;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}

