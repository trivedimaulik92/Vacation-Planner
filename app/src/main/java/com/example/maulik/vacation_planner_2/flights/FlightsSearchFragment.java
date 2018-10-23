package com.example.maulik.vacation_planner_2.flights;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maulik.vacation_planner_2.ActivityCommunicator;
import com.example.maulik.vacation_planner_2.FragmentCommunicator;
import com.example.maulik.vacation_planner_2.R;

public class FlightsSearchFragment extends Fragment implements FragmentCommunicator{

    private View mView;

    private Context mContext;

    private AutoCompleteTextView mOrigin;

    private AutoCompleteTextView mDestination;

    private EditText mDepDate;

    private EditText mArrDate;

    private RadioGroup mRadioGroup;

    private RadioButton mRadioButtonOnewy;

    private RadioButton mRadioButtonReturn;

    public ActivityCommunicator mActivityCommunicator;

    private ArrayAdapter<CharSequence> adapter;

    public FlightsSearchFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (Context)getActivity();
        mActivityCommunicator = (FlightsActivity)mContext;
        ((FlightsActivity)context).mFlightsSearchFragmentCommunicator = this;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.city_iata_codes,
                                android.R.layout.simple_expandable_list_item_1);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.flights_search_fragment, container, false);
        mOrigin = mView.findViewById(R.id.flights_search_et_origin);
        mOrigin.setAdapter(adapter);
        mOrigin.setThreshold(3);
        mDestination = mView.findViewById(R.id.flights_search_et_destination);
        mDestination.setAdapter(adapter);
        mOrigin.setThreshold(3);
        mDepDate = mView.findViewById(R.id.flights_search_et_dep_date);
        mArrDate = mView.findViewById(R.id.flights_search_et_arr_date);
        mRadioGroup = mView.findViewById(R.id.flights_search_rg);
        mRadioButtonOnewy = mView.findViewById(R.id.flights_search_rb_oneway);
        mRadioButtonReturn = mView.findViewById(R.id.flights_search_rb_return);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public static FlightsSearchFragment newInstance(){
        return new FlightsSearchFragment();
    }




    @Override
    public void passDataToFragment(String name, Object... objects) {
        if(name.equals("FLIGHTS_FAB_SEARCH")){
            String origin = mOrigin.getText().toString();
            origin = origin.substring(origin.indexOf('(')+1, origin.indexOf(')'));
            String destination =mDestination.getText().toString();
            destination = destination.substring(destination.indexOf('(')+1, destination.indexOf(')'));
            String depDate = mDepDate.getText().toString();
            depDate = depDate.substring(6)+"-"+depDate.substring(0,5);
            String arrDate = mArrDate.getText().toString();
            arrDate = arrDate.substring(6)+"-"+arrDate.substring(0,5);
            if(!origin.isEmpty() && !destination.isEmpty() && !depDate.isEmpty()){
                mActivityCommunicator.passDataToActivity("FLIGHT_SEARCH",
                        (Object) new String[]{origin, destination, depDate, arrDate});
            }
            else {
                Toast.makeText(getContext(),"Enter all values", Toast.LENGTH_LONG).show();
            }
        }
    }
}
