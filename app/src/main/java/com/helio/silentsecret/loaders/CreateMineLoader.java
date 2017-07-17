package com.helio.silentsecret.loaders;

import android.content.Context;

import com.helio.silentsecret.activities.CreateSecretActivity;
import com.helio.silentsecret.callbacks.UpdateCallback;
import com.helio.silentsecret.models.Secret;


import java.util.ArrayList;
import java.util.List;

public class CreateMineLoader {

    private UpdateCallback mCallback;
    private List<Secret> mResponseList;
    private CreateSecretActivity activity;


    public CreateMineLoader(Context context, UpdateCallback callback) {
        mCallback = callback;
        mResponseList = new ArrayList<Secret>();
        activity = ((CreateSecretActivity) context);
    }


    /*public MineLoader(Context context, UpdateCallback callback)
    {
        mCallback = callback;
        createSecretActivity = ((CreateSecretActivity) context);
        mResponseList = new ArrayList<Secret>();

    }*/


    /*private ParseQuery getState(int state) {
        ArrayList<String> user = new ArrayList<String>();
        user.add(Preference.getUserName());
        ParseQuery query = null;
        switch (state) {
            case Constants.STATE_MINE_SECRETS:
                query = ParseQuery.getQuery(Constants.SECRET_CLASS);
                query.whereNotEqualTo(Constants.SECRET_DELETED, true);
                query.addDescendingOrder(Constants.SECRET_CREATED_AT).setLimit(Constants.SECRET_LIMIT).whereEqualTo(Constants.SECRET_CREATED_BY_USER, Preference.getUserName());
                break;
        }

        query.include(Constants.INCLUDE_USER_POINTER);

        return query;
    }*/



    /*public void mysecretexecute(int state)
    {


        CacheHelper.makeCache(getState(state)).findInBackground(new FindCallback<ParseObject>()
        {

            @Override
            public void done(final List<ParseObject> objects, ParseException e) {

                if (e == null)
                {

                    try {
                        for (int i = 0; i < objects.size(); i++)
                        {
                            mResponseList.add(ObjectMaker.form(objects.get(i)));
                        }
                    } catch (Exception e1)
                    {


                    }

                    mCallback.onUpdate(mResponseList);

                }
            }
        });

    }*/




}
