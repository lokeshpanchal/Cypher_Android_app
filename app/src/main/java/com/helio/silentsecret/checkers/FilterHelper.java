package com.helio.silentsecret.checkers;

import android.os.AsyncTask;

import com.helio.silentsecret.callbacks.UpdateCallback;
import com.helio.silentsecret.models.Secret;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FilterHelper {

    List<String> data;

    UpdateCallback mUpdate;

    public FilterHelper(List<String> data, final UpdateCallback update) {
        this.data = data;
        this.mUpdate = update;
    }

    public void filterAndSort() {
        if (data == null || data.size() == 0)
            mUpdate.onUpdate(new ArrayList<Secret>());

        new AsyncTask<Void, List<String>, List<String>>() {
            @Override
            protected List<String> doInBackground(Void... params) {
                List<String> users = new ArrayList<>();
                //remove duplicates if exist and clear not verified
                for (String item : data) {
                    if (!users.contains(item)) {
                        users.add(item);
                    }
                }

                return users;
            }

            @Override
            protected void onPostExecute(List<String> users) {
                super.onPostExecute(users);
               // makeQueryForUsers(users, mUpdate);
            }
        }.execute();

    }

/*
    private void makeQueryForUsers(final List<String> users, final UpdateCallback update) {

        final List<Secret> temp = new ArrayList<>();

        FeedLoader.getMe2UsersQuery(users).findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() == 0)
                        update.onUpdate(new ArrayList<Secret>());
                    try {
                        for (ParseObject item : objects) {
                            temp.add(ObjectMaker.form(item));
                        }
                        finalFilter(temp, users);
                    } catch (NullPointerException nullData) {

                    }
                } else {
                    update.onUpdate(new ArrayList<Secret>());
                }
            }
        });

    }
*/

    private void finalFilter(List<Secret> temp, List<String> users) {

        HashMap<String, String> keys = new HashMap<>();
        List<Secret> result = new ArrayList<>();

        while (result.size() != temp.size()) {
            for (String user : users) {
                for (Secret item : temp) {
                    if (item.getCreatedByUser().equals(user) && !keys.containsKey(item.getObjectId())) {
                        result.add(item);
                        keys.put(item.getObjectId(), item.getObjectId());
                        break;
                    }
                }
            }
        }
        mUpdate.onUpdate(result); //final result
    }
}
