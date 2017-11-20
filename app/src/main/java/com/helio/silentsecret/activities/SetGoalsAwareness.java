package com.helio.silentsecret.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.helio.silentsecret.EncryptionDecryption.CryptLib;
import com.helio.silentsecret.R;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.models.DailyGoalList;
import com.helio.silentsecret.utils.AppSession;
import com.helio.silentsecret.utils.CommonFunction;
import com.helio.silentsecret.utils.Constants;
import com.helio.silentsecret.utils.KeyboardUtil;
import com.helio.silentsecret.utils.ToastUtil;
import com.helio.silentsecret.utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static com.helio.silentsecret.connection.IfriendRequest.toStringArray;

public class SetGoalsAwareness extends AppCompatActivity {

    TextView back_iv = null;
    TextView saved_back = null;
    boolean is_done_submitting = false;
    TextView saved_goal = null;
    TextView add_bullet = null, done_button = null, hide_keyboard = null;
    Context ct = this;
    LinearLayout main_bullet_layout = null, save_goal_linear = null;
    RelativeLayout progress_bar = null;
    RelativeLayout saved_goal_layout = null;
    List<DailyGoalList> dailyGoalLists = new ArrayList<>();
    List<String> final_note_list = new ArrayList<>();
    List<String> todays_note = new ArrayList<>();

    List<Integer> remove_indexes = new ArrayList<>();
    List<EditText> format_edit_text_list = new ArrayList<>();
    List<LinearLayout> main_formate_layout_list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_goals_awareness);
        back_iv = (TextView) findViewById(R.id.back_iv);
        saved_back = (TextView) findViewById(R.id.saved_back);
        saved_goal = (TextView) findViewById(R.id.saved_goal);
        hide_keyboard = (TextView) findViewById(R.id.hide_keyboard);
        add_bullet = (TextView) findViewById(R.id.add_bullet);
        done_button = (TextView) findViewById(R.id.done_button);
        main_bullet_layout = (LinearLayout) findViewById(R.id.main_bullet_layout);
        save_goal_linear = (LinearLayout) findViewById(R.id.save_goal_linear);
        progress_bar = (RelativeLayout) findViewById(R.id.progress_bar);
        saved_goal_layout = (RelativeLayout) findViewById(R.id.saved_goal_layout);

        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        saved_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saved_goal_layout.setVisibility(View.GONE);
            }
        });

        add_bullet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                        Toast.makeText(ct, "Added point should not be empty.", Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });
        Add_new_bullet();

        hide_keyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    KeyboardUtil.hideKeyBoard(v, ct);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        done_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtil.hideKeyBoard(v, ct);
                if(is_done_submitting == false) {
                    is_done_submitting = true;
                    sugmitGoal();
                }
            }
        });
        saved_goal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saved_goal_layout.setVisibility(View.VISIBLE);
            }
        });
        new GetSetGoal().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
       /* add_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                if (main_formate_layout_list.size() == 0)
                    Add_new_bullet();

                add_note_layout.setVisibility(View.VISIBLE);
            }
        });*/


    }

    int index_counter = 0;

    private void Add_new_bullet() {

        int width = CommonFunction.getScreenWidth();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout mainlayout = new LinearLayout(ct);
        mainlayout.setOrientation(LinearLayout.HORIZONTAL);
        // lp.setMargins(width / 150, width / 100,width / 100, width / 100);
        mainlayout.setLayoutParams(lp);
        lp = new LinearLayout.LayoutParams(width / 25, width / 25);
        lp.setMargins(width / 400, width / 100, 0, 0);

        TextView dot = new TextView(ct);
        dot.setLayoutParams(lp);
        dot.setTextSize(13);
        dot.setGravity(Gravity.CENTER);
        dot.setBackgroundResource(R.drawable.dot);
        /*if (layout_format_state.equalsIgnoreCase("dot"))
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

        }*/
        dot.setId(1 + 1);

        mainlayout.addView(dot);


        lp = new LinearLayout.LayoutParams(width / 2 + width / 5, LinearLayout.LayoutParams.WRAP_CONTENT);
        EditText topic_desc = new EditText(ct);


        lp.setMargins(width / 40, 0, 0, 0);
        topic_desc.setLayoutParams(lp);
        topic_desc.setGravity(Gravity.LEFT);
        topic_desc.setTextColor(Color.parseColor("#000000"));
        topic_desc.setTextSize(16);
        topic_desc.setTypeface(null, Typeface.BOLD);
        topic_desc.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/ubuntu.ttf"));

        topic_desc.setBackgroundColor(Color.parseColor("#ffffff"));
        format_edit_text_list.add(topic_desc);
        mainlayout.addView(topic_desc);


        //lp = new LinearLayout.LayoutParams(width / 20, width / 20);
        lp = new LinearLayout.LayoutParams(width / 15, width / 15);
        lp.setMargins(width / 100, 0, 0, 0);
        lp.gravity = Gravity.CENTER_VERTICAL;
        TextView delete_button = new TextView(ct);
        delete_button.setLayoutParams(lp);
        delete_button.setBackgroundResource(R.drawable.delete_topic);
        delete_button.setId(index_counter + 1);
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = view.getId() - 1;
                main_formate_layout_list.get(index).setVisibility(View.GONE);
                //   main_formate_layout_list.remove(index);
                remove_indexes.add(index);


            }
        });

        if (main_formate_layout_list.size() == 0)
            delete_button.setVisibility(View.INVISIBLE);


        mainlayout.addView(delete_button);


        topic_desc.setFocusable(true);
        topic_desc.requestFocus();

        main_formate_layout_list.add(mainlayout);
        main_bullet_layout.addView(mainlayout);
        index_counter++;
    }

    private void resetView() {
        main_formate_layout_list.clear();
        main_bullet_layout.removeAllViews();
        remove_indexes.clear();
        format_edit_text_list.clear();
        final_note_list.clear();
        Add_new_bullet();
    }

    private void sugmitGoal()
    {
        if (format_edit_text_list.size() == 0) {
            new ToastUtil(this, "Please add goal.");
            is_done_submitting = false;
        }
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
            if (add_new)
            {

                final_note_list.clear();

                for (int i = 0; i < format_edit_text_list.size(); i++) {
                    String text = format_edit_text_list.get(i).getText().toString();
                    if (remove_indexes.size() > 0 && remove_indexes.contains(i)) {
                        continue;
                    } else {
                        final_note_list.add(text);

                    }

                }

                new AddnewGoal().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else {
                is_done_submitting = false;
                Toast.makeText(ct, "Added goal should not be empty.", Toast.LENGTH_SHORT).show();
            }

        }
    }


    private class AddnewGoal extends android.os.AsyncTask<String, String, Bitmap> {


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
                String mediator_support_uniq_id = AppSession.getValue(ct, Constants.MEDIATOR_SUPPORT_WORKER_NAME);
                String agency_unq_id = AppSession.getValue(ct, Constants.MEDIATOR_AGENCY_ID);

                JSONArray bullet_note = new JSONArray();


                for (int i = 0; i < todays_note.size(); i++) {
                    bullet_note.put(CryptLib.encrypt(todays_note.get(i)));
                }
                for (int i = 0; i < final_note_list.size(); i++) {
                    bullet_note.put(CryptLib.encrypt(final_note_list.get(i)));
                }


                mJsonObjectSub.put("clun01", MainActivity.enc_username);
                mJsonObjectSub.put("clmediatorun01", mediator_support_uniq_id);
                mJsonObjectSub.put("goals", bullet_note);
                mJsonObjectSub.put("agency_unq_id", agency_unq_id);


                requestdata.put("apikey", "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R");
                requestdata.put("data", mJsonObjectSub);
                requestdata.put("requestType", "addMediatorGoals");
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
                is_done_submitting = false;
                progress_bar.setVisibility(View.GONE);
                if (response != null) {

                    saved_goal_layout.setVisibility(View.VISIBLE);
                    resetView();
                    new GetSetGoal().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                } else
                    new ToastUtil(ct, "Please try after some time.");


            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }


    private class GetSetGoal extends android.os.AsyncTask<String, String, Bitmap> {


        String response = "";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progress_bar.setVisibility(View.VISIBLE);
            dailyGoalLists.clear();

        }

        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest httpRequest = new IfriendRequest(ct);
                JSONObject mJsonObjectSub = new JSONObject();
                JSONObject requestdata = new JSONObject();
                JSONObject main_object = new JSONObject();


                mJsonObjectSub.put("clun01", MainActivity.enc_username);

                requestdata.put("apikey", "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R");
                requestdata.put("data", mJsonObjectSub);
                requestdata.put("requestType", "getMediatorGoals");
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
                if (response != null) {
                    String status = "";
                    if (response != null) {
                        try {
                            Object object = new JSONTokener(response).nextValue();
                            if (object instanceof JSONObject) {
                                JSONObject jsonObject = new JSONObject(response);

                                if (jsonObject.has("status"))
                                    status = jsonObject.getString("status");

                                if (status != null && status.equalsIgnoreCase("true")) {

                                    String userinfo = "";

                                    if (jsonObject.has("data"))
                                        userinfo = jsonObject.getString("data");

                                    JSONArray json_array = null;
                                    if (jsonObject.has("data"))
                                        json_array = jsonObject.getJSONArray("data");

                                    for (int i = 0; i < json_array.length(); i++) {
                                        String emoji = "", created_at = "";
                                        Date Created_at = null;
                                        JSONObject UserInfoobj = new JSONObject(json_array.getString(i));


                                        JSONArray keys_array1 = null;
                                        if (UserInfoobj.has("goals"))
                                            keys_array1 = UserInfoobj.getJSONArray("goals");

                                        String flausers2[] = toStringArray(keys_array1);
                                        List<String> flagusers1 = new ArrayList<String>();
                                        for (int k = 0; k < flausers2.length; k++) {
                                            flagusers1.add(CryptLib.decrypt(flausers2[k]));

                                        }


                                        if (UserInfoobj.has("created_at"))
                                            created_at = UserInfoobj.getString("created_at");


                                        try {
                                            Created_at = Utils.StringTodate(created_at);
                                            Created_at = getLocalTime(Created_at);
                                            //comment.setCreatedAt(Created_at);

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        dailyGoalLists.add(new DailyGoalList(flagusers1, Created_at));
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (dailyGoalLists != null && dailyGoalLists.size() > 0) {
                            saved_goal.setVisibility(View.VISIBLE);
                            todays_note.clear();
                            showSaved();
                        } else
                            saved_goal.setVisibility(View.GONE);
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }


    private Calendar current;
    private long miliSeconds;
    private Date resultdate;

    private Date getLocalTime(Date myDate) {

        Date resultdate1 = null;
        try {

            current = Calendar.getInstance();


            TimeZone tzCurrent = current.getTimeZone();
            int offset = tzCurrent.getRawOffset();
            if (tzCurrent.inDaylightTime(new Date())) {
                offset = offset + tzCurrent.getDSTSavings();
            }

            Calendar current1 = Calendar.getInstance();

            current1.setTime(myDate);
            miliSeconds = current1.getTimeInMillis();
            miliSeconds = miliSeconds + offset;
            resultdate = new Date(miliSeconds);

           /* TimeZone london = TimeZone.getTimeZone("Europe/London");
            long now = resultdate.getTime();
             resultdate1 = new Date(miliSeconds - london.getOffset(now));*/

        } catch (Exception e) {
            e.printStackTrace();
        }


        return resultdate;
    }

    private void showSaved() {
        todays_note.clear();
        save_goal_linear.removeAllViews();
        boolean is_from_today = false;
        int width = CommonFunction.getScreenWidth();
        for (int i = 0; i < dailyGoalLists.size(); i++) {

            String todays_date = CommonFunction.getDateToString(MainActivity.currentdatetime);
            String emoji_post_date = CommonFunction.getDateToString(dailyGoalLists.get(i).getCreated_at());
            int date_diff = CommonFunction.Getdatediff(todays_date, emoji_post_date);
            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            is_from_today = false;
            llp.gravity = Gravity.CENTER_HORIZONTAL;
        //    llp.setMargins(0, width / 50, 0, width / 50);
            TextView date_text = new TextView(ct);
            date_text.setLayoutParams(llp);
            date_text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/ubuntu.ttf"));
            if (date_diff == 0) {
                is_from_today = true;

                date_text.setText("Today");

            } else if (date_diff == 1) {

                date_text.setText("Yesterday");

            } else {


                String[] datefor = emoji_post_date.split("/");
                emoji_post_date = datefor[1] + "/" + datefor[0] + "/" + datefor[2];
                date_text.setText("" + emoji_post_date);

            }
            date_text.setTextColor(Color.parseColor("#ffffff"));
           // date_text.setBackgroundResource(R.drawable.theme_shape);
            date_text.setPadding(width / 50, width / 100, width / 50, width / 100);
            date_text.setTextSize(20);
            date_text.setGravity(Gravity.CENTER);
            date_text.setBackgroundColor(Color.parseColor(Constants.emotion_color_array[i%5]));
            save_goal_linear.addView(date_text);
            RelativeLayout.LayoutParams lp = null;

            for (int j = 0; j < dailyGoalLists.get(i).getGoals().size(); j++)
            {
                lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                RelativeLayout mainlayout = new RelativeLayout(this);
                mainlayout.setLayoutParams(lp);

                mainlayout.setPadding(width / 100, width / 50, width / 100, 0);

                lp = new RelativeLayout.LayoutParams(width / 25, width / 25);
                lp.setMargins(width / 100, width / 50, width / 100, 0);
                TextView dot = new TextView(this);
                dot.setLayoutParams(lp);


                dot.setBackgroundResource(R.drawable.dot);


                dot.setId(j + 1);
                mainlayout.addView(dot);


                lp = new RelativeLayout.LayoutParams(width - width / 6, RelativeLayout.LayoutParams.WRAP_CONTENT);
                TextView topic_desc = new TextView(this);
                lp.addRule(RelativeLayout.RIGHT_OF, dot.getId());
                lp.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
                lp.setMargins(width / 20, 0, width / 200, 0);
                topic_desc.setText(dailyGoalLists.get(i).getGoals().get(j));
                topic_desc.setLayoutParams(lp);
                topic_desc.setGravity(Gravity.LEFT);
                topic_desc.setTextColor(Color.parseColor("#000000"));
                topic_desc.setTextSize(16);
                topic_desc.setPadding(width / 100, width / 50, width / 100, 0);
                topic_desc.setId(j + 2);
                topic_desc.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/ubuntu.ttf"));


                if (is_from_today) {
                    todays_note.add(dailyGoalLists.get(i).getGoals().get(j));
                }

                mainlayout.addView(topic_desc);


                save_goal_linear.addView(mainlayout);
            }


        }


    }
}
