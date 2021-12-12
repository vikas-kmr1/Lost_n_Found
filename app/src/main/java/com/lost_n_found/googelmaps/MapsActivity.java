package com.lost_n_found.googelmaps;

// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lost_n_found.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    FloatingActionButton fab,fab1;
    SearchView searchView;



    // Set the fields to specify which types of place data to
    // return after the user has made a selection.

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        searchView=findViewById(R.id.search_Tab);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override





            public boolean onQueryTextSubmit(String query) {
                List<Address> addresses=null;
                String loc_name=searchView.getQuery().toString();
                Geocoder geocoder=new Geocoder(MapsActivity.this, Locale.getDefault());
                try {
                   addresses=geocoder.getFromLocationName(loc_name,5);
                } catch (IOException | ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
                Address address = addresses.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                mMap.addMarker(new MarkerOptions().position(latLng).title(loc_name));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                Toast.makeText(getApplicationContext(),address.getLatitude()+" "+address.getLongitude(),Toast.LENGTH_LONG).show();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });




        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fab = findViewById(R.id.fab);
        fab1 = findViewById(R.id.fab1);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentLocation();
            }
        });



        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              onMapReady( mMap);

            }
        });


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts
                                .RequestMultiplePermissions(), result -> {
                            Boolean fineLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_FINE_LOCATION, false);
                            Boolean coarseLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_COARSE_LOCATION, false);
                            if (fineLocationGranted != null && fineLocationGranted) {
                                // Precise location access granted.
                                Toast.makeText(MapsActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                            } else if (coarseLocationGranted != null && coarseLocationGranted) {
                                // Only approximate location access granted.
                                Toast.makeText(MapsActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getPackageName(), "");
                                intent.setData(uri);
                                startActivity(intent);
                                Toast.makeText(MapsActivity.this, "Permission requird for location", Toast.LENGTH_SHORT).show();

                                // No location access granted.
                            }
                        }
                );

// ...

// Before you perform the actual permission request, check whether your app
// already has the permissions, and whether your app needs to show a permission
// rationale dialog. For more details, see Request permissions.
        locationPermissionRequest.launch(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });


    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     *
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setBuildingsEnabled(true);
        UiSettings uiSetting=mMap.getUiSettings();
        uiSetting.setCompassEnabled(true);
        mMap.setTrafficEnabled(true);
        uiSetting.setMapToolbarEnabled(true);
        uiSetting.setZoomControlsEnabled(true);
        uiSetting.setZoomGesturesEnabled(true);
        uiSetting.setMyLocationButtonEnabled(true);
        uiSetting.setAllGesturesEnabled(true);
        uiSetting.setScrollGesturesEnabledDuringRotateOrZoom(true);

        LatLng amity = new LatLng(28.4650362, 77.4844781);
        mMap.addMarker(new MarkerOptions()
                .position(amity)
                .title("AUGN"));
        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(amity));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(18f));
    }





    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {


        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        UiSettings uiSetting=mMap.getUiSettings();
        uiSetting.setCompassEnabled(true);
        mMap.setBuildingsEnabled(true);
        mMap.setTrafficEnabled(true);
        uiSetting.setMapToolbarEnabled(true);
        uiSetting.setZoomControlsEnabled(true);
        uiSetting.setZoomGesturesEnabled(true);
        uiSetting.setMyLocationButtonEnabled(true);
        uiSetting.setAllGesturesEnabled(true);
        uiSetting.setScrollGesturesEnabledDuringRotateOrZoom(true);

        fusedLocationClient.getLastLocation().addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                Location location = task.getResult();
                LatLng cur_loc = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions()
                        .position(cur_loc)
                        .title("You"));
                //move camera
                mMap.moveCamera(CameraUpdateFactory.newLatLng(cur_loc));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(18f));

            }

        });

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }








}


