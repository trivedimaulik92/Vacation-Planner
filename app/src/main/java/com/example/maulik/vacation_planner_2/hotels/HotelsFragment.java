package com.example.maulik.vacation_planner_2.hotels;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.maulik.vacation_planner_2.ActivityCommunicator;
import com.example.maulik.vacation_planner_2.FragmentCommunicator;
import com.example.maulik.vacation_planner_2.R;

import java.util.Date;

public class HotelsFragment extends Fragment implements FragmentCommunicator {

    public Context mContext;

    private View mView;

    private EditText mFromDate;

    private EditText mToDate;

    private EditText mCount;

    private ActivityCommunicator activityCommunicator;

    public HotelsFragment() {

    }


    public static HotelsFragment newInstance(){
        return new HotelsFragment();
    }

    @Override
    public void passDataToFragment(String name, Object... objects) {
        if(name.equals("HOTELS_FAB_SEARCH")){
            String fromDate = mFromDate.getText().toString();
            String toDate = mToDate.getText().toString();
            fromDate = fromDate.substring(6)+"-"+fromDate.substring(0,5);
            toDate = toDate.substring(6)+"-"+toDate.substring(0,5);
            String count = mCount.getText().toString();
            activityCommunicator.passDataToActivity("HOTEL_SEARCH", (Object) new String[]{fromDate, toDate, count});
        }
    }

    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);
        mContext = getActivity();
        activityCommunicator = (ActivityCommunicator)getContext();
        ((HotelsActivity)mContext).mHotelsFragmentCommunicator = this;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.hotels_fragment, container, false);
        mFromDate =  mView.findViewById(R.id.hotels_et_fromdate);
        mToDate = mView.findViewById(R.id.hotels_et_todate);
        mCount = mView.findViewById(R.id.hotels_et_count);
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
