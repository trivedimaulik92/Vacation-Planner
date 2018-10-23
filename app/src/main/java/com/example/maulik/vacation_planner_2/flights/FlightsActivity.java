package com.example.maulik.vacation_planner_2.flights;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.amazonaws.mobile.auth.core.IdentityManager;
import com.example.maulik.vacation_planner_2.ActivityCommunicator;
import com.example.maulik.vacation_planner_2.FragmentCommunicator;
import com.example.maulik.vacation_planner_2.R;
import com.example.maulik.vacation_planner_2.SplashActivity;
import com.example.maulik.vacation_planner_2.attractions.AttractionsActivity;
import com.example.maulik.vacation_planner_2.bookings.BookingsActivity;
import com.example.maulik.vacation_planner_2.car_rentals.CarRentalsActivity;
import com.example.maulik.vacation_planner_2.data.Flight;
import com.example.maulik.vacation_planner_2.hotels.HotelsActivity;
import com.example.maulik.vacation_planner_2.util.ActivityUtils;
import com.example.maulik.vacation_planner_2.util.GetNearbyFlightsData;
import com.example.maulik.vacation_planner_2.util.ResultListFragment;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FlightsActivity extends AppCompatActivity implements ActivityCommunicator {


    private DrawerLayout mDrawerLayout;

    private FloatingActionButton mFabSearch;


    public FragmentCommunicator mFlightsSearchFragmentCommunicator;

    public FragmentCommunicator mResultlistFragmentCommunicator;

    private Fragment mFlightsSearchFragment;

    private Fragment mResultListFragment;

    private String url;

    private String mOrigin;

    private String mDestination;

    private String mDepDate;

    private String mArrDate;

    private Object dataTransfer[] = new Object[2];

    Flight mFlight;

    private List<Flight> mFlights;


    public static final int REQUEST_LOCATION_CODE = 99;

    public static final int RADIUS = 25;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.flights_activity);

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        // Set up the navigation drawer.
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        mFlightsSearchFragment = (FlightsSearchFragment) getSupportFragmentManager().findFragmentById(R.id.flights_content_frame);
        if(mFlightsSearchFragment == null){
            mFlightsSearchFragment = FlightsSearchFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mFlightsSearchFragment, R.id.flights_content_frame);
        }

        mFabSearch = findViewById(R.id.flights_fab_search);
        mFabSearch.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                mFlightsSearchFragmentCommunicator.passDataToFragment("FLIGHTS_FAB_SEARCH",null);
            }
        });
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.attractions_navigation_menu_item:
                                // Do nothing, we're already on that screen
                                break;
                            case R.id.hotels_navigation_menu_item:
                                Intent intentHotels =
                                        new Intent(FlightsActivity.this, HotelsActivity.class);
                                startActivity(intentHotels);
                                break;
                            case R.id.flights_navigation_menu_item:
                                Intent intentFlights =
                                        new Intent(FlightsActivity.this, FlightsActivity.class);
                                startActivity(intentFlights);
                                break;
                            case R.id.car_rentals_navigation_menu_item:
                                Intent intentCarRentals =
                                        new Intent(FlightsActivity.this, CarRentalsActivity.class);
                                startActivity(intentCarRentals);
                                break;
                            case R.id.bookings_navigation_menu_item:
                                Intent intentBookings =
                                        new Intent(FlightsActivity.this, BookingsActivity.class);
                                startActivity(intentBookings);
                                break;

                            default:
                                break;
                        }
                        // Close the navigation drawer when an item is selected.
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    public void onSearch(String origin, String destination, String depDate, String arrDate)  {
        //mSearchLocation = (EditText) v.findViewById(R.id.attr_et_location);
        List<Address> addressList = null;
        GetNearbyFlightsData getNearbyFlightsData = new GetNearbyFlightsData();

            Toast.makeText(this, "Getting flights", Toast.LENGTH_LONG).show();
            url = getUrl(origin, destination, depDate, arrDate);
            dataTransfer[0] = url;
            getNearbyFlightsData.execute(dataTransfer);
        try {
            synchronized (getNearbyFlightsData) {
                getNearbyFlightsData.wait(2000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mFlights = getNearbyFlightsData.getFlights();

        if(mResultListFragment == null){
            mResultListFragment = ResultListFragment.newInstance();
            ActivityUtils.replaceFragmentToActivity(getSupportFragmentManager(), mResultListFragment, R.id.flights_content_frame);
        }

        mResultlistFragmentCommunicator.passDataToFragment("SHOW_FLIGHTS_LIST", mFlights);
    }

    private String getUrl(String origin, String destination, String depDate, String arrDate) {
        StringBuilder sb = new StringBuilder();
        sb.append("https://api.sandbox.amadeus.com/v1.2/flights/low-fare-search?");
        sb.append("&origin="+origin);
        sb.append("&destination="+destination);
        sb.append("&departure_date="+depDate);
        sb.append("&return_date="+arrDate);
        sb.append("&nonstop=true&number_of_results=20");
        sb.append(("&apikey=ZyPWVaMnmLmVDUWYfEn7fOSwioGgHjyG"));
        return sb.toString();
    }

    @Override
    public void passDataToActivity(String name, Object... objects) {
        if (name.equals("FLIGHT_SEARCH")) {
            String[] args = (String[]) objects[0];
            if (args[0] != "" && args[1] != "") {
                mOrigin = args[0];
                mDestination = args[1];
                mDepDate = args[2];
                mArrDate = args[3];
                onSearch(mOrigin, mDestination, mDepDate, mArrDate);
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_logout){
            IdentityManager identityManager = IdentityManager.getDefaultIdentityManager();
            identityManager.signOut();
            identityManager.getUserID( null);
            this.startActivity(new Intent(this, SplashActivity.class).
                    setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            this.finish();

        }
        return true;
    }

}
