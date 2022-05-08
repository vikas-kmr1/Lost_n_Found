package com.lost_n_found.home;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyPostAdapter extends FragmentPagerAdapter {


    private Context context;

    int total_Tabs;

    public MyPostAdapter(FragmentManager fm, Context context, int total_Tabs) {
        super(fm);
        this.context = context;
        this.total_Tabs = total_Tabs;


    }


    @Override
    public int getCount() {
        return total_Tabs;
    }



    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                PostItemFragment tab1 = new PostItemFragment();
                return tab1;


            default:
                return null;


        }


    }
}
