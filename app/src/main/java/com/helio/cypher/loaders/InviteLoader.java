package com.helio.cypher.loaders;

import com.helio.cypher.callbacks.InviteCallback;
import com.helio.cypher.callbacks.InviteObjectCallback;

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
