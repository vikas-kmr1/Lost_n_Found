package com.lost_n_found.home;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.lost_n_found.R;
import com.lost_n_found.login.LoginActivity;

import java.io.File;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public DrawerLayout drawerLayout;
    private FirebaseAuth mAuth;
    TabLayout tabLayout;
    ViewPager  viewPager;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FirebaseStorage firebaseStorage;

    private FirebaseDatabase firebaseDatabase;
    TextView drawer_username ;
    ShapeableImageView  userdp;
    private DatabaseReference databaseReference;
    public ImageView avatar;
    ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    public String f_name, l_name, enrollment, mobile;
    StorageReference storageRef;
    private Bitmap bitmap;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        mAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();


        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        String uid= firebaseUser.getUid();

        DatabaseReference databaseReference = firebaseDatabase.getReference().child("user").child(uid);
        if(firebaseUser != null) {
            uid = firebaseUser.getUid(); //Do what you need to do with the id
        }


        // Attach a listener to read the data at our posts reference
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                String avatarUrl = dataSnapshot.child("avatar").getValue(String.class).toString();
                setDP(avatarUrl.substring(35));


                String fullname = dataSnapshot.child("username").getValue(String.class);
                drawer_username.setText("Hello, "+fullname);

                String[] F_Lname = fullname.split(" ");
                f_name = F_Lname[0];
                l_name = F_Lname[1];
                enrollment = dataSnapshot.child("enrollment_no").getValue(String.class);

                mobile = dataSnapshot.child("phone").getValue(String.class);


                //Toast.makeText(getContext(), dataSnapshot.child("username").getValue(String.class), Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

        ExtendedFloatingActionButton newPost = root.findViewById(R.id.newPost);

        mAuth = FirebaseAuth.getInstance();

        drawerLayout= root.findViewById(R.id.drawer_layout);
        tabLayout = root.findViewById(R.id.tab_layoutHome);
        tabLayout.addTab(tabLayout.newTab().setText("Lost"));
        tabLayout.addTab(tabLayout.newTab().setText("Found"));
        viewPager = root.findViewById(R.id.viewpagerHome);
        NavigationView navigationView = root.findViewById(R.id.nav_view);

        View headerLayout = navigationView.inflateHeaderView(R.layout.drawer_headert);
        drawer_username = headerLayout.findViewById(R.id.drawer_username);
         userdp = headerLayout.findViewById(R.id.ham_head_img);


        Toolbar toolbar = root.findViewById(R.id.toolbar);
        Menu menu = navigationView.getMenu();

        navigationView.bringToFront();


        ActionBarDrawerToggle toggle=new
                ActionBarDrawerToggle(getActivity(),drawerLayout, toolbar ,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        final HomePostsAdapter adapter= new HomePostsAdapter(getChildFragmentManager(),getContext(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(),true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        newPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),NewPost.class);
                startActivity(intent);

            }
        });


        //checking network connection
        boolean connected = false;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager)
                    getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() &&
                    networkInfo.isConnected();
            if (!connected){
                AlertDialog alertDialog = new AlertDialog.Builder(getContext()).setIcon(R.drawable.no_net).setTitle("NO INTERNET").setMessage("turn on mobile data or wifi")
                        .setPositiveButton("turn on", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
                                startActivity(i);

                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
                                startActivity(i);
                            }
                        }).show();
            }


        } catch (Exception e) {
            // System.out.println("CheckConnectivity Exception: " + e.getMessage());
            Log.v("connectivity", e.toString());
        }




        return root;
    }

    private void setDP(String avatarUrl) {
        StorageReference httpsReference = firebaseStorage.getReference(avatarUrl+"");
        try {
            File localfile = File.createTempFile("avatar","gif");
            httpsReference.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                    bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                    userdp.setImageBitmap(bitmap);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }






    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                }

                else{
                     this.setEnabled(false);
                     requireActivity().onBackPressed();

                }
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.edit_pfile_menu:
                Intent intent = new Intent(getContext(), editProfile.class);
                intent.putExtra("f_name", f_name);
                intent.putExtra("l_name", l_name);
                intent.putExtra("enrollment", enrollment);
                intent.putExtra("mobile", mobile);
                startActivity(intent);

                break;

            case R.id.LogOut_menu:
                mAuth.signOut();
                Intent intent1 = new Intent(getContext(), LoginActivity.class);
                requireActivity().finish();
                startActivity(intent1);
                Toast.makeText(getContext(),"Logout Successfully",Toast.LENGTH_SHORT).show();
                break;

            case R.id.Rate_us_menu:
                try{
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+requireActivity().getPackageName())));
                }
                catch (ActivityNotFoundException e){
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+requireActivity().getPackageName())));
                }
                break;
            case R.id.About_us_menu:
                Intent intent2 = new Intent(getContext(), AboutActivity.class);
                startActivity(intent2);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return false;
    }



}