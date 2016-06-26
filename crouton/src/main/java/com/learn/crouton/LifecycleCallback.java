/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package com.learn.crouton;

/**
 * Created by panhongchao on 16/6/25.
 */
public interface LifecycleCallback {
    /** Will be called when your Crouton has been displayed. */
    void onDisplayed();

    /** Will be called when your {@link Crouton} has been removed. */
    void onRemoved();
}