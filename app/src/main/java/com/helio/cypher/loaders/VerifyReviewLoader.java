package com.helio.cypher.loaders;

import com.helio.cypher.callbacks.MineVerifyCallback;
import com.helio.cypher.callbacks.VerifyCallback;


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
