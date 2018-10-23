package com.example.maulik.vacation_planner_2.util;

import android.os.AsyncTask;

import com.example.maulik.vacation_planner_2.data.Hotel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetNearbyHotelsData extends AsyncTask<Object, String, String> {

    String googleHotelsData;
    GoogleMap mMap;
    String url;
    List<Hotel> hotels = new ArrayList<>();

    public List<Hotel> getHotels() {
        return hotels;
    }

    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap) objects[0];
        url = (String) objects[1];

        DownloadUrl downloadUrl = new DownloadUrl();
        try {
            googleHotelsData = downloadUrl.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return googleHotelsData;
    }

    @Override
    protected void onPostExecute(String s) {
        List<HashMap<String, String>> nearbyHotelList = null;
        DataParser parser = new DataParser();
        nearbyHotelList = parser.parseHotels(s);
        for(int i=0; i< nearbyHotelList.size(); i++){
            Hotel hotel = new Hotel();
            hotel.setName((nearbyHotelList.get(i)).get("hotel_name"));
            hotel.setPrice((nearbyHotelList.get(i)).get("totalPrice"));
            hotel.setLatitude(Double.parseDouble((nearbyHotelList.get(i)).get("lat")));
            hotel.setLongitude(Double.parseDouble((nearbyHotelList.get(i)).get("lng")));
            hotels.add(hotel);
        }
        showNearbyHotels(nearbyHotelList);
    }

    private void showNearbyHotels(List<HashMap<String, String>> nearbyHotelList){
        for(int i=0; i < nearbyHotelList.size(); i++){
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String, String> googlePlace = nearbyHotelList.get(i);

            String hotelName = googlePlace.get("hotel_name");
            String totalPrice = googlePlace.get("totalPrice");
            double lat = Double.parseDouble(googlePlace.get("lat"));
            double lng = Double.parseDouble(googlePlace.get("lng"));

            LatLng latLng = new LatLng(lat, lng);
            markerOptions.position(latLng);
            markerOptions.title(hotelName);

            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(13));

        }
    }
}
