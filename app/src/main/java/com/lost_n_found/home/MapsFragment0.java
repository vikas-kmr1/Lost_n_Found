package com.lost_n_found.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lost_n_found.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsFragment0 extends Fragment {
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    FloatingActionButton fab, fab1;

    SearchView searchView;
    PlacesClient placesClient;


    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            UiSettings uiSetting = mMap.getUiSettings();
            uiSetting.setCompassEnabled(true);
            mMap.setBuildingsEnabled(true);
            mMap.setPadding(0, 225, 0, 0);

            uiSetting.setMapToolbarEnabled(true);
            uiSetting.setZoomControlsEnabled(true);
            uiSetting.setZoomGesturesEnabled(true);
            uiSetting.setRotateGesturesEnabled(true);
            uiSetting.setAllGesturesEnabled(true);
            uiSetting.setScrollGesturesEnabledDuringRotateOrZoom(true);

            LatLng amity = new LatLng(28.4650362, 77.4844781);
            mMap.addMarker(new MarkerOptions()
                    .position(amity)
                    .title("AUGN"));
            //move map camera
            // mMap.moveCamera(CameraUpdateFactory.newLatLng(amity));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(amity, 18));
        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_maps, container, false);



        String api_Key= getString(R.string.google_maps_key);

        searchView = (SearchView) root.findViewById(R.id.search_Tab);
        try {

            if (!Places.isInitialized()) {
                Places.initialize(getContext(), api_Key);
            }
            placesClient = Places.createClient(getContext());


            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override

                public boolean onQueryTextSubmit(String query) {
                    List<Address> addresses = null;
                    String loc_name = searchView.getQuery().toString();
                    Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                    try {
                        addresses = geocoder.getFromLocationName(loc_name, 5);
                    } catch (IOException | ArrayIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }

                    Address address = addresses.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title(loc_name));

                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
                    Toast.makeText(getContext(), address.getLatitude() + " " + address.getLongitude(), Toast.LENGTH_LONG).show();

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {

                    return false;
                }
            });
        }

        catch (Exception e){

        }


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        fab = root.findViewById(R.id.fab);
        fab1 = root.findViewById(R.id.fab1);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentLocation();
            }
        });


        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onMapReady(mMap);


            }
        });


        return root;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapF);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }


    private void getCurrentLocation() {


        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        UiSettings uiSetting = mMap.getUiSettings();

        uiSetting.setCompassEnabled(true);
        mMap.setBuildingsEnabled(true);
        mMap.setTrafficEnabled(true);
        mMap.setBuildingsEnabled(true);
        mMap.setPadding(0, 225, 0, 0);
        uiSetting.setMapToolbarEnabled(true);
        uiSetting.setZoomControlsEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        uiSetting.setZoomGesturesEnabled(true);
        uiSetting.setMyLocationButtonEnabled(true);
        uiSetting.setAllGesturesEnabled(true);
        uiSetting.setRotateGesturesEnabled(true);
        uiSetting.setScrollGesturesEnabledDuringRotateOrZoom(true);




        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


        fusedLocationClient.getLastLocation().addOnCompleteListener(task -> {
            try {
                if (task.isSuccessful()) {
                    Location location = task.getResult();
                    LatLng cur_loc = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions()
                            .position(cur_loc)
                            .title("You"));



                    //move camera


                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cur_loc, 18));


                }
            } catch (NullPointerException e) {
                Toast.makeText(getContext(), "Turn ON Location", Toast.LENGTH_SHORT).show();
            }




        });



    }






    public void onConnected(@Nullable Bundle bundle) {

    }


    public void onConnectionSuspended(int i) {

    }


    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }







}