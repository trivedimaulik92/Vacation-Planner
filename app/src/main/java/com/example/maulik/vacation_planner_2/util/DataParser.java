package com.example.maulik.vacation_planner_2.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser {

    private HashMap<String, String> getPlace(JSONObject googlePlaceJson){
        HashMap<String, String> googlePlaceMap = new HashMap<>();
        String placeName = "-NA-";
        String vicinity = "-NA-";
        String latitude = "";
        String longitude = "";
        String reference = "";

        try {
            if(!googlePlaceJson.isNull("name")){
                placeName = googlePlaceJson.getString("name");
            }
            if(!googlePlaceJson.isNull("vicinity")){
                vicinity = googlePlaceJson.getString("vicinity");
            }

            latitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");

            reference = googlePlaceJson.getString("reference");

            googlePlaceMap.put("place_name", placeName);
            googlePlaceMap.put("vicintiy", vicinity);
            googlePlaceMap.put("lat", latitude);
            googlePlaceMap.put("lng", longitude);
            googlePlaceMap.put("reference", reference);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return googlePlaceMap;
    }

    private List<HashMap<String, String>> getPlaces (JSONArray jsonArray){
        int count = jsonArray.length();
        List<HashMap<String,String>> placeList = new ArrayList<>();
        HashMap<String, String> placeMap = null;

        for (int i=0; i < count; i++){
            try {
                placeMap = getPlace((JSONObject) jsonArray.get(i));
                placeList.add(placeMap);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return placeList;
    }

    public List<HashMap<String, String>> parsePlaces(String jsonData){
        JSONArray jsonArray = null;
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getPlaces(jsonArray);
    }

    private HashMap<String, String> getHotel(JSONObject googleHotelJson){
        HashMap<String, String> googleHotelMap = new HashMap<>();
        String hotelName = "-NA-";
        String latitude = "";
        String longitude = "";
        String totalPrice = "";
        String rating = "-NA-";

        try {
            if(!googleHotelJson.isNull("property_name")){
                hotelName = googleHotelJson.getString("property_name");
            }


            latitude = googleHotelJson.getJSONObject("location").getString("latitude");
            longitude = googleHotelJson.getJSONObject("location").getString("longitude");

            totalPrice = googleHotelJson.getJSONObject("total_price").getString("amount");
            JSONArray awards = null;
            JSONObject obj = null;
            awards = googleHotelJson.getJSONArray("awards");
            if (awards.length() != 0){
                for(int i=0;i<awards.length(); i++){
                    obj = (JSONObject) awards.get(i);
                    obj.getString("rating");
                    if(obj.getString("rating").isEmpty()) break;
                }
            }
            if(obj != null)
            rating = obj.getString("rating");



            googleHotelMap.put("hotel_name", hotelName);
            googleHotelMap.put("totalPrice", totalPrice);
            googleHotelMap.put("lat", latitude);
            googleHotelMap.put("lng", longitude);
            googleHotelMap.put("rating", rating);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return googleHotelMap;
    }

    private List<HashMap<String, String>> getHotels (JSONArray jsonArray){
        int count = jsonArray.length();
        List<HashMap<String,String>> hotelList = new ArrayList<>();
        HashMap<String, String> hotelMap = null;

        for (int i=0; i < count; i++){
            try {
                hotelMap = getHotel((JSONObject) jsonArray.get(i));
                hotelList.add(hotelMap);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return hotelList;
    }

    public List<HashMap<String, String>> parseHotels(String jsonData){
        JSONArray jsonArray = null;
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getHotels(jsonArray);
    }

    private HashMap<String, String> getCarRental(JSONObject googleCarRentalJson){
        HashMap<String, String> googleCarRentalMap = new HashMap<>();
        String agency = "-NA-";
        String latitude = "";
        String longitude = "";
        String type = "";
        String totalPrice = "";

        try {
            if(!googleCarRentalJson.isNull("provider")){
                agency = googleCarRentalJson.getJSONObject("provider").getString("company_name");
            }


            latitude = googleCarRentalJson.getJSONObject("location").getString("latitude");
            longitude = googleCarRentalJson.getJSONObject("location").getString("longitude");

            JSONArray cars = googleCarRentalJson.getJSONArray("cars");
            type = ((JSONObject)cars.get(0)).getJSONObject("vehicle_info").getString("category");
            totalPrice = ((JSONObject)cars.get(0)).getJSONObject("estimated_total").getString("amount");



            googleCarRentalMap.put("agency", agency);
            googleCarRentalMap.put("totalPrice", totalPrice);
            googleCarRentalMap.put("lat", latitude);
            googleCarRentalMap.put("lng", longitude);
            googleCarRentalMap.put("type", type);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return googleCarRentalMap;
    }

    private List<HashMap<String, String>> getCarRentals (JSONArray jsonArray){
        int count = jsonArray.length();
        List<HashMap<String,String>> carRentalList = new ArrayList<>();
        HashMap<String, String> carRentalMap = null;

        for (int i=0; i < count; i++){
            try {
                carRentalMap = getCarRental((JSONObject) jsonArray.get(i));
                carRentalList.add(carRentalMap);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return carRentalList;
    }

    public List<HashMap<String, String>> parseCarRentals(String jsonData){
        JSONArray jsonArray = null;
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getCarRentals(jsonArray);
    }

    private HashMap<String, String> getFlight(JSONObject googleFlightJson){
        HashMap<String, String> googleFlightMap = new HashMap<>();
        String origin = "";
        String destination = "";
        String outProvider = "-NA-";
        String outDepDate = "";
        String outDepTime = "";
        String outArrDate = "";
        String outArrTime = "";
        String inProvider = "";
        String inDepDate = "";
        String inDepTime = "";
        String inArrDate = "";
        String inArrTime = "";
        String totalPrice = "";

        try {
            JSONArray itineraries = googleFlightJson.getJSONArray("itineraries");
            JSONArray outFlights = null;
            JSONArray inFlights = null;
            JSONObject flight;
            JSONObject fare = null;
            if(itineraries.length() > 0){
                outFlights = ((JSONObject)itineraries.get(0)).getJSONObject("outbound").getJSONArray("flights");
                inFlights = ((JSONObject)itineraries.get(0)).getJSONObject("inbound").getJSONArray("flights");
                if(googleFlightJson.isNull("fare")) return null;
                fare = googleFlightJson.getJSONObject("fare");

            }

            totalPrice = fare.getString("total_price");

            origin = ((JSONObject)outFlights.get(0)).getJSONObject("origin").getString("airport");
            destination = ((JSONObject)outFlights.get(0)).getJSONObject("destination").getString("airport");
            outProvider = ((JSONObject)outFlights.get(0)).getString("operating_airline");
            outDepDate = ((JSONObject)outFlights.get(0)).getString("departs_at").substring(0,10);
            outDepTime = ((JSONObject)outFlights.get(0)).getString("departs_at").substring(11);
            outArrDate = ((JSONObject)outFlights.get(0)).getString("arrives_at").substring(0,10);
            outArrTime = ((JSONObject)outFlights.get(0)).getString("arrives_at").substring(11);
            if(inFlights.length() > 0){
                inProvider = ((JSONObject)inFlights.get(0)).getString("operating_airline");
                inDepDate = ((JSONObject)inFlights.get(0)).getString("departs_at").substring(0,10);
                inDepTime = ((JSONObject)inFlights.get(0)).getString("departs_at").substring(11);
                inArrDate = ((JSONObject)inFlights.get(0)).getString("arrives_at").substring(0,10);
                inArrTime = ((JSONObject)inFlights.get(0)).getString("arrives_at").substring(11);
            }






            googleFlightMap.put("origin", origin);
            googleFlightMap.put("destination", destination);
            googleFlightMap.put("outProvider", outProvider);
            googleFlightMap.put("outDepDate", outDepDate);
            googleFlightMap.put("outDepTime", outDepTime);
            googleFlightMap.put("outArrDate", outArrDate);
            googleFlightMap.put("outArrTime", outArrTime);
            googleFlightMap.put("inProvider", inProvider);
            googleFlightMap.put("inDepDate", inDepDate);
            googleFlightMap.put("inDepTime", inDepTime);
            googleFlightMap.put("inArrDate", inArrDate);
            googleFlightMap.put("inArrTime", inArrTime);
            googleFlightMap.put("totalPrice", totalPrice);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return googleFlightMap;
    }

    private List<HashMap<String, String>> getFlights (JSONArray jsonArray){
        List<HashMap<String,String>> flightList = null;
        if(jsonArray != null){
            int count = jsonArray.length();
            flightList = new ArrayList<>();
            HashMap<String, String> flightMap = null;

            for (int i=0; i < count; i++){
                try {
                    flightMap = getFlight((JSONObject) jsonArray.get(i));
                    if(flightMap != null)
                        flightList.add(flightMap);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return flightList;
    }

    public List<HashMap<String, String>> parseFlights(String jsonData){
        JSONArray jsonArray = null;
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getFlights(jsonArray);
    }
}
