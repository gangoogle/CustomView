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
import com.naive.customview01.utils.Lunar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zgyi on 2016/12/1.
 */

public class HzDateView extends BaseView implements AdapterView.OnItemClickListener {

    private RecyclerView mRcDateSelect;
    private HZDateSelectAdapter mHzDateSelectAdaper;
    private int mSelectedDateIndex = 0;
    private boolean mListerFlag = false;
    private OnDateSwitchListener onDateSwitchListener;
    private static final int INITDATEUI = 0;
    private ArrayList<HZDate> mHzDates = null;
    private int mDefaultIndex = 0;

    public HzDateView(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.view_hz_date, null);
        mRcDateSelect = (RecyclerView) view.findViewById(R.id.rc_date_select);
        return view;
    }

    /**
     * 根据传递的日期按照计算数据
     *
     * @param date 默认选中的当天日期
     */
    public void initData(final String date, final int yesterdayNumber, final int tomorrowNumber) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!DateUtils.isValidDate(date, "yyyy-MM-dd")) {
                    try {
                        throw new Exception("日期不合法必须为yyyy-MM-dd");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;
                }
                ArrayList<String> dateStrList = DateUtils.getDateList(date, -Math.abs(yesterdayNumber));
                dateStrList.add(date);
                dateStrList.addAll(DateUtils.getDateList(date, tomorrowNumber));
                ArrayList<HZDate> allDateList = new ArrayList<>();
                for (String dateStr : dateStrList) {
                    HZDate hzDate = new HZDate();
                    Date iDate = DateUtils.stringToDate(dateStr, "yyyy-MM-dd");
                    //day 日期字符串
                    hzDate.day = iDate.getDate() + "";
                    hzDate.dateStr = dateStr;
                    //周
                    hzDate.Week = DateUtils.getWeekOfDate(dateStr);
                    //设置农历
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(iDate);
                    hzDate.lunarObj = new Lunar(calendar);
                    hzDate.lunar = Lunar.getChinaDayString(hzDate.lunarObj.day);
                    //添加数据
                    allDateList.add(hzDate);
                }
                //设置默认选中的下标及观察日期下标
                mDefaultIndex = Math.abs(yesterdayNumber);
                mSelectedDateIndex = Math.abs(yesterdayNumber);
                Message message = new Message();
                message.what = INITDATEUI;
                message.obj = allDateList;
                handler.sendMessage(message);
            }
        }).start();

    }

    private void initDateUI(ArrayList<HZDate> hzDates) {
        if (hzDates != null && hzDates.size() > 0) {
            mHzDates = hzDates;
        }
        mHzDateSelectAdaper = new HZDateSelectAdapter(mContext, mHzDates, mDefaultIndex);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        //设置RecyclerView为横向滚动
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRcDateSelect.setLayoutManager(linearLayoutManager);
        mRcDateSelect.setAdapter(mHzDateSelectAdaper);
        mHzDateSelectAdaper.setOnItemClickListener(HzDateView.this);
        //监听listview数据刷新完成
        mRcDateSelect.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int
                    oldTop, int oldRight, int oldBottom) {
                if (mListerFlag == false) {
                    //自动滚动到默认日期
                    int itemWidth = mRcDateSelect.getLayoutManager().findViewByPosition(0).getWidth();
                    //计算默认滚动居中的距离
                    int srollX = (mHzDateSelectAdaper.getItemCount() / 2 - 1) * itemWidth;
                    //recyclerView的宽度
                    int recylerViewWidth = mRcDateSelect.getWidth();
                    mRcDateSelect.scrollBy((srollX - recylerViewWidth / 2) + itemWidth / 2, 0);
                    mListerFlag = true;
                }
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case INITDATEUI:
                    initDateUI((ArrayList<HZDate>) msg.obj);
                    break;
            }
        }
    };

    /**
     * recycler adapter onItemClick
     *
     * @param parent   null
     * @param view     linearlayout
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mSelectedDateIndex = position;
        Snackbar.make(view, "position:" + position, Snackbar.LENGTH_SHORT).show();
        if (onDateSwitchListener != null) {
            onDateSwitchListener.onDateSwitch(mHzDates.get(position));
        }
    }

    /**
     * 日期切换监听
     */
    public interface OnDateSwitchListener {
        void onDateSwitch(HZDate hzDate);
    }

    /**
     * 设置日期切换监听器
     *
     * @param onDateSwitchListener
     */
    public void setOnDateSwitchListener(OnDateSwitchListener onDateSwitchListener) {
        this.onDateSwitchListener = onDateSwitchListener;
    }
}
