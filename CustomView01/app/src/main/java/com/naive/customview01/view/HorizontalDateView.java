package com.naive.customview01.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

import com.naive.customview01.R;
import com.naive.customview01.adapter.HZDateSelectAdapter;
import com.naive.customview01.model.HZDate;
import com.naive.customview01.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by zgyi on 2016/12/1.
 */

public class HorizontalDateView extends BaseView implements AdapterView.OnItemClickListener {

    private RecyclerView mRcDateSelect;
    private HZDateSelectAdapter mHzDateSelectAdaper;
    private int mSelectedDateIndex = 6;
    private boolean mListerFlag = false;

    public HorizontalDateView(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.view_horizontal_date, null);
        mRcDateSelect = (RecyclerView) view.findViewById(R.id.rc_date_select);
        initData("2016-12-01");
        return view;
    }

    /**
     * 根据传递的日期按照【过去6天，未来7天】计算数据
     *
     * @param date 默认选中的当天日期
     */
    private void initData(final String date) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<String> dateStrList = DateUtils.getDateList(date, -6);
                dateStrList.add(date);
                dateStrList.addAll(DateUtils.getDateList(date, 7));
                ArrayList<HZDate> allDateList = new ArrayList<>();
                for (String dateStr : dateStrList) {
                    HZDate hzDate = new HZDate();
                    Date iDate = DateUtils.stringToDate(dateStr, "yyyy-MM-dd");
                    hzDate.date = iDate.getDate() + "";
                    hzDate.Week = DateUtils.getWeekOfDate(dateStr);
                    hzDate.lunar = iDate.getDate() + "";
                    hzDate.dateStr = dateStr;
                    allDateList.add(hzDate);
                }
                Message message = new Message();
                message.what = 0;
                message.obj = allDateList;
                handler.sendMessage(message);
            }
        }).start();

    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    ArrayList<HZDate> hzDates = (ArrayList<HZDate>) msg.obj;
                    mHzDateSelectAdaper = new HZDateSelectAdapter(mContext, hzDates, mSelectedDateIndex);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
                    linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    mRcDateSelect.setLayoutManager(linearLayoutManager);
                    mRcDateSelect.setAdapter(mHzDateSelectAdaper);
                    mHzDateSelectAdaper.setOnItemClickListener(HorizontalDateView.this);
                    //监听listview数据刷新完成
                    mRcDateSelect.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                        @Override
                        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int
                                oldTop, int oldRight, int oldBottom) {
                            if (mListerFlag == false) {
                                //自动滚动到默认日期
                                int itemWidth = mRcDateSelect.getLayoutManager().findViewByPosition(0).getWidth();
                                int srollX = (mHzDateSelectAdaper.getItemCount() / 2 - 1) * itemWidth;
                                int recylerViewWidth = mRcDateSelect.getWidth();
                                mRcDateSelect.scrollBy((srollX - recylerViewWidth / 2) + itemWidth / 2, 0);
                                mListerFlag = true;
                            }
                        }
                    });
                    break;
            }
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mSelectedDateIndex=position;
        Snackbar.make(view,"position:"+position,Snackbar.LENGTH_SHORT).show();
    }
}
