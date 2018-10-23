package com.example.maulik.vacation_planner_2.util;

import android.os.AsyncTask;

import com.example.maulik.vacation_planner_2.data.CarRental;
import com.example.maulik.vacation_planner_2.data.Hotel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetNeatbyCarRentalsData extends AsyncTask<Object, String, String> {

    String googleCarRentalsData;
    GoogleMap mMap;
    String url;
    List<CarRental> carRentals = new ArrayList<>();

    public List<CarRental> getCarRentals() {
        return carRentals;
    }

    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap) objects[0];
        url = (String) objects[1];

        DownloadUrl downloadUrl = new DownloadUrl();
        try {
            googleCarRentalsData = downloadUrl.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return googleCarRentalsData;
    }

    @Override
    protected void onPostExecute(String s) {
        List<HashMap<String, String>> nearbyCarRentalList = null;
        DataParser parser = new DataParser();
        nearbyCarRentalList = parser.parseCarRentals(s);
        for(int i=0; i< nearbyCarRentalList.size(); i++){
            CarRental carRental = new CarRental();
            carRental.setAgency((nearbyCarRentalList.get(i)).get("agency"));
            carRental.setLatitude(Double.parseDouble((nearbyCarRentalList.get(i)).get("lat")));
            carRental.setLongitude(Double.parseDouble((nearbyCarRentalList.get(i)).get("lng")));
            carRental.setCarType((nearbyCarRentalList.get(i)).get("type"));
            carRental.setFromDate((nearbyCarRentalList.get(i)).get("fromDate"));
            carRental.setToDate((nearbyCarRentalList.get(i)).get("toDate"));
            carRental.setTotalPrice((nearbyCarRentalList.get(i)).get("totalPrice"));
            carRentals.add(carRental);
        }
        showNearbyCarRentals(nearbyCarRentalList);
    }

    private void showNearbyCarRentals(List<HashMap<String, String>> nearbyCarRentalList){
        for(int i=0; i < nearbyCarRentalList.size(); i++){
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String, String> carRentalMap = nearbyCarRentalList.get(i);

            String agency = carRentalMap.get("agency");
            String totalPrice = carRentalMap.get("totalPrice");
            String type = carRentalMap.get("type");
            double lat = Double.parseDouble(carRentalMap.get("lat"));
            double lng = Double.parseDouble(carRentalMap.get("lng"));

            LatLng latLng = new LatLng(lat, lng);
            markerOptions.position(latLng);
            markerOptions.title(agency+" : "+type+" : "+totalPrice);

            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(13));

        }
    }
}
