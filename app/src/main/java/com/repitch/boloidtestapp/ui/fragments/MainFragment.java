package com.repitch.boloidtestapp.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.repitch.boloidtestapp.R;
import com.repitch.boloidtestapp.activities.BaseActivity;

/**
 * Created by repitch on 01.09.15.
 */
public class MainFragment extends BaseFragment {

    public static final String TAG = "MainFragment";
    Button btnShowMap;
    @Override
    public String getFragmentTitle() {
        return null;
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        btnShowMap = (Button) rootView.findViewById(R.id.btnShowMap);
        btnShowMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("MainFragment", "onClick");
                ((BaseActivity) mActivity).launchFragment(
                        MapFragment.newInstance(),
                        MapFragment.getFragmentTag()
                );
                Log.v("MainFragment", "onClick2");
            }
        });
        return rootView;
    }

}
