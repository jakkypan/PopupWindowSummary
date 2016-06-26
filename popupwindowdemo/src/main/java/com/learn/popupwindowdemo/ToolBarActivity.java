/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package com.learn.popupwindowdemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.Toast;

/**
 * Created by panhongchao on 16/6/18.
 */
public class ToolBarActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {
    private List<Map<String, String>> moreList;
    private PopupWindow pwMyPopWindow;
    Toolbar toolbar;
    private ListView lvPopupList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.toolbar);

        toolbar = (Toolbar) findViewById(R.id.tool);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        if (toolbar != null) {
//            toolbar.setNavigationIcon(buildBtn());
//            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    finish();
//                }
//            });

            toolbar.addView(buildBtn(), 0);
            toolbar.setOnMenuItemClickListener(this);
        }

        iniData();
        iniPopupWindow();
    }

    private View buildBtn() {
        Button button = new Button(this);
        button.setText("返回");
        button.setGravity(Gravity.CENTER);
        button.setTextColor(getResources().getColor(android.R.color.white));
        button.setBackgroundResource(R.drawable.btn_back_selector);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        return button;
    }

    private void iniData() {
        moreList = new ArrayList<>();
        Map<String, String> map;
        map = new HashMap<>();
        map.put("share_key", "复制");
        moreList.add(map);
        map = new HashMap<>();
        map.put("share_key", "删除");
        moreList.add(map);
        map = new HashMap<>();
        map.put("share_key", "修改");
        moreList.add(map);
    }

    private void iniPopupWindow() {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.task_detail_popupwindow, null);
        lvPopupList = (ListView) layout.findViewById(R.id.lv_popup_list);
        pwMyPopWindow = new PopupWindow(layout);
        pwMyPopWindow.setFocusable(true);// 加上这个popupwindow中的ListView才可以接收点击事件

        lvPopupList.setAdapter(new SimpleAdapter(ToolBarActivity.this, moreList,
                R.layout.list_item_popupwindow, new String[] {"share_key"},
                new int[] {R.id.tv_list_item}));
        lvPopupList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(ToolBarActivity.this, moreList.get(position).get("share_key"), Toast.LENGTH_LONG).show();
                pwMyPopWindow.dismiss();
            }
        });

        // 控制popupwindow的宽度和高度自适应
        lvPopupList.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        pwMyPopWindow.setWidth(lvPopupList.getMeasuredWidth());
        pwMyPopWindow.setHeight((lvPopupList.getMeasuredHeight() + 20) * 3);

        // 控制popupwindow点击屏幕其他地方消失
        // 设置背景图片，不能在布局中设置，要通过代码来设置
        pwMyPopWindow.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.bg_popupwindow));
        pwMyPopWindow.setOutsideTouchable(true);
        // 触摸popupwindow外部，popupwindow消失。这个要求你的popupwindow要有背景图片才可以成功，如上
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.more) {
            if (pwMyPopWindow.isShowing()) {
                pwMyPopWindow.dismiss();
            } else {
                pwMyPopWindow.showAsDropDown(toolbar, toolbar.getWidth(), 0);
            }

            return true;
        }
        return false;
    }
}
