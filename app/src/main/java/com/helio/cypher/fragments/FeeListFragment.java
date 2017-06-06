package com.helio.cypher.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.helio.cypher.R;
import com.helio.cypher.activities.MainActivity;
import com.helio.cypher.adapters.WebFeedAdapter;
import com.helio.cypher.models.Feed;
import com.helio.cypher.utils.Constants;
import com.nineoldandroids.animation.Animator;


import java.util.ArrayList;
import java.util.List;

public class FeeListFragment extends Fragment {

    private static final String TYPE_KEY = "type_key";
    private static final String TYPE_FILTER_ARRAY = "type_array";
    private static final String TYPE_FILTER_LIST_STATE = "type_list_state";
    private static final String TYPE_GLIMPSE_STATE = "glimpse_sate";

    private View mView;
    private ListView mListView;
    private WebFeedAdapter adapter;
    private List<Feed> mDataListCopy = new ArrayList<>();
    private List<Feed> mDataList = new ArrayList<>();

    private CheckBox youtube;
    private CheckBox vine;
    private CheckBox web;

    private boolean showFilter = true;
    private View mFilter;
    private TextView mTitle;

    private boolean[] rules = new boolean[3];

    public static FeeListFragment instance(String type, boolean[] filter, int list, boolean fromGlimpse) {
        FeeListFragment fragment = new FeeListFragment();
        Bundle data = new Bundle();
        data.putString(TYPE_KEY, type);
        data.putBooleanArray(TYPE_FILTER_ARRAY, filter);
        data.putInt(TYPE_FILTER_LIST_STATE, list);
        data.putBoolean(TYPE_GLIMPSE_STATE, fromGlimpse);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.feed_web_fragment, null);

        mView.setOnClickListener(null);

        mListView = (ListView) mView.findViewById(R.id.feed_data_list_view);
        mTitle = (TextView) mView.findViewById(R.id.feed_data_title);

        switch (getArguments().getString(TYPE_KEY)) {
            case Constants.ENTERTAINMENT:
                mTitle.setText(getString(R.string.entertainment));
                break;
            case Constants.NEWS:
                mTitle.setText(getString(R.string.news));
                break;
            case Constants.LIFESTYLE:
                mTitle.setText(getString(R.string.lifestyle));
                break;
        }

        youtube = (CheckBox) mView.findViewById(R.id.feed_youtube);
        vine = (CheckBox) mView.findViewById(R.id.feed_vine);
        web = (CheckBox) mView.findViewById(R.id.feed_web);

        mDataList = new ArrayList<>();
        adapter = new WebFeedAdapter(LayoutInflater.from(getActivity()), mDataList, getActivity());
        mListView.setAdapter(adapter);

        rules = getArguments().getBooleanArray(TYPE_FILTER_ARRAY);

        youtube.setChecked(rules[0]);
        vine.setChecked(rules[1]);
        web.setChecked(rules[2]);

        mFilter = mView.findViewById(R.id.feed_filter_layout);
        mFilter.setOnClickListener(null);
        mView.findViewById(R.id.feed_data_filter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showFilter) {
                    showFilter = false;
                    mFilter.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.FadeInDown).duration(500).playOn(mFilter);
                } else {
                    showFilter = true;
                    hideFilter();
                }
            }
        });

        mView.findViewById(R.id.feed_data_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction().remove(FeeListFragment.this).commit();
            }
        });

        youtube.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                rules[0] = isChecked;
                makeFilter(rules);
            }
        });

        vine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                rules[1] = isChecked;
                makeFilter(rules);
            }
        });

        web.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                rules[2] = isChecked;
                makeFilter(rules);
            }
        });

        ((MainActivity) getActivity()).showProgress();
       /* FeedLoader.getFeedQuery(getArguments().getString(TYPE_KEY)).findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    if (list.size() == 0) {
                        ((MainActivity) getActivity()).stopProgress();
                        return;
                    }

                    mDataListCopy.clear();

                    for (ParseObject item : list)
                        mDataListCopy.add(ObjectMaker.formFeed(item));

                    makeFilter(rules);
                } else {
                    if (isAdded() && getActivity() != null)
                        ((MainActivity) getActivity()).stopProgress();
                }
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!ConnectionDetector.isNetworkAvailable(getActivity()))
                    return;

                Feed item = mDataList.get(position);
                ObjectMaker.formTrack(ParseUser.getCurrentUser(),
                        item.getTitle(), item.getParseObject()).saveInBackground();
                ((MainActivity) getActivity()).replaceFragmentInside(FeedViewFragment
                                .instance(item.getType(), rules, position, item.getUrl(), item.getSource(),
                                        item.getTitle(), item.getThumbnail()),
                        getArguments().getBoolean(TYPE_GLIMPSE_STATE));
            }
        });*/

        return mView;
    }

    private void hideFilter() {
        YoYo.with(Techniques.FadeOutUp).duration(500).withListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mFilter.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).playOn(mFilter);

    }


    private void makeFilter(boolean[] rules) {
        mDataList.clear();

        for (Feed item : mDataListCopy) {
            if (item.getSource().equals(Constants.YOUTUBE)) {
                if (rules[0])
                    mDataList.add(item);
            } else if (item.getSource().equals(Constants.VINE)) {
                if (rules[1])
                    mDataList.add(item);
            } else if (item.getSource().equals(Constants.WEB)) {
                if (rules[2])
                    mDataList.add(item);
            }
        }

        adapter.notifyDataSetChanged();

        int position = getArguments().getInt(TYPE_FILTER_LIST_STATE);
        if (mDataList.size() > 0 && (position < mDataList.size())) {
            mListView.setSelection(position);
        }

        if (isAdded() && getActivity() != null)
            ((MainActivity) getActivity()).stopProgress();
    }
}