package com.helio.cypher.loaders;

import android.content.Context;

import com.helio.cypher.activities.MySecretsActivity;
import com.helio.cypher.callbacks.DeleteDataCallback;

public class DeleteDataLoader {

    private DeleteDataCallback mCallback;
    private MySecretsActivity activity;

    public DeleteDataLoader(Context context, DeleteDataCallback callback) {
        mCallback = callback;
        activity = ((MySecretsActivity) context);
    }

    /*public void execute(List<String> ids) {
        activity.showProgress();

        getState(ids).findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(final List<ParseObject> objects, ParseException e) {
                if (e == null) {

                    if (objects.size() > 0) {

                        new AsyncTask<String, String, String>() {

                            @Override
                            protected String doInBackground(String... params) {
                                for (ParseObject item : objects) {
                                    item.put(Constants.SECRET_DELETED, true);
                                    item.saveInBackground();
                                }
                                return null;
                            }

                            @Override
                            protected void onPostExecute(String s) {
                                finishThat();
                                super.onPostExecute(s);
                            }
                        }.execute();
                    } else {
                        finishThat();
                    }
                } else {
                    finishThat();
                }
            }
        });
    }

    private ParseQuery getState(List<String> ids) {
        ArrayList<String> user = new ArrayList<String>();
        user.add(Preference.getUserName());
        ParseQuery query = ParseQuery.getQuery(Constants.SECRET_CLASS).whereContainedIn(Constants.OBJECT_ID, ids);
        return query;
    }*/

    private void finishThat() {
        mCallback.onDone(true);
    }

}
