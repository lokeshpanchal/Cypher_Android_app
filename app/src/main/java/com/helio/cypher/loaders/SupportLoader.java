package com.helio.cypher.loaders;

import com.helio.cypher.callbacks.SupportCallback;
import com.helio.cypher.callbacks.SupportFreeCallback;

public class SupportLoader {

    private SupportCallback mCallback;
    private SupportFreeCallback mFreeCallback;

    public SupportLoader(SupportCallback callback) {
        mCallback = callback;
    }

    public SupportLoader(SupportFreeCallback callback) {
        mFreeCallback = callback;
    }


}
