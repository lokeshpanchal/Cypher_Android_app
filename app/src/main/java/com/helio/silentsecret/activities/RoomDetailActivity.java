package com.helio.silentsecret.activities;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.helio.silentsecret.EncryptionDecryption.CryptLib;
import com.helio.silentsecret.R;
import com.helio.silentsecret.adapters.SecretPagerAdapter;
import com.helio.silentsecret.callbacks.KeywordCheckerCallback;
import com.helio.silentsecret.checkers.KeywordsChecker;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.creator.ObjectMaker;
import com.helio.silentsecret.models.DisplaySizes;
import com.helio.silentsecret.models.RoomsInfoDTO;
import com.helio.silentsecret.models.TopicInfoDTO;
import com.helio.silentsecret.utils.CommonFunction;
import com.helio.silentsecret.utils.KeyboardUtil;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.fabric.sdk.android.services.concurrency.AsyncTask;

public class RoomDetailActivity extends BaseActivity  implements View.OnClickListener{

    TextView back = null, room_name = null, join_text = null, done_button = null, cancel_reply = null;
    public static RoomsInfoDTO roomsInfoDTO = null;

    private List<String> keyWords;

    private File outPutFile = null;
    List<TopicInfoDTO> topicInfoDTOs = null;
    TextView room_topic_name = null;
    RelativeLayout progress_bar = null;
    Context ct;
    LinearLayout topic_desc_linear_format = null;
    ImageView room_icon = null;
    boolean is_joined = true;
    RelativeLayout reply_layout = null;
    EditText edt_reply = null;
    String reply_text = "" , search_string = "";
boolean is_flagged = false;
    TextView reply = null , image_title = null , sync= null;
    RelativeLayout explore_layout = null;
    ImageView back_image_exploare = null, explore_image = null;
    ImageView reply_image = null, add_reply_image = null;
    Bitmap topic_reply_bitmap = null;
    ViewPager pic_image = null;
    String reply_image_url  = "";
    int STATE =0;
    RelativeLayout room_title_layout = null;
    ImageView post_image_picker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);
        ct = this;
        back = (TextView) findViewById(R.id.back);
        reply = (TextView) findViewById(R.id.reply);
        done_button = (TextView) findViewById(R.id.done_button);
        cancel_reply = (TextView) findViewById(R.id.cancel_reply);
        pic_image = (ViewPager) findViewById(R.id.pic_image);

        room_name = (TextView) findViewById(R.id.room_name);
        image_title = (TextView) findViewById(R.id.image_title);
        room_icon = (ImageView) findViewById(R.id.room_icon);
        back_image_exploare = (ImageView) findViewById(R.id.back_image_exploare);
        explore_image = (ImageView) findViewById(R.id.explore_image);
        post_image_picker = (ImageView) findViewById(R.id.post_image_picker);

        room_topic_name = (TextView) findViewById(R.id.room_topic_name);
        sync = (TextView) findViewById(R.id.sync);
        join_text = (TextView) findViewById(R.id.join_text);
        topic_desc_linear_format = (LinearLayout) findViewById(R.id.topic_desc_linear_format);
        progress_bar = (RelativeLayout) findViewById(R.id.progress_bar);
        reply_layout = (RelativeLayout) findViewById(R.id.reply_layout);
        explore_layout = (RelativeLayout) findViewById(R.id.explore_layout);
        room_title_layout = (RelativeLayout) findViewById(R.id.room_title_layout);
        edt_reply = (EditText) findViewById(R.id.edt_reply);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                KeyboardUtil.hideKeyBoard(edt_reply, ct);
            }
        });

        back_image_exploare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                explore_layout.setVisibility(View.GONE);
            }
        });
        sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GetRooomTopic().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

            }
        });
        room_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(roomsInfoDTO.getRoom_image_url()!= null && !roomsInfoDTO.getRoom_image_url().equalsIgnoreCase("") && roomsInfoDTO.getRoom_image_url().contains("http"))
                {

                    Glide.with(ct)
                            .load(roomsInfoDTO.getRoom_image_url())
                            .into(explore_image);

                }
                else {

                    explore_image.setImageBitmap(roomsInfoDTO.getRoom_image());
                }

                explore_layout.setVisibility(View.VISIBLE);

            }
        });

        reply_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        post_image_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reply_text = edt_reply.getText().toString().trim();
                if (reply_text != null && !reply_text.equalsIgnoreCase(""))
                {

                    post_image_picker.setVisibility(View.GONE);
                    String banned_word = CommonFunction.checkBannedword(reply_text);

                    if(banned_word == null || banned_word.equalsIgnoreCase(""))
                    {
                        search_string = reply_text;
                        STATE = 1;
                        page = 1;
                        KeyboardUtil.hideKeyBoard(edt_reply, ct);
                        done_button.setText("Done");
                        cancel_reply.setText("Back");
                        new SearchAsync().execute();
                    }
                    else
                        Toast.makeText(ct,"\""  + banned_word + "\" is an inappropriate word remove it.",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(ct,"Please enter a post.",Toast.LENGTH_SHORT).show();
            }
        });
        done_button.setOnClickListener(this);
        cancel_reply.setOnClickListener(this);

        try {
            if (MainActivity.stataicObjectDTO != null) {
                if (MainActivity.stataicObjectDTO.getKeywords() != null) {
                    if (keyWords == null)
                        keyWords = new ArrayList<>();
                    else
                        keyWords.clear();
                    keyWords.addAll(Arrays.asList(MainActivity.stataicObjectDTO.getKeywords().split(", ")));
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }



        cancel_reply.setOnClickListener(this);


        join_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (roomsInfoDTO.getJoin_users().contains(MainActivity.enc_username))
                    is_joined = false;
                else
                    is_joined = true;

                new JoinRoomAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });


        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try
                {
                    post_image_picker.setVisibility(View.VISIBLE);
                    edt_reply.setFocusable(true);
                    edt_reply.requestFocus();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(edt_reply, InputMethodManager.SHOW_IMPLICIT);

                    reply_layout.setVisibility(View.VISIBLE);
                    reply.setVisibility(View.GONE);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        try {


            if (roomsInfoDTO != null)
            {
                room_name.setText(CryptLib.decrypt(roomsInfoDTO.getRoom_name()));


                if(roomsInfoDTO.getRoom_image_url()!= null && !roomsInfoDTO.getRoom_image_url().equalsIgnoreCase("") && roomsInfoDTO.getRoom_image_url().contains("http"))
                {
                   // new ImageLoaderUtil(this).loadImageAlphaCache(roomsInfoDTO.getRoom_image_url(), room_icon);
                    Glide.with(this)
                            .load(roomsInfoDTO.getRoom_image_url())
                            .into(room_icon);

                    Glide.with(this)
                            .load(roomsInfoDTO.getRoom_image_url())
                            .into(explore_image);

                }
                else {

                    room_icon.setImageBitmap(roomsInfoDTO.getRoom_image());
                    explore_image.setImageBitmap(roomsInfoDTO.getRoom_image());
                }



                if (!roomsInfoDTO.getJoin_users().contains(MainActivity.enc_username))
                {
                    join_text.setText("Join");
                    reply.setVisibility(View.GONE);

                } else {
                    reply.setVisibility(View.VISIBLE);
                    join_text.setText("Unjoin");
                }

                /*if (roomsInfoDTO.getCreated_user().contains(MainActivity.enc_username)) {
                    reply.setVisibility(View.VISIBLE);
                    join_text.setText("Unjoin");
                }*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        new GetRooomTopic().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }



    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    protected int sizeOf(Bitmap data) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR1) {
            return data.getRowBytes() * data.getHeight();
        } else {
            return data.getByteCount();
        }
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {


            case R.id.done_button:
                checkstate();
                break;

            case R.id.cancel_reply:
                backbutton();
                break;
        }
    }
    private void checkstate()
    {
        if(STATE ==0)
        {
            reply_text = edt_reply.getText().toString().trim();
            if (reply_text != null && !reply_text.equalsIgnoreCase(""))
            {

                post_image_picker.setVisibility(View.GONE);
                String banned_word = CommonFunction.checkBannedword(reply_text);

                if(banned_word == null || banned_word.equalsIgnoreCase(""))
                {
                    KeyboardUtil.hideKeyBoard(edt_reply, ct);

                    String[] data = reply_text.split(" ");
                    String phone = null;
                    for (String item : data)
                    {


                        item = item.replaceAll("[^0-9]", ObjectMaker.EMPTY);
                        if (item.length() > 1)
                        {
                            int unm_len = item.length();
                            int numnber = Integer.parseInt(item);

                            String dev_num_str = "1";
                            for (int kk = 1; kk < unm_len; kk++) {
                                dev_num_str = dev_num_str + "0";
                            }
                            int dev_num = Integer.parseInt(dev_num_str);
                            int remind = numnber % dev_num;

                            if (remind == 0)
                            {

                            }
                            else if (CommentSecretActivity.isNumeric(item) || item.matches("[0-9]+") || item.matches("(\\\\d-)?(\\\\d{3}-)?\\\\d{3}-\\\\d{4}")) {
                                is_flagged = true;
                                KeyboardUtil.hideKeyBoard(edt_reply, ct);
                                image_title.setVisibility(View.GONE);
                                new AddReply().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                                return;
                            }
                        } else if (CommentSecretActivity.isNumeric(item) || item.matches("[0-9]+") || item.matches("(\\\\d-)?(\\\\d{3}-)?\\\\d{3}-\\\\d{4}")) {
                            is_flagged = true;
                            KeyboardUtil.hideKeyBoard(edt_reply, ct);
                            image_title.setVisibility(View.GONE);
                            new AddReply().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                            return;
                        }


                        /*{
                            phone = item;
                            item = item.replaceAll("[^0-9]", ObjectMaker.EMPTY);
                            if (CommentSecretActivity.isNumeric(item) || item.matches("[0-9]+") || item.matches("(\\\\d-)?(\\\\d{3}-)?\\\\d{3}-\\\\d{4}"))
                            {
                                is_flagged = true;
                                KeyboardUtil.hideKeyBoard(edt_reply, ct);
                                image_title.setVisibility(View.GONE);
                                new AddReply().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                                return;
                            }
                        }*/
                    }

                    new KeywordsChecker(new KeywordCheckerCallback() {
                        @Override
                        public void onDone(boolean result, String riskword) {

                            if (!result)
                            {
                                String key_name = riskword;
                                key_name = key_name.replaceAll("[^0-9]", ObjectMaker.EMPTY);
                                if (key_name.length() > 0)
                                    is_flagged = false;
                                else
                                    is_flagged = true;
                            }

                                uploadToParse();
                        }
                    }).execute(keyWords, reply_text);




                }
                else
                    Toast.makeText(ct," \""  + banned_word + "\" is an inappropriate word remove it.",Toast.LENGTH_SHORT).show();



            }
            else
                Toast.makeText(ct,"Please enter a post.",Toast.LENGTH_SHORT).show();
        }
        else if((STATE ==1))
        {

            String[] data = reply_text.split(" ");
            String phone = null;
            for (String item : data)
            {

                item = item.replaceAll("[^0-9]", ObjectMaker.EMPTY);
                if (item.length() > 1)
                {
                    int unm_len = item.length();
                    int numnber = Integer.parseInt(item);

                    String dev_num_str = "1";
                    for (int kk = 1; kk < unm_len; kk++) {
                        dev_num_str = dev_num_str + "0";
                    }
                    int dev_num = Integer.parseInt(dev_num_str);
                    int remind = numnber % dev_num;

                    if (remind == 0)
                    {

                    }
                    else if (CommentSecretActivity.isNumeric(item) || item.matches("[0-9]+") || item.matches("(\\\\d-)?(\\\\d{3}-)?\\\\d{3}-\\\\d{4}")) {
                        is_flagged = true;
                        KeyboardUtil.hideKeyBoard(edt_reply, ct);
                        image_title.setVisibility(View.GONE);
                        new AddReply().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                        return;
                    }
                } else if (CommentSecretActivity.isNumeric(item) || item.matches("[0-9]+") || item.matches("(\\\\d-)?(\\\\d{3}-)?\\\\d{3}-\\\\d{4}")) {
                    is_flagged = true;
                    KeyboardUtil.hideKeyBoard(edt_reply, ct);
                    image_title.setVisibility(View.GONE);
                    new AddReply().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                    return;
                }

                // if (item.length() > 5)
                /*{
                    phone = item;
                    item = item.replaceAll("[^0-9]", ObjectMaker.EMPTY);
                    if (CommentSecretActivity.isNumeric(item) || item.matches("[0-9]+") || item.matches("(\\\\d-)?(\\\\d{3}-)?\\\\d{3}-\\\\d{4}"))
                    {
                        is_flagged = true;
                        KeyboardUtil.hideKeyBoard(edt_reply, ct);
                        image_title.setVisibility(View.GONE);
                        new AddReply().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                        return;
                    }
                }*/
            }

            new KeywordsChecker(new KeywordCheckerCallback() {
                @Override
                public void onDone(boolean result, String riskword)
                {
                    if (!result)
                    {
                        String key_name = riskword;
                        key_name = key_name.replaceAll("[^0-9]", ObjectMaker.EMPTY);
                        if (key_name.length() > 0)
                            is_flagged = false;
                        else
                            is_flagged = true;
                    }
                    try {
                        uploadToParse();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).execute(keyWords, reply_text);



        }
    }



    private void uploadToParse()
    {
        new android.os.AsyncTask<Void, Void, Boolean>()
        {

            @Override
            protected Boolean doInBackground(Void... voids) {

                return false;
            }

            @Override
            protected void onPostExecute(final Boolean result)
            {
                super.onPostExecute(result);

    /*            KeyboardUtil.hideKeyBoard(edt_reply, ct);
                image_title.setVisibility(View.GONE);
                new AddReply().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);*/

                done_button.setText("Done");
                KeyboardUtil.hideKeyBoard(edt_reply, ct);
                new AddReply().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


            }
        }.executeOnExecutor(android.os.AsyncTask.THREAD_POOL_EXECUTOR);
    }


    private void backbutton()
    {
        if(STATE ==0)
        {
            edt_reply.setText("");
            reply_layout.setVisibility(View.GONE);
            reply.setVisibility(View.VISIBLE);
            KeyboardUtil.hideKeyBoard(edt_reply, ct);
        }
        else if((STATE ==1))
        {
            STATE = 0;
            reply_image_url = "";
            post_image_picker.setVisibility(View.VISIBLE);
            room_title_layout.setVisibility(View.VISIBLE);
            pic_image.setVisibility(View.GONE);
            image_title.setVisibility(View.GONE);
            done_button.setText("Done");
            cancel_reply.setText("Cancel");
        }
    }


    int page = 1;
    int selected_image = 0;

    List<DisplaySizes> PhotoModels = new ArrayList<>();

    private class SearchAsync extends android.os.AsyncTask<String, String, Bitmap> {


        List<DisplaySizes> Models = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress_bar.setVisibility(View.VISIBLE);

        }


        protected Bitmap doInBackground(String... args) {
            try {

                if(search_string.contains(" "))
                    search_string = search_string.replace(" ", "%20");

                Models = CommonFunction.search(search_string,page,ct);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            progress_bar.setVisibility(View.GONE);

            if (page == 1)
            {
                if (Models != null && Models.size() > 1)
                {
                    Models.remove(0);
                    Models.remove(1);
                }
                else  if (Models != null && Models.size() > 0)
                    Models.remove(0);

                PhotoModels.clear();
            }

            if (Models != null && Models.size() > 0) {

                image_title.setVisibility(View.VISIBLE);
                //image_title.setText("Select room image");
                pic_image.setVisibility(View.VISIBLE);
                cancel_reply.setVisibility(View.VISIBLE);
                room_title_layout.setVisibility(View.GONE);


                if (page == 1) {

                    PhotoModels.addAll(Models);
                    showViewPager();
                } else {
                    PhotoModels.addAll(Models);
                    mAdapter.notifyDataSetChanged();
                }

            } else {
                if (page == 1) {
                    if (page == 1) {
                        search_string = CommonFunction.GetdefualtWord();
                        new SearchAsync().executeOnExecutor(android.os.AsyncTask.THREAD_POOL_EXECUTOR);
                    }
                }
            }
        }
    }

    SecretPagerAdapter mAdapter = null;

    private void showViewPager()
    {
        try {
            pic_image.setVisibility(View.VISIBLE);


                mAdapter = new SecretPagerAdapter(reply_text, this, PhotoModels);

            pic_image.setAdapter(mAdapter);
            // viewPager.setPageTransformer(true, new ZoomOutSlideTransformer());
            pic_image.setCurrentItem(0);
            pic_image.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    reply_image_url = PhotoModels.get(position).getImageUrl();
                    if (position == PhotoModels.size() - 1) {
                        int pagesize = PhotoModels.size() / 30;
                        if (pagesize == page) {
                            page++;
                            new SearchAsync().execute();
                        }
                    }
                }

                @Override
                public void onPageSelected(int position) {
                    selected_image = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private class GetRooomTopic extends android.os.AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String data = "0";
        Bitmap bitmap;


        String response = "";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress_bar.setVisibility(View.VISIBLE);

        }

        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest httpRequest = new IfriendRequest(ct);


                JSONObject mJsonObjectSub = new JSONObject();
                JSONObject requestdata = new JSONObject();
                JSONObject main_object = new JSONObject();


                mJsonObjectSub.put("rm_unq_id", roomsInfoDTO.getRoom_id());

                requestdata.put("apikey", "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R");
                requestdata.put("data", mJsonObjectSub);
                requestdata.put("requestType", "getRoomTopic");

                main_object.put("requestData", requestdata);
                try {
                    topicInfoDTOs = httpRequest.get_room_topic(main_object.toString());
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

                if (topicInfoDTOs != null)
                {
                    restallvalue();
                    ShowFormateLayout();
                }

            } catch (Exception e) {
                e.printStackTrace();

            }


        }
    }

    private void restallvalue()
    {

        STATE = 0;
        reply_image_url = "";
        reply_text = "";
        edt_reply.setText("");
        post_image_picker.setVisibility(View.GONE);
        pic_image.setVisibility(View.GONE);
        room_title_layout.setVisibility(View.VISIBLE);
        image_title.setVisibility(View.GONE);
        done_button.setText("Done");
        cancel_reply.setText("Cancel");
        reply_layout.setVisibility(View.GONE);
    }

    private void ShowFormateLayout() {

        try {
            int counter =0;
            int width = CommonFunction.getScreenWidth();


            topic_desc_linear_format.removeAllViews();

            room_topic_name.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/ubuntu.ttf"));

            room_topic_name.setText(CryptLib.decrypt(topicInfoDTOs.get(0).getTopic_title()));

            for (int i = 0; i < topicInfoDTOs.size(); i++)
            {
                if(CryptLib.decrypt(topicInfoDTOs.get(i).getTopic_description()).contains("#"))
                {
                    String room_topic = CryptLib.decrypt(topicInfoDTOs.get(i).getTopic_description());
                    String [] room_topic_aray = room_topic.split("#");
                    for(int k =0; k <room_topic_aray.length; k++)
                    {
                        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        RelativeLayout mainlayout = new RelativeLayout(this);
                        mainlayout.setLayoutParams(lp);
                        if (topicInfoDTOs.get(i).getTopic_format() != null && topicInfoDTOs.get(i).getTopic_format().equalsIgnoreCase("number"))
                            lp = new RelativeLayout.LayoutParams(width / 20, width / 20);
                        else if (topicInfoDTOs.get(i).getTopic_format() != null && topicInfoDTOs.get(i).getTopic_format().equalsIgnoreCase("plus"))
                            lp = new RelativeLayout.LayoutParams(width / 20, width / 20);
                        else
                            lp = new RelativeLayout.LayoutParams(width / 50, width / 50);
                        lp.setMargins(width / 100, width / 50, width / 100, 0);
                        TextView dot = new TextView(this);
                        dot.setLayoutParams(lp);

                        if (topicInfoDTOs.get(i).getTopic_format() != null && topicInfoDTOs.get(i).getTopic_format().equalsIgnoreCase("number")) {
                            int j = k + 1;
                            dot.setText("" + j);
                        } else if (topicInfoDTOs.get(i).getTopic_format() != null && topicInfoDTOs.get(i).getTopic_format().equalsIgnoreCase("plus"))
                            dot.setText("+");
                        else
                            dot.setBackgroundResource(R.drawable.dot);


                        dot.setId(1 + 1);
                        mainlayout.addView(dot);


                        lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        TextView topic_desc = new TextView(this);
                        lp.addRule(RelativeLayout.RIGHT_OF, dot.getId());
                        lp.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
                        lp.setMargins(width / 20, 0, width / 200, 0);
                        topic_desc.setText(room_topic_aray[k]);
                        topic_desc.setLayoutParams(lp);
                        topic_desc.setGravity(Gravity.LEFT);
                        topic_desc.setTextColor(Color.parseColor("#000000"));
                        topic_desc.setTextSize(15);

                        topic_desc.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/ubuntu.ttf"));


                        mainlayout.addView(topic_desc);


                        lp = new RelativeLayout.LayoutParams(width, width / 20);
                        TextView margin1 = new TextView(this);
                        margin1.setLayoutParams(lp);

                        topic_desc_linear_format.addView(margin1);

                        topic_desc_linear_format.addView(mainlayout);
                    }

                    counter = room_topic_aray.length;
                }
                else
                {
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    RelativeLayout mainlayout = new RelativeLayout(this);
                    mainlayout.setLayoutParams(lp);
                    if(topicInfoDTOs.get(i).getTopic_format() != null && topicInfoDTOs.get(i).getTopic_format().equalsIgnoreCase("number"))
                        lp = new RelativeLayout.LayoutParams(width / 20, width / 20);
                    else  if(topicInfoDTOs.get(i).getTopic_format() != null && topicInfoDTOs.get(i).getTopic_format().equalsIgnoreCase("plus"))
                        lp = new RelativeLayout.LayoutParams(width / 20, width / 20);
                    else
                        lp = new RelativeLayout.LayoutParams(width / 50, width / 50);
                    lp.setMargins(width / 100, width / 50, width / 100, 0);
                    TextView dot = new TextView(this);
                    dot.setLayoutParams(lp);

                    if(topicInfoDTOs.get(i).getTopic_format() != null && topicInfoDTOs.get(i).getTopic_format().equalsIgnoreCase("number"))
                    {
                        int j =0;
                        if(counter>0)
                         j = i+counter;
                        else
                             j = i+1;
                        dot.setText("" + j);
                    }
                    else  if(topicInfoDTOs.get(i).getTopic_format() != null && topicInfoDTOs.get(i).getTopic_format().equalsIgnoreCase("plus"))
                        dot.setText("+");
                    else
                        dot.setBackgroundResource(R.drawable.dot);


                    dot.setId(1 + 1);
                    mainlayout.addView(dot);


                    lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    TextView topic_desc = new TextView(this);
                    lp.addRule(RelativeLayout.RIGHT_OF, dot.getId());
                    lp.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
                    lp.setMargins(width / 20, 0, width / 200, 0);
                    topic_desc.setText(CryptLib.decrypt(topicInfoDTOs.get(i).getTopic_description()));
                    topic_desc.setLayoutParams(lp);
                    topic_desc.setGravity(Gravity.LEFT);
                    topic_desc.setTextColor(Color.parseColor("#000000"));
                    topic_desc.setTextSize(15);

                    topic_desc.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/ubuntu.ttf"));


                    mainlayout.addView(topic_desc);


                    lp = new RelativeLayout.LayoutParams(width, width / 20);
                    TextView margin1 = new TextView(this);
                    margin1.setLayoutParams(lp);

                    topic_desc_linear_format.addView(margin1);

                    topic_desc_linear_format.addView(mainlayout);
                }

                addimage(i);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void addimage(final int index)
    {
        Drawable d = null;

        boolean is_from_url= false;
        if (topicInfoDTOs.get(index).getTopic_image_url()!=null && !topicInfoDTOs.get(index).getTopic_image_url().equalsIgnoreCase("") && topicInfoDTOs.get(index).getTopic_image_url().contains("http"))
        {
            is_from_url = true;


        }
        else if(topicInfoDTOs.get(index).getTopic_image()!= null)
        {
             d = new BitmapDrawable(getResources(), topicInfoDTOs.get(index).getTopic_image());
        }
        else
        return;


        int width = CommonFunction.getScreenWidth();
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout mainlayout = new RelativeLayout(this);
        mainlayout.setLayoutParams(lp);
        lp = new RelativeLayout.LayoutParams(width / 50, width / 50);
        lp.setMargins(width / 100, width / 4, width / 10, 0);
        TextView dot = new TextView(this);
        dot.setLayoutParams(lp);
        // dot.setBackgroundResource(R.drawable.dot);

       /* if (layout_format_state.equalsIgnoreCase("dot"))
            dot.setBackgroundResource(R.drawable.dot);
        else if (layout_format_state.equalsIgnoreCase("number"))
        {
            dot.setBackgroundColor(Color.parseColor("#ffffff"));
            int siz = format_textv_list.size() + 1;
            dot.setText("" + siz);
            dot.setTextColor(Color.parseColor("#000000"));
            dot.setTextSize(13);
        } else {
            dot.setBackgroundColor(Color.parseColor("#ffffff"));
            dot.setText("+");
            dot.setTextColor(Color.parseColor("#000000"));
            dot.setTextSize(13);
        }*/
        dot.setId(1 + 1);
        mainlayout.addView(dot);


        lp = new RelativeLayout.LayoutParams(2 * width / 3, 2 * width / 4);
        ImageView topic_desc = new ImageView(this);
        lp.addRule(RelativeLayout.RIGHT_OF, dot.getId());
        lp.setMargins(width / 100, width / 50, width / 10, 0);
        topic_desc.setScaleType(ImageView.ScaleType.FIT_XY);
        topic_desc.setLayoutParams(lp);
      //  topic_desc.setId(index +1);
        if(is_from_url) {
            Glide.with(this)
                    .load(topicInfoDTOs.get(index).getTopic_image_url())
                    .into(topic_desc);
            //new ImageLoaderUtil(this).loadImageAlphaCache(topicInfoDTOs.get(index).getTopic_image_url(), topic_desc);
        }
        else {

            topic_desc.setBackgroundDrawable(d);
        }


        topic_desc.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
               // int index = view.getId()-1;
                if (topicInfoDTOs.get(index).getTopic_image_url()!=null && !topicInfoDTOs.get(index).getTopic_image_url().equalsIgnoreCase("") && topicInfoDTOs.get(index).getTopic_image_url().contains("http"))
                {

                    Glide.with(ct)
                            .load(topicInfoDTOs.get(index).getTopic_image_url())
                            .into(explore_image);

                }
                else if(topicInfoDTOs.get(index).getTopic_image()!= null)
                {
                    Drawable d = new BitmapDrawable(getResources(), topicInfoDTOs.get(index).getTopic_image());
                    explore_image.setBackgroundDrawable(d);
                }

                explore_layout.setVisibility(View.VISIBLE);
            }
        });


        mainlayout.addView(topic_desc);
        topic_desc_linear_format.addView(mainlayout);




    }

    private class JoinRoomAsync extends android.os.AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String data = "0";
        Bitmap bitmap;


        String response = "";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress_bar.setVisibility(View.VISIBLE);

        }

        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest httpRequest = new IfriendRequest(ct);

                JSONObject mJsonObjectSub = new JSONObject();
                JSONObject requestdata = new JSONObject();
                JSONObject main_object = new JSONObject();

                mJsonObjectSub.put("rm_unq_id", roomsInfoDTO.getRoom_id());
                mJsonObjectSub.put("clun01", MainActivity.enc_username);
                mJsonObjectSub.put("is_joined", is_joined);


                requestdata.put("apikey", "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R");
                requestdata.put("data", mJsonObjectSub);
                requestdata.put("requestType", "joinRoom");

                main_object.put("requestData", requestdata);
                try {
                    httpRequest.JoinRoom(main_object.toString());
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

                if (roomsInfoDTO.getJoin_users().contains(MainActivity.enc_username)) {
                    roomsInfoDTO.getJoin_users().remove(MainActivity.enc_username);
                    join_text.setText("Join");
                    reply.setVisibility(View.GONE);

                } else {
                    roomsInfoDTO.getJoin_users().add(MainActivity.enc_username);
                    join_text.setText("Unjoin");
                    reply.setVisibility(View.VISIBLE);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }


    private class AddReply extends android.os.AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String data = "0";
        Bitmap bitmap;


        String response = "";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress_bar.setVisibility(View.VISIBLE);

        }

        protected Bitmap doInBackground(String... args) {
            try {



                IfriendRequest httpRequest = new IfriendRequest(ct);

                JSONObject mJsonObjectSub = new JSONObject();
                JSONObject requestdata = new JSONObject();
                JSONObject main_object = new JSONObject();

                mJsonObjectSub.put("rm_unq_id", roomsInfoDTO.getRoom_id());
                mJsonObjectSub.put("clun01", MainActivity.enc_username);
                mJsonObjectSub.put("is_flagged",""+ is_flagged);
                mJsonObjectSub.put("rm_topic_unq_id", topicInfoDTOs.get(0).getTopic_id());
                mJsonObjectSub.put("cltxt01", CryptLib.encrypt(reply_text));


                if (reply_image_url != null && !reply_image_url.equalsIgnoreCase(""))
                    mJsonObjectSub.put("reply_image", reply_image_url);
                else
                    mJsonObjectSub.put("reply_image", "");


                mJsonObjectSub.put("user_firstname", "");

                requestdata.put("apikey", "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R");
                requestdata.put("data", mJsonObjectSub);
                requestdata.put("requestType", "addReply");

                main_object.put("requestData", requestdata);
                try {
                    httpRequest.JoinRoom(main_object.toString());
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
                reply.setVisibility(View.VISIBLE);
                reply_layout.setVisibility(View.GONE);


                new GetRooomTopic().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }


}
