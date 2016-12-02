package com.naive.customview01.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.naive.customview01.R;

import java.util.ArrayList;

/**
 * Created by zgyi on 2016/11/30.
 */

public class MenuAdaper extends RecyclerView.Adapter<MenuAdaper.ViewHolder> {

    private Context ctx;
    private ArrayList<String> mMenuData;

    public MenuAdaper(Context context, ArrayList<String> aMenuData) {
        super();
        this.ctx = context;
        this.mMenuData = aMenuData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_list_menu, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mTvMenu.setText(mMenuData.get(position));
        holder.mTvMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClick != null) {
                    onItemClick.onClick(holder.mTvMenu,position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        Log.d("yzg", mMenuData.size() + "");
        return mMenuData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvMenu;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvMenu = (TextView) itemView.findViewById(R.id.tv_menu);
        }
    }

    public interface OnItemClick {
        void onClick(View view,int position);
    }

    private OnItemClick onItemClick;

    public void setOnItemClickLister(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }
}
