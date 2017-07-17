package com.helio.silentsecret.Services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.helio.silentsecret.connection.ConnectionDetector;


import io.fabric.sdk.android.services.concurrency.AsyncTask;

/**
 * Created by ABC on 9/12/2016.
 */
public class RefreshFriendList extends Service {
;


    boolean is_refreshcout = false;
    TextView refreshcount = null;

     Context context = null;

    @Override
    public void onCreate() {
        super.onCreate();
        refreshcount = new TextView(this);
        context = this;
        refreshcount.postDelayed(Refrescount, 10000);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


/*
    public void callWS() {

        try {
            if (!is_refreshcout)
            {
                is_refreshcout = true;

                ParseQuery myfriend1 = new ParseQuery(MainActivity.friend_table_name);//DONE
                myfriend1.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
                myfriend1.whereEqualTo("status", "approved");
                myfriend1.setLimit(1000);
                myfriend1.addAscendingOrder("createdAt");

                myfriend1.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> userList, ParseException e) {

                        if (e == null) {
                            if (userList != null && userList.size() > 0) {

                                if (IFriendsFragment.iFriendList.size() > 0) {
                                    IFriendsFragment.iFriendList.clear();
                                }

                                for (int i = 0; i < userList.size(); i++) {
                                    ParseObject currentFriendObject = userList.get(i);
                                    IFriendsFragment.currentuser = currentFriendObject.getString("username");
                                    IFriendsFragment.requestuser = currentFriendObject.getString("friend");
                                    IFriendsFragment.requestId = currentFriendObject.getString("requestid");
                                    int profilepic = currentFriendObject.getInt("profilepic");


                                    final IFriendsBean bean = new IFriendsBean();
                                    bean.setUsername(IFriendsFragment.currentuser);
                                    bean.setFriend(IFriendsFragment.requestuser);
                                    bean.setProfilepic(profilepic);

                                    bean.setMe(true);
                                    bean.setiFriendName("iFriend ");
                                    bean.setDummyName(IFriendsFragment.requestuser);
                                    bean.setUnreadcount(getUnreadCount(IFriendsFragment.requestuser));
                                    IFriendsFragment.iFriendList.add(bean);

                                }

                                is_refreshcout = false;

                            } else {
                                is_refreshcout = false;

                                if (IFriendsFragment.Emptyview != null)
                                    IFriendsFragment.iFriends_listview.setEmptyView(IFriendsFragment.Emptyview);
                            }


                        } else {

                            System.out.println("Something wrong ----> " + e.getMessage());
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/


    Runnable Refrescount = new Runnable() {
        @Override
        public void run()
        {
            try
            {
                refreshcount.removeCallbacks(Refrescount);
                refreshcount.postDelayed(Refrescount, 10000);
                if (ConnectionDetector.isNetworkAvailable(context))
                    new AsyncCaller().execute();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


        }
    };

    private class AsyncCaller extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            //callWS();

            return null;
        }

    }

/*    public static int getUnreadCount(String freindnaem) {

        int chatcount = 0;
        ParseQuery chatUnreadCount = new ParseQuery("Chat");
        chatUnreadCount.whereEqualTo("sender", freindnaem);
        chatUnreadCount.whereEqualTo("receiver", ParseUser.getCurrentUser().getUsername());
        chatUnreadCount.whereEqualTo("read", "false");

        try {
            chatcount = chatUnreadCount.count();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return chatcount;
    }*/
}
