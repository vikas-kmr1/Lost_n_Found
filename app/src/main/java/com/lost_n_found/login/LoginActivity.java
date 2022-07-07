package com.lost_n_found.login;

import android.annotation.SuppressLint;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.tabs.TabLayout;
import com.lost_n_found.R;

public class LoginActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    TabLayout tabLayout;
    ViewPager viewPager;
    TextView login_using;
    ImageView floatingGooogle, floatingFb, floatingTwitter;
    private ConnectivityManager connectivityManager;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


             //ToDo
            // Configure Google Sign In
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);






            tabLayout = findViewById(R.id.tab_layout);
            viewPager = findViewById(R.id.viewpager);
            floatingGooogle = findViewById(R.id.fab_google);
//        floatingFb=findViewById(R.id.fab_fb);
//        floatingTwitter=findViewById(R.id.fab_tw);
            login_using = findViewById(R.id.login_using);

            tabLayout.addTab(tabLayout.newTab().setText("Login"));
            tabLayout.addTab(tabLayout.newTab().setText("Signup"));
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


            final Login_Singup_Adapter adapter = new Login_Singup_Adapter(getSupportFragmentManager(), this, tabLayout.getTabCount());
            viewPager.setAdapter(adapter);
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.setOnTabSelectedListener(this);


            floatingGooogle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(LoginActivity.this, "Google", Toast.LENGTH_SHORT).show();
                }
            });


//        floatingTwitter.setBackground(getDrawable(R.drawable.ic_header_twitter));
//        floatingFb.setBackground(getDrawable(R.drawable.facebook_icon));
//            floatingGooogle.setBackground(getDrawable(R.drawable.google_icon));
//            floatingGooogle.setBackground(getDrawable(R.drawable.google_icon));


            // floatingTwitter.setTranslationY(300);
            floatingGooogle.setTranslationY(300);
//        floatingFb.setTranslationY(300);
            login_using.setTranslationX(300);
            tabLayout.setTranslationY(300);

//        floatingFb.setAlpha(0);
            floatingGooogle.setAlpha(0);
//        floatingTwitter.setAlpha(0);
            tabLayout.setAlpha(0);
            login_using.setAlpha(0);

            floatingGooogle.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(400).start();
//        floatingTwitter.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(600).start();
//        floatingFb.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(800).start();
            login_using.animate().translationX(0).alpha(1).setDuration(600).setStartDelay(800).start();
            tabLayout.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(100).start();

        }



    // ontabselected implementation
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }




}