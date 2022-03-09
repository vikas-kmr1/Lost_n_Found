package com.lost_n_found.home;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.common.ConnectionResult;
import com.lost_n_found.R;
import com.lost_n_found.databinding.ActivityHomeBinding;

import me.ibrahimsn.lib.OnItemSelectedListener;

public class home extends AppCompatActivity {
    ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());




        FragmentTransaction Home= getSupportFragmentManager().beginTransaction();
        Home.replace(R.id.fragment_container_view_tag,new HomeFragment());
        Home.commit();



        binding.bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                switch (i)
                {
                    case 0:
                        FragmentTransaction home= getSupportFragmentManager().beginTransaction();
                        home.replace(R.id.fragment_container_view_tag,new HomeFragment());
                        home.commit();
                        break;
                    case 1:

                        FragmentTransaction Post= getSupportFragmentManager().beginTransaction();
                        Post.replace(R.id.fragment_container_view_tag,new PostsFragment());
                        Post.commit();
                        break;
                    case 2:
                        FragmentTransaction Map= getSupportFragmentManager().beginTransaction();
                        Map.replace(R.id.fragment_container_view_tag,new MapsFragment0());
                        Map.commit();
                        break;

                    case 3:

                        FragmentTransaction Profile= getSupportFragmentManager().beginTransaction();
                        Profile.replace(R.id.fragment_container_view_tag,new ProfielFragment());
                        Profile.commit();
                        break;
                }
                return true;
            }
        });




        ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts
                                .RequestMultiplePermissions(), result -> {
                    Boolean fineLocationGranted = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        fineLocationGranted = result.getOrDefault(
                                Manifest.permission.ACCESS_FINE_LOCATION, false);
                    }
                    Boolean coarseLocationGranted = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        coarseLocationGranted = result.getOrDefault(
                                Manifest.permission.ACCESS_COARSE_LOCATION, false);
                    }
                    if (fineLocationGranted != null && fineLocationGranted) {
                                // Precise location access granted.
                                Toast.makeText(home.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                            }

                    else if (coarseLocationGranted != null && coarseLocationGranted) {
                                // Only approximate location access granted.
                                Toast.makeText(home.this, "Permission Granted", Toast.LENGTH_SHORT).show();

                            } else {
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getPackageName(), "");
                                intent.setData(uri);
                                startActivity(intent);
                                Toast.makeText(home.this, "Permission requird for location", Toast.LENGTH_SHORT).show();

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



    public void onConnected(@Nullable Bundle bundle) {

    }


    public void onConnectionSuspended(int i) {

    }


    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}