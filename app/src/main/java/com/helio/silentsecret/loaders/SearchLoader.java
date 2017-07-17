package com.helio.silentsecret.loaders;

import android.content.Context;

import com.helio.silentsecret.activities.MainActivity;
import com.helio.silentsecret.callbacks.SearchUpdateCallback;
import com.helio.silentsecret.models.Secret;


import java.util.ArrayList;
import java.util.List;

public class SearchLoader {

    private SearchUpdateCallback mCallback;
    private List<Secret> mResponseList;
    private MainActivity activity;

    public SearchLoader(Context context, SearchUpdateCallback callback) {
        mCallback = callback;
        mResponseList = new ArrayList<Secret>();
        activity = ((MainActivity) context);
    }













}
