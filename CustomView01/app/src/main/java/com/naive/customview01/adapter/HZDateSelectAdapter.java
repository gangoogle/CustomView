package com.naive.customview01.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.naive.customview01.R;
import com.naive.customview01.model.HZDate;
import com.naive.customview01.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by zgyi on 2016/12/1.
 */

public class HZDateSelectAdapter extends RecyclerView.Adapter<HZDateSelectAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<HZDate> mData;
    private int mSelectedDateIndex;
    private int mDefaultDayIndex;
    private AdapterView.OnItemClickListener onItemClickListener;

    public HZDateSelectAdapter(Context context, ArrayList<HZDate> data, int defaultDayIndex) {
        this.mContext = context;
        this.mData = data;
        this.mSelectedDateIndex = defaultDayIndex;
        this.mDefaultDayIndex = defaultDayIndex;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_hz_date, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HZDate hzDate = mData.get(position);
        holder.mTvlunar.setText(hzDate.lunar);
        if (position == mSelectedDateIndex) {
            Date date = DateUtils.stringToDate(hzDate.dateStr, "yyyy-MM-dd");
            holder.mTvDay.setText(date.getMonth() + 1 + "/" + date.getDate());
            holder.mTvWeek.setTextColor(mContext.getResources().getColor(R.color.blue));
            holder.mTvlunar.setTextColor(mContext.getResources().getColor(R.color.red));
            holder.mTvDay.setTextColor(mContext.getResources().getColor(R.color.blue));
            holder.mFlBottomLine.setVisibility(View.VISIBLE);
        } else {
            holder.mTvDay.setText(hzDate.day);
            holder.mTvWeek.setTextColor(mContext.getResources().getColor(R.color.lightGray));
            holder.mTvlunar.setTextColor(mContext.getResources().getColor(R.color.lightGray));
            holder.mTvDay.setTextColor(mContext.getResources().getColor(R.color.drakGray));
            holder.mFlBottomLine.setVisibility(View.INVISIBLE);
        }
        if (position == mDefaultDayIndex) {
            holder.mTvWeek.setText("Today");
        } else {
            holder.mTvWeek.setText(hzDate.Week);
        }

    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout mLlContainer;
        private TextView mTvWeek;
        private TextView mTvDay;
        private TextView mTvlunar;
        private FrameLayout mFlBottomLine;

        public ViewHolder(View itemView) {
            super(itemView);
            mLlContainer = (LinearLayout) itemView.findViewById(R.id.ll_date_container);
            mTvWeek = (TextView) itemView.findViewById(R.id.tv_week);
            mTvDay = (TextView) itemView.findViewById(R.id.tv_day);
            mTvlunar = (TextView) itemView.findViewById(R.id.tv_lunar);
            mFlBottomLine = (FrameLayout) itemView.findViewById(R.id.fl_bottom_line);
            //设置点击事件
            mLlContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mSelectedDateIndex == getPosition()) {
                        return;
                    }
                    mSelectedDateIndex = getPosition();
                    notifyDataSetChanged();
                    onItemClickListener.onItemClick(null, v, getPosition(), 0);
                }
            });
        }
    }

}
