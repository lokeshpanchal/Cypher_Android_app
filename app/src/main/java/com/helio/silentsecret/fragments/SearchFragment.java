package com.helio.silentsecret.fragments;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.helio.silentsecret.R;
import com.helio.silentsecret.WebserviceDTO.GetSecretbyIdDataDTO;
import com.helio.silentsecret.WebserviceDTO.GetSecretbyidDTO;
import com.helio.silentsecret.WebserviceDTO.GetSecretbyidObjectDTO;
import com.helio.silentsecret.WebserviceDTO.SearchSecretConditionDTO;
import com.helio.silentsecret.WebserviceDTO.SearchSecretDTO;
import com.helio.silentsecret.WebserviceDTO.SearchSecretObjectDTO;
import com.helio.silentsecret.activities.MainActivity;
import com.helio.silentsecret.activities.MySecretsActivity;
import com.helio.silentsecret.adapters.FeedAdapter;
import com.helio.silentsecret.callbacks.SearchUpdateCallback;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.models.Secret;
import com.helio.silentsecret.utils.AppSession;
import com.helio.silentsecret.utils.CommonFunction;
import com.helio.silentsecret.utils.Constants;
import com.helio.silentsecret.views.ScrollDisabledListView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.helio.silentsecret.activities.MainActivity.ct;

public class SearchFragment extends Fragment implements SearchUpdateCallback {

    private View mView;
    private ScrollDisabledListView mListView;
    private FeedAdapter adapter;
    private List<Secret> mDataList;
    private int SKIP = 0;
    private int preLast;
    public static String secret_id = "";
    public static String secret_text = "";
    int width =0 , height =0;
List<String> shor_sent_list = new ArrayList<>();
    ProgressBar progress_bar = null;
    RelativeLayout main_layout;

    String[] short_sentence = {"this was hilarious",
            "don't give up",
            "wow",
            "you inspired me",
            "powerful",
            "LMAO",
            "keep going",
            "you got a friend in me",
            "we love you",
            "keep your head up",
            "keep smiling",
            "been there",
            "FML",
            "you're not alone"
    };

    int [] short_sent_drawable = {R.drawable.hilarious,R.drawable.dont_giveup,R.drawable.wow,R.drawable.you_inspired_me,
            R.drawable.powerful,
            R.drawable.lamo,R.drawable.keep_going,R.drawable.you_got_afriend,R.drawable.we_love_you,R.drawable.keep_you_headup,
            R.drawable.keep_smiling,R.drawable.been_there,R.drawable.fml_short,R.drawable.you_are_not_alone,};

    List<String> all_short_sent_list = new ArrayList<>();


    int x1, y1 = 0;
    int minusValue = -140;

    int speed = 90000, duration = 10;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.search_feed, null);

        mListView = (ScrollDisabledListView) mView.findViewById(R.id.search_list_view);
        main_layout = (RelativeLayout) mView.findViewById(R.id.main_layout);

        progress_bar = (ProgressBar) mView.findViewById(R.id.progress_bar);

    /*    if (MainActivity.is_from_commNotif == false)
            progress_bar.setVisibility(View.VISIBLE);*/
        width = CommonFunction.getScreenWidth();
        height = CommonFunction.getScreenHeight();
    for(String sentence : short_sentence)
    {
        all_short_sent_list.add(sentence);
    }
        if(secret_id!= null && !secret_id.equalsIgnoreCase("")) {
            new GetSecretByID().execute();
            new Gethugheart().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
        else
            new Searchsecret().execute();

        mDataList = new ArrayList<>();
        adapter = new FeedAdapter(LayoutInflater.from(getActivity()), mDataList, getActivity() , mListView);
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
            progress_bar.setVisibility(View.VISIBLE);
           // ((MainActivity) getActivity()).showProgress();

        }


        protected Bitmap doInBackground(String... args) {
            try {


                String age = AppSession.getValue(getActivity(), Constants.USER_AGE);
                IfriendRequest http = new IfriendRequest(getActivity());

                int intage = Integer.parseInt(age);
                if(intage <11)
                {
                    age = "11";
                }

                SearchSecretObjectDTO searchSecretObjectDTO = new SearchSecretObjectDTO(new SearchSecretDTO(new SearchSecretConditionDTO(((MySecretsActivity) getActivity()).searchText, age,""+SKIP)));
                list = http.GetSearchSecret(searchSecretObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image)
        {
            try {

                MainActivity.is_running = true;
               try
               {
                   progress_bar.setVisibility(View.GONE);
              //     ((MainActivity) getActivity()).stopProgress();
               }
               catch (Exception e)
               {
                   e.printStackTrace();
               }

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

                    adapter = new FeedAdapter(LayoutInflater.from(getActivity()), mDataList, getActivity() , mListView);

                    mListView.setAdapter(adapter);

                    adapter.notifyDataSetChanged();

                    mListView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                            MainActivity.is_running = true;
                        }
                    },1000);

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
            MainActivity.is_running = true;

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

                adapter = new FeedAdapter(LayoutInflater.from(getActivity()), mDataList, getActivity()  , mListView);

                mListView.setAdapter(adapter);



                adapter.notifyDataSetChanged();
                mListView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MainActivity.is_running = true;
                        adapter.notifyDataSetChanged();

                    }
                },1000);
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



            }

        }
    }

    private class Gethugheart extends AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String data = "0";
        Bitmap bitmap;


        String response = "";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress_bar.setVisibility(View.VISIBLE);

        }

        protected Bitmap doInBackground(String... args)
        {
            try {
                IfriendRequest httpRequest = new IfriendRequest(ct);
                JSONObject mJsonObjectSub = new JSONObject();
                JSONObject requestdata = new JSONObject();
                JSONObject main_object = new JSONObject();

                mJsonObjectSub.put("clun01", MainActivity.enc_username);
                mJsonObjectSub.put("secret_id", secret_id);
                mJsonObjectSub.put("type", "All");

                requestdata.put("apikey", "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R");
                requestdata.put("data", mJsonObjectSub);
                requestdata.put("requestType", "getShortSentense");

                main_object.put("requestData", requestdata);
                try {
                    response =  httpRequest.Gethughearme2(main_object.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {
                progress_bar.setVisibility(View.GONE);
                shor_sent_list.clear();
                if(response!= null)
                {


                    try {
                        String status = "" , short_sentense = "";
                        Object object = new JSONTokener(response).nextValue();
                        if (object instanceof JSONObject) {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.has("status"))
                                status = jsonObject.getString("status");

                            if (status != null && status.equalsIgnoreCase("true"))
                            {

                                JSONArray json_array = null;
                                if (jsonObject.has("data"))
                                    json_array = jsonObject.getJSONArray("data");

                                for (int i = 0; i < json_array.length(); i++)
                                {

                                    JSONObject UserInfoobj = new JSONObject(json_array.getString(i));

                                    if (UserInfoobj.has("short_sentense"))
                                        short_sentense = UserInfoobj.getString("short_sentense");

                                  if(short_sentense!= null && !short_sentense.equalsIgnoreCase(""))
                                      shor_sent_list.add(short_sentense);

                                }
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            secret_id = "";
            if(shor_sent_list != null && shor_sent_list.size() >0)
            {
                showBubble();
            }

        }
    }




    private void showBubble() {


        try
        {
            for (int i = 0; i < shor_sent_list.size(); i++)
            {

                int index = all_short_sent_list.indexOf(shor_sent_list.get(i));
                final TextView bubble = new TextView(ct);
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width /8, width / 8);
                bubble.setLayoutParams(lp);
                bubble.setBackgroundResource(short_sent_drawable[index]);

                main_layout.addView(bubble);

                int min1 = 4;
                int max1 = 12;
                Random xpos = new Random();
                int yposition = xpos.nextInt(max1 - min1 + 1) + min1;
                int min12 = 1;
                int max12 = 3;
                Random Xpos = new Random();
                int Xposition = Xpos.nextInt(max12 - min12 + 1) + min12;
                rotationnew(bubble, yposition, Xposition);


            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private void rotationnew(final TextView music1, final int speed1, final int xspeed) {

        int min = 12;
        int max = 20;
        Random r = new Random();
        int i1 = r.nextInt(max - min + 1) + min;
        music1.setId(speed1);
        int min1 = 1;
        int max1 = 5;
        Random xpos = new Random();
        final int xposition = xpos.nextInt(max1 - min1 + 1) + min1;

        int xposxx = 0;
        minusValue = width / 8;
        if (xposition == 8)
            xposxx = 0;
        else if (xposition == 1)
            xposxx = (width / xposition) - minusValue;
        else
            xposxx = (width / xposition);


        final int x1 = xposxx;

        y1 = height + (i1 * 70);
        music1.setTag(y1 + "#" + speed1 + "#" + x1 );

        CountDownTimer timer = new CountDownTimer(speed, duration) {
            @Override
            public void onTick(long millisUntilFinished) {

                try {
                    int speed;


                    int ypos = 0;
                    int xxxpos = 0;


                    String text = (String) music1.getTag();
                    String ara[] = text.split("#");
                    int bubbleheight = Integer.parseInt(ara[0]);
                    speed = Integer.parseInt(ara[1]);



                    int xvalue = Integer.parseInt(ara[2]);




                    ypos = bubbleheight - speed;
                    xxxpos = xvalue ;
                    music1.setX(xxxpos);

                    music1.setY(ypos);

                    if(ypos < -width/8)
                    {

                        cancel();
                    }
                    /*if (ypos < height - minusValue * 2 && !is_from_minus)
                    {
                        music1.setY(ypos);
                    } else if (ypos > 0) {
                        if (speed > 0)
                            speed = -speed;
                        music1.setY(ypos);
                    } else {
                        if (speed < 0)
                            speed = -speed;
                        music1.setY(ypos);
                    }*/
                    music1.setTag(ypos + "#" + speed + "#" + xxxpos );

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFinish() {
                this.start();
            }
        };


        timer.start();
    }

}
