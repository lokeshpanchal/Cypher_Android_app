package com.helio.cypher.fragments;

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
import com.helio.cypher.EncryptionDecryption.CryptLib;
import com.helio.cypher.R;
import com.helio.cypher.WebserviceDTO.GetSecretbyIdDataDTO;
import com.helio.cypher.WebserviceDTO.GetSecretbyidDTO;
import com.helio.cypher.WebserviceDTO.GetSecretbyidObjectDTO;
import com.helio.cypher.WebserviceDTO.MysecretConditionDTO;
import com.helio.cypher.WebserviceDTO.MysecretObjectDTO;
import com.helio.cypher.WebserviceDTO.MysecretsDTO;
import com.helio.cypher.activities.MainActivity;
import com.helio.cypher.activities.MySecretsActivity;
import com.helio.cypher.adapters.FeedAdapter;
import com.helio.cypher.adapters.MineAdapter;
import com.helio.cypher.adapters.MineAdapterJS;
import com.helio.cypher.callbacks.DeleteCallback;
import com.helio.cypher.callbacks.UpdateCallback;
import com.helio.cypher.connection.ConnectionDetector;
import com.helio.cypher.connection.IfriendRequest;
import com.helio.cypher.models.OfflineSecretObjectDTO;
import com.helio.cypher.models.Secret;
import com.helio.cypher.utils.AppSession;
import com.helio.cypher.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class MineSecretsFragment extends Fragment implements UpdateCallback, DeleteCallback {

    private View mView;
    private ListView mListView;
    private MineAdapterJS adapter;
    private MineAdapter adapter2;
    private List<Secret> mDataList;
    public  static  String envelop_secre_id = "";
RelativeLayout progress_bar = null;
    private List<Secret> mysecretlist = null;
    private List<Secret> myhuglist = null;
    private List<Secret> myheartlist = null;
    private List<Secret> myme2list = null;

    private static final String TYPE_KEY = "data";
    private static final String DELETE_KEY = "delete_key";

    private int SKIP = 0;
    private int preLast;

    public static MineSecretsFragment instance(int type, boolean mode) {
        MineSecretsFragment fragment = new MineSecretsFragment();
        Bundle data = new Bundle();
        data.putInt(TYPE_KEY, type);
        data.putBoolean(DELETE_KEY, mode);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MySecretsActivity) getActivity()).attachCallback(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.mine_feed, null);

        mListView = (ListView) mView.findViewById(R.id.mine_list_view);
        progress_bar = (RelativeLayout) mView.findViewById(R.id.progress_bar);
        mDataList = new ArrayList<>();



        adapter = new MineAdapterJS(LayoutInflater.from(getActivity()), mDataList, getActivity(), getArguments().getBoolean(DELETE_KEY));
        adapter2 = new MineAdapter(LayoutInflater.from(getActivity()), mDataList, getActivity(), getArguments().getBoolean(DELETE_KEY));
        switch (getArguments().getInt(TYPE_KEY))
        {
            case Constants.STATE_MINE_SECRETS:

                if (ConnectionDetector.isNetworkAvailable(getActivity()))
                {
                    if(envelop_secre_id!= null && !envelop_secre_id.equalsIgnoreCase(""))
                        new GetSecretByID().execute();
                        else
                    new FindSecret().execute();
                }
                else
                    new OfflineFindSecret().execute();

                ((MySecretsActivity) getActivity()).setUpMine(this);
                adapter2.setShowEnvelope(true);
                break;
            case Constants.STATE_HUGS_SECRETS:


                if (ConnectionDetector.isNetworkAvailable(getActivity()))
                    new FindHugSecret().execute();
                else
                    new OfflineHugFindSecret().execute();


                ((MySecretsActivity) getActivity()).setUpHugs(this);
                break;
            case Constants.STATE_HEART_SECRETS:


                if (ConnectionDetector.isNetworkAvailable(getActivity()))
                    new FindHeartSecret().execute();
                else
                    new OfflineHeartFindSecret().execute();

                ((MySecretsActivity) getActivity()).setUpHearts(this);
                break;
            case Constants.STATE_ME2S_SECRETS:

                if (ConnectionDetector.isNetworkAvailable(getActivity()))
                    new FindMe2Secret().execute();
                else
                    new OfflineMe2FindSecret().execute();

                ((MySecretsActivity) getActivity()).setUpMe2s(this);
                break;
        }

        if (getArguments().getBoolean(DELETE_KEY))
            adapter.setDeleteMode(true);

        if(getArguments().getInt(TYPE_KEY) == Constants.STATE_MINE_SECRETS)
            mListView.setAdapter(adapter2);
        else
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

                                    switch (getArguments().getInt(TYPE_KEY)) {
                                        case Constants.STATE_MINE_SECRETS:
                                            SKIP++;
                                            if(envelop_secre_id == null || envelop_secre_id.equalsIgnoreCase(""))
                                              new FindSecret().execute();

                                            break;
                                        case Constants.STATE_HUGS_SECRETS:
                                            SKIP++;
                                            new FindHugSecret().execute();

                                            break;
                                        case Constants.STATE_HEART_SECRETS:
                                            SKIP++;
                                            new FindHeartSecret().execute();

                                            break;
                                        case Constants.STATE_ME2S_SECRETS:
                                            SKIP++;
                                            new FindMe2Secret().execute();
                                            break;
                                    }

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
                        if(MySecretsActivity.mIsSelectedAll)
                        {
                            for (int k=0; k<mysecretlist.size(); k++)
                            {
                                mysecretlist.get(k).setIsDeleted(true);
                                ((MySecretsActivity) getActivity()).addDeleteItem(mysecretlist.get(k).getObjectId());
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
                        adapter2.notifyDataSetChanged();
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




    private class OfflineFindSecret extends AsyncTask<String, String, Bitmap> {

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

                String offline_string = AppSession.getValue(getActivity(), Constants.OFFLINE_MY_SECRET);
                if (offline_string != null) {

                    IfriendRequest http = new IfriendRequest(getActivity());
                    mysecretlist = http.ParseOfflinesecret(offline_string);

                }


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
                        if(MySecretsActivity.mIsSelectedAll)
                        {
                            for (int k=0; k<mysecretlist.size(); k++)
                            {
                                mysecretlist.get(k).setIsDeleted(true);
                                ((MySecretsActivity) getActivity()).addDeleteItem(mysecretlist.get(k).getObjectId());
                            }
                        }
                        else
                        {

                        }
                    }

                    mDataList.addAll(mysecretlist);



                    mListView.setVisibility(View.VISIBLE);
                    mView.findViewById(R.id.mine_not_found).setVisibility(View.GONE);
                    //mListView.setSelection(0);

                    try
                    {
                        adapter2.notifyDataSetChanged();
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



    private class OfflineHeartFindSecret extends AsyncTask<String, String, Bitmap> {

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

                if(myheartlist!= null) {
                    myheartlist.clear();
                    myheartlist = null;
                }

                String offline_string = AppSession.getValue(getActivity(), Constants.OFFLINE_MYHEART_SECRET);
                if (offline_string != null) {

                    IfriendRequest http = new IfriendRequest(getActivity());
                    myheartlist = http.ParseOfflinesecret(offline_string);

                }


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
                if (myheartlist!= null && myheartlist.size() > 0)
                {
                    if(SKIP ==0)
                        mDataList.clear();

                    mDataList.addAll(myheartlist);

                    mListView.setVisibility(View.VISIBLE);
                    mView.findViewById(R.id.mine_not_found).setVisibility(View.GONE);
                    //  mListView.setSelection(0);

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
                    if(SKIP == 0) {
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





    private class FindHeartSecret extends AsyncTask<String, String, Bitmap> {

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

                if(myheartlist!= null) {
                    myheartlist.clear();
                    myheartlist = null;
                }

                IfriendRequest http = new IfriendRequest(getActivity());

                String username = AppSession.getValue(getActivity(), Constants.USER_NAME);

                String age = AppSession.getValue(getActivity(), Constants.USER_AGE);
                MysecretConditionDTO blankConditionDTO = new MysecretConditionDTO(CryptLib.encrypt(username),""+SKIP);
                MysecretsDTO loginDTO = new MysecretsDTO(Constants.ENCRYPT_HEART_METHOD,blankConditionDTO);
                MysecretObjectDTO loginbjectDTO = new MysecretObjectDTO(loginDTO);

                myheartlist =  http.FindMySecret(loginbjectDTO);

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
                if (myheartlist!= null && myheartlist.size() > 0)
                {
                    if(SKIP ==0)
                        mDataList.clear();

                    mDataList.addAll(myheartlist);


                    try
                    {
                        OfflineSecretObjectDTO offlineSecretObjectDTO = new OfflineSecretObjectDTO(mDataList);
                        String jsonData = new Gson().toJson(offlineSecretObjectDTO);
                        AppSession.save(getActivity(), Constants.OFFLINE_MYHEART_SECRET, jsonData);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }


                    mListView.setVisibility(View.VISIBLE);
                    mView.findViewById(R.id.mine_not_found).setVisibility(View.GONE);
                  //  mListView.setSelection(0);

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
                    if(SKIP == 0) {
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





    private class OfflineHugFindSecret extends AsyncTask<String, String, Bitmap> {

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

                if(myhuglist!= null) {
                    myhuglist.clear();
                    myhuglist = null;
                }

                String offline_string = AppSession.getValue(getActivity(), Constants.OFFLINE_MYHUG_SECRET);
                if (offline_string != null) {

                    IfriendRequest http = new IfriendRequest(getActivity());
                    myhuglist = http.ParseOfflinesecret(offline_string);

                }


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
                if (myhuglist!= null && myhuglist.size() > 0)
                {
                    if(SKIP ==0)
                        mDataList.clear();

                    mDataList.addAll(myhuglist);

                    mListView.setVisibility(View.VISIBLE);
                    mView.findViewById(R.id.mine_not_found).setVisibility(View.GONE);
                    //  mListView.setSelection(0);

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
                    if(SKIP == 0) {
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



    private class FindHugSecret extends AsyncTask<String, String, Bitmap> {

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

                if(myhuglist!= null) {
                    myhuglist.clear();
                    myhuglist = null;
                }

                IfriendRequest http = new IfriendRequest(getActivity());
                String username = AppSession.getValue(getActivity(), Constants.USER_NAME);
                MysecretConditionDTO blankConditionDTO = new MysecretConditionDTO(CryptLib.encrypt(username),""+SKIP);
                MysecretsDTO loginDTO = new MysecretsDTO(Constants.ENCRYPT_HUG_METHOD,blankConditionDTO);
                MysecretObjectDTO loginbjectDTO = new MysecretObjectDTO(loginDTO);

                myhuglist =  http.FindMySecret(loginbjectDTO);

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
                if (myhuglist!= null && myhuglist.size() > 0)
                {
                    if(SKIP ==0)
                        mDataList.clear();
                    mDataList.addAll(myhuglist);


                    try
                    {
                        OfflineSecretObjectDTO offlineSecretObjectDTO = new OfflineSecretObjectDTO(mDataList);
                        String jsonData = new Gson().toJson(offlineSecretObjectDTO);
                        AppSession.save(getActivity(), Constants.OFFLINE_MYHUG_SECRET, jsonData);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    mListView.setVisibility(View.VISIBLE);
                    mView.findViewById(R.id.mine_not_found).setVisibility(View.GONE);
                  //  mListView.setSelection(0);

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
                    if(SKIP == 0) {
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





    private class OfflineMe2FindSecret extends AsyncTask<String, String, Bitmap> {

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

                if(myme2list!= null) {
                    myme2list.clear();
                    myme2list = null;
                }

                String offline_string = AppSession.getValue(getActivity(), Constants.OFFLINE_MYME2_SECRET);
                if (offline_string != null) {

                    IfriendRequest http = new IfriendRequest(getActivity());
                    myme2list = http.ParseOfflinesecret(offline_string);

                }


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
                if (myme2list!= null && myme2list.size() > 0)
                {
                    if(SKIP ==0)
                        mDataList.clear();

                    mDataList.addAll(myme2list);

                    mListView.setVisibility(View.VISIBLE);
                    mView.findViewById(R.id.mine_not_found).setVisibility(View.GONE);
                    //  mListView.setSelection(0);

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
                    if(SKIP == 0) {
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




    private class FindMe2Secret extends AsyncTask<String, String, Bitmap> {

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

                if(myme2list!= null) {
                    myme2list.clear();
                    myme2list = null;
                }

                IfriendRequest http = new IfriendRequest(getActivity());

                String username = AppSession.getValue(getActivity(), Constants.USER_NAME);
                MysecretConditionDTO blankConditionDTO = new MysecretConditionDTO(CryptLib.encrypt(username),""+SKIP);
                MysecretsDTO loginDTO = new MysecretsDTO(Constants.ENCRYPT_ME2_METHOD,blankConditionDTO);

                MysecretObjectDTO loginbjectDTO = new MysecretObjectDTO(loginDTO);

                myme2list =  http.FindMySecret(loginbjectDTO);

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
                if (myme2list!= null && myme2list.size() > 0)
                {
                    if(SKIP ==0)
                        mDataList.clear();
                    mDataList.addAll(myme2list);


                    try
                    {
                        OfflineSecretObjectDTO offlineSecretObjectDTO = new OfflineSecretObjectDTO(mDataList);
                        String jsonData = new Gson().toJson(offlineSecretObjectDTO);
                        AppSession.save(getActivity(), Constants.OFFLINE_MYME2_SECRET, jsonData);
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
                    if(SKIP == 0) {
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





    private class GetSecretByID extends android.os.AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String data;
        List<Secret> list;


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

                GetSecretbyidObjectDTO searchSecretObjectDTO = new GetSecretbyidObjectDTO(new GetSecretbyidDTO(new GetSecretbyIdDataDTO(envelop_secre_id)));
                mysecretlist = http.GetSecretbyID(searchSecretObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image)
        {

            progress_bar.setVisibility(View.GONE);


            try
            {
                progress_bar.setVisibility(View.GONE);
                if (mysecretlist!= null && mysecretlist.size() > 0)
                {

                    if(SKIP ==0)
                        mDataList.clear();



                    mDataList.addAll(mysecretlist);



                    mListView.setVisibility(View.VISIBLE);
                    mView.findViewById(R.id.mine_not_found).setVisibility(View.GONE);

                    try
                    {
                        adapter2.notifyDataSetChanged();
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
