package com.helio.silentsecret.loaders;

import android.content.Context;

import com.helio.silentsecret.activities.CreateSecretActivity;
import com.helio.silentsecret.activities.MySecretsActivity;
import com.helio.silentsecret.callbacks.UpdateCallback;
import com.helio.silentsecret.models.Secret;


import java.util.ArrayList;
import java.util.List;

public class MineLoader {

    private UpdateCallback mCallback;
    private List<Secret> mResponseList;
    private MySecretsActivity activity;

    private CreateSecretActivity createSecretActivity;

    public MineLoader(Context context, UpdateCallback callback) {
        mCallback = callback;
        mResponseList = new ArrayList<Secret>();
        activity = ((MySecretsActivity) context);
    }


    /*public MineLoader(Context context, UpdateCallback callback)
    {
        mCallback = callback;
        createSecretActivity = ((CreateSecretActivity) context);
        mResponseList = new ArrayList<Secret>();

    }*/


    public void execute(int state, final boolean showProgress) {

      /*  if (showProgress && ConnectionDetector.checkInternetConnection(activity))
            activity.showProgress();

     */
        mResponseList.clear();
        mCallback.onUpdate(mResponseList);

        /*CacheHelper.makeCache(getState(state)).findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(final List<ParseObject> objects, ParseException e) {

                if (e == null) {

                    if (objects.size() == 0) {
                        mCallback.onUpdate(mResponseList);
                        if (showProgress)
                            activity.stopProgress();
                        return;
                    }

                    try {
                        for (int i = 0; i < objects.size(); i++) {
                            mResponseList.add(ObjectMaker.form(objects.get(i)));
                        }
                    } catch (NullPointerException nullData) {
                        mCallback.onUpdate(mResponseList);
                        if (showProgress)
                            activity.stopProgress();
                    }

                    mCallback.onUpdate(mResponseList);
                    if (showProgress)
                        activity.stopProgress();
                } else {
                    mCallback.onUpdate(mResponseList);
                    if (showProgress)
                        activity.stopProgress();
                }
            }
        });
*/
    }

/*

    public void executeNext(int state, int skip) {
        mResponseList.clear();

        CacheHelper.makeCache(getState(state)).setSkip(skip).findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(final List<ParseObject> objects, ParseException e) {



                if (e == null) {

                    if (objects.size() == 0) {
                        mCallback.onUpdate(mResponseList);
                        return;
                    }

                    try {
                        for (int i = 0; i < objects.size(); i++) {
                            mResponseList.add(ObjectMaker.form(objects.get(i)));
                        }
                    } catch (NullPointerException nullData) {
                        mCallback.onUpdate(mResponseList);
                    }

                    mCallback.onUpdate(mResponseList);
                } else {
                    mCallback.onUpdate(mResponseList);
                }
            }
        });

    }

*/

}
