/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package com.lean.newpopupmenu;

import android.content.Intent;
import android.graphics.drawable.Drawable;

public class MyMenuItem {

    private int itemId;
    private String title;
    private Drawable icon;
    private Intent intent;

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
    public int getItemId() {
        return itemId;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
    public Drawable getIcon() {
        return icon;
    }
    public void setIntent(Intent intent) {
        this.intent = intent;
    }
    public Intent getIntent() {
        return intent;
    }
}
