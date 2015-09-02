package com.repitch.boloidtestapp.ui.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.repitch.boloidtestapp.R;

import com.repitch.boloidtestapp.ui.activities.BaseActivity;


public abstract class BaseFragment extends Fragment {

    private static String TAG;

    protected Activity mActivity;
    protected boolean mShowBackBtn;

    public abstract String getFragmentTitle();

    public static String getFragmentTag() {
        return TAG;
    }

    @Override
    public void onAttach(Activity activity) {
        Log.e("STATES", "onAttach (frag) by " + this.getClass());
        super.onAttach(activity);
        mActivity = activity;
        mShowBackBtn = true;
        TAG = getClass().getSimpleName();
    }

    @Override
    public void onDetach() {
        Log.e("STATES", "onDetach (frag) by " + this.getClass());
        super.onDetach();
        mActivity = null;
    }

    @Override
    public void onResume() {
        Log.e("STATES", "onResume (frag) by " + this.getClass());
        super.onResume();
        try {
            if (((BaseActivity) mActivity).getSupportActionBar() != null) {
                if (mShowBackBtn) {
                    ((BaseActivity) mActivity).shouldDisplayHomeUp();
                } else {
                    ((BaseActivity) mActivity).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                }
            }
        } catch (ClassCastException e) {
            Log.e("ClassCastException", e.toString());
        }

        setTitle(null);
        ((BaseActivity) mActivity).getSupportActionBar().setSubtitle(null);
    }

    protected void setTitle(String title) {
        if (((BaseActivity) mActivity).getSupportActionBar() != null) {
            if (title!=null) {
                ((BaseActivity) mActivity).getSupportActionBar().setTitle(title);
            } else {
                if (getFragmentTitle() == null) {
                    ((BaseActivity) mActivity).getSupportActionBar().setTitle(getString(R.string.app_name));
                } else {
                    ((BaseActivity) mActivity).getSupportActionBar().setTitle(getFragmentTitle());
                }
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        Log.e("STATES", "onDestroyView (frag) by " + this.getClass());
        super.onDestroyView();
    }

    protected void hideKeyboard() {
        View view = mActivity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
