package com.example.maulik.vacation_planner_2.flights;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maulik.vacation_planner_2.R;
import com.example.maulik.vacation_planner_2.bookings.BookingsActivity;
import com.example.maulik.vacation_planner_2.data.Booking;
import com.example.maulik.vacation_planner_2.data.Flight;
import com.example.maulik.vacation_planner_2.data.Hotel;
import com.example.maulik.vacation_planner_2.util.MyApp;

import java.util.ArrayList;

public class FlightsAdapter extends ArrayAdapter<Flight> {

    public FlightsAdapter(Context context, ArrayList<Flight> flights) {
        super(context, 0, flights);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Flight flight = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if(flight.getRetDepDate() != null && flight.getRetDepDate() != ""){
            if (convertView == null) {
                if(getContext() instanceof FlightsActivity)
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.flights_list_view_return, parent, false);
                else if(getContext() instanceof BookingsActivity)
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.bookings_list_flights_2, parent, false);
            }
            // Lookup view for data population
            TextView origin = (TextView) convertView.findViewById(R.id.flights_list_origin_value);
            TextView destination = (TextView) convertView.findViewById(R.id.flights_list_destination_value);
            TextView outProvider = (TextView) convertView.findViewById(R.id.flights_list_provider_value);
            TextView outDepDate = (TextView) convertView.findViewById(R.id.flights_list_dep_date_value);
            TextView outDepTime = (TextView) convertView.findViewById(R.id.flights_list_dep_time_value);
            TextView outArrDate = (TextView) convertView.findViewById(R.id.flights_list_arr_date_value);
            TextView outArrTime = (TextView) convertView.findViewById(R.id.flights_list_arr_time_value);
            TextView inOrigin = (TextView) convertView.findViewById(R.id.flights_list_origin_value_2);
            TextView inDestination = (TextView) convertView.findViewById(R.id.flights_list_destination_value_2);
            TextView inProvider = (TextView) convertView.findViewById(R.id.flights_list_provider_value_2);
            TextView inDepDate = (TextView) convertView.findViewById(R.id.flights_list_dep_date_value_2);
            TextView inDepTime = (TextView) convertView.findViewById(R.id.flights_list_dep_time_value_2);
            TextView inArrDate = (TextView) convertView.findViewById(R.id.flights_list_arr_date_value_2);
            TextView inArrTime = (TextView) convertView.findViewById(R.id.flights_list_arr_time_value_2);
            TextView totalPrice = (TextView) convertView.findViewById(R.id.flights_list_total_price_value);
            Button book = convertView.findViewById(R.id.flights_list_b_book);
            if(book != null){
                book.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Booking booking = new Booking();
                        booking.setType("flight");
                        booking.setFlight(flight);
                        MyApp.getBookings().add(booking);
                        Toast.makeText(getContext(),"Flight Booked", Toast.LENGTH_LONG);
                    }
                });
            }

            // Populate the data into the template view using the data object
            origin.setText(flight.getOrigin());
            destination.setText(flight.getDestination());
            inOrigin.setText(flight.getDestination());
            inDestination.setText(flight.getOrigin());
            outProvider.setText(flight.getProvider());
            outDepDate.setText(flight.getDepDate());
            outDepTime.setText(flight.getDepTime());
            outArrDate.setText(flight.getArrDate());
            outArrTime.setText(flight.getArrTime());
            inProvider.setText(flight.getRetProvider());
            inDepDate.setText(flight.getRetDepDate());
            inDepTime.setText(flight.getRetDepTime());
            inArrDate.setText(flight.getRetArrDate());
            inArrTime.setText(flight.getRetArrTime());
            totalPrice.setText(flight.getTotalPrice());
        }
        else
        {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.flights_list_view_return, parent, false);
            }
            // Lookup view for data population
            TextView origin = (TextView) convertView.findViewById(R.id.flights_list_origin_value);
            TextView destination = (TextView) convertView.findViewById(R.id.flights_list_destination_value);
            TextView outProvider = (TextView) convertView.findViewById(R.id.flights_list_provider_value);
            TextView outDepDate = (TextView) convertView.findViewById(R.id.flights_list_dep_date_value);
            TextView outDepTime = (TextView) convertView.findViewById(R.id.flights_list_dep_time_value);
            TextView outArrDate = (TextView) convertView.findViewById(R.id.flights_list_arr_date_value);
            TextView outArrTime = (TextView) convertView.findViewById(R.id.flights_list_arr_time_value);
            TextView totalPrice = (TextView) convertView.findViewById(R.id.flights_list_total_price_value);
            Button book = convertView.findViewById(R.id.flights_list_b_book);
            book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Booking booking = new Booking();
                    booking.setType("flight");
                    booking.setFlight(flight);
                    MyApp.getBookings().add(booking);
                    Toast.makeText(getContext(),"Flight Booked", Toast.LENGTH_LONG);
                }
            });

            // Populate the data into the template view using the data object
            origin.setText(flight.getOrigin());
            destination.setText(flight.getDestination());
            outProvider.setText(flight.getProvider());
            outDepDate.setText(flight.getDepDate());
            outDepTime.setText(flight.getDepTime());
            outArrDate.setText(flight.getArrDate());
            outArrTime.setText(flight.getArrTime());
            totalPrice.setText(flight.getTotalPrice());
        }

        // Return the completed view to render on screen
        return convertView;
    }

}
