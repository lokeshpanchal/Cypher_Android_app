package com.helio.silentsecret.fragments;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;
import com.helio.silentsecret.R;
import com.helio.silentsecret.WebserviceDTO.BlankConditionDTO;
import com.helio.silentsecret.WebserviceDTO.FindDTO;
import com.helio.silentsecret.WebserviceDTO.FindObjectDTO;
import com.helio.silentsecret.activities.MainActivity;
import com.helio.silentsecret.adapters.FeedAdapter;
import com.helio.silentsecret.application.SilentSecretApplication;
import com.helio.silentsecret.callbacks.UpdateCallback;
import com.helio.silentsecret.connection.ConnectionDetector;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.models.OfflineSecretObjectDTO;
import com.helio.silentsecret.models.Secret;
import com.helio.silentsecret.utils.AppSession;
import com.helio.silentsecret.utils.Constants;
import com.helio.silentsecret.views.ScrollDisabledListView;

import java.util.ArrayList;
import java.util.List;

public class HappyFragment extends Fragment implements UpdateCallback {

    private View mView;
    private ScrollDisabledListView mListView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FeedAdapter adapter;
    private List<Secret> mDataList;

    private List<Secret> list = null;



    boolean is_scroll_done = false;

    private int SKIP = 0;
    public int preLast;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_happy_paralax_feed, null);

        mListView = (ScrollDisabledListView) mView.findViewById(R.id.feed_list_view);

        mListView.setScrollEnabled(true);


       /* mListView.post(new Runnable() {
            @Override
            public void run() {
                new CountDownTimer(totalScrollTime, scrollPeriod )
                {
                    public void onTick(long millisUntilFinished)
                    {
                        int scroll = mListView.getScrollY();
                        if(scroll == 0)
                        {
                            mListView.scrollBy(0, heightToScroll);

                        }
                    }

                    public void onFinish() {
                        //you can add code for restarting timer here
                    }
                }.start();
            }
        });*/

        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!ConnectionDetector.checkInternetConnection(getActivity())) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    return;
                } else {

                    //   mSwipeRefreshLayout.setRefreshing(false);
                    //((MainActivity) getActivity()).loadFeed(Constants.STATE_HAPPINESS, false);


                    SKIP = 0;
                    new FindSecret().execute();
                }
            }
        });


        mDataList = new ArrayList<>();
        adapter = new FeedAdapter(LayoutInflater.from(getActivity()), mDataList, getActivity(), true , mListView);
        //  ((MainActivity) getActivity()).setupHappyCallback(this);

       /* mView.findViewById(R.id.shareemotion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.postEmotion();
            }
        });
*/

        mListView.setAdapter(adapter);




        if (ConnectionDetector.isNetworkAvailable(getActivity()))
            new FindSecret().execute();
        else
            new FindOfflineSecret().execute();








        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if (mDataList != null && mDataList.size() > 0) {
                    if (lastItem == totalItemCount) {
                        if (preLast != lastItem) {
                            preLast = lastItem;
                            if (ConnectionDetector.isNetworkAvailable(getActivity()))
                            {
                                SKIP = SKIP + 1;
                                new FindSecret().execute();
                            }
                            /*new FeedLoader(getActivity(), new UpdateCallback() {
                                @Override
                                public void onUpdate(List<Secret> list) {
                                    try {

                                        if (list != null && list.size() > 0) {

                                            mDataList.addAll(list);
                                            adapter.notifyDataSetChanged();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            }).executeNext(Constants.STATE_HAPPINESS, SKIP);*/
                        }
                    }
                }
            }
        });

        return mView;
    }

    @Override
    public void onUpdate(List<Secret> list) {
        // new FindSecret().execute();
        /*try {
            SKIP = 0;
            if (mDataList != null && mDataList.size() > 0)
                mDataList.clear();
            if (list != null && list.size() > 0)
            {

                mDataList.addAll(list);

                if (mDataList != null && mDataList.size() > 0)

                {
                    adapter.notifyDataSetChanged();

                    mSwipeRefreshLayout.setRefreshing(false);

                    mListView.setSelection(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/

    }


    protected void startTracking(String path) {
        Tracker t = ((SilentSecretApplication) MainActivity.ct.getApplicationContext()).getTracker(
                SilentSecretApplication.TrackerName.APP_TRACKER);
        t.setScreenName(path);
        t.send(new HitBuilders.AppViewBuilder().build());
    }


    private class FindSecret extends AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String status = "";
        Bitmap bitmap;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (SKIP != 0)
                ((MainActivity) getActivity()).showProgress();
        }

        protected Bitmap doInBackground(String... args) {
            try {

                if (list != null) {
                    list.clear();
                    list = null;
                }

                IfriendRequest http = new IfriendRequest(getActivity());


                BlankConditionDTO blankConditionDTO = new BlankConditionDTO("", "" + SKIP);
                FindDTO loginDTO = new FindDTO(Constants.ENCRYPT_HAPPY_METHOD, blankConditionDTO);
                FindObjectDTO loginbjectDTO = new FindObjectDTO(loginDTO);

                list = http.FindHappySecret(loginbjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {

                ((MainActivity) getActivity()).stopProgress();

                if (list != null && list.size() > 0) {

                    if (SKIP == 0)
                        mDataList.clear();

                    mDataList.addAll(list);

                    if (mDataList != null && mDataList.size() > 0)
                    {
                        try
                        {
                            OfflineSecretObjectDTO offlineSecretObjectDTO = new OfflineSecretObjectDTO(mDataList);
                            String jsonData = new Gson().toJson(offlineSecretObjectDTO);
                            AppSession.save(getActivity(), Constants.OFFLINE_HAPPY_SECRET, jsonData);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }


                        adapter.notifyDataSetChanged();

                        mSwipeRefreshLayout.setRefreshing(false);

                        //   mListView.setSelection(0);
                    }
                }

                //((MainActivity) getActivity()).stopProgress();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    private class FindOfflineSecret extends AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String status = "";
        Bitmap bitmap;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //  ((MainActivity) getActivity()).showProgress();
            // showProgress();
        }

        protected Bitmap doInBackground(String... args) {
            try {

                if (list != null) {
                    list.clear();
                    list = null;
                }

                String offline_string = AppSession.getValue(getActivity(), Constants.OFFLINE_HAPPY_SECRET);
                if (offline_string != null) {

                    IfriendRequest http = new IfriendRequest(getActivity());
                    list = http.ParseOfflinesecret(offline_string);

                }




            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {

                ((MainActivity) getActivity()).stopProgress();

                if (list != null && list.size() > 0) {

                    if (SKIP == 0)
                        mDataList.clear();

                    mDataList.addAll(list);

                    if (mDataList != null && mDataList.size() > 0)
                    {
                       /* OfflineSecretObjectDTO offlineSecretObjectDTO = new OfflineSecretObjectDTO(mDataList);
                        String jsonData = new Gson().toJson(offlineSecretObjectDTO);

                        AppSession.save(getActivity(), Constants.OFFLINE_HAPPY_SECRET, jsonData);
*/
                        adapter.notifyDataSetChanged();

                        mSwipeRefreshLayout.setRefreshing(false);

                        //   mListView.setSelection(0);
                    }
                }

                //((MainActivity) getActivity()).stopProgress();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
