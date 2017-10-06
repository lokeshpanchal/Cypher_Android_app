package com.helio.silentsecret.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by hp on 15-09-2017.
 */
public class MyTextView extends TextView {

    Context context;
    String ttfName;

    String TAG = getClass().getName();

    public MyTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;

        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            init();
        }

    }

    private void init() {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/ubuntu.ttf");
        setTypeface(font);
    }

    @Override
    public void setTypeface(Typeface tf) {

        // TODO Auto-generated method stub
        super.setTypeface(tf);
    }

}