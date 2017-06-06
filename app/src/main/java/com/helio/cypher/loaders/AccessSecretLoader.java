package com.helio.cypher.loaders;

import com.helio.cypher.callbacks.AccessSecretCallback;

public class AccessSecretLoader {

    private AccessSecretCallback mCallback;

    public AccessSecretLoader(AccessSecretCallback callback) {
        mCallback = callback;
    }

    /*public void execute(String secretId) {
        ParseQuery query = ParseQuery.getQuery(Constants.SECRET_CLASS).whereEqualTo(Constants.OBJECT_ID, secretId);
        query.include(Constants.INCLUDE_USER_POINTER);

        CacheHelper.makeCache(query).getFirstInBackground(new GetCallback() {
            @Override
            public void done(ParseObject object, ParseException e) {

            }

            @Override
            public void done(Object o, Throwable throwable) {
                if (o == null) {
                    mCallback.onReceive(null);
                } else {
                    mCallback.onReceive(ObjectMaker.form((ParseObject) o));
                }
            }
        });
    }*/
}
