package com.naive.customview01.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.naive.customview01.R;
import com.naive.customview01.view.HzDateView;

/**
 * Created by zgyi on 2016/12/1.
 */

public class HzDateSelectActivity extends AppCompatActivity {

    private FrameLayout mFlContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hz_date);
        mFlContainer=(FrameLayout)findViewById(R.id.fl_container);
        HzDateView horizontalDateView=new HzDateView(this);
        View view =horizontalDateView.initView();
        mFlContainer.addView(view);
        horizontalDateView.initData("2016-12-02",6,7);
    }

}
