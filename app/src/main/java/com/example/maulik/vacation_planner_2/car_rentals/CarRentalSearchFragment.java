package com.example.maulik.vacation_planner_2.car_rentals;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.maulik.vacation_planner_2.ActivityCommunicator;
import com.example.maulik.vacation_planner_2.FragmentCommunicator;
import com.example.maulik.vacation_planner_2.R;
import com.example.maulik.vacation_planner_2.flights.FlightsActivity;


public class CarRentalSearchFragment extends Fragment implements FragmentCommunicator{


    private View mView;

    private Context mContext;

    private AutoCompleteTextView mCity;

    private EditText mFromDate;

    private EditText mToDate;

    public ActivityCommunicator mActivityCommunicator;

    private ArrayAdapter<CharSequence> adapter;

    public CarRentalSearchFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (Context)getActivity();
        mActivityCommunicator = (CarRentalsActivity)mContext;
        ((CarRentalsActivity)context).mCarRentalsSearchFragmentCommunicator = this;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.cities,
                android.R.layout.simple_expandable_list_item_1);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.car_rentals_search_fragment, container, false);
        mCity = mView.findViewById(R.id.car_rentals_search_et_city);
        mCity.setAdapter(adapter);
        mCity.setThreshold(3);
        mFromDate = mView.findViewById(R.id.car_rentals_search_et_from_date);

        mToDate = mView.findViewById(R.id.car_rentals_search_et_to_date);

        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public static CarRentalSearchFragment newInstance(){
        return new CarRentalSearchFragment();
    }




    @Override
    public void passDataToFragment(String name, Object... objects) {
        if(name.equals("CAR_RENTALS_FAB_SEARCH")){
            String city = mCity.getText().toString();
            String fromDate = mFromDate.getText().toString();
            fromDate = fromDate.substring(6)+"-"+fromDate.substring(0,5);

            String toDate = mToDate.getText().toString();
            toDate = toDate.substring(6)+"-"+toDate.substring(0,5);

            if(!city.isEmpty() && !fromDate.isEmpty() && !toDate.isEmpty()){
                mActivityCommunicator.passDataToActivity("CAR_RENTALS_SEARCH",
                        (Object) new String[]{city, fromDate, toDate});
            }
            else {
                Toast.makeText(getContext(),"Enter all values", Toast.LENGTH_LONG).show();
            }
        }
    }
}
