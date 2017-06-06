package com.helio.cypher.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.helio.cypher.R;
import com.helio.cypher.activities.ChatDetailsScreen;
import com.helio.cypher.activities.MainActivity;
import com.helio.cypher.creator.ObjectMaker;
import com.helio.cypher.utils.Constants;
import com.helio.cypher.utils.ImageLoaderUtil;
import com.helio.cypher.utils.Utils;

public class FeedViewFragment extends Fragment {

    private static final String FEED_TYPE_KEY = "type_key";
    private static final String FEED_TYPE_TITLE = "type_title";
    private static final String FEED_TYPE_URL = "type_url";
    private static final String FEED_TYPE_SOURCE = "type_source";
    private static final String FEED_TYPE_FILTER_ARRAY = "type_array";
    private static final String FEED_TYPE_FILTER_LIST_STATE = "type_list_state";
    private static final String FEED_TYPE_THUMB = "type_thumb";
    private View mView;
    private TextView mTitle;

    private ImageView mBackView;
    private ImageLoaderUtil mLoader;
    private WebView mWebView;

    boolean loadingFinished = true;
    boolean redirect = false;

    public static boolean shareLink  =false;



    public static FeedViewFragment instance(String type, boolean[] filter, int list, String url, String source, String title, String thumb) {
        FeedViewFragment fragment = new FeedViewFragment();
        Bundle data = new Bundle();
        data.putString(FEED_TYPE_KEY, type);
        data.putString(FEED_TYPE_URL, url);
        data.putString(FEED_TYPE_TITLE, title);
        data.putBooleanArray(FEED_TYPE_FILTER_ARRAY, filter);
        data.putInt(FEED_TYPE_FILTER_LIST_STATE, list);
        data.putString(FEED_TYPE_SOURCE, source);
        data.putString(FEED_TYPE_THUMB, thumb);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.feed_view_fragment, null);

        mView.setOnClickListener(null);

        mTitle = (TextView) mView.findViewById(R.id.feed_view_title);
        mTitle.setText(getArguments().getString(FEED_TYPE_TITLE));
        mView.findViewById(R.id.feed_view_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction().remove(FeedViewFragment.this).commit();
            }
        });

        mView.findViewById(R.id.feed_view_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (shareLink) {

                    ChatDetailsScreen.mUrl =  getArguments().getString(FEED_TYPE_URL);
                    Intent mChat = new Intent(getActivity(),ChatDetailsScreen.class);
                    mChat.putExtra("NAME", ChatDetailsScreen.NAME);
                    startActivity(mChat);


                    shareLink = false;


                    /*Intent send With Data*/
                } else {
                    Utils.shareData(getActivity(), getArguments().getString(FEED_TYPE_URL));
                }

            }
        });

        mLoader = new ImageLoaderUtil(getActivity());
        mBackView = (ImageView) mView.findViewById(R.id.feed_view_item_bg);

        if (getArguments().getString(FEED_TYPE_THUMB).equals(ObjectMaker.EMPTY)) {
            switch (getArguments().getString(FEED_TYPE_SOURCE)) {
                case Constants.YOUTUBE:
                    mLoader.loadDrawable(R.drawable.ic_youtube_bg, mBackView);
                    break;
                case Constants.VINE:
                    mLoader.loadDrawable(R.drawable.ic_vine_bg, mBackView);
                    break;
                case Constants.WEB:
                    mLoader.loadDrawable(R.drawable.ic_web_bg, mBackView);
                    break;
            }
        } else {
            mLoader.loadImageAlphaCache(getArguments().getString(FEED_TYPE_THUMB), mBackView);
            mBackView.setAlpha(0.6f);
        }

        initWebView(getArguments().getString(FEED_TYPE_URL));


        return mView;
    }

    private void initWebView(String url) {
        mWebView = (WebView) mView.findViewById(R.id.feed_view_vb);
        mWebView.getSettings().setJavaScriptEnabled(true);

        ((MainActivity) getActivity()).showProgress();

        mWebView.loadUrl(url);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!loadingFinished) {
                    redirect = true;
                }

                loadingFinished = false;
                mWebView.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                loadingFinished = false;
            }

            @Override
            public void onPageFinished(WebView view, final String url) {
                if (!redirect) {
                    loadingFinished = true;
                }

                if (loadingFinished && !redirect) {
                    try {
                        ((MainActivity) getActivity()).stopProgress();
                    } catch (NullPointerException nil) {
                    }
                } else {
                    redirect = false;
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                ((MainActivity) getActivity()).stopProgress();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mWebView.destroy();
    }
}