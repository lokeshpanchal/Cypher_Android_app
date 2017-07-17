package com.helio.silentsecret.loaders;

import com.helio.silentsecret.callbacks.MineVerifyCallback;
import com.helio.silentsecret.callbacks.VerifyCallback;


public class VerifyReviewLoader {

    private VerifyCallback mCallback;
    private MineVerifyCallback mineCallback;

    public VerifyReviewLoader(VerifyCallback callback) {
        mCallback = callback;
    }

    public VerifyReviewLoader(MineVerifyCallback callback) {
        mineCallback = callback;
    }



}
