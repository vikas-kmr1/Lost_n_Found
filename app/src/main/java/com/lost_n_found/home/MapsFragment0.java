package com.lost_n_found.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lost_n_found.LocationAddress;
import com.lost_n_found.R;

import java.util.ArrayList;
import java.util.Objects;

public class MapsFragment0 extends Fragment {
    FloatingActionButton fab, fab1;
    AutoCompleteTextView searchView;
    PlacesClient placesClient;
    Handler handler;
    Fragment mapView;
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
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
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            UiSettings uiSetting = mMap.getUiSettings();
            uiSetting.setCompassEnabled(true);
            mMap.setBuildingsEnabled(true);
            mMap.setPadding(0, 225, 0, 0);

            uiSetting.setMapToolbarEnabled(true);

            uiSetting.setTiltGesturesEnabled(true);
            uiSetting.setCompassEnabled(true);
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


            LatLng amity = new LatLng(28.4650362, 77.4844781);
            Objects.requireNonNull(mMap.addMarker(new MarkerOptions()
                    .position(amity)
                    .title("AUGN"))).setInfoWindowAnchor(0, 0);

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
        mapView = getFragmentManager().findFragmentById(R.id.mapF);




        String api_Key = getString(R.string.google_maps_key);

        handler = new Handler();
        searchView = (AutoCompleteTextView) root.findViewById(R.id.search_Tab);
        ImageView clear = (ImageView) root.findViewById(R.id.clear);

        ArrayList<LocationAddress> addresses = new ArrayList<LocationAddress>();
        addresses.add(new LocationAddress("library", 28.465050297574223, 77.48553663280784));
        addresses.add(new LocationAddress("canteen", 28.466739917619222, 77.48698151307954));
        addresses.add(new LocationAddress("boys hostel", 28.466968549960846, 77.48675929538683));
        addresses.add(new LocationAddress("girls hostel", 28.46648358441695, 77.48717249720775));
        addresses.add(new LocationAddress("basketball court", 28.46579990518758, 77.48404459193));
        addresses.add(new LocationAddress("cricket ground", 28.465823346959688, 77.48634796428594));
        addresses.add(new LocationAddress("football ground", 28.46570860808506, 77.48612740798906));
        addresses.add(new LocationAddress("badminton court", 28.465316187569606, 77.48585525984998));
        addresses.add(new LocationAddress("Workshop", 28.465161593031073, 77.48615366866387));
        addresses.add(new LocationAddress("Management Block", 28.46604108986469, 77.48569738493114));
        addresses.add(new LocationAddress("Engineering Block", 28.464937256969765, 77.48521822319103));
        addresses.add(new LocationAddress("Auditorium Engineering Block", 28.464905496514692, 77.48530541632739));
        addresses.add(new LocationAddress("Auditorium Management Block", 28.46610087544097, 77.4854550363742));
        addresses.add(new LocationAddress("Registrar Office", 28.46520893693702, 77.48508225657666));

        ArrayList<String> places = new ArrayList<String>();
        for (int i = 0; i < addresses.size(); i++) {
            places.add(addresses.get(i).place);
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_expandable_list_item_1, places);
        searchView.setAdapter(adapter);
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    clear.setVisibility(View.VISIBLE);
                }
                if (s.length() == 0) {
                    clear.setVisibility(View.GONE);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setText("");
            }
        });

        searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemPos = places.indexOf(adapter.getItem(position));
                adapterItemLocation(addresses.get(itemPos).place, addresses.get(itemPos).lat, addresses.get(itemPos).lon);
            }
        });


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
                goToAugn();


            }
        });

        return root;

    }

    public void goToAugn() {
        LatLng amity = new LatLng(28.4650362, 77.4844781);



        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(amity, 18));
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                mMap.addMarker(new MarkerOptions()
                        .position(amity)
                        .title("AUGN"));
                //move map camera
                // mMap.moveCamera(CameraUpdateFactory.newLatLng(amity));
                mMap.animateCamera(CameraUpdateFactory.zoomIn());

            }
        },1000);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        mapView.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);

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


      //  mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        UiSettings uiSetting = mMap.getUiSettings();
//
//        uiSetting.setCompassEnabled(true);
//        mMap.setBuildingsEnabled(true);
//        mMap.setTrafficEnabled(true);
//        mMap.setBuildingsEnabled(true);
//        mMap.setPadding(0, 225, 0, 0);
//        uiSetting.setMapToolbarEnabled(true);
//        uiSetting.setZoomControlsEnabled(true);
//        mMap.getUiSettings().setRotateGesturesEnabled(true);
//        uiSetting.setZoomGesturesEnabled(true);
//        uiSetting.setMyLocationButtonEnabled(true);
//        uiSetting.setAllGesturesEnabled(true);
//        uiSetting.setRotateGesturesEnabled(true);
//        uiSetting.setScrollGesturesEnabledDuringRotateOrZoom(true);


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



                    //move camera
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cur_loc, 18f));
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mMap.addMarker(new MarkerOptions()
                                    .position(cur_loc)
                                    .title("You"));
                            mMap.animateCamera(CameraUpdateFactory.zoomIn());
                        }
                    }, 1000);
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


    public void adapterItemLocation(String loc_name, double lat, double lon) {

        LatLng latLng = new LatLng(lat, lon);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,18));
        mMap.addMarker(new MarkerOptions().position(latLng).title(loc_name));
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mMap.animateCamera(CameraUpdateFactory.zoomIn());
                searchView.setText("");
            }

        },1000);


    }


}