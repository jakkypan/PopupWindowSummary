/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package com.learn.actionlikepopup;

import android.content.Context;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.view.ViewGroup.LayoutParams;

/**
 * Created by panhongchao on 16/6/19.
 */
public class PopupHelper {
    private PopupWindow window;
    private Context context;

    public PopupHelper(Context context) {
        this.context = context;
        window = new PopupWindow(context);
        window.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        window.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        window.setTouchable(true);
        window.setFocusable(true);
        window.setOutsideTouchable(true);

        window.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    window.dismiss();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 带动画效果的展现
     */
    public void amimationShow(View rootView, View anchorView) {
        window.setContentView(rootView);

        int[] location = new int[2];
        anchorView.getLocationOnScreen(location);
        Rect anchorRect =
                new Rect(location[0], location[1], location[0] + anchorView.getWidth(), location[1]
                        + anchorView.getHeight());

        rootView.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        int rootWidth = rootView.getMeasuredWidth();
        int rootHeight = rootView.getMeasuredHeight();

        int screenWidth = Util.getScreenWidth(context);
        int screenHeight = Util.getScreenHeight(context);

        int xPos = 0;
        int yPos = 0;

        // view底部的坐标都比popupview要小，那么不能在view的上面展示，必须在底部展示
        if (anchorRect.bottom < rootHeight) {
            if (anchorRect.right < screenWidth / 2) {
                window.setAnimationStyle(R.style.Animations_PopDownMenuLeft);
            } else {
                window.setAnimationStyle(R.style.Animations_PopDownMenuRight);
            }
            window.showAsDropDown(anchorView);
            return;
        }

        // 屏幕的高度减去view底部坐标都比popupview要小，那么不能在view的底部展示，必须在上面展示
        if ((screenHeight - anchorRect.bottom) < rootHeight) {
            if (anchorRect.right < rootWidth) {
                window.setAnimationStyle(R.style.Animations_GrowFromBottom);
                xPos = 0;
            } else {
                window.setAnimationStyle(R.style.Animations_GrowFromBottomRight);
                xPos = screenWidth;
            }
            yPos = anchorRect.top - rootHeight;
            window.showAtLocation(anchorView, Gravity.NO_GRAVITY, xPos, yPos);
            return;
        }

        xPos = (screenWidth - rootWidth) / 2;
        yPos = anchorRect.top - rootHeight;
        window.setAnimationStyle(R.style.Animations_GrowFromBottom);
        window.showAtLocation(anchorView, Gravity.NO_GRAVITY, xPos, yPos);
    }
}
