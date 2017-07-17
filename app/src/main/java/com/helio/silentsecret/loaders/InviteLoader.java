package com.helio.silentsecret.loaders;

import com.helio.silentsecret.callbacks.InviteCallback;
import com.helio.silentsecret.callbacks.InviteObjectCallback;

public class InviteLoader {

    private InviteCallback mCallback;
    private InviteObjectCallback mObjectCall;

    public InviteLoader(InviteCallback callback) {
        mCallback = callback;
    }

    public InviteLoader(InviteObjectCallback callback) {
        mObjectCall = callback;
    }


}
