package com.lost_n_found.onboard;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lost_n_found.R;
import com.lost_n_found.home.CreatePost;
import com.lost_n_found.home.home;
import com.lost_n_found.home.placeholder.PlaceholderContent;
import com.lost_n_found.home.placeholder.PlaceholderFoundContent;
import com.lost_n_found.home.placeholder.PlaceholderLostContent;

public class Splashscreen extends AppCompatActivity {

    ImageView iconView;
    TextView tag1,tag2,name,start;

    ImageView bg_img;
    private static final int num_pages=3;
    private ViewPager viewPager;
    private ScreenSlidePagerAdapter pagerAdapter;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        // creating post database


        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String uid = firebaseUser.getUid();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DatabaseReference databaseReferenceUser = firebaseDatabase.getReference("user");
        DatabaseReference databaseReferencePosts = firebaseDatabase.getReference("posts/"+uid);
        DatabaseReference databaseReference = firebaseDatabase.getReference("posts");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PlaceholderFoundContent.ITEMS.clear();
                PlaceholderLostContent.ITEMS.clear();
                for( DataSnapshot snapshot1: snapshot.getChildren()){
                    //Log.v("-11>",snapshot1.getChildrenCount()+"");
                    for(DataSnapshot snapshot2: snapshot1.getChildren()){
                        CreatePost post = snapshot2.getValue(CreatePost.class);
                        String title = post.getTitle().toString();
                        String date = post.getDate().toString();
                        String descp = post.getDescription();
                        String status = post.getStatus().toString();
                        String location = post.getLocation().toString();
                        String userid = post.getUid().toString();
                        String username = post.getUsername().toString();
                        String imageUrl = post.getPostImgUrl().toString();
                        String contact = post.getContact().toString();
                        if (status.equals("lost")) {
                            PlaceholderLostContent.PlaceholderItem obj = new PlaceholderLostContent.PlaceholderItem(uid + "", title + "", status + "", descp + "", date + "", location + "", username + "", imageUrl + "",contact);
                            PlaceholderLostContent.ITEMS.add(obj);
                        } else {
                            PlaceholderFoundContent.PlaceholderItem obj = new PlaceholderFoundContent.PlaceholderItem(uid + "", title + "", status + "", descp + "", date + "", location + "", username + "", imageUrl + "",contact);
                            PlaceholderFoundContent.ITEMS.add(obj);
                        }
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






        databaseReferencePosts.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                
                CreatePost post = snapshot.getValue(CreatePost.class);
                String title = post.getTitle().toString();
                String date = post.getDate().toString();
                String descp = post.getDescription();
                String status = post.getStatus().toString();
                String location = post.getLocation().toString();
                String userid = post.getUid().toString();
                String contact = post.getContact().toString();
                String username = post.getUsername().toString();
                String imageUrl = post.getPostImgUrl().toString();
                PlaceholderContent.PlaceholderItem obj  =  new PlaceholderContent.PlaceholderItem(uid+"",title+"",status+"",descp+"",date+"",location+"", username+"",imageUrl+"",contact);
                PlaceholderContent.ITEMS.add(obj);


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        //status bar color


        if (Build.VERSION.SDK_INT >= 21) {
           Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.splashstatus));
        }

        //initialization
        tag1=findViewById(R.id.tag1);
        tag2=findViewById(R.id.tag2);
        name=findViewById(R.id.app_name);
        bg_img=findViewById(R.id.bg_img);
        iconView=findViewById(R.id.iconView);



        //splash animation
        tag1.animate().translationY(-2500).setDuration(1200).setStartDelay(2500);
        tag2.animate().translationY(2500).setDuration(1200).setStartDelay(2500);
        name.animate().translationY(2500).setDuration(1200).setStartDelay(2500);
        bg_img.animate().translationY(2500).setDuration(1200).setStartDelay(2500);
        iconView.animate().translationY(2500).setDuration(1200).setStartDelay(2500);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null && user.isEmailVerified()){
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms
                    Intent intent = new Intent(getApplicationContext(), home.class);
                    startActivity(intent);
                    finish();

                }
            }, 1000);
        }
        else  {
            pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
            viewPager = findViewById(R.id.liquid_swipe);
            viewPager.setAdapter(pagerAdapter);
            animation = AnimationUtils.loadAnimation(this, R.anim.fade_onboard_anim);
            viewPager.startAnimation(animation);

        }



    }

    @Override
    public int checkPermission(String permission, int pid, int uid) {
        return super.checkPermission(permission, pid, uid);
    }

    //onboard Fragment call for liquid_swipe

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter{

        public  ScreenSlidePagerAdapter(@NonNull FragmentManager fm)
        {
            super(fm);
        }



        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position)
            {
                case 0:
                    OnBoardingFragment1 tab1=new OnBoardingFragment1();
                    return tab1;
                case 1:
                    OnBoardingFragment2 tab2=new OnBoardingFragment2();

                    return tab2;
                case 2:
                    OnBoardingFragment3 tab3=new OnBoardingFragment3();
                    return tab3;
            }
            return null;
        }

        @Override
        public int getCount() {
            return num_pages;
        }
    }


    @Override
    public Resources getResources() {
        return super.getResources();
    }


}