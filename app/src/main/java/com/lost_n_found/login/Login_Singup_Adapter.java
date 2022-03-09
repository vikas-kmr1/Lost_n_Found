package com.lost_n_found.login;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class Login_Singup_Adapter extends FragmentPagerAdapter {

    private Context context;

    int total_Tabs;

    public Login_Singup_Adapter(FragmentManager fm, Context context, int total_Tabs) {
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
                login_fragment tab1 = new login_fragment();

              ;

                return tab1;
            case 1:
                Login_Signup_Fragment tab2 = new Login_Signup_Fragment();
                return tab2;

            default:
                return null;


        }


    }
}
