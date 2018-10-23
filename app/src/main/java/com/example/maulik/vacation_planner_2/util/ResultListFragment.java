package com.example.maulik.vacation_planner_2.util;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.maulik.vacation_planner_2.ActivityCommunicator;
import com.example.maulik.vacation_planner_2.FragmentCommunicator;
import com.example.maulik.vacation_planner_2.R;
import com.example.maulik.vacation_planner_2.car_rentals.CarRentalsActivity;
import com.example.maulik.vacation_planner_2.car_rentals.CarRentalsAdapter;
import com.example.maulik.vacation_planner_2.data.CarRental;
import com.example.maulik.vacation_planner_2.data.Flight;
import com.example.maulik.vacation_planner_2.data.Hotel;
import com.example.maulik.vacation_planner_2.flights.FlightsActivity;
import com.example.maulik.vacation_planner_2.flights.FlightsAdapter;
import com.example.maulik.vacation_planner_2.hotels.HotelsActivity;
import com.example.maulik.vacation_planner_2.hotels.HotelsAdapter;

import java.util.ArrayList;

public class ResultListFragment extends Fragment implements FragmentCommunicator {

    private Context mContext;
    private ListView mListView;
    private ActivityCommunicator mHotelsActivityCommunicator;
    private ActivityCommunicator mFlightsActivityCommunicator;
    private ActivityCommunicator mCarRentalsActivityCommunicator;
    private ArrayList<Hotel> mHotels;
    private ArrayList<Flight> mFlights;
    private ArrayList<CarRental> mCarRentals;
    private View mView;
    public ResultListFragment(){

    }

    public static ResultListFragment newInstance(){
        return new ResultListFragment();
    }

    public void passDataToFragment(String name, Object... objects) {
        if(name.equals("SHOW_HOTELS_LIST")){
            mHotels = (ArrayList<Hotel>) objects[0];
            HotelsAdapter hotelsAdapter = new HotelsAdapter(getActivity(), mHotels);
            mListView.setAdapter(hotelsAdapter);
        }
        else if (name.equals("SHOW_FLIGHTS_LIST")) {
            mFlights = (ArrayList<Flight>) objects[0];
            FlightsAdapter flightsAdapter = new FlightsAdapter(getActivity(), mFlights);
            mListView.setAdapter(flightsAdapter);
        }
        else if (name.equals("SHOW_CAR_RENTALS_LIST")){
            mCarRentals = (ArrayList<CarRental>) objects[0];
            CarRentalsAdapter carRentalsAdapter = new CarRentalsAdapter(getActivity(), mCarRentals);
            mListView.setAdapter(carRentalsAdapter);
        }
        else {

        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = getActivity();
        if(mContext instanceof HotelsActivity){
            mHotelsActivityCommunicator = (ActivityCommunicator)getContext();
            ((HotelsActivity)mContext).mResultListFragmentCommunicator = this;
        }
        else if(mContext instanceof CarRentalsActivity){
            mCarRentalsActivityCommunicator = (ActivityCommunicator)getContext();
            ((CarRentalsActivity)mContext).mResultlistFragmentCommunicator = this;
        }
        else if(mContext instanceof FlightsActivity){
            mFlightsActivityCommunicator = (ActivityCommunicator)getContext();
            ((FlightsActivity)mContext).mResultlistFragmentCommunicator = this;
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if(mContext instanceof HotelsActivity){
            mHotelsActivityCommunicator = (ActivityCommunicator)mContext;
            ((HotelsActivity)mContext).mResultListFragmentCommunicator = this;
        }
        else if(mContext instanceof CarRentalsActivity){
            mCarRentalsActivityCommunicator = (ActivityCommunicator)mContext;
            ((CarRentalsActivity)mContext).mResultlistFragmentCommunicator = this;
        }
        else if(mContext instanceof FlightsActivity){
            mFlightsActivityCommunicator = (ActivityCommunicator)mContext;
            ((FlightsActivity)mContext).mResultlistFragmentCommunicator = this;
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.result_list_fragment, container, false);
        mListView = mView.findViewById(R.id.result_list_view);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
