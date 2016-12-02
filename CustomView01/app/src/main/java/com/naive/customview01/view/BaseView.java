package com.naive.customview01.view;

import android.content.Context;
import android.view.View;

/**
 * Created by zgyi on 2016/12/1.
 */

public abstract  class BaseView {

    protected Context mContext;

    public BaseView(Context ctx){
        this.mContext=ctx;
    }

    public abstract View initView();

}
