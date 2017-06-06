package com.helio.cypher.loaders;

import com.helio.cypher.callbacks.UpdateCallback;
import com.helio.cypher.models.Secret;


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
