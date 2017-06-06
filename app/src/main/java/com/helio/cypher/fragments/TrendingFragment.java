package com.helio.cypher.fragments;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.helio.cypher.R;
import com.helio.cypher.WebserviceDTO.BlankConditionDTO;
import com.helio.cypher.WebserviceDTO.FindDTO;
import com.helio.cypher.WebserviceDTO.FindObjectDTO;
import com.helio.cypher.activities.MainActivity;
import com.helio.cypher.adapters.FeedAdapter;
import com.helio.cypher.callbacks.UpdateCallback;
import com.helio.cypher.connection.ConnectionDetector;
import com.helio.cypher.connection.IfriendRequest;
import com.helio.cypher.models.Me2Object;
import com.helio.cypher.models.OfflineSecretObjectDTO;
import com.helio.cypher.models.Secret;
import com.helio.cypher.utils.AppSession;
import com.helio.cypher.utils.Constants;


import java.util.ArrayList;
import java.util.List;

public class TrendingFragment extends Fragment implements UpdateCallback {

    private View mView;
    private ListView mListView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FeedAdapter adapter;
    private List<Secret> mDataList;
    private int SKIP = 0;
    private int preLast;

    private List<Secret> list = null;

    private List<Me2Object> me2Items = new ArrayList<>();
    private List<Secret> me2Secrets = new ArrayList<>();
    private List<String> me2Users = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_feed, null);

        mListView = (ListView) mView.findViewById(R.id.feed_list_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!ConnectionDetector.checkInternetConnection(getActivity())) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    return;
                }
                SKIP =0;
                new FindSecret().execute();
             //   ((MainActivity) getActivity()).loadFeed(Constants.STATE_TRENDING, false);
            }
        });


        mDataList = new ArrayList<>();
        adapter = new FeedAdapter(LayoutInflater.from(getActivity()), mDataList, getString(R.string.trending_overlay_text), getString(R.string.trending_last_item_text), getActivity());
       // ((MainActivity) getActivity()).setupTrendingCallback(this);

        mListView.setAdapter(adapter);

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount) {
                    if (mDataList != null && mDataList.size()>0 )
                    {
                        if (lastItem == totalItemCount) {
                            if (preLast != lastItem) {
                                preLast = lastItem;

                                SKIP++;
                                new FindSecret().execute();
                            }
                        }
                    }
                }
            }
        });

        SKIP =0;
        if (ConnectionDetector.isNetworkAvailable(getActivity()))
            new FindSecret().execute();
        else
            new FindOfflineSecret().execute();

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onUpdate(List<Secret> list) {

       /* try {
            SKIP = 0;
            if (mDataList != null && mDataList.size() > 0)
                mDataList.clear();
            if (list != null && list.size() > 0) {


                for (int i = 0; i < list.size(); i++) {

                    int secretAge = Integer.parseInt(list.get(i).getAge());

                    if (FeedLoader.myAge != 0 && FeedLoader.myAge < 17) {
                        if (FeedLoader.myAge == (secretAge + 1) || (FeedLoader.myAge >= (secretAge - 2) && FeedLoader.myAge - 1 <= (secretAge))) {
                            mDataList.add(list.get(i));
                        }
                    } else if (FeedLoader.myAge != 0 && FeedLoader.myAge >= 17) {
                        if (FeedLoader.myAge >= (secretAge - 2) && secretAge > 14) {

                            mDataList.add(list.get(i));

                        }
                    }


                }

                if (mDataList != null && mDataList.size() > 0) {
                    adapter.notifyDataSetChanged();
                    mSwipeRefreshLayout.setRefreshing(false);

                    mListView.setSelection(0);
                }
                loadAndUpdateMe2();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }




    private class FindSecret extends AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String status = "";
        Bitmap bitmap;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // showProgress();
        }

        protected Bitmap doInBackground(String... args) {
            try {

                if(list!= null) {
                    list.clear();
                    list = null;
                }

                IfriendRequest http = new IfriendRequest(getActivity());


                String age = AppSession.getValue(getActivity(),Constants.USER_AGE);
                BlankConditionDTO blankConditionDTO = new BlankConditionDTO(age,""+SKIP);
                FindDTO loginDTO = new FindDTO(Constants.ENCRYPT_TRENDING,blankConditionDTO);

                FindObjectDTO loginbjectDTO = new FindObjectDTO(loginDTO);

                list =  http.FindHappySecret(loginbjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image)
        {
            try {


                if ( SKIP == 0)
                    mDataList.clear();

                if (list != null && list.size() > 0)
                {

                    mDataList.addAll(list);

                    if (mDataList != null && mDataList.size() > 0)
                    {
                        try
                        {
                            OfflineSecretObjectDTO offlineSecretObjectDTO = new OfflineSecretObjectDTO(mDataList);
                            String jsonData = new Gson().toJson(offlineSecretObjectDTO);

                            AppSession.save(getActivity(), Constants.OFFLINE_TRENDING_SECRET, jsonData);

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

                String offline_string = AppSession.getValue(getActivity(), Constants.OFFLINE_TRENDING_SECRET);
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

                        AppSession.save(getActivity(), Constants.OFFLINE_TRENDING_SECRET, jsonData);*/

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



    private void loadAndUpdateMe2() {
        /*FeedLoader.getMe2TrendingFeed().findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(final List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() == 0)
                        return;

                    try {

                        for (int i = 0; i < objects.size(); i++) {
                            me2Items.add(ObjectMaker.formMe2(objects.get(i)));
                            me2Users.add(me2Items.get(i).getReceiver().getString(Constants.USER_NAME));
                        }

                        new FilterHelper(me2Users, new UpdateCallback() {
                            @Override
                            public void onUpdate(List<Secret> list) {
                                me2Secrets.clear();
                                me2Secrets.addAll(list);
                                updateView(me2Secrets);
                            }
                        }).filterAndSort();


                    } catch (NullPointerException nullData) {

                    }
                }
            }
        });*/
    }

    private void updateView(List<Secret> list) {
        if (isAdded())
            adapter.updateAttachment(list);
        else
            adapter.notifyAnyway();
    }
}
