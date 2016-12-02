package com.naive.customview01.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.naive.customview01.R;
import com.naive.customview01.adapter.MenuAdaper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MenuAdaper.OnItemClick {

    Context ctx;
    Toolbar mToolbar;
    CollapsingToolbarLayout mCasToolLayout;
    FloatingActionButton mFloatActionButton;
    RecyclerView mRecyclerView;
    MenuAdaper mMenuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.NoActionBar);
        super.onCreate(savedInstanceState);
        SystemClock.sleep(2000);
        setContentView(R.layout.activity_main);
        ctx = this;
        mToolbar = (Toolbar) findViewById(R.id.toobar);
        mCasToolLayout = (CollapsingToolbarLayout) findViewById(R.id.cas_toolbar);
        mFloatActionButton = (FloatingActionButton) findViewById(R.id.bt_float_action);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mToolbar.setTitle("Custom View");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.mipmap.exit);
        mToolbar.getNavigationIcon();
        onNavigationClick();
        mFloatActionButton.setOnClickListener(this);
        initMenuData();
        mMenuAdapter.setOnItemClickLister(this);
    }

    /**
     * 初始化字符串数据
     */
    private void initMenuData() {
        ArrayList<String> menuData = new ArrayList<>();
        menuData.add("横向日期选择");
        mMenuAdapter = new MenuAdaper(ctx, menuData);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ctx);
        // 设置布局管理器 不能缺少
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mMenuAdapter);
    }

    private void onNavigationClick() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar.make(v, "NavigationButton", Snackbar.LENGTH_LONG);
                snackbar.setAction("EXIT", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
                snackbar.setActionTextColor(Color.RED);
                snackbar.show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == mFloatActionButton) {
            Snackbar snackbar = Snackbar.make(v, "Float Action button", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
    }

    @Override
    public void onClick(View view, int position) {
        if (position == 0) {
            Intent intent = new Intent(this, HorizontalDateSelect.class);
            startActivity(intent);
        }
    }
}
