package com.example.spark;


import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MapActivity extends FragmentActivity implements NavigationView.OnNavigationItemSelectedListener
        , OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {


    //Firebase
    private FirebaseAuth auth;

    //Permissions
    private final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private final int FINE_LOCATION_PERMISSION_REQUEST_CODE = 1;
    private final int COARSE_LOCATION_PERMISSION_REQUEST_CODE = 2;
    private boolean locPermission;

    //Map variables
    private GoogleMap mGoogleMap;
    private Marker marker;
    private List<Marker> markerList;

    //API
    private GoogleApiClient googleApiClient;

    //Display objects
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private AutoCompleteTextView autoCompleteTextView;
    private LinearLayout searchLayout;
    private ImageView recenter;
    private ImageView details;

    //ParkingSpot Variable
    private double radius;
    public Location location;
    public Location currentLocation;
    private LatLng current;
    private LatLng dest;

    private FusedLocationProviderClient fusedLocationProviderClient;

    //ParkingSpot requests
    private LocationRequest locationRequest;
    private int locPriority;
    private final int PRIORITY_BALANCED_POWER_ACCURACY = 2;
    private final int PRIORITY_HIGH_ACCURACY = 3;
    private final int PRIORITY_LOW_POWER = 1;
    private final int PRIORITY_NO_POWER = 0;

    //Map elements
    private final float DEFAULT_ZOOM = 15.0f;
    //Places
    private String placeID;
    private com.example.spark.PlaceAutocompleteAdapter placeAutocompleteAdapter;
    private final LatLngBounds LATLNG_BOUNDS = new LatLngBounds(
            new LatLng(-40, -168), new LatLng(71, 136));
    private Place place;
    private List<Address> addresses;
    private AutocompletePrediction prediction;

    //Listener


    //Miscellaneous
    private final int ERROR_SERVICES = 9001;

    private final String TAG = getClass().getSimpleName();
    public static final int REQUEST_CODE=101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();
        googleApiClient = new GoogleApiClient.Builder(this).addApi(Places.GEO_DATA_API).addApi(LocationServices.API)
                .addApi(Places.PLACE_DETECTION_API).build();
        locPermission = false;
        radius = 10;
        setUpDisplay();
        locPriority = PRIORITY_BALANCED_POWER_ACCURACY;
        checkPermissions();
    }

    private void fetchLastLocation() {
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            public void onSuccess(Location location) {
                if(location != null){
                    currentLocation = location;
                    Toast.makeText(getApplicationContext(),currentLocation.getLatitude()
                    +""+currentLocation.getLongitude(),Toast.LENGTH_SHORT).show();
                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    supportMapFragment.getMapAsync(MapActivity.this);
                }
            }
        });
    }

    private void hideKeyboard() {
        InputMethodManager methodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (methodManager != null) {
            methodManager.hideSoftInputFromWindow(autoCompleteTextView.getWindowToken(), 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 101:
                if (resultCode == RESULT_OK) {
                    ArrayList<ParkingSpot> parkingSpots =(ArrayList<ParkingSpot>)data.getSerializableExtra("requestLoc");
                    for (ParkingSpot parkingSpot : parkingSpots) {
                        mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(parkingSpot.getLatitiude(),parkingSpot.getLongitude()))
                                .title(parkingSpot.getName()));
                    }
                }
                break;
        }
    }

    private void setUpDisplay() {

        markerList = new ArrayList<>();

        toolbar = findViewById(R.id.map_toolbar);


        drawerLayout = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        searchLayout = findViewById(R.id.search_layout);
        searchLayout.setAlpha(0.65f);

        details = findViewById(R.id.details);
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                intent.putExtra("loc", location);
                startActivityForResult(intent, 101);
            }
        });

        autoInit();

        recenter = findViewById(R.id.reset);
        recenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentLocation();
            }
        });

    }

    private void autoInit() {
        autoCompleteTextView = findViewById(R.id.search_auto);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        AutocompleteFilter filter = new AutocompleteFilter.Builder().setTypeFilter(Place.TYPE_COUNTRY).setCountry("IN").build();
        placeAutocompleteAdapter = new PlaceAutocompleteAdapter(this, googleApiClient, LATLNG_BOUNDS, filter);
        //autoCompleteTextView.setOnItemClickListener(adapterClickListener);
        autoCompleteTextView.setAdapter(placeAutocompleteAdapter);
        autoCompleteTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE
                        || actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == KeyEvent.ACTION_DOWN
                        || actionId == KeyEvent.KEYCODE_ENTER) {
                    hideKeyboard();
                    moveToSearchedAddress(v.getText().toString());

                }
                return false;
            }
        });
    }

    public boolean isServicesLatest() {
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if (available == ConnectionResult.SUCCESS) {
            //everything's fine
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //fixable error
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(this, available, ERROR_SERVICES);
            dialog.show();
        } else //no can't do anything
            Toast.makeText(this, "You can't make Map requests", Toast.LENGTH_SHORT).show();
        return false;
    }

    private BroadcastReceiver mLocationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            float latitude = intent.getFloatExtra(LocationService.USER_LATITUDE, 0);
            float longitude = intent.getFloatExtra(LocationService.USER_LONGITUDE, 0);
            current = new LatLng(latitude, longitude);
            moveCamera(current, DEFAULT_ZOOM);
            //do something
        }
    };

    private void moveToSearchedAddress(String query) {
        if (query == null)
            return;
        autoCompleteTextView.clearListSelection();
        Geocoder geocoder = new Geocoder(this);
        try {
            addresses = geocoder.getFromLocationName(query, 5);

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses.size() > 0) {
            if (addresses.get(0) != null) {
                Address address = addresses.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                moveCamera(latLng, DEFAULT_ZOOM, address.getAddressLine(0));
            }
        }

    }

    private void checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(this, COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{COARSE_LOCATION}, COARSE_LOCATION_PERMISSION_REQUEST_CODE);
            } else {
                locPermission = true;
                initMap();
            }
        } else
            ActivityCompat.requestPermissions(this, new String[]{FINE_LOCATION}, FINE_LOCATION_PERMISSION_REQUEST_CODE);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        locPermission = false;
        switch (requestCode) {
            case FINE_LOCATION_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                        locPermission = false;
                        return;
                    } else {
                        locPermission = true;
                        initMap();
                    }
                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void initMap() {
        googleApiClient.connect();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent service = new Intent(this, LocationService.class);

        IntentFilter intentLocationServiceFilter = new IntentFilter(LocationService
                .LOCATION_SERVICE);
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mLocationReceiver, intentLocationServiceFilter);
        if (isServicesLatest()) {
            startService(service);
        }
    }

    private void getCurrentLocation() {
        if (locPermission) {
            mGoogleMap.clear();
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                checkPermissions();
            } else {
                fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            location = task.getResult();
                            current = new LatLng(location.getLatitude(), location.getLongitude());
                            moveCamera(current, DEFAULT_ZOOM);
                        }
                    }
                });
            }
        }
    }


    private void moveCamera(LatLng latLng, float zoom) {
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void moveCamera(LatLng latLng, float zoom, String desc) {
        marker = mGoogleMap.addMarker(new MarkerOptions().position(latLng).title(desc));
        markerList.add(marker);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_account:
                startActivity(new Intent(getApplicationContext(), AccountActivity.class));
                finish();
                break;
            case R.id.nav_logout:
                auth = FirebaseAuth.getInstance();
                if (auth.getCurrentUser() != null) {
                    auth.signOut();
                }
                startActivity(new Intent(getApplicationContext(), Login.class));
                break;
        }
        return false;
    }


    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        this.marker = marker;
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng latLng = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("i am here");
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,5));
        googleMap.addMarker(markerOptions);

        if (mGoogleMap == null) {
            mGoogleMap = googleMap;
        }
        mGoogleMap.getUiSettings().setMapToolbarEnabled(true);
        mGoogleMap.getUiSettings().setRotateGesturesEnabled(false);
        mGoogleMap.getUiSettings().setCompassEnabled(false);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);

        if (locPermission) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                checkPermissions();
            } else {
                getCurrentLocation();
                mGoogleMap.setMyLocationEnabled(true);
            }
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        if (marker != null) {
//            dest = new LatLng(marker.getPosition().latitude,marker.getPosition().longitude);
//            ParkingSpot abc = new ParkingSpot("Random");
//            abc.setLongitude(dest.longitude);
//            abc.setLatitude(dest.latitude);
//            Intent intent = new Intent(this, TrackerService.class);
//            intent.putExtra("loc", abc);
//            startService(intent);
//            marker=null;
//        }

    }
}
