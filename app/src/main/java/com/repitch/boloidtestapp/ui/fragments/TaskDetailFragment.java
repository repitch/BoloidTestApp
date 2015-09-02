package com.repitch.boloidtestapp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.repitch.boloidtestapp.R;
import com.repitch.boloidtestapp.models.Price;
import com.repitch.boloidtestapp.ui.activities.BaseActivity;
import com.repitch.boloidtestapp.models.Task;
import com.repitch.boloidtestapp.utils.Consts;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
    private ViewGroup mLlPricesWrap;
    private TextView mTxtDate;
    private TextView mTxtLocationDesc;

    private Task mTask;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_task_detail, container, false);

        mTask = (Task) getArguments().getSerializable(TASK_KEY);


        // init UI
        mTxtText = (TextView) rootView.findViewById(R.id.txtText);
        mTxtLongText = (TextView) rootView.findViewById(R.id.txtLongText);
        mLlPricesWrap = (ViewGroup) rootView.findViewById(R.id.llPricesWrap);
        mTxtDate = (TextView) rootView.findViewById(R.id.txtDate);
        mTxtLocationDesc = (TextView) rootView.findViewById(R.id.txtLocationDesc);


        mTxtText.setText(mTask.getText());
        mTxtLongText.setText(mTask.getLongText());
        // добавим цены
        if (mTask.getPrices().size()>0){
            // убираю placeholder
            mLlPricesWrap.removeAllViews();
        }
        for (Price price: mTask.getPrices()){
            ViewGroup priceView = (ViewGroup) LayoutInflater.from(mActivity).inflate(R.layout.item_price, mLlPricesWrap, false);
            TextView txtPrice = (TextView) priceView.findViewById(R.id.txtPrice);
            TextView txtDesc = (TextView) priceView.findViewById(R.id.txtDesc);

            txtPrice.setText(""+price.getPrice());
            txtDesc.setText(""+price.getDescription());

            mLlPricesWrap.addView(priceView);
        }
        // установим дату
        mTxtDate.setText(mTask.getDateStr());

        // установим описание положения
        mTxtLocationDesc.setText(mTask.getLocationText());

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
