package com.example.maulik.vacation_planner_2.bookings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.amazonaws.mobile.auth.core.IdentityManager;
import com.example.maulik.vacation_planner_2.ActivityCommunicator;
import com.example.maulik.vacation_planner_2.FragmentCommunicator;
import com.example.maulik.vacation_planner_2.R;
import com.example.maulik.vacation_planner_2.SplashActivity;
import com.example.maulik.vacation_planner_2.car_rentals.CarRentalsActivity;
import com.example.maulik.vacation_planner_2.flights.FlightsActivity;
import com.example.maulik.vacation_planner_2.flights.FlightsSearchFragment;
import com.example.maulik.vacation_planner_2.hotels.HotelsActivity;
import com.example.maulik.vacation_planner_2.util.ActivityUtils;
import com.example.maulik.vacation_planner_2.util.MyApp;

public class BookingsActivity extends AppCompatActivity implements ActivityCommunicator {

    private DrawerLayout mDrawerLayout;
    private BookingsFragment mBookingsFragment;
    public FragmentCommunicator mBookingsFragmentCommunicator;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.bookings_activity);

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

        mBookingsFragment = (BookingsFragment) getSupportFragmentManager().findFragmentById(R.id.bookings_content_frame);
        if( mBookingsFragment == null){
            mBookingsFragment = BookingsFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mBookingsFragment, R.id.bookings_content_frame);
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(MyApp.isUpdate()){
            mBookingsFragmentCommunicator.passDataToFragment("UPDATE", new Object[] {true});
        }
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
                                        new Intent(BookingsActivity.this, HotelsActivity.class);
                                startActivity(intentHotels);
                                break;
                            case R.id.flights_navigation_menu_item:
                                Intent intentFlights =
                                        new Intent(BookingsActivity.this, FlightsActivity.class);
                                startActivity(intentFlights);
                                break;
                            case R.id.car_rentals_navigation_menu_item:
                                Intent intentCarRentals =
                                        new Intent(BookingsActivity.this, CarRentalsActivity.class);
                                startActivity(intentCarRentals);
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


    @Override
    public void passDataToActivity(String name, Object... objects) {

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
