package com.example.maulik.vacation_planner_2.bookings;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.maulik.vacation_planner_2.ActivityCommunicator;
import com.example.maulik.vacation_planner_2.FragmentCommunicator;
import com.example.maulik.vacation_planner_2.R;
import com.example.maulik.vacation_planner_2.car_rentals.CarRentalsActivity;
import com.example.maulik.vacation_planner_2.car_rentals.CarRentalsAdapter;
import com.example.maulik.vacation_planner_2.data.Booking;
import com.example.maulik.vacation_planner_2.data.CarRental;
import com.example.maulik.vacation_planner_2.data.Flight;
import com.example.maulik.vacation_planner_2.data.Hotel;
import com.example.maulik.vacation_planner_2.flights.FlightsAdapter;
import com.example.maulik.vacation_planner_2.hotels.HotelsActivity;
import com.example.maulik.vacation_planner_2.hotels.HotelsAdapter;
import com.example.maulik.vacation_planner_2.util.MyApp;

import java.util.ArrayList;

public class BookingsFragment extends Fragment implements FragmentCommunicator {
    public FragmentCommunicator mBookingsFragmentCommunicator;
    private ArrayList<Booking> mBookings;
    private ArrayList<Hotel> mHotels = new ArrayList<>();
    private ArrayList<Flight> mFlights =new ArrayList<>();
    private ArrayList<CarRental> mCarRentals =new ArrayList<>();
    private ListView mHotelsListView;
    private ListView mFlightsListView;
    private ListView mCarRentalsListView;
    private HotelsAdapter mHotelsAdapter;
    private FlightsAdapter mFlightsAdapter;
    private CarRentalsAdapter mCarRentalsAdapter;
    private ActivityCommunicator mActivityCommunicator;
    private Context mContext;
    private TextView mHotelsTitle;
    private TextView mFlightsTitle;
    private TextView mCarRentalsTitle;


    private View mView;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (Context)getActivity();
        mActivityCommunicator = (BookingsActivity)mContext;
        ((BookingsActivity)context).mBookingsFragmentCommunicator = this;
    }

    public static BookingsFragment newInstance(){
        return new BookingsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.bookings_list_view, container, false);
        mHotelsListView = mView.findViewById(R.id.bookings_hotels_list);
        mFlightsListView = mView.findViewById(R.id.bookings_flights_list);
        mCarRentalsListView = mView.findViewById(R.id.bookings_car_rentals_list);
        mBookings = MyApp.getBookings();
        for(int i=0; i < mBookings.size(); i++){
            Booking booking = mBookings.get(i);
            if(booking.getType().equals("hotel")) mHotels.add(booking.getHotel());
            if(booking.getType().equals("flight")) mFlights.add(booking.getFlight());
            if(booking.getType().equals("car_rental")) mCarRentals.add(booking.getCarRental());
        }
        mHotelsAdapter = new HotelsAdapter(getActivity(),mHotels);
        mFlightsAdapter = new FlightsAdapter(getActivity(), mFlights);
        mCarRentalsAdapter = new CarRentalsAdapter(getActivity(), mCarRentals);
        mHotelsListView.setAdapter(mHotelsAdapter);
        mFlightsListView.setAdapter(mFlightsAdapter);
        mCarRentalsListView.setAdapter(mCarRentalsAdapter);
        mHotelsTitle = mView.findViewById(R.id.bookings_list_tl_hotel);
        mFlightsTitle = mView.findViewById(R.id.bookings_list_tl_flights);
        mCarRentalsTitle = mView.findViewById(R.id.bookings_list_tl_car_rentals);
        if(mHotels.size() > 0) mHotelsTitle.setText("Hotels"); else mHotelsTitle.setText("");
        if(mFlights.size() > 0) mFlightsTitle.setText("Flights"); else mFlightsTitle.setText("");
        if(mCarRentals.size() > 0) mCarRentalsTitle.setText("Car Rentals"); else mCarRentalsTitle.setText("");

        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void passDataToFragment(String name, Object... objects) {
        if(name.equals("UPDATE") && (boolean)objects[0] == true){
            for(int i=0; i < mBookings.size(); i++) {
                Booking booking = mBookings.get(i);
                if (booking.getType().equals("hotel")) mHotels.add(booking.getHotel());
                if (booking.getType().equals("flight")) mFlights.add(booking.getFlight());
                if (booking.getType().equals("car_rental")) mCarRentals.add(booking.getCarRental());
            }
        }
    }
}
