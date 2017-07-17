package com.helio.silentsecret.loaders;

import com.helio.silentsecret.callbacks.UpdateCallback;
import com.helio.silentsecret.models.Secret;


import java.util.ArrayList;
import java.util.List;

public class MoodLoader {

    private UpdateCallback mCallback;
    private List<Secret> mResponseList;

    public MoodLoader(UpdateCallback callback) {
        mCallback = callback;
        mResponseList = new ArrayList<>();
    }


}
