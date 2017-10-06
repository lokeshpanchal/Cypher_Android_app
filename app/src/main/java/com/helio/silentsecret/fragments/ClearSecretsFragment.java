package com.helio.silentsecret.fragments;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.helio.silentsecret.EncryptionDecryption.CryptLib;
import com.helio.silentsecret.R;
import com.helio.silentsecret.WebserviceDTO.MysecretConditionDTO;
import com.helio.silentsecret.WebserviceDTO.MysecretObjectDTO;
import com.helio.silentsecret.WebserviceDTO.MysecretsDTO;
import com.helio.silentsecret.activities.ClearMySecretsActivity;
import com.helio.silentsecret.adapters.ClearSecreAdapterJS;
import com.helio.silentsecret.callbacks.DeleteCallback;
import com.helio.silentsecret.callbacks.UpdateCallback;
import com.helio.silentsecret.connection.ConnectionDetector;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.models.OfflineSecretObjectDTO;
import com.helio.silentsecret.models.Secret;
import com.helio.silentsecret.utils.AppSession;
import com.helio.silentsecret.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class ClearSecretsFragment extends Fragment implements UpdateCallback, DeleteCallback {

    private View mView;
    private ListView mListView;
    private ClearSecreAdapterJS adapter;

    private List<Secret> mDataList;
    public  static  String envelop_secre_id = "";
RelativeLayout progress_bar = null;
    private List<Secret> mysecretlist = null;


    private static final String TYPE_KEY = "data";
    private static final String DELETE_KEY = "delete_key";
    private int SKIP = 0;
    private int preLast;

    public static ClearSecretsFragment instance(int type, boolean mode) {
        ClearSecretsFragment fragment = new ClearSecretsFragment();
        Bundle data = new Bundle();
        data.putInt(TYPE_KEY, type);
        data.putBoolean(DELETE_KEY, mode);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // ((ClearMySecretsActivity) getActivity()).attachCallback(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.mine_feed, null);

        mListView = (ListView) mView.findViewById(R.id.mine_list_view);
        progress_bar = (RelativeLayout) mView.findViewById(R.id.progress_bar);
        mDataList = new ArrayList<>();



        adapter = new ClearSecreAdapterJS(LayoutInflater.from(getActivity()), mDataList, getActivity(), getArguments().getBoolean(DELETE_KEY));

        new FindSecret().execute();


            adapter.setDeleteMode(true);

            mListView.setAdapter(adapter);

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                try
                {
                    final int lastItem = firstVisibleItem + visibleItemCount;
                    if (mDataList != null && mDataList.size()>0 )
                    {
                        if (ConnectionDetector.isNetworkAvailable(getActivity()))
                        {
                            if (lastItem == totalItemCount) {
                                if (preLast != lastItem) {
                                    preLast = lastItem;

                                    SKIP++;
                                    if(envelop_secre_id == null || envelop_secre_id.equalsIgnoreCase(""))
                                        new FindSecret().execute();

                                    }

                                }
                            }
                        }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        return mView;
    }



    @Override
    public void onUpdate(final List<Secret> list) {
       /* try
        {
            if (list.size() > 0)
            {
                SKIP = 0;
                mDataList.clear();
                mDataList.addAll(list);



                mListView.setVisibility(View.VISIBLE);
                mView.findViewById(R.id.mine_not_found).setVisibility(View.GONE);
                mListView.setSelection(0);

                try
                {
                    adapter.notifyDataSetChanged();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }


            } else {
                mListView.setVisibility(View.GONE);
                mView.findViewById(R.id.mine_not_found).setVisibility(View.VISIBLE);
            }
        }
        catch (Exception e)
        {
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
            progress_bar.setVisibility(View.VISIBLE);
        }

        protected Bitmap doInBackground(String... args) {
            try {

                if(mysecretlist!= null) {
                    mysecretlist.clear();
                    mysecretlist = null;
                }

                IfriendRequest http = new IfriendRequest(getActivity());
                String username = AppSession.getValue(getActivity(), Constants.USER_NAME);

                MysecretConditionDTO blankConditionDTO = new MysecretConditionDTO(CryptLib.encrypt(username),""+SKIP);
                MysecretsDTO loginDTO = new MysecretsDTO(Constants.ENCRYPT_MYSECRET_METHOD,blankConditionDTO);
                MysecretObjectDTO loginbjectDTO = new MysecretObjectDTO(loginDTO);

                mysecretlist =  http.FindMySecret(loginbjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image)
        {
            try
            {
                progress_bar.setVisibility(View.GONE);
                if (mysecretlist!= null && mysecretlist.size() > 0)
                {

                    if(SKIP ==0)
                        mDataList.clear();


                    if (getArguments().getBoolean(DELETE_KEY))
                    {
                        if(ClearMySecretsActivity.mIsSelectedAll)
                        {
                            for (int k=0; k<mysecretlist.size(); k++)
                            {
                                mysecretlist.get(k).setIsDeleted(true);
                                ((ClearMySecretsActivity) getActivity()).addDeleteItem(mysecretlist.get(k).getObjectId());
                            }
                        }
                        else
                        {

                        }
                    }

                    mDataList.addAll(mysecretlist);


                    try
                    {
                        OfflineSecretObjectDTO offlineSecretObjectDTO = new OfflineSecretObjectDTO(mDataList);
                        String jsonData = new Gson().toJson(offlineSecretObjectDTO);
                        AppSession.save(getActivity(), Constants.OFFLINE_MY_SECRET, jsonData);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    mListView.setVisibility(View.VISIBLE);
                    mView.findViewById(R.id.mine_not_found).setVisibility(View.GONE);
                    //mListView.setSelection(0);

                    try
                    {
                        adapter.notifyDataSetChanged();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }


                } else
                {
                    if(SKIP == 0)
                    {
                        mListView.setVisibility(View.GONE);
                        mView.findViewById(R.id.mine_not_found).setVisibility(View.VISIBLE);
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onSelectAll() {
        adapter.selectAll();
    }

    @Override
    public void onUnSelectAll() {
        adapter.unSelectAll();
    }

    @Override
    public void onDelete(List<String> items) {
        adapter.deleteOwn(items);
    }
}
