package com.example.maulik.vacation_planner_2.hotels;

import android.content.Context;
import android.support.annotation.NonNull;
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
import com.example.maulik.vacation_planner_2.data.Hotel;
import com.example.maulik.vacation_planner_2.util.MyApp;

import java.util.ArrayList;

public class HotelsAdapter extends ArrayAdapter<Hotel> {

    public HotelsAdapter(Context context, ArrayList<Hotel> hotels) {
        super(context, 0, hotels);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Hotel hotel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            if(getContext() instanceof  HotelsActivity)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.hotels_list_view, parent, false);
            else if(getContext() instanceof BookingsActivity)
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.bookings_list_hotels_2, parent, false);
        }
        // Lookup view for data population
        TextView hotelName = (TextView) convertView.findViewById(R.id.hotels_list_name_value);
        TextView fromDate = (TextView) convertView.findViewById(R.id.hotels_list_fromDate_value);
        TextView toDate = (TextView) convertView.findViewById(R.id.hotels_list_toDate_value);
        TextView price = (TextView) convertView.findViewById(R.id.hotels_list_price_value);
        Button book = convertView.findViewById(R.id.hotels_list_b_book);
        if(book != null){
            book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Booking booking = new Booking();
                    booking.setType("hotel");
                    booking.setHotel(hotel);
                    MyApp.getBookings().add(booking);
                    Toast.makeText(getContext(),"Hotel Booked", Toast.LENGTH_LONG);
                }
            });
        }


        // Populate the data into the template view using the data object
        hotelName.setText(hotel.getName());
        fromDate.setText(hotel.getFromDate());
        toDate.setText(hotel.getToDate());
        price.setText(hotel.getPrice());
        // Return the completed view to render on screen
        return convertView;
    }



}
