package com.helio.silentsecret.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.helio.silentsecret.R;
import com.helio.silentsecret.WebserviceDTO.GetSecretbyIdDataDTO;
import com.helio.silentsecret.WebserviceDTO.GetSecretbyidDTO;
import com.helio.silentsecret.WebserviceDTO.GetSecretbyidObjectDTO;
import com.helio.silentsecret.WebserviceDTO.SearchSecretConditionDTO;
import com.helio.silentsecret.WebserviceDTO.SearchSecretDTO;
import com.helio.silentsecret.WebserviceDTO.SearchSecretObjectDTO;
import com.helio.silentsecret.activities.MainActivity;
import com.helio.silentsecret.adapters.FeedAdapter;
import com.helio.silentsecret.callbacks.SearchUpdateCallback;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.models.Secret;
import com.helio.silentsecret.utils.AppSession;
import com.helio.silentsecret.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements SearchUpdateCallback {

    private View mView;
    private ListView mListView;
    private FeedAdapter adapter;
    private List<Secret> mDataList;
    private int SKIP = 0;
    private int preLast;
    public static String secret_id = "";
    public static String secret_text = "";

    ProgressBar progress_bar = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.search_feed, null);

        mListView = (ListView) mView.findViewById(R.id.search_list_view);

        progress_bar = (ProgressBar) mView.findViewById(R.id.progress_bar);

    /*    if (MainActivity.is_from_commNotif == false)
            progress_bar.setVisibility(View.VISIBLE);*/

        if(secret_id!= null && !secret_id.equalsIgnoreCase(""))
            new GetSecretByID().execute();
        else
            new Searchsecret().execute();

        mDataList = new ArrayList<>();
        adapter = new FeedAdapter(LayoutInflater.from(getActivity()), mDataList, getActivity());
        //((MainActivity) getActivity()).setupSearchCallback(this);

        mListView.setAdapter(adapter);

        if (secret_id == null || secret_id.equalsIgnoreCase(""))
        {
            mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {

                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    final int lastItem = firstVisibleItem + visibleItemCount;
                    if (mDataList != null && mDataList.size()>0 )
                    {
                        if (lastItem == totalItemCount) {
                            if (preLast != lastItem) {
                                preLast = lastItem;

                                SKIP++;
                                new Searchsecret().execute();
                            }
                        }
                    }
                }
            });
        }

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mListView.setVisibility(View.VISIBLE);
        mView.findViewById(R.id.search_not_found).setVisibility(View.GONE);
    }

    @Override
    public void onUpdate(List<Secret> list, int skip) {
        try {

           /* progress_bar.setVisibility(View.GONE);

            if (list != null && list.size() > 0)
            {
                if (skip == 0) {
                    if (mDataList != null && mDataList.size() > 0)
                        mDataList.clear();
                    SKIP = 0;
                }


                if(MainActivity.is_from_commNotif == false)
                {
                    for (int i = 0; i < list.size(); i++)
                    {

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
                }
                else
                {
                    for (int i = 0; i < list.size(); i++) {
                        mDataList.add(list.get(i));
                        MainActivity.is_from_commNotif = false;
                    }
                }


                adapter = new FeedAdapter(LayoutInflater.from(getActivity()), mDataList, getActivity());
               // ((MainActivity) getActivity()).setupSearchCallback(this);
                // }
                mListView.setAdapter(adapter);

                adapter.notifyDataSetChanged();

                if (skip == 0 && mDataList != null && mDataList.size() > 0) {
                    mListView.setVisibility(View.VISIBLE);
                    mView.findViewById(R.id.search_not_found).setVisibility(View.GONE);
                    mListView.setSelection(0);
                } else {
                    mListView.setVisibility(View.GONE);
                    mView.findViewById(R.id.search_not_found).setVisibility(View.VISIBLE);
                }


                mListView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        secret_id = "";
                    }
                }, 15000);
            }
            else if (skip == 0)
            {
                mListView.setVisibility(View.GONE);
                mView.findViewById(R.id.search_not_found).setVisibility(View.VISIBLE);
            }*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mListView.setVisibility(View.VISIBLE);
        mView.findViewById(R.id.search_not_found).setVisibility(View.GONE);
    }


    private class Searchsecret extends android.os.AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String data;
        List<Secret> list;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ((MainActivity) getActivity()).showProgress();

        }


        protected Bitmap doInBackground(String... args) {
            try {


                String age = AppSession.getValue(getActivity(), Constants.USER_AGE);
                IfriendRequest http = new IfriendRequest((MainActivity) getActivity());

                SearchSecretObjectDTO searchSecretObjectDTO = new SearchSecretObjectDTO(new SearchSecretDTO(new SearchSecretConditionDTO(((MainActivity) getActivity()).searchText, age,""+SKIP)));
                list = http.GetSearchSecret(searchSecretObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image)
        {
            progress_bar.setVisibility(View.GONE);
            ((MainActivity) getActivity()).stopProgress();
            secret_id = "";

            if(SKIP == 0)
                mDataList.clear();

            if (list != null && list.size() > 0)
            {

                for (int i = 0; i < list.size(); i++)
                {
                    mDataList.add(list.get(i));
                    MainActivity.is_from_commNotif = false;
                }

                adapter = new FeedAdapter(LayoutInflater.from(getActivity()), mDataList, getActivity());

                mListView.setAdapter(adapter);

                adapter.notifyDataSetChanged();

                if (SKIP == 0 && mDataList != null && mDataList.size() > 0)
                {
                    mListView.setVisibility(View.VISIBLE);
                    mView.findViewById(R.id.search_not_found).setVisibility(View.GONE);
                    mListView.setSelection(0);
                } else
                {
                    if(SKIP == 0)
                    {
                        mListView.setVisibility(View.GONE);
                        mView.findViewById(R.id.search_not_found).setVisibility(View.VISIBLE);
                    }
                }


              /*  mListView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        secret_id = "";
                    }
                }, 15000);*/
            }else
            {
                if(SKIP == 0)
                {
                    mListView.setVisibility(View.GONE);
                    mView.findViewById(R.id.search_not_found).setVisibility(View.VISIBLE);
                }
            }

        }
    }





    private class GetSecretByID extends android.os.AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String data;
        List<Secret> list;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ((MainActivity) getActivity()).showProgress();

        }


        protected Bitmap doInBackground(String... args) {
            try {


                String age = AppSession.getValue(getActivity(), Constants.USER_AGE);
                IfriendRequest http = new IfriendRequest((MainActivity) getActivity());

                GetSecretbyidObjectDTO searchSecretObjectDTO = new GetSecretbyidObjectDTO(new GetSecretbyidDTO(new GetSecretbyIdDataDTO(secret_id)));
                list = http.GetSecretbyID(searchSecretObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image)
        {
            progress_bar.setVisibility(View.GONE);
            ((MainActivity) getActivity()).stopProgress();

            if(SKIP == 0)
                mDataList.clear();

            if (list != null && list.size() > 0)
            {

                for (int i = 0; i < list.size(); i++)
                {
                    mDataList.add(list.get(i));
                    MainActivity.is_from_commNotif = false;
                }

                adapter = new FeedAdapter(LayoutInflater.from(getActivity()), mDataList, getActivity());

                mListView.setAdapter(adapter);

                adapter.notifyDataSetChanged();

                if (SKIP == 0 && mDataList != null && mDataList.size() > 0)
                {
                    mListView.setVisibility(View.VISIBLE);
                    mView.findViewById(R.id.search_not_found).setVisibility(View.GONE);
                    mListView.setSelection(0);
                } else
                {
                    if(SKIP == 0)
                    {
                        mListView.setVisibility(View.GONE);
                        mView.findViewById(R.id.search_not_found).setVisibility(View.VISIBLE);
                    }
                }

                secret_id = "";

            }

        }
    }



}
