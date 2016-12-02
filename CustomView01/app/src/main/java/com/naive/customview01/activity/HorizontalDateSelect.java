package com.naive.customview01.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.naive.customview01.R;
import com.naive.customview01.view.HorizontalDateView;

/**
 * Created by zgyi on 2016/12/1.
 */

public class HorizontalDateSelect extends AppCompatActivity {

    private FrameLayout mFlContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_date);
        mFlContainer=(FrameLayout)findViewById(R.id.fl_container);
        HorizontalDateView horizontalDateView=new HorizontalDateView(this);
        View view =horizontalDateView.initView();
        mFlContainer.addView(view);
    }

}
