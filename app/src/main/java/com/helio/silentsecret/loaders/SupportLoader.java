package com.helio.silentsecret.loaders;

import com.helio.silentsecret.callbacks.SupportCallback;
import com.helio.silentsecret.callbacks.SupportFreeCallback;

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
