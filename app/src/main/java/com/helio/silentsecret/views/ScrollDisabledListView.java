package com.helio.silentsecret.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by hp on 23-10-2017.
 */

public class ScrollDisabledListView extends ListView {

    /**
     * Flag which determines whether vertical scrolling is enabled in this {@link ListView}.
     */
    private boolean scrollEnabled = true;

    public ScrollDisabledListView(Context context) {
        super(context);
    }

    public ScrollDisabledListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollDisabledListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!scrollEnabled) {
            return false;
        } else {
            return super.onInterceptTouchEvent(ev);
        }
    }

    /**
     * Sets the value of {@link #scrollEnabled}.
     *
     * @param scrollEnabled
     */
    public void setScrollEnabled(boolean scrollEnabled) {
        this.scrollEnabled = scrollEnabled;
    }
}

