package com.lost_n_found.home;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class HomePostsAdapter extends FragmentPagerAdapter {


    private Context context;

    int total_Tabs;

    public HomePostsAdapter(FragmentManager fm, Context context, int total_Tabs) {
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
                LostFragment tab1 = new LostFragment();
                return tab1;
            case 1:
                FoundFragment tab2 = new FoundFragment();
                return tab2;

            default:
                return null;


        }


    }
}
