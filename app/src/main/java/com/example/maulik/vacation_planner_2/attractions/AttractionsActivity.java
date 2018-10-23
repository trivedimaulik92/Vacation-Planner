package com.example.maulik.vacation_planner_2.attractions;

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
import com.example.maulik.vacation_planner_2.R;
import com.example.maulik.vacation_planner_2.SplashActivity;
import com.example.maulik.vacation_planner_2.bookings.BookingsActivity;
import com.example.maulik.vacation_planner_2.car_rentals.CarRentalsActivity;
import com.example.maulik.vacation_planner_2.flights.FlightsActivity;
import com.example.maulik.vacation_planner_2.hotels.HotelsActivity;
import com.example.maulik.vacation_planner_2.util.ActivityUtils;
import com.example.maulik.vacation_planner_2.util.GetNearbyPlacesData;
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
import java.util.List;

public class AttractionsActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        AdapterView.OnItemSelectedListener,
        ActivityCommunicator,
        GoogleMap.OnMarkerClickListener{

    private DrawerLayout mDrawerLayout;

    private GoogleMap mGoogleMap;

    private GoogleApiClient mClient;

    private Location lastLocation;

    private LocationRequest mLocationRequest;

    private FloatingActionButton mFabHotels;

    private FloatingActionButton mFabFlights;

    private FloatingActionButton mFabCarRentals;

    private Marker currentLocationMarker;

    private LatLng mSelectedPlaceLocation;

    private String mSelectedPlaceTitle;


    private String url;

    private Object dataTransfer[] = new Object[2];

    public static final int REQUEST_LOCATION_CODE = 99;

    public static final int RADIUS = 600;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.attractions_activity);

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

        AttractionsFragment attractionsFragment = (AttractionsFragment) getSupportFragmentManager().findFragmentById(R.id.attr_content_frame);
        if(attractionsFragment == null){
            attractionsFragment = AttractionsFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), attractionsFragment, R.id.attr_content_frame);
        }


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.attr_map);
        mapFragment.getMapAsync(this);

        mFabHotels = findViewById(R.id.attr_fab_hotels);
        mFabHotels.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(mSelectedPlaceLocation != null){
                    Intent intent = new Intent(getApplicationContext(), HotelsActivity.class);
                    intent.putExtra("EXTRA_PLACE_LAT",mSelectedPlaceLocation.latitude );
                    intent.putExtra("EXTRA_PLACE_LNG",mSelectedPlaceLocation.longitude );
                    intent.putExtra("EXTRA_PLACE_TITLE", mSelectedPlaceTitle);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Select a Place marker", Toast.LENGTH_LONG).show();
                }
            }
        });

        mFabFlights = findViewById(R.id.attr_fab_flights);
        mFabFlights.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(mSelectedPlaceLocation != null){
                    Intent intent = new Intent(getApplicationContext(), FlightsActivity.class);
                    intent.putExtra("EXTRA_PLACE_LAT",mSelectedPlaceLocation.latitude );
                    intent.putExtra("EXTRA_PLACE_LNG",mSelectedPlaceLocation.longitude );
                    intent.putExtra("EXTRA_PLACE_TITLE", mSelectedPlaceTitle);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Select a Place marker", Toast.LENGTH_LONG).show();
                }
            }
        });

        mFabCarRentals = findViewById(R.id.attr_fab_car_rentals);
        mFabCarRentals.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(mSelectedPlaceLocation != null){
                    Intent intent = new Intent(getApplicationContext(), CarRentalsActivity.class);
                    intent.putExtra("EXTRA_PLACE_LAT",mSelectedPlaceLocation.latitude );
                    intent.putExtra("EXTRA_PLACE_LNG",mSelectedPlaceLocation.longitude );
                    intent.putExtra("EXTRA_PLACE_TITLE", mSelectedPlaceTitle);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Select a Place marker", Toast.LENGTH_LONG).show();
                }
            }
        });

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
                                        new Intent(AttractionsActivity.this, HotelsActivity.class);
                                startActivity(intentHotels);
                                break;
                            case R.id.flights_navigation_menu_item:
                                Intent intentFlights =
                                        new Intent(AttractionsActivity.this, FlightsActivity.class);
                                startActivity(intentFlights);
                                break;
                            case R.id.car_rentals_navigation_menu_item:
                                Intent intentCarRentals =
                                        new Intent(AttractionsActivity.this, CarRentalsActivity.class);
                                startActivity(intentCarRentals);
                                break;
                            case R.id.bookings_navigation_menu_item:
                                Intent intentBookings =
                                        new Intent(AttractionsActivity.this, BookingsActivity.class);
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

    public void onSearch(String location, String activity) {
        //mSearchLocation = (EditText) v.findViewById(R.id.attr_et_location);
        List<Address> addressList = null;
        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
        //mSpinner = (Spinner) v.findViewById(R.id.attr_spinner_activity);
        mGoogleMap.clear();

        if(!location.equals(""))
        {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 5);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(addressList.size() == 0)
            {
                Toast.makeText(this, "Invalid location!", Toast.LENGTH_LONG).show();
            }
            else
            {
                Address myAddress = addressList.get(0);
                //Toast.makeText(this, myAddress.toString(), Toast.LENGTH_LONG).show();
                //activity = "park";
                activity.replace(" ", "%20");
                url = getUrl(myAddress.getLatitude(), myAddress.getLongitude(), activity);
                Toast.makeText(this, url, Toast.LENGTH_LONG).show();
                dataTransfer[0] = mGoogleMap;
                dataTransfer[1] = url;
                getNearbyPlacesData.execute(dataTransfer);
            }
        }
        else
        {
            activity.replace(" ", "%20");
            Toast.makeText(this, "Getting nearby "+activity, Toast.LENGTH_LONG).show();
            if(activity.equals("Hotel")) activity = "lodging";
            url = getUrl(lastLocation.getLatitude(), lastLocation.getLongitude(), activity);
            dataTransfer[0] = mGoogleMap;
            dataTransfer[1] = url;
            getNearbyPlacesData.execute(dataTransfer);
        }
    }

    private String getUrl(double latitude, double longitude, String activity) {
        StringBuilder sb = new StringBuilder();
        sb.append("https://maps.googleapis.com/maps/api/place/textsearch/json?");
        sb.append("location="+latitude+","+longitude);
        sb.append("&radius="+RADIUS);
        sb.append("&query="+activity);
        sb.append(("&key=AIzaSyA1zabXEfdBHpAlhFXeZMw_faoCsOL31Kw"));
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
        String[] args = (String[]) objects[0];
        onSearch(args[0], args[1]);
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        mSelectedPlaceLocation = marker.getPosition();
        mSelectedPlaceTitle = marker.getTitle();
        return false;
    }
}
