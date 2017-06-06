package com.helio.cypher.views;

import android.content.Context;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.widget.EditText;

import com.helio.cypher.activities.CreateSecretActivity;

public class LimitEditText extends EditText {

    private Context mContext;

    public LimitEditText(Context context) {
        this(context, null);
    }

    public LimitEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LimitEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    public boolean onPreDraw() {
        return super.onPreDraw();
    }

    @Override
    protected void onTextChanged(CharSequence text, int start,
                                 int lengthBefore, int lengthAfter) {

        try {

            if (mContext != null)
                ((CreateSecretActivity) mContext).updateCounter(text.length());
            InputFilter[] filters = new InputFilter[1];
            filters[0] = new InputFilter.LengthFilter(200);
            setFilters(filters);
        } catch (IndexOutOfBoundsException e) {

        }

        super.onTextChanged(text, start, lengthBefore, lengthAfter);
    }
}