package com.example.maulik.vacation_planner_2.util;

import android.os.AsyncTask;

import com.example.maulik.vacation_planner_2.data.Flight;
import com.example.maulik.vacation_planner_2.data.Hotel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetNearbyFlightsData extends AsyncTask<Object, String, String> {

    String googleFlightsData;
    String url;
    List<Flight> flights = new ArrayList<>();

    public List<Flight> getFlights() {
        return flights;
    }

    @Override
    protected String doInBackground(Object... objects) {
        url = (String) objects[0];

        DownloadUrl downloadUrl = new DownloadUrl();
        try {
            googleFlightsData = downloadUrl.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<HashMap<String, String>> nearbyFlightList = null;
        DataParser parser = new DataParser();
        nearbyFlightList = parser.parseFlights(googleFlightsData);
        if(nearbyFlightList == null) return "";
        for(int i=0; i< nearbyFlightList.size(); i++){
            Flight flight = new Flight();
            flight.setOrigin((nearbyFlightList.get(i)).get("origin"));
            flight.setDestination((nearbyFlightList.get(i)).get("destination"));
            flight.setProvider((nearbyFlightList.get(i)).get("outProvider"));
            flight.setDepDate((nearbyFlightList.get(i)).get("outDepDate"));
            flight.setDepTime((nearbyFlightList.get(i)).get("outDepTime"));
            flight.setArrDate((nearbyFlightList.get(i)).get("outArrDate"));
            flight.setArrTime((nearbyFlightList.get(i)).get("outArrTime"));
            flight.setRetProvider((nearbyFlightList.get(i)).get("inProvider"));
            flight.setRetDepDate((nearbyFlightList.get(i)).get("inDepDate"));
            flight.setRetDepTime((nearbyFlightList.get(i)).get("inDepTime"));
            flight.setRetArrDate((nearbyFlightList.get(i)).get("inArrDate"));
            flight.setRetArrTime((nearbyFlightList.get(i)).get("inArrTime"));
            flight.setTotalPrice((nearbyFlightList.get(i)).get("totalPrice"));
            flights.add(flight);
        }
        return googleFlightsData;
    }

    @Override
    protected void onPostExecute(String s) {

    }


}
