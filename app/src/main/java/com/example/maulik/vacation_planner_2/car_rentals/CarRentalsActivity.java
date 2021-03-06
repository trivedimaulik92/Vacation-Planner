package com.example.maulik.vacation_planner_2.car_rentals;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.amazonaws.mobile.auth.core.IdentityManager;
import com.example.maulik.vacation_planner_2.ActivityCommunicator;
import com.example.maulik.vacation_planner_2.FragmentCommunicator;
import com.example.maulik.vacation_planner_2.R;
import com.example.maulik.vacation_planner_2.SplashActivity;
import com.example.maulik.vacation_planner_2.attractions.AttractionsActivity;
import com.example.maulik.vacation_planner_2.bookings.BookingsActivity;
import com.example.maulik.vacation_planner_2.data.CarRental;
import com.example.maulik.vacation_planner_2.data.Hotel;
import com.example.maulik.vacation_planner_2.hotels.HotelsActivity;
import com.example.maulik.vacation_planner_2.hotels.HotelsFragment;
import com.example.maulik.vacation_planner_2.util.ActivityUtils;
import com.example.maulik.vacation_planner_2.util.GetNearbyHotelsData;
import com.example.maulik.vacation_planner_2.util.GetNearbyPlacesData;
import com.example.maulik.vacation_planner_2.util.GetNeatbyCarRentalsData;
import com.example.maulik.vacation_planner_2.util.ResultListFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CarRentalsActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        AdapterView.OnItemSelectedListener,
        ActivityCommunicator,
        GoogleMap.OnMarkerClickListener {
    private DrawerLayout mDrawerLayout;

    private CarRentalSearchFragment mCarRentalsSearchFragment;

    private ResultListFragment mResultListFragment;

    private GoogleMap mGoogleMap;

    private GoogleApiClient mClient;

    private LocationRequest mLocationRequest;

    private FloatingActionButton mFabSearch;

    private Marker currentLocationMarker;

    private LatLng mSelectedPlaceLocation;

    private String mSelectedPlaceTitle;

    private Location lastLocation;

    private LatLng mSelectedCarRentalLocation;

    private String mSelectedCarRentalTitle;

    private String url;

    private String mCity;

    private String mFromDate;

    private String mToDate;

    CarRental mCarRental;

    private List<CarRental> mCarRentals;

    public FragmentCommunicator mCarRentalsSearchFragmentCommunicator;

    public FragmentCommunicator mResultlistFragmentCommunicator;

    private Object dataTransfer[] = new Object[2];

    public static final int REQUEST_LOCATION_CODE = 99;

    public static final int RADIUS = 15;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.car_rentals_activity);

        mSelectedPlaceLocation = new LatLng(getIntent().getDoubleExtra("EXTRA_PLACE_LAT", 0.0),
                getIntent().getDoubleExtra("EXTRA_PLACE_LNG", 0.0));
        mSelectedPlaceTitle = getIntent().getStringExtra("EXTRA_PLACE_TITLE");

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

        if(mCarRentalsSearchFragment == null){
            mCarRentalsSearchFragment = CarRentalSearchFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mCarRentalsSearchFragment, R.id.car_rentals_content_frame);
        }


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.car_rentals_map);
        mapFragment.getMapAsync(this);


        mFabSearch = findViewById(R.id.car_rentals_fab_search);
        mFabSearch.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                mCarRentalsSearchFragment.passDataToFragment("CAR_RENTALS_FAB_SEARCH",null);
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
                                Intent intent =
                                        new Intent(CarRentalsActivity.this, HotelsActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.bookings_navigation_menu_item:
                                Intent intentBookings =
                                        new Intent(CarRentalsActivity.this, BookingsActivity.class);
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

    public void onSearch(String mCity, String fromDate, String toDate) {
        //mSearchLocation = (EditText) v.findViewById(R.id.attr_et_location);
        List<Address> addressList = null;
        Address myAddress = null;
        GetNeatbyCarRentalsData getNeatbyCarRentalsData = new GetNeatbyCarRentalsData();

        if(!mCity.equals(""))
        {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(mCity, 5);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(addressList.size() == 0)
            {
                Toast.makeText(this, "Invalid location!", Toast.LENGTH_LONG).show();
            }
            else
            {
                 myAddress = addressList.get(0);
                //Toast.makeText(this, myAddress.toString(), Toast.LENGTH_LONG).show();
                //activity = "park";
            }
            Toast.makeText(this, "Getting nearby car rentals", Toast.LENGTH_LONG).show();
            url = getUrl(myAddress.getLatitude(), myAddress.getLongitude(), fromDate, toDate);
            dataTransfer[0] = mGoogleMap;
            dataTransfer[1] = url;
            getNeatbyCarRentalsData.execute(dataTransfer);
            mCarRentals = getNeatbyCarRentalsData.getCarRentals();
        }
    }


    private String getUrl(double latitude, double longitude, String fromDate, String toDate) {
        StringBuilder sb = new StringBuilder();
        sb.append("https://api.sandbox.amadeus.com/v1.2/cars/search-circle?");
        sb.append("latitude="+latitude);
        sb.append("&longitude="+longitude);
        sb.append("&radius="+RADIUS);
        sb.append("&pick_up="+fromDate);
        sb.append("&drop_off="+toDate);
        sb.append(("&apikey=ZyPWVaMnmLmVDUWYfEn7fOSwioGgHjyG"));
        return sb.toString();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
        }
        mGoogleMap.setMyLocationEnabled(true);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(mSelectedPlaceLocation);
        markerOptions.title(mSelectedPlaceTitle);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mGoogleMap.addMarker(markerOptions);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(mSelectedPlaceLocation));
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(13));
        mGoogleMap.setOnMarkerClickListener(this);
    }


    protected synchronized void buildGoogleApiClient(){
        mClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mClient.connect();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            LocationServices.FusedLocationApi.requestLocationUpdates(mClient, mLocationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;

        if(currentLocationMarker != null){
            currentLocationMarker.remove();
        }

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        currentLocationMarker = mGoogleMap.addMarker(markerOptions);

        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomBy(10));

        if(mClient != null){
            LocationServices.FusedLocationApi.removeLocationUpdates(mClient, this);
        }
    }


    @Override
    public void passDataToActivity(String name, Object... objects) {
        if(name.equals("CAR_RENTALS_SEARCH")){
            String[] args = (String[]) objects[0];
            if(args[0] != "" && args[1] != "" && args[2] != ""){
                mCity = args[0];
                mFromDate = args[1];
                mToDate = args[2];

                onSearch( mCity, mFromDate, mToDate);
            }

        }
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        mSelectedCarRentalLocation = marker.getPosition();
        mSelectedCarRentalTitle = marker.getTitle();

        for(int i=0; i<mCarRentals.size(); i++){
            mCarRental = mCarRentals.get(i);
            if(mSelectedCarRentalTitle.contains(mCarRental.getAgency())){
                break;
            }
        }
        mCarRental.setFromDate(mFromDate);
        mCarRental.setToDate(mToDate);

        showCarRental(mCarRental);
        return false;
    }

    private void showCarRental(CarRental mCarRental) {
        //ActivityUtils.detachFragmentFromActivity(getSupportFragmentManager(), mHotelsFragment);

        if(mResultListFragment == null){
            mResultListFragment = ResultListFragment.newInstance();
            ActivityUtils.replaceFragmentToActivity(getSupportFragmentManager(), mResultListFragment, R.id.car_rentals_content_frame);
        }
        ArrayList<CarRental> carRentals = new ArrayList<>();
        carRentals.add(mCarRental);
        mResultlistFragmentCommunicator.passDataToFragment("SHOW_CAR_RENTALS_LIST", carRentals);
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
