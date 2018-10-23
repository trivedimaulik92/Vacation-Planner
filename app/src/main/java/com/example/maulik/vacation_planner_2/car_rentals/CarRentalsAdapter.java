package com.example.maulik.vacation_planner_2.car_rentals;

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
import com.example.maulik.vacation_planner_2.data.CarRental;
import com.example.maulik.vacation_planner_2.util.MyApp;

import java.util.ArrayList;

public class CarRentalsAdapter extends ArrayAdapter<CarRental> {

    public CarRentalsAdapter(Context context, ArrayList<CarRental> carRentals) {
        super(context, 0, carRentals);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        CarRental carRental = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            if(getContext() instanceof CarRentalsActivity)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.car_rentals_list_view, parent, false);
            else if (getContext() instanceof BookingsActivity)
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.bookings_list_car_rentals_2, parent, false);
        }
        // Lookup view for data population
        TextView agency = (TextView) convertView.findViewById(R.id.car_rentals_list_agency_value);
        TextView type = (TextView) convertView.findViewById(R.id.car_rentals_list_type_value);
        TextView fromDate = (TextView) convertView.findViewById(R.id.car_rentals_list_from_date_value);
        TextView toDate = (TextView) convertView.findViewById(R.id.car_rentals_list_to_date_value);
        TextView price = (TextView) convertView.findViewById(R.id.car_rentals_list_total_price_value);
        Button book = convertView.findViewById(R.id.car_rentals_list_b_book);
        if(book != null){
            book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Booking booking = new Booking();
                    booking.setType("car_rental");
                    booking.setCarRental(carRental);
                    MyApp.getBookings().add(booking);
                    Toast.makeText(getContext(),"Car Rental Booked", Toast.LENGTH_LONG);
                }
            });
        }

        // Populate the data into the template view using the data object
        agency.setText(carRental.getAgency());
        type.setText(carRental.getCarType());
        fromDate.setText(carRental.getFromDate());
        toDate.setText(carRental.getToDate());
        price.setText(carRental.getTotalPrice());
        // Return the completed view to render on screen
        return convertView;
    }
}