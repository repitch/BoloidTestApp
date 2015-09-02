package com.repitch.boloidtestapp.activities;

import android.os.Bundle;

import com.repitch.boloidtestapp.ui.fragments.MainFragment;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        launchFragmentNoBackStack(
                MainFragment.newInstance(),
                MainFragment.TAG
        );
    }

}
