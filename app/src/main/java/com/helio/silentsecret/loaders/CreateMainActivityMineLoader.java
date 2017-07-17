package com.helio.silentsecret.loaders;

import android.content.Context;

import com.helio.silentsecret.activities.MainActivity;
import com.helio.silentsecret.callbacks.UpdateCallback;
import com.helio.silentsecret.models.Secret;


import java.util.ArrayList;
import java.util.List;

public class CreateMainActivityMineLoader {

    private UpdateCallback mCallback;
    private List<Secret> mResponseList;
    private MainActivity activity;



    public CreateMainActivityMineLoader(Context context, UpdateCallback callback) {
        mCallback = callback;
        mResponseList = new ArrayList<Secret>();
        activity = ((MainActivity) context);
    }


    /*public MineLoader(Context context, UpdateCallback callback)
    {
        mCallback = callback;
        createSecretActivity = ((CreateSecretActivity) context);
        mResponseList = new ArrayList<Secret>();

    }*/









}
