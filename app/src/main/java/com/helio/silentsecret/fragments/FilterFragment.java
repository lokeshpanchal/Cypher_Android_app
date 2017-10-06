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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.helio.silentsecret.R;
import com.helio.silentsecret.WebserviceDTO.BlankConditionDTO;
import com.helio.silentsecret.WebserviceDTO.FindDTO;
import com.helio.silentsecret.WebserviceDTO.FindObjectDTO;
import com.helio.silentsecret.activities.MainActivity;
import com.helio.silentsecret.adapters.FeedAdapter;
import com.helio.silentsecret.callbacks.FilterUpdateCallback;
import com.helio.silentsecret.connection.ConnectionDetector;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.models.Me2Object;
import com.helio.silentsecret.models.OfflineSecretObjectDTO;
import com.helio.silentsecret.models.Secret;
import com.helio.silentsecret.utils.AppSession;
import com.helio.silentsecret.utils.Constants;
import com.helio.silentsecret.utils.Preference;
import com.helio.silentsecret.utils.TimeUtil;
import com.nirhart.parallaxscroll.views.ParallaxListView;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class FilterFragment extends Fragment implements FilterUpdateCallback
{

    private View mView;
    private ParallaxListView mListView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FeedAdapter adapter;
    private List<Secret> mDataList;
    private int state = Constants.STATE_SEE_ALL;
    private int SKIP = 0;
    private int preLast;

    String _method = "";

    private List<Me2Object> me2Items = new ArrayList<>();
    private List<String> me2Users = new ArrayList<>();
    private List<Secret> me2Secrets = new ArrayList<>();

    private Date mStartOver;
    private String mCommentsTitle = "";
    private String mSecretsTitle = "";

    private LinearLayout mainContainer;

    private List<Secret> list = null;

    private Date mSecretsDate;
    private Date mCurrentMonth;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStartOver = TimeUtil.checkDateRange();
        SKIP = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_paralax_feed, null);

        mListView = (ParallaxListView) mView.findViewById(R.id.feed_list_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!ConnectionDetector.checkInternetConnection(getActivity())) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    return;
                }

                SKIP = 0;
                new FindSecret().execute();
                //((MainActivity) getActivity()).loadFeed(state, false);
            }
        });

        if (mDataList != null)
            mDataList = null;

        mDataList = new ArrayList<>();

        adapter = new FeedAdapter(LayoutInflater.from(getActivity()), mDataList, getString(R.string.latest_overlay_text), getString(R.string.latest_last_item_text), getActivity());
        ((MainActivity) getActivity()).setupFilterCallback(this);
        setupAdapter();
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                try {
                    final int lastItem = firstVisibleItem + visibleItemCount;
                    if (mDataList != null && mDataList.size() > 0) {
                        if (lastItem == totalItemCount) {
                            if (preLast != lastItem) {
                                preLast = lastItem;

                                if (ConnectionDetector.isNetworkAvailable(getActivity())) {
                                    SKIP = SKIP + 1;
                                    new FindSecret().execute();
                                }


                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        mainContainer = new LinearLayout(getActivity());
        mainContainer.setOrientation(LinearLayout.VERTICAL);

        if (mStartOver != null) {
            mCommentsTitle = String.format(getString(R.string.comments_received), TimeUtil.getYear());

            View commentsBanner = inflater.inflate(R.layout.comments_banner, null, false);
            ((TextView) commentsBanner.findViewById(R.id.comments_banner_text))
                    .setText(mCommentsTitle);
            commentsBanner.findViewById(R.id.comments_banner_icon)
                    .setBackgroundResource(R.drawable.blue_circle);
            commentsBanner.setOnClickListener(mCommentsBannerClick);
            mainContainer.addView(commentsBanner);
        }

        //checkUserSecretsCount(inflater);
        SKIP = 0;
        MainActivity.is_Refresh_Latest = false;

        if (ConnectionDetector.isNetworkAvailable(getActivity()))
            new FindSecret().execute();
        else
            new FindOfflineSecret().execute();

        return mView;
    }

/*    private void checkUserSecretsCount(final LayoutInflater inflater)
    {
        MineLoader.getSecretsCount().countInBackground(new CountCallback()
        {
            @Override
            public void done(int count, ParseException e) {
                try {


                    if (e == null) {
                        if (count >= 3) {
                            if (mStartOver != null) {
                                mSecretsTitle = String.format(getString(R.string.secrets_highlights), TimeUtil.getYear());
                                mSecretsDate = mStartOver;
                            } else {
                                // Un till 26 DEC
                                if (Calendar.getInstance().get(Calendar.YEAR) == 2015) {
                                    mSecretsDate = null;
                                } else {
                                    mSecretsTitle = String.format(
                                            getString(R.string.secrets_highlights_text), TimeUtil.getPreviousMonthName());
                                    mCurrentMonth = TimeUtil.getCurrentMonthDate();
                                    mSecretsDate = TimeUtil.getPreviousMonthDate();
                                }
                            }

                            if (mSecretsDate != null) {
                                View secretsBanner = inflater.inflate(R.layout.comments_banner, null, false);
                                ((TextView) secretsBanner.findViewById(R.id.comments_banner_text))
                                        .setText(mSecretsTitle);
                                secretsBanner.findViewById(R.id.comments_banner_icon)
                                        .setBackgroundResource(R.drawable.pages_circle);
                                secretsBanner.setOnClickListener(mSecretsBannerClick);
                                mainContainer.addView(secretsBanner);
                            }
                        }

                        if (mainContainer.getChildCount() > 0) {
                            mListView.addParallaxedHeaderView(mainContainer);
                        }

                        setupAdapter();
                    } else {
                        setupAdapter();

                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }

        });
    }*/

    private void setupAdapter() {
        try {
            mListView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    View.OnClickListener mCommentsBannerClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ((MainActivity) getActivity()).replaceDialog(Constants.ACCESS_YEAR_COMMENTS);
        }
    };

    View.OnClickListener mSecretsBannerClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ((MainActivity) getActivity()).replaceDialog(Constants.ACCESS_HIGHTLIGHTS);
        }
    };

    public void startComments() {
        try {
           /* Intent intent = new Intent(getActivity(), BannerResultActivity.class);
            intent.putExtra(BannerResultActivity.START_OVER_DATE, mStartOver);
            intent.putExtra(BannerResultActivity.COMMENTS_MODE_KEY,
                    BannerResultActivity.COMMENTS_MODE);
            intent.putExtra(BannerResultActivity.TITLE, mCommentsTitle);
            startActivity(intent);*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void startHighLights() {

        try {
            /*Intent intent = new Intent(getActivity(), BannerResultActivity.class);
            intent.putExtra(BannerResultActivity.START_OVER_DATE, mSecretsDate);
            intent.putExtra(BannerResultActivity.SECRETS_MODE_KEY,
                    BannerResultActivity.SECRETS_MODE);
            if (mCurrentMonth != null)
                intent.putExtra(BannerResultActivity.END_OVER_DATE, mCurrentMonth);
            intent.putExtra(BannerResultActivity.TITLE, mSecretsTitle);
            startActivity(intent);*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onUpdate() {

        SKIP = 0;

        if (ConnectionDetector.isNetworkAvailable(getActivity()))
            new FindSecret().execute();
        else
            new FindOfflineSecret().execute();
    }

    @Override
    public void onHighLightAccess() {

    }

    @Override
    public void onCommentsAccess() {

    }


    private class FindSecret extends AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String status = "";
        Bitmap bitmap;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (SKIP != 0 ||  MainActivity.is_Refresh_Latest )
                ((MainActivity) getActivity()).showProgress();
        }

        protected Bitmap doInBackground(String... args) {
            try {

                if (list != null) {
                    list.clear();
                    list = null;
                }

                IfriendRequest http = new IfriendRequest(getActivity());


                int state = Preference.getFilter();

                if (state == Constants.STATE_SEE_ALL) {
                    _method = Constants.ENCRYPT_LF_See_All;
                } else if (state == Constants.STATE_LIGHT) {
                    _method = Constants.ENCRYPT_LF_Light;
                } else if (state == Constants.STATE_DEEP) {
                    _method = Constants.ENCRYPT_LF_Deep;
                } else if (state == Constants.STATE_TENSE) {
                    _method = Constants.ENCRYPT_LF_Tense;
                }
                String age = AppSession.getValue(getActivity(), Constants.USER_AGE);
                int intage = Integer.parseInt(age);
                if(intage <11)
                {
                    age = "11";
                }
                BlankConditionDTO blankConditionDTO = new BlankConditionDTO(age, "" + SKIP);
                blankConditionDTO.setClun01(MainActivity.enc_username);
                FindDTO loginDTO = new FindDTO(_method, blankConditionDTO);
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
                MainActivity.is_Refresh_Latest = false;

                if (list != null && list.size() > 0) {

                    if (SKIP == 0) {
                        mDataList.clear();
                    }

                    mDataList.addAll(list);


                    if (mDataList != null && mDataList.size() > 0) {

                        try {
                            OfflineSecretObjectDTO offlineSecretObjectDTO = new OfflineSecretObjectDTO(mDataList);
                            String jsonData = new Gson().toJson(offlineSecretObjectDTO);

                            AppSession.save(getActivity(), Constants.OFFLINE_LATEST_SECRET, jsonData);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        adapter.notifyDataSetChanged();

                        mListView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        }, 1000);
                        mSwipeRefreshLayout.setRefreshing(false);


                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try
            {
                mSwipeRefreshLayout.setRefreshing(false);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();

        if (MainActivity.is_Refresh_Latest) {
            SKIP = 0;

            new FindSecret().execute();
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

                String offline_string = AppSession.getValue(getActivity(), Constants.OFFLINE_LATEST_SECRET);
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

                    if (mDataList != null && mDataList.size() > 0) {
                        adapter.notifyDataSetChanged();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }

                //((MainActivity) getActivity()).stopProgress();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


/*    @Override
    public void onHighLightAccess() {
        startHighLights();
    }

    @Override
    public void onCommentsAccess() {
        startComments();
    }*/

/*    private void loadAndUpdateMe2() {
        FeedLoader.getMe2LatestFeed().findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(final List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() == 0) {
                        return;
                    }

                    try {
                        for (int i = 0; i < objects.size(); i++) {
                            me2Items.add(ObjectMaker.formMe2(objects.get(i)));
                            me2Users.add(me2Items.get(i).getUser().getString(Constants.USER_NAME));
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
        });
    }*/

 /*   private void updateView(List<Secret> list) {
        try {
            if (isAdded())
                adapter.updateAttachment(list);
            else
                adapter.notifyAnyway();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/
}
