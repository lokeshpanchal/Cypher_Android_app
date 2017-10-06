package com.helio.silentsecret.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.helio.silentsecret.EncryptionDecryption.CryptLib;
import com.helio.silentsecret.R;
import com.helio.silentsecret.activities.CommentSecretActivity;
import com.helio.silentsecret.activities.MainActivity;
import com.helio.silentsecret.activities.RoomsActivity;
import com.helio.silentsecret.adapters.SecretPagerAdapter;
import com.helio.silentsecret.callbacks.KeywordCheckerCallback;
import com.helio.silentsecret.checkers.KeywordsChecker;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.creator.ObjectMaker;
import com.helio.silentsecret.models.DisplaySizes;
import com.helio.silentsecret.models.PhotoModel;
import com.helio.silentsecret.utils.AppSession;
import com.helio.silentsecret.utils.CommonFunction;
import com.helio.silentsecret.utils.Constants;
import com.helio.silentsecret.utils.KeyboardUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class Create_rooms_fragement extends Fragment implements View.OnClickListener {


    private View mView;
    TextView topic_image_picker, bullet_view = null, add_bullet = null;
    int index_counter = 0;
    RelativeLayout room_title_layout = null;
    LinearLayout layout_format_layout = null, dot_list, number_list, plus_list, topic_desc_linear_format = null, main_bullet_layout = null;
    TextView edt_room_topic_name = null;

    ViewPager pic_image, topic_pic_image;
    ScrollView room_topic_layout = null, room_topic_fromat_layout = null;
    int STATE = 0;
    RelativeLayout progress_bar = null;
    String room_title = "", room_topic = "", room_desc = "";
    RelativeLayout create_room_layout = null;
    EditText edt_room_title = null, edt_room_topic = null;

    private SecretPagerAdapter mAdapter;

    boolean is_from_room_topic = true;
    String layout_format_state = "dot";
    List<TextView> format_textv_list = new ArrayList<>();

    List<Integer> remove_indexes = new ArrayList<>();
    List<EditText> format_edit_text_list = new ArrayList<>();
    List<LinearLayout> main_formate_layout_list = new ArrayList<>();

    private List<String> keyWords;


    TextView done_buton = null, create_room_back = null;
    TextView image_title = null;
    boolean is_flagged = false;
    String search_string = "";

    public static Create_rooms_fragement instance() {
        Create_rooms_fragement fragment = new Create_rooms_fragement();
        Bundle data = new Bundle();

        fragment.setArguments(data);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.create_rooms_fragment, null);

        //textView = (TextView) mView.findViewById(R.id.search_list_view);
        topic_image_picker = (TextView) mView.findViewById(R.id.topic_image_picker);
        add_bullet = (TextView) mView.findViewById(R.id.add_bullet);
        bullet_view = (TextView) mView.findViewById(R.id.bullet_view);

        create_room_layout = (RelativeLayout) mView.findViewById(R.id.create_room_layout);
        progress_bar = (RelativeLayout) mView.findViewById(R.id.progress_bar);
        edt_room_title = (EditText) mView.findViewById(R.id.edt_room_title);
        edt_room_topic = (EditText) mView.findViewById(R.id.edt_room_topic);

        layout_format_layout = (LinearLayout) mView.findViewById(R.id.layout_format_layout);
        main_bullet_layout = (LinearLayout) mView.findViewById(R.id.main_bullet_layout);

        edt_room_topic_name = (TextView) mView.findViewById(R.id.edt_room_topic_name);
        image_title = (TextView) mView.findViewById(R.id.image_title);
        room_topic_layout = (ScrollView) mView.findViewById(R.id.room_topic_layout);
        room_topic_fromat_layout = (ScrollView) mView.findViewById(R.id.room_topic_fromat_layout);
        pic_image = (ViewPager) mView.findViewById(R.id.pic_image);
        topic_pic_image = (ViewPager) mView.findViewById(R.id.topic_pic_image);

        room_title_layout = (RelativeLayout) mView.findViewById(R.id.room_title_layout);
        topic_desc_linear_format = (LinearLayout) mView.findViewById(R.id.topic_desc_linear_format);
        dot_list = (LinearLayout) mView.findViewById(R.id.dot_list);
        number_list = (LinearLayout) mView.findViewById(R.id.number_list);
        plus_list = (LinearLayout) mView.findViewById(R.id.plus_list);
        create_room_back = (TextView) mView.findViewById(R.id.create_room_back);
        done_buton = (TextView) mView.findViewById(R.id.done_button);

        progress_bar.setOnClickListener(this);
        done_buton.setOnClickListener(this);

        create_room_back.setOnClickListener(this);
        dot_list.setOnClickListener(this);
        number_list.setOnClickListener(this);
        plus_list.setOnClickListener(this);


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


        add_bullet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                room_topic = edt_room_topic.getText().toString().trim();
                if (room_topic != null && !room_topic.equalsIgnoreCase("")) {
                    if (format_edit_text_list.size() == 0)
                        Add_new_bullet();
                    else {
                        boolean add_new = true;
                        for (int i = 0; i < format_edit_text_list.size(); i++) {
                            String text = format_edit_text_list.get(i).getText().toString();
                            if (remove_indexes.size() > 0 && remove_indexes.contains(i)) {
                                continue;
                            } else {
                                if (text == null || text.equalsIgnoreCase("")) {
                                    add_new = false;
                                    break;
                                }
                            }
                        }
                        if (add_new) {
                            Add_new_bullet();
                        } else {
                            Toast.makeText(getActivity(), "Added point should not be empty.", Toast.LENGTH_SHORT).show();
                        }

                    }
                } else
                    Toast.makeText(getActivity(), "Please enter a topic.", Toast.LENGTH_SHORT).show();

            }
        });


        create_room_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        layout_format_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        topic_image_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                room_topic = edt_room_topic.getText().toString().trim();
                boolean add_new = true;
                for (int i = 0; i < format_edit_text_list.size(); i++) {
                    String text = format_edit_text_list.get(i).getText().toString();
                    if (remove_indexes.size() > 0 && remove_indexes.contains(i)) {
                        continue;
                    } else {
                        if (text == null || text.equalsIgnoreCase("")) {
                            add_new = false;
                            break;
                        }
                    }
                }

                if (!add_new) {
                    Toast.makeText(getActivity(), "Added point should not be empty, or remove it.", Toast.LENGTH_SHORT).show();
                } else {


                    if (room_topic != null && !room_topic.equalsIgnoreCase(""))
                    {

                        String banned_word = CommonFunction.checkBannedword(room_topic);

                        if (banned_word == null || banned_word.equalsIgnoreCase(""))
                        {
                            for (int i = 0; i < format_edit_text_list.size(); i++) {
                                String text = format_edit_text_list.get(i).getText().toString();
                                if (remove_indexes.size() > 0 && remove_indexes.contains(i)) {
                                    continue;
                                } else {
                                    banned_word = CommonFunction.checkBannedword(text);

                                    if (banned_word != null && !banned_word.equalsIgnoreCase("")) {
                                        Toast.makeText(getActivity(), "\"" + banned_word + "\" is an inappropriate word remove it.", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                }
                                room_topic = room_topic + "#" + text;
                            }

                            STATE = 3;
                            is_from_room_topic = false;
                            add_bullet.setVisibility(View.GONE);
                            room_topic_layout.setVisibility(View.GONE);
                            layout_format_layout.setVisibility(View.GONE);
                            KeyboardUtil.hideKeyBoard(edt_room_title, getActivity());

                            page = 1;
                            done_buton.setText("Done");

                            search_string = room_topic;
                            new SearchTopicAsync().execute();

                        } else
                            Toast.makeText(getActivity(), "\"" + banned_word + "\" is an inappropriate word remove it.", Toast.LENGTH_SHORT).show();


                    } else
                        Toast.makeText(getActivity(), "Please enter a topic.", Toast.LENGTH_SHORT).show();
                }



                /*room_topic = edt_room_topic.getText().toString().trim();
                if (room_topic != null && !room_topic.equalsIgnoreCase(""))
                {

                    String banned_word = CommonFunction.checkBannedword(room_topic);

                    if (banned_word == null || banned_word.equalsIgnoreCase(""))
                    {
                        STATE = 3;
                        is_from_room_topic = false;
                        add_bullet.setVisibility(View.GONE);
                        room_topic_layout.setVisibility(View.GONE);
                        layout_format_layout.setVisibility(View.GONE);
                        KeyboardUtil.hideKeyBoard(edt_room_title, getActivity());

                        page = 1;
                        done_buton.setText("Done");

                        search_string = room_topic;
                        new SearchTopicAsync().execute();
                    } else
                        Toast.makeText(getActivity(), "Change text into \""  + banned_word + "\" is an inappropriate word.", Toast.LENGTH_SHORT).show();


                } else
                    Toast.makeText(getActivity(), "Please enter a topic.", Toast.LENGTH_SHORT).show();*/
            }
        });

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void backbutton() {
        try {
            done_buton.setText("Next");
            if (STATE == 1) {
                STATE = 0;
                image_title.setVisibility(View.GONE);
                pic_image.setVisibility(View.GONE);
                create_room_back.setVisibility(View.GONE);
                room_title_layout.setVisibility(View.VISIBLE);
                edt_room_title.setFocusable(true);
                edt_room_title.requestFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(edt_room_title, InputMethodManager.SHOW_IMPLICIT);

            } else if (STATE == 2) {
                STATE = 1;
                add_bullet.setVisibility(View.GONE);
                KeyboardUtil.hideKeyBoard(edt_room_title, getActivity());
                image_title.setVisibility(View.VISIBLE);
                layout_format_layout.setVisibility(View.GONE);
                is_from_room_topic = true;
                pic_image.setVisibility(View.VISIBLE);
                room_topic_layout.setVisibility(View.GONE);
            } else if (STATE == 3) {
                STATE = 2;
                room_topic = "";
                add_bullet.setVisibility(View.VISIBLE);
                done_buton.setText("Done");
                image_title.setVisibility(View.GONE);
                edt_room_topic.setFocusable(true);
                edt_room_topic.requestFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(edt_room_topic, InputMethodManager.SHOW_IMPLICIT);
                room_topic_layout.setVisibility(View.VISIBLE);
                layout_format_layout.setVisibility(View.VISIBLE);
                topic_pic_image.setVisibility(View.GONE);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void checkstate() {

        try {
            done_buton.setText("Next");
            if (STATE == 0) {
                room_title = edt_room_title.getText().toString().trim();


                if (!room_title.isEmpty()) {
                    String banned_word = CommonFunction.checkBannedword(room_title);
                    if (banned_word == null || banned_word.equalsIgnoreCase("")) {
                        KeyboardUtil.hideKeyBoard(edt_room_title, getActivity());
                        STATE = 1;
                        page = 1;
                        is_from_room_topic = true;
                        search_string = room_title;
                        new SearchAsync().execute();
                    } else
                        Toast.makeText(getActivity(), "\"" + banned_word + "\" is an inappropriate word remove it.", Toast.LENGTH_SHORT).show();

                } else
                    Toast.makeText(getActivity(), "Please enter room title.", Toast.LENGTH_SHORT).show();
            } else if (STATE == 1) {

                try {
                    STATE = 2;
                    image_title.setVisibility(View.GONE);
                    pic_image.setVisibility(View.GONE);
                    room_topic_layout.setVisibility(View.VISIBLE);
                    layout_format_layout.setVisibility(View.VISIBLE);
                    done_buton.setText("Done");
                    add_bullet.setVisibility(View.VISIBLE);

                    edt_room_topic.setFocusable(true);
                    edt_room_topic.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(edt_room_topic, InputMethodManager.SHOW_IMPLICIT);


                    // getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (STATE == 2) {
                done_buton.setText("Done");
                flicker_topic_image = "";
                room_topic = edt_room_topic.getText().toString().trim();


                boolean add_new = true;
                for (int i = 0; i < format_edit_text_list.size(); i++) {
                    String text = format_edit_text_list.get(i).getText().toString();
                    if (remove_indexes.size() > 0 && remove_indexes.contains(i)) {
                        continue;
                    } else {
                        if (text == null || text.equalsIgnoreCase("")) {
                            add_new = false;
                            break;
                        }
                    }
                }

                if (!add_new) {
                    Toast.makeText(getActivity(), "Added point should not be empty, or remove it.", Toast.LENGTH_SHORT).show();
                } else {


                    if (room_topic != null && !room_topic.equalsIgnoreCase("")) {

                        String banned_word = CommonFunction.checkBannedword(room_topic);

                        if (banned_word == null || banned_word.equalsIgnoreCase(""))
                        {
                            for (int i = 0; i < format_edit_text_list.size(); i++)
                            {
                                String text = format_edit_text_list.get(i).getText().toString();
                                if (remove_indexes.size() > 0 && remove_indexes.contains(i)) {
                                    continue;
                                } else {
                                    banned_word = CommonFunction.checkBannedword(text);

                                    if (banned_word != null && !banned_word.equalsIgnoreCase("")) {
                                        Toast.makeText(getActivity(), "\"" + banned_word + "\" is an inappropriate word remove it.", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                }

                                room_topic = room_topic + "#" + text;
                            }

                            String[] data = room_title.split(" ");
                            String phone = null;
                            for (String item : data)
                            {
                               // if (item.length() > 5)
                                {
                                    phone = item;
                                    item = item.replaceAll("[^0-9]", ObjectMaker.EMPTY);
                                    if (CommentSecretActivity.isNumeric(item) || item.matches("[0-9]+") || item.matches("(\\\\d-)?(\\\\d{3}-)?\\\\d{3}-\\\\d{4}"))
                                    {
                                       is_flagged = true;
                                        KeyboardUtil.hideKeyBoard(edt_room_title, getActivity());
                                        image_title.setVisibility(View.GONE);
                                        new CreateRoomAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                                        return;
                                    }
                                }
                            }


                       data = room_topic.split(" ");
                             phone = null;
                            for (String item : data)
                            {
                               // if (item.length() > 5)
                                {
                                    phone = item;
                                    item = item.replaceAll("[^0-9]", ObjectMaker.EMPTY);
                                    if (CommentSecretActivity.isNumeric(item) || item.matches("[0-9]+") || item.matches("(\\\\d-)?(\\\\d{3}-)?\\\\d{3}-\\\\d{4}"))
                                    {
                                        is_flagged = true;
                                        KeyboardUtil.hideKeyBoard(edt_room_title, getActivity());
                                        image_title.setVisibility(View.GONE);
                                        new CreateRoomAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                                        return;
                                    }
                                }
                            }


                            new KeywordsChecker(new KeywordCheckerCallback() {
                                @Override
                                public void onDone(boolean result, String riskword) {

                                    if (!result)
                                    {
                                        is_flagged = true;
                                        try {
                                            uploadToParse();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        new KeywordsChecker(new KeywordCheckerCallback() {
                                            @Override
                                            public void onDone(boolean result, String riskword) {

                                                if (!result)
                                                    is_flagged = true;


                                                try {

                                                    uploadToParse();
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }

                                            }
                                        }).execute(keyWords, room_topic);
                                    }
                                }
                            }).execute(keyWords, room_title);


                        } else
                            Toast.makeText(getActivity(), "\"" + banned_word + "\" is an inappropriate word remove it.", Toast.LENGTH_SHORT).show();


                    } else
                        Toast.makeText(getActivity(), "Please enter a topic.", Toast.LENGTH_SHORT).show();
                }

            } else {


                String[] data = room_title.split(" ");
                String phone = null;
                for (String item : data)
                {
                    //if (item.length() > 5)
                    {
                        phone = item;
                        item = item.replaceAll("[^0-9]", ObjectMaker.EMPTY);
                        if (CommentSecretActivity.isNumeric(item) || item.matches("[0-9]+") || item.matches("(\\\\d-)?(\\\\d{3}-)?\\\\d{3}-\\\\d{4}"))
                        {
                            is_flagged = true;
                            KeyboardUtil.hideKeyBoard(edt_room_title, getActivity());
                            image_title.setVisibility(View.GONE);
                            new CreateRoomAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                            return;
                        }
                    }
                }


                data = room_topic.split(" ");
                phone = null;
                for (String item : data)
                {
                    //if (item.length() > 5)
                    {
                        phone = item;
                        item = item.replaceAll("[^0-9]", ObjectMaker.EMPTY);
                        if (CommentSecretActivity.isNumeric(item) || item.matches("[0-9]+") || item.matches("(\\\\d-)?(\\\\d{3}-)?\\\\d{3}-\\\\d{4}"))
                        {
                            is_flagged = true;
                            KeyboardUtil.hideKeyBoard(edt_room_title, getActivity());
                            image_title.setVisibility(View.GONE);
                            new CreateRoomAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                            return;
                        }
                    }
                }


                new KeywordsChecker(new KeywordCheckerCallback()
                {
                    @Override
                    public void onDone(boolean result, String riskword)
                    {

                        if (!result)
                        {
                            is_flagged = true;


                            uploadToParse();

                        } else {
                            new KeywordsChecker(new KeywordCheckerCallback() {
                                @Override
                                public void onDone(boolean result, String riskword)
                                {

                                    if (!result)
                                        is_flagged = true;
                                    uploadToParse();


                                }
                            }).execute(keyWords, room_topic);
                        }
                    }
                }).execute(keyWords, room_title);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void uploadToParse()
    {
        new AsyncTask<Void, Void, Boolean>()
        {

            @Override
            protected Boolean doInBackground(Void... voids) {

                return false;
            }

            @Override
            protected void onPostExecute(final Boolean result)
            {
                super.onPostExecute(result);

                done_buton.setText("Done");
                image_title.setVisibility(View.GONE);
                room_topic_fromat_layout.setVisibility(View.GONE);
                layout_format_layout.setVisibility(View.GONE);
                KeyboardUtil.hideKeyBoard(edt_room_topic, getActivity());
                new CreateRoomAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }



    private void ShowFormateLayout() {
        format_textv_list.clear();
        int width = CommonFunction.getScreenWidth();
        KeyboardUtil.hideKeyBoard(edt_room_topic, getActivity());
        room_topic_fromat_layout.setVisibility(View.VISIBLE);
        room_topic_layout.setVisibility(View.GONE);
        topic_desc_linear_format.removeAllViews();
        edt_room_topic_name.setText(room_topic);
        layout_format_layout.setVisibility(View.VISIBLE);

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout mainlayout = new RelativeLayout(getActivity());
        mainlayout.setLayoutParams(lp);
        lp = new RelativeLayout.LayoutParams(width / 25, width / 25);
        lp.setMargins(width / 100, width / 50, width / 100, 0);
        TextView dot = new TextView(getActivity());
        dot.setLayoutParams(lp);
        dot.setBackgroundResource(R.drawable.dot);
        dot.setId(1 + 1);
        mainlayout.addView(dot);
        format_textv_list.add(dot);

        lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        TextView topic_desc = new TextView(getActivity());
        lp.addRule(RelativeLayout.RIGHT_OF, dot.getId());
        lp.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        topic_desc.setText(room_desc);
        topic_desc.setLayoutParams(lp);
        topic_desc.setGravity(Gravity.CENTER);
        topic_desc.setTextColor(Color.parseColor("#000000"));
        topic_desc.setTextSize(15);
        mainlayout.addView(topic_desc);
        topic_desc_linear_format.addView(mainlayout);

        /*if (topic_bitmap != null)
        {
            Drawable d = new BitmapDrawable(getResources(), topic_bitmap);
            addimage(d);
        }*/
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {


            case R.id.done_button:
                checkstate();
                break;
            case R.id.create_room_back:
                backbutton();
                break;
            case R.id.dot_list: {
                try {
                    layout_format_state = "dot";
                    bullet_view.setBackgroundResource(R.drawable.dot);

                    for (int i = 0; i < format_textv_list.size(); i++) {
                        format_textv_list.get(i).setBackgroundResource(R.drawable.dot);
                        format_textv_list.get(i).setText("");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            break;
            case R.id.number_list: {
                try {
                    layout_format_state = "number";

                    bullet_view.setBackgroundResource(R.drawable.number);
                    //  bullet_view.setText("1");
                    //bullet_view.setTextColor(Color.parseColor("#000000"));
                    int number_counter = 2;
                    for (int i = 0; i < format_textv_list.size(); i++) {
                        if (remove_indexes.size() > 0 && remove_indexes.contains(i)) {

                        } else {
                            format_textv_list.get(i).setText("" + number_counter);
                            format_textv_list.get(i).setTextColor(Color.parseColor("#000000"));
                            format_textv_list.get(i).setBackgroundColor(Color.parseColor("#ffffff"));
                            number_counter++;
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            break;

            case R.id.plus_list: {
                try {
                    layout_format_state = "plus";

                    bullet_view.setBackgroundResource(R.drawable.plus_sign);


                    for (int i = 0; i < format_textv_list.size(); i++) {
                        format_textv_list.get(i).setBackgroundResource(R.drawable.plus_sign);
                        format_textv_list.get(i).setText("");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            break;
        }
    }


    private class CreateRoomAsync extends android.os.AsyncTask<String, String, Bitmap> {

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

                ArrayList<String> join_users = new ArrayList<>();

                IfriendRequest httpRequest = new IfriendRequest(getActivity());

                String age = AppSession.getValue(getActivity(), Constants.USER_AGE);


                String search_text = "";

                try {
                    String srtext = room_title;
                    srtext = srtext.toLowerCase();
                    String[] secr_text = srtext.split(" ");

                    if (secr_text != null && secr_text.length > 0) {
                        for (int i = 0; i < secr_text.length; i++) {
                            String withplus = CryptLib.encrypt(secr_text[i]);
                            withplus = withplus.replace("+", "");

                            search_text = search_text + " " + withplus;
                            search_text = search_text.trim();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                join_users.add(MainActivity.enc_username);
                JSONObject mJsonObjectSub = new JSONObject();
                JSONObject requestdata = new JSONObject();
                JSONObject main_object = new JSONObject();

                JSONArray join_se = new JSONArray();
                join_se.put(MainActivity.enc_username);

                mJsonObjectSub.put("clrmnm01", CryptLib.encrypt(room_title));
                mJsonObjectSub.put("clrmimg01", flicker_image);
                mJsonObjectSub.put("clsearchrmnm", search_text);
                mJsonObjectSub.put("ruby_code", "");
                mJsonObjectSub.put("is_private", "false");
                mJsonObjectSub.put("created_by", "user");
                mJsonObjectSub.put("user_age", age);
                mJsonObjectSub.put("cltopicname01", CryptLib.encrypt(room_title));
                mJsonObjectSub.put("cltopicdescription01", CryptLib.encrypt(room_topic));

                mJsonObjectSub.put("cltopicimage01", flicker_topic_image);
                mJsonObjectSub.put("join_users", join_se);

                mJsonObjectSub.put("is_flagged", ""+is_flagged);

                mJsonObjectSub.put("clun01", MainActivity.enc_username);
                mJsonObjectSub.put("user_firstname", "");
                mJsonObjectSub.put("topic_format", layout_format_state);

                requestdata.put("apikey", "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R");
                requestdata.put("data", mJsonObjectSub);
                requestdata.put("requestType", "createRoom");

                main_object.put("requestData", requestdata);
                try {
                    response = httpRequest.Create_room(main_object.toString());
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
                STATE = 0;

                try {
                    image_title.setVisibility(View.GONE);
                    ((RoomsActivity) getActivity()).GetMyrooms();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // new GetmYRoomAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }


    private void setdefualt() {
        try {
            done_buton.setText("Next");
            room_title = "";

            room_topic = "";
            room_desc = "";


            layout_format_state = "dot";
            edt_room_title.setText("");
            edt_room_topic.setText("");

            create_room_back.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    int page = 1;
    int selected_image = 0;

    List<DisplaySizes> PhotoModels = new ArrayList<>();

    private static final String API_KEY = "n88pxentayj653upyb8476z8";
    private static final String COLUMN_PHOTO = "images";
    private static final long CACHE_SIZE_IN_MB = 10 * 1024 * 1024;
    private static String CACHE_PATH = "";
    private List<PhotoModel> search() throws IOException, JSONException
    {
        String url = "https://api.gettyimages.com/v3/search/images?phrase="+search_string;
        Request request = new Request.Builder()
                .url(url)
                .addHeader("api-key", API_KEY)
                .build();

        Response response = getClient().newCall(request).execute();
        String json = response.body().string();

        JSONArray jsonArray = new JSONObject(json).getJSONArray(COLUMN_PHOTO);
        Type listType = new TypeToken<List<PhotoModel>>() {
        }.getType();
        return getGson().fromJson(jsonArray.toString(), listType);

    }

    private OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .cache(new Cache(new File(CACHE_PATH), CACHE_SIZE_IN_MB))
                .build();
    }

    private Gson getGson() {
        return new GsonBuilder().create();
    }


    String flicker_image = "", flicker_topic_image = "";


    private class SearchAsync extends android.os.AsyncTask<String, String, Bitmap> {


        List<DisplaySizes> Models = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress_bar.setVisibility(View.VISIBLE);

        }


        protected Bitmap doInBackground(String... args) {
            try {


             //   String reply_text_local = "";
                if(search_string.contains(" "))
                    search_string = search_string.replace(" ", "%20");

              //  Models = search();
                Models = CommonFunction.search(search_string, page, getActivity());

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
               /* if (Models != null && Models.size() > 1)
                {
                    Models.remove(0);
                    Models.remove(1);
                }
                else  if (Models != null && Models.size() > 0)
                    Models.remove(0);*/

                //PhotoModels.clear();
            }

            if (Models != null && Models.size() > 0) {

                image_title.setVisibility(View.VISIBLE);
                image_title.setText("Select room image");
                pic_image.setVisibility(View.VISIBLE);
                create_room_back.setVisibility(View.VISIBLE);
                room_title_layout.setVisibility(View.GONE);


                if (page == 1)
                {
                    PhotoModels.clear();
                    PhotoModels.addAll(Models);
                    showViewPager();
                } else {
                    PhotoModels.addAll(Models);
                    mAdapter.notifyDataSetChanged();
                }

            } else {
                if (page == 1) {

                    search_string = CommonFunction.GetdefualtWord();
                    new SearchAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            }
        }
    }


    private class SearchTopicAsync extends android.os.AsyncTask<String, String, Bitmap> {


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
               // Models =search();
                Models = CommonFunction.search(search_string, page, getActivity());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            progress_bar.setVisibility(View.GONE);


            if (Models != null && Models.size() > 0)
            {

                image_title.setVisibility(View.VISIBLE);
                image_title.setText("Select topic image");

                topic_pic_image.setVisibility(View.VISIBLE);
                create_room_back.setVisibility(View.VISIBLE);


                if (page == 1)
                {
                    PhotoModels.clear();
                    PhotoModels.addAll(Models);
                    showTopicViewPager();
                    ;
                } else {
                    PhotoModels.addAll(Models);
                    mAdapter.notifyDataSetChanged();
                }

            } else {
                if (page == 1) {
                    search_string = CommonFunction.GetdefualtWord();
                    new SearchTopicAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            }
        }
    }


    private void showViewPager() {
        try {
            pic_image.setVisibility(View.VISIBLE);

            if (is_from_room_topic)
                mAdapter = new SecretPagerAdapter(room_title, getActivity(), PhotoModels);
            else
                mAdapter = new SecretPagerAdapter(room_topic, getActivity(), PhotoModels);

            pic_image.setAdapter(mAdapter);
            // viewPager.setPageTransformer(true, new ZoomOutSlideTransformer());
            pic_image.setCurrentItem(0);
            pic_image.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    flicker_image = PhotoModels.get(position).getImageUrl();

                    if (position == PhotoModels.size() - 1)
                    {
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


    private void showTopicViewPager() {
        try {
            topic_pic_image.setVisibility(View.VISIBLE);


            mAdapter = new SecretPagerAdapter(room_topic, getActivity(), PhotoModels);

            topic_pic_image.setAdapter(mAdapter);
            // viewPager.setPageTransformer(true, new ZoomOutSlideTransformer());
            topic_pic_image.setCurrentItem(0);
            topic_pic_image.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    flicker_topic_image = PhotoModels.get(position).getImageUrl();

                    if (position == PhotoModels.size() - 1) {
                        int pagesize = PhotoModels.size() / 30;
                        if (pagesize == page) {
                            page++;
                            new SearchTopicAsync().execute();
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



   /* private List<PhotoModel> search() throws IOException, JSONException {
        String url = "";
        if (is_from_room_topic)
            url = URL_BASE + URL_SEARCH + room_title + PAGE_INFO + page;
        else
            url = URL_BASE + URL_SEARCH + room_topic + PAGE_INFO + page;


        HttpRequest httpRequest = new HttpRequest(getActivity());
        String json = httpRequest.makeconnection(url);
        ;
        JSONArray jsonArray = new JSONObject(json).getJSONObject(COLUMN_PHOTOS).getJSONArray(COLUMN_PHOTO);
        Type listType = new TypeToken<List<PhotoModel>>() {
        }.getType();
        return getGson().fromJson(jsonArray.toString(), listType);
    }


    private Gson getGson() {
        return new GsonBuilder().create();
    }
*/


    private void Add_new_bullet() {


        int width = CommonFunction.getScreenWidth();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout mainlayout = new LinearLayout(getActivity());
        mainlayout.setOrientation(LinearLayout.HORIZONTAL);
        // lp.setMargins(width / 150, width / 100,width / 100, width / 100);
        mainlayout.setLayoutParams(lp);
        lp = new LinearLayout.LayoutParams(width / 25, width / 25);
        lp.setMargins(width / 400, width / 100, 0, 0);

        TextView dot = new TextView(getActivity());
        dot.setLayoutParams(lp);
        dot.setTextSize(13);
        dot.setGravity(Gravity.CENTER);
        if (layout_format_state.equalsIgnoreCase("dot"))
            dot.setBackgroundResource(R.drawable.dot);
        else if (layout_format_state.equalsIgnoreCase("number")) {
            dot.setBackgroundColor(Color.parseColor("#ffffff"));
            int siz = format_textv_list.size() - remove_indexes.size() + 2;
            dot.setText("" + siz);
            dot.setTextColor(Color.parseColor("#000000"));

        } else {
            dot.setBackgroundResource(R.drawable.plus_sign);
            dot.setText("");
            dot.setTextColor(Color.parseColor("#000000"));

        }
        dot.setId(1 + 1);

        mainlayout.addView(dot);
        format_textv_list.add(dot);

        lp = new LinearLayout.LayoutParams(width / 2 + width /4 -width /100, LinearLayout.LayoutParams.WRAP_CONTENT);
        EditText topic_desc = new EditText(getActivity());


        lp.setMargins(width / 40, 0, 0, 0);
        topic_desc.setLayoutParams(lp);
        topic_desc.setGravity(Gravity.LEFT);
        topic_desc.setTextColor(Color.parseColor("#000000"));
        topic_desc.setTextSize(16);
        topic_desc.setTypeface(null, Typeface.BOLD);

        topic_desc.setBackgroundColor(Color.parseColor("#ffffff"));
        format_edit_text_list.add(topic_desc);
        mainlayout.addView(topic_desc);


        lp = new LinearLayout.LayoutParams(width / 20, width / 20);
        lp.setMargins(width / 100, 0, 0, 0);

        TextView delete_button = new TextView(getActivity());
        delete_button.setLayoutParams(lp);
        delete_button.setBackgroundResource(R.drawable.delete_topic);
        delete_button.setId(index_counter + 1);
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = view.getId() - 1;
                main_formate_layout_list.get(index).setVisibility(View.GONE);
                remove_indexes.add(index);


                if (layout_format_state.equalsIgnoreCase("number")) {
                    try {
                        layout_format_state = "number";

                        bullet_view.setBackgroundResource(R.drawable.number);

                        int number_counter = 2;
                        for (int i = 0; i < format_textv_list.size(); i++) {
                            if (remove_indexes.size() > 0 && remove_indexes.contains(i)) {

                            } else {
                                format_textv_list.get(i).setText("" + number_counter);
                                format_textv_list.get(i).setTextColor(Color.parseColor("#000000"));
                                format_textv_list.get(i).setBackgroundColor(Color.parseColor("#ffffff"));
                                number_counter++;
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        mainlayout.addView(delete_button);

        topic_desc.setFocusable(true);
        topic_desc.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(topic_desc, InputMethodManager.SHOW_IMPLICIT);

        main_formate_layout_list.add(mainlayout);
        main_bullet_layout.addView(mainlayout);
        index_counter++;
    }
}
