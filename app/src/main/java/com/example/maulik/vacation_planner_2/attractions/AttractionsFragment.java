package com.example.maulik.vacation_planner_2.attractions;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.maulik.vacation_planner_2.ActivityCommunicator;
import com.example.maulik.vacation_planner_2.FragmentCommunicator;
import com.example.maulik.vacation_planner_2.R;
import com.example.maulik.vacation_planner_2.util.GetNearbyPlacesData;

import java.io.IOException;
import java.util.List;

public class AttractionsFragment extends Fragment implements FragmentCommunicator
        {

    private EditText mSearchLocation;

    private EditText mDate;

    private Spinner mSpinner;

    private Button mButton;

    private ArrayAdapter<CharSequence> adapter;

    private View mView;

    private ActivityCommunicator activityCommunicator;

            private String activity;
            private String url;
            private Object dataTransfer[] = new Object[2];
            public static final int REQUEST_LOCATION_CODE = 99;
            public static final int RADIUS = 600;

    public AttractionsFragment() {
        // requires public constructor
    }

    public static AttractionsFragment newInstance() {
        return new AttractionsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.activities,
                android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.attractions_fragment, container, false);
        mSpinner = (Spinner) mView.findViewById(R.id.attr_spinner_activity);
        mSpinner.setAdapter(adapter);
        mSearchLocation = mView.findViewById(R.id.attr_et_location);
        mDate = mView.findViewById(R.id.attr_et_date);
        mButton = mView.findViewById(R.id.attr_b_search);
        setRetainInstance(true);
        return mView;
    }

            @Override
            public void onAttach(Activity activity){
                super.onAttach(activity);
                activityCommunicator =(ActivityCommunicator)getActivity();
            }

            @Override
            public void onActivityCreated(Bundle savedInstanceState) {
                super.onActivityCreated(savedInstanceState);

                //communicating to activity via ActivityCommunicator interface
                mButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        String[] args = new String[3];
                        args[0] = mSearchLocation.getText().toString();
                        args[1] = mSpinner.getSelectedItem().toString().replace(" ", "%20");
                        args[2] = mDate.getText().toString();
                        activityCommunicator.passDataToActivity(null, (Object) args);
                    }
                });
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

            @Override
            public void passDataToFragment(String name, Object... objects) {

            }
        }
