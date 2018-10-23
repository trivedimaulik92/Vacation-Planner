package com.example.maulik.vacation_planner_2.util;

import android.app.Application;

import com.example.maulik.vacation_planner_2.data.Booking;

import java.util.ArrayList;

public class MyApp extends Application {
    private static ArrayList<Booking> bookings = new ArrayList<>();
    private static boolean update;

    public static boolean isUpdate() {
        return update;
    }

    public static void setUpdate(boolean update) {
        MyApp.update = update;
    }

    public static ArrayList<Booking> getBookings() {
        return bookings;
    }

    public static void setBookings(ArrayList<Booking> bookings) {
        bookings = bookings;
    }
}
