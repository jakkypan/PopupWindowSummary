/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package com.lean.newpopupmenu;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * Created by panhongchao on 16/6/17.
 */
public class MyPopupMenu {
    private Context mContext;
    private WindowManager mWindowManager;
    private List<MyMenuItem> mItems;

    private PopupWindow mPopupWindow;
    private View mContentView;
    private ListView mItemsView;
    private TextView mHeaderTitleView;

    public MyPopupMenu(Context context) {
        this.mContext = context;
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getMetrics(metrics);
        mItems = new ArrayList<>();

        mContentView = LayoutInflater.from(context).inflate(R.layout.popup_menu, null);
        mItemsView = (ListView) mContentView.findViewById(R.id.items);
        mHeaderTitleView = (TextView) mContentView.findViewById(R.id.header_title);
        mPopupWindow = new PopupWindow(context);
        mPopupWindow.setContentView(mContentView);

        mPopupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        mPopupWindow.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.panel_background));
    }

    public void show(View view) {
        MenuItemAdapter adapter = new MenuItemAdapter(mContext, mItems);
        mItemsView.setAdapter(adapter);
        mItemsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPopupWindow.dismiss();
            }
        });

        if (view == null) {
            View parent = ((Activity)mContext).getWindow().getDecorView();
            mPopupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
            return;
        }

        // 根据点击位置显示
        int xPos, yPos;
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        Rect viewRect = new Rect(location[0], location[1],
                location[0] + view.getWidth(), location[0] + view.getHeight());

        xPos = (viewRect.centerX() - mPopupWindow.getWidth()) / 2;

        int rootHeight = mContentView.getMeasuredHeight();
        int screenHeight = mWindowManager.getDefaultDisplay().getHeight();
        int dyTop = viewRect.top;
        int dyBottom = screenHeight + rootHeight;
        boolean onTop = dyTop > dyBottom;

        if (onTop) {
            yPos = viewRect.top - rootHeight;
        } else {
            if (viewRect.bottom > dyTop) {
                yPos = viewRect.bottom - 20;
            } else {
                yPos = viewRect.top - viewRect.bottom + 50;
            }
        }
        mPopupWindow.showAtLocation(view, Gravity.NO_GRAVITY, xPos / 2, yPos);
    }

    public MyMenuItem add(int itemId, int titleRes) {
        MyMenuItem item = new MyMenuItem();
        item.setItemId(itemId);
        item.setTitle(mContext.getString(titleRes));
        mItems.add(item);

        return item;
    }

    public void setHeaderTitle(CharSequence title) {
        mHeaderTitleView.setText(title);
        mHeaderTitleView.setVisibility(View.VISIBLE);
        mHeaderTitleView.requestFocus();
    }

    // adapter
    private class MenuItemAdapter extends ArrayAdapter<MyMenuItem> {

        public MenuItemAdapter(Context context, List<MyMenuItem> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_list_item, null);
                holder = new ViewHolder();
                holder.icon = (ImageView) convertView.findViewById(R.id.icon);
                holder.title = (TextView) convertView.findViewById(R.id.title);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            MyMenuItem item = getItem(position);
            if (item.getIcon() != null) {
                holder.icon.setImageDrawable(item.getIcon());
                holder.icon.setVisibility(View.VISIBLE);
            } else {
                holder.icon.setVisibility(View.GONE);
            }
            holder.title.setText(item.getTitle());

            return convertView;
        }
    }

    static class ViewHolder {
        ImageView icon;
        TextView title;
    }
}
