package com.helio.silentsecret.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class OwnPager extends ViewPager {

    private boolean isPagingEnabled = true;

    public OwnPager(Context context) {
        super(context);
    }

    public OwnPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        try {
            return this.isPagingEnabled && super.onTouchEvent(event);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        try {
            return this.isPagingEnabled && super.onInterceptTouchEvent(event);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
      return true;
    }


    public void setPagingEnabled(boolean b) {
        this.isPagingEnabled = b;
    }
}