package com.repitch.boloidtestapp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.repitch.boloidtestapp.R;
import com.repitch.boloidtestapp.activities.BaseActivity;
import com.repitch.boloidtestapp.models.Task;

/**
 * Created by repitch on 02.09.15.
 */
public class TaskDetailFragment extends BaseFragment{

    public static final String TASK_KEY = "task";

    @Override
    public String getFragmentTitle() {
        return "Task detail";
    }

    public static TaskDetailFragment newInstance(Task task) {
        TaskDetailFragment fragment = new TaskDetailFragment();

        Bundle args = new Bundle();
        args.putSerializable(TASK_KEY, task);
        fragment.setArguments(args);

        return fragment;
    }

    private TextView mTxtText;
    private TextView mTxtLongText;

    private Task mTask;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_task_detail, container, false);

        mTask = (Task) getArguments().getSerializable(TASK_KEY);


        // init UI
        mTxtText = (TextView) rootView.findViewById(R.id.txtText);
        mTxtLongText = (TextView) rootView.findViewById(R.id.txtLongText);

        mTxtText.setText(mTask.getText());
        mTxtLongText.setText(mTask.getLongText());

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        // set title
        setTitle(mTask.getTitle());
        ((BaseActivity) mActivity).getSupportActionBar().setSubtitle(getFragmentTitle());
    }
}
