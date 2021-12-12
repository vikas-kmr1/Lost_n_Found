package com.lost_n_found.onboard;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.LottieAnimationView;
import com.lost_n_found.R;

public class Splashscreen extends AppCompatActivity {

    LottieAnimationView lottieAnimationView;
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
        lottieAnimationView=findViewById(R.id.lottieAnimationView);


        viewPager=findViewById(R.id.liquid_swipe);
        pagerAdapter=new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        animation= AnimationUtils.loadAnimation(this,R.anim.fade_onboard_anim);
        viewPager.startAnimation(animation);




        //splash animation
        tag1.animate().translationY(-2500).setDuration(1000).setStartDelay(5150);
        tag2.animate().translationY(2500).setDuration(1000).setStartDelay(5150);
        name.animate().translationY(2500).setDuration(1000).setStartDelay(5150);
        bg_img.animate().translationY(2500).setDuration(1000).setStartDelay(5150);
        lottieAnimationView.animate().translationY(2500).setDuration(1000).setStartDelay(5150);




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