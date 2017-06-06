package com.helio.cypher.loaders;

import android.content.Context;

import com.helio.cypher.activities.MainActivity;
import com.helio.cypher.callbacks.SearchUpdateCallback;
import com.helio.cypher.models.Secret;


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
