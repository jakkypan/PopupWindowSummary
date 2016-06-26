/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package com.lean.newpopupmenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * Created by panhongchao on 16/6/17.
 */
public class TestActivity extends Activity implements ListView.OnItemClickListener {
    private final static int PLAY_SELECTION = 0;
    private final static int ADD_TO_PLAYLIST = 1;
    private final static int SEARCH = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] array = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n" };
        setContentView(R.layout.tt);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, array));
        listView.setOnItemClickListener(this);
    }

    private View getContentView() {
        ViewGroup view = (ViewGroup) getWindow().getDecorView();
        LinearLayout content = (LinearLayout) view.getChildAt(0);
        return content.getChildAt(0);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MyPopupMenu menu = new MyPopupMenu(this);
        menu.setHeaderTitle("TitleTitleTitleTitleTitleTitle" + position);
//        menu.setOnItemSelectedListener(this);
        menu.add(PLAY_SELECTION, R.string.play).setIcon(getResources().getDrawable(R.drawable.ic_context_menu_play_normal));
        menu.add(ADD_TO_PLAYLIST, R.string.add_to_playlist).setIcon(getResources().getDrawable(R.drawable.ic_context_menu_add_to_playlist_normal));
        menu.add(SEARCH, R.string.search).setIcon(getResources().getDrawable(R.drawable.ic_context_menu_search_normal));
        menu.show(view);
    }
}
