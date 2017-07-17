package com.helio.silentsecret.loaders;

import android.content.Context;

import com.helio.silentsecret.activities.MainActivity;
import com.helio.silentsecret.callbacks.UpdateCallback;
import com.helio.silentsecret.connection.ConnectionDetector;
import com.helio.silentsecret.models.Secret;

import java.util.ArrayList;
import java.util.List;

public class FeedLoader {

    private UpdateCallback mCallback;
    private List<Secret> mResponseList;
    private MainActivity activity;

    public static int myAge = 0;

    public FeedLoader(Context context, UpdateCallback callback) {
        mCallback = callback;
        mResponseList = new ArrayList<>();
        activity = ((MainActivity) context);
    }

    public void execute(int state, final boolean showProgress) {

        if (showProgress && ConnectionDetector.isNetworkAvailable(activity))
            activity.showProgress();

       /* mResponseList.clear();

        CacheHelper.makeCache(getState(state)).findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(final List<ParseObject> objects, ParseException e) {
*/

               /* if (e == null) {
                    if (objects.size() == 0)
                    {
                        mCallback.onUpdate(mResponseList);
                        if (showProgress)
                         //   activity.stopProgress();
                        return;
                    }

                    try {
                        for (int i = 0; i < objects.size(); i++) {
                            mResponseList.add(ObjectMaker.form(objects.get(i)));
                            mResponseList.get(i).setShowCoupon(i%3);
                        }
                    } catch (NullPointerException nullData) {
                        mCallback.onUpdate(mResponseList);
                        if (showProgress)
                          //  activity.stopProgress();
                    }
*/

                   /* mCallback.onUpdate(mResponseList);
                    if (showProgress)
                        activity.stopProgress();
                } else {
                    mCallback.onUpdate(mResponseList);
                    if (showProgress)
                        activity.stopProgress();
                }*/


/*
            }
        });*/
        mCallback.onUpdate(mResponseList);
    }

  /*  public void executeNext(int state, int skip) {
        mResponseList.clear();
        ParseQuery query = CacheHelper.makeCache(getState(state));
        query.setSkip(skip);
        query.findInBackground(new FindCallback<ParseObject>() {

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
                            mResponseList.get(i).setShowCoupon(i%3);
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

    private ParseQuery getState(int state) {
        int userAge = Integer.parseInt(Preference.getUserAge());
        myAge = userAge;
        ParseQuery query = null;
        switch (state) {
            case Constants.STATE_HAPPINESS:
                query = ParseQuery.getQuery(Constants.SECRET_CLASS);
                query.addDescendingOrder(Constants.SECRET_CREATED_AT)
                        .whereEqualTo(Constants.SECRET_HOME, "1")
                        .setLimit(Constants.SECRET_LIMIT)
                        .whereLessThan(Constants.SECRET_FLAGS, 3);
                break;
            case Constants.STATE_TRENDING:
                ParseQuery<ParseObject> q1 = ParseQuery.getQuery(Constants.SECRET_CLASS);
                q1.whereGreaterThan(Constants.SECRET_ME2S, MainActivity
                        .trendingData[Constants.TRENDING_ME2S]);
                q1.whereGreaterThan(Constants.SECRET_HEARTS, MainActivity
                        .trendingData[Constants.TRENDING_HEARTS]);
                ParseQuery<ParseObject> q2 = ParseQuery.getQuery(Constants.SECRET_CLASS);
                q2.whereGreaterThan(Constants.SECRET_HEARTS, MainActivity
                        .trendingData[Constants.TRENDING_HEARTS]);
                q2.whereGreaterThan(Constants.SECRET_HUGS, MainActivity
                        .trendingData[Constants.TRENDING_HUGS]);
                ParseQuery<ParseObject> q3 = ParseQuery.getQuery(Constants.SECRET_CLASS);
                q3.whereGreaterThan(Constants.SECRET_HUGS, MainActivity
                        .trendingData[Constants.TRENDING_HUGS]);
                q3.whereGreaterThan(Constants.SECRET_ME2S, MainActivity
                        .trendingData[Constants.TRENDING_ME2S]);
                ParseQuery<ParseObject> q4 = ParseQuery.getQuery(Constants.SECRET_CLASS);
                q4.whereGreaterThan(Constants.SECRET_HUGS, MainActivity
                        .trendingData[Constants.TRENDING_HUGS]);
                q4.whereGreaterThan(Constants.SECRET_ME2S, MainActivity
                        .trendingData[Constants.TRENDING_ME2S]);
                q4.whereGreaterThan(Constants.SECRET_HEARTS, MainActivity
                        .trendingData[Constants.TRENDING_HEARTS]);

                List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
                queries.add(q1);
                queries.add(q2);
                queries.add(q3);
                queries.add(q4);
                query = ParseQuery.or(queries);
                query.whereLessThan(Constants.SECRET_FLAGS, 3)
                        .setLimit(Constants.SECRET_LIMIT)
                        .addDescendingOrder(Constants.SECRET_UPDATED_AT);
                break;
            case Constants.STATE_SEE_ALL:
                ParseQuery<ParseObject> allOne = ParseQuery.getQuery(Constants.SECRET_CLASS);
                allOne.whereGreaterThanOrEqualTo(Constants.SECRET_FLAGS, 3);
                allOne.whereEqualTo(Constants.SECRET_CREATED_BY_USER, Preference.getUserName());

                ParseQuery<ParseObject> allTwo = ParseQuery.getQuery(Constants.SECRET_CLASS);
                allTwo.whereLessThan(Constants.SECRET_FLAGS, 3);

                List<ParseQuery<ParseObject>> queriesAll = new ArrayList<ParseQuery<ParseObject>>();
                queriesAll.add(allOne);
                queriesAll.add(allTwo);

                query = ParseQuery.or(queriesAll);
                query.addDescendingOrder(Constants.SECRET_CREATED_AT)
                        .setLimit(Constants.SECRET_LIMIT);
                if (userAge >= 11 && userAge <= 15) {
                    query.whereGreaterThanOrEqualTo(Constants.SECRET_AGE, "11");
                    query.whereLessThanOrEqualTo(Constants.SECRET_AGE, "15");
                } else if (userAge > 15) {
                    query.whereGreaterThanOrEqualTo(Constants.SECRET_AGE, "16");
                } else {
                    query.whereLessThan(Constants.SECRET_AGE, "11");
                }
                break;
            case Constants.STATE_TENSE:
                query = ParseQuery.getQuery(Constants.SECRET_CLASS);
                query.addDescendingOrder(Constants.SECRET_CREATED_AT)
                        .setLimit(Constants.SECRET_LIMIT)
                        .whereLessThan(Constants.SECRET_FLAGS, 3);
                query.whereEqualTo(Constants.SECRET_CATEGORY, "tense");
                if (userAge >= 11 && userAge <= 15) {
                    query.whereGreaterThanOrEqualTo(Constants.SECRET_AGE, "11");
                    query.whereLessThanOrEqualTo(Constants.SECRET_AGE, "15");
                } else if (userAge > 15) {
                    query.whereGreaterThanOrEqualTo(Constants.SECRET_AGE, "16");
                } else {
                    query.whereLessThan(Constants.SECRET_AGE, "11");
                }
                break;
            case Constants.STATE_DEEP:
                query = ParseQuery.getQuery(Constants.SECRET_CLASS);
                query.addDescendingOrder(Constants.SECRET_CREATED_AT)
                        .setLimit(Constants.SECRET_LIMIT)
                        .whereLessThan(Constants.SECRET_FLAGS, 3);
                query.whereEqualTo(Constants.SECRET_CATEGORY, "deep");
                if (userAge >= 11 && userAge <= 15) {
                    query.whereGreaterThanOrEqualTo(Constants.SECRET_AGE, "11");
                    query.whereLessThanOrEqualTo(Constants.SECRET_AGE, "15");
                } else if (userAge > 15) {
                    query.whereGreaterThanOrEqualTo(Constants.SECRET_AGE, "16");
                } else {
                    query.whereLessThan(Constants.SECRET_AGE, "11");
                }
                break;
            case Constants.STATE_LIGHT:
                query = ParseQuery.getQuery(Constants.SECRET_CLASS);
                query.addDescendingOrder(Constants.SECRET_CREATED_AT)
                        .setLimit(Constants.SECRET_LIMIT)
                        .whereLessThan(Constants.SECRET_FLAGS, 3);
                query.whereEqualTo(Constants.SECRET_CATEGORY, "light");
                if (userAge >= 11 && userAge <= 15) {
                    query.whereGreaterThanOrEqualTo(Constants.SECRET_AGE, "11");
                    query.whereLessThanOrEqualTo(Constants.SECRET_AGE, "15");
                } else if (userAge > 15) {
                    query.whereGreaterThanOrEqualTo(Constants.SECRET_AGE, "16");
                } else {
                    query.whereLessThan(Constants.SECRET_AGE, "11");
                }
                break;
        }

        query.include(Constants.INCLUDE_USER_POINTER);

        return query;
    }

    public static ParseQuery getMe2LatestFeed() {
        ParseQuery<ParseObject> me2Query = ParseQuery.getQuery(Constants.ME2_CLASS);
        me2Query.addDescendingOrder(Constants.SECRET_CREATED_AT);
        me2Query.whereEqualTo(Constants.RECEIVING_SOCIAL, ParseUser.getCurrentUser());
        me2Query.include(Constants.INCLUDE_USER_POINTER);
        return CacheHelper.makeCache(me2Query);
    }

    public static ParseQuery getMe2TrendingFeed() {
        ParseQuery<ParseObject> me2Query = ParseQuery.getQuery(Constants.ME2_CLASS);
        me2Query.addDescendingOrder(Constants.SECRET_CREATED_AT);
        me2Query.whereEqualTo(Constants.USER_SOCIAL, ParseUser.getCurrentUser());
        me2Query.include(Constants.INCLUDE_RUSER_POINTER);
        return CacheHelper.makeCache(me2Query);
    }

    public static ParseQuery getMe2UsersQuery(List<String> users) {
        ParseQuery<ParseObject> me2Query = ParseQuery.getQuery(Constants.SECRET_CLASS);

        if (ParseUser.getCurrentUser().get(Constants.USER_SECRET_VIEWED) != null)
            me2Query.whereNotContainedIn(Constants.OBJECT_ID,
                    (List<String>) ParseUser.getCurrentUser().get(Constants.USER_SECRET_VIEWED));

        me2Query.addDescendingOrder(Constants.SECRET_CREATED_AT);
        me2Query.whereLessThan(Constants.SECRET_FLAGS, 3);
        me2Query.whereNotEqualTo(Constants.SECRET_CREATED_BY_USER, Preference.getUserName());
        me2Query.whereContainedIn(Constants.SECRET_CREATED_BY_USER, users);
        me2Query.include(Constants.INCLUDE_USER_POINTER);
        return CacheHelper.makeCache(me2Query);
    }

    public static ParseQuery getFeedQuery(String type) {
        ParseQuery<ParseObject> feed = ParseQuery.getQuery(Constants.GLIMPSE);
        feed.addDescendingOrder(Constants.SECRET_UPDATED_AT);
        feed.whereEqualTo(Constants.GLIMPSE_TYPE, type.toLowerCase());
        return CacheHelper.makeCache(feed);
    }

    public static ParseQuery getCommentsOfYear(Date date, List<ParseObject> list) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(Constants.COMMENTS_CLASS);
        query.addDescendingOrder(Constants.SECRET_UPDATED_AT);
        query.whereNotEqualTo(Constants.COMMENTS_FLAGGED, true);
        query.whereGreaterThan(Constants.SECRET_CREATED_AT, date);
        query.whereContainedIn(Constants.COMMENTS_SECRET, list);
        return CacheHelper.makeCache(query);
    }*/

}
