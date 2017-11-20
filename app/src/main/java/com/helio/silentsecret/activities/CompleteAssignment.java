package com.helio.silentsecret.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.helio.silentsecret.R;
import com.helio.silentsecret.appCounsellingDTO.CommonRequestTypeDTO;
import com.helio.silentsecret.appCounsellingDTO.FinalObjectDTO;
import com.helio.silentsecret.connection.ConnectionDetector;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.models.QuestionOptionModel;
import com.helio.silentsecret.models.QuestioniarAnswerDTO;
import com.helio.silentsecret.models.QuestioniarDTO;
import com.helio.silentsecret.models.QuestioniarModel;
import com.helio.silentsecret.utils.AppSession;
import com.helio.silentsecret.utils.CommonFunction;
import com.helio.silentsecret.utils.Constants;
import com.helio.silentsecret.utils.KeyboardUtil;
import com.helio.silentsecret.utils.ToastUtil;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CompleteAssignment extends AppCompatActivity {
    TextView back_iv = null;
    TextView submit_questionnair = null , not_found;
    RelativeLayout  congrats_message = null;
    Context ct = this;
    RelativeLayout progress_bar = null , questionair_layout = null;

    QuestioniarDTO questioniarDTO = null;
    //List<TextView> check_box_list = new ArrayList<>();
/*List<ImageView> radio_box = new ArrayList<>();*/
    LinearLayout main_layout = null;
    boolean is_submited = false;
    int textsize = 18;
    int smalltextsize = 16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_assignment);
        back_iv = (TextView) findViewById(R.id.back_iv);
        submit_questionnair = (TextView) findViewById(R.id.submit_questionnair);
        not_found = (TextView) findViewById(R.id.not_found);
        congrats_message = (RelativeLayout) findViewById(R.id.congrats_message);
        main_layout = (LinearLayout) findViewById(R.id.main_layout);
        progress_bar = (RelativeLayout) findViewById(R.id.progress_bar);
        questionair_layout = (RelativeLayout) findViewById(R.id.questionair_layout);
        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        submit_questionnair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConnectionDetector.isNetworkAvailable(ct)) {
                    if (is_submited == false) {
                        try {
                            KeyboardUtil.hideKeyBoard(v, ct);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        is_submited = true;
                        new SubmitQuestionnair().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    }
                } else
                    new ToastUtil(ct, Constants.NETWORK_FAILER);
            }
        });
        new GetAssignment().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }


    private class GetAssignment extends AsyncTask<String, String, Bitmap> {

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

                String suport_woker = AppSession.getValue(ct, Constants.MEDIATOR_SUPPORT_WORKER_ID);
                mJsonObjectSub.put("swuser_unq_id", suport_woker);
                mJsonObjectSub.put("clun01", MainActivity.enc_username);

                requestdata.put("apikey", "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R");
                requestdata.put("data", mJsonObjectSub);
                requestdata.put("requestType", "getAssesment");

                main_object.put("requestData", requestdata);
                try {
                    response = httpRequest.UpdateUserFirstName(main_object.toString());
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

                String status = "", cltitle01 = "", agency_unq_id = "", questioner_unq_id = "";
                List<QuestioniarModel> clquestioneranswer01 = new ArrayList<>();

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

                              /*  JSONArray json_array = null;
                                if (jsonObject.has("data"))
                                    json_array = jsonObject.getJSONArray("data");*/


                                //for (int i = 0; i < json_array.length(); i++)

                                JSONObject UserInfoobj = new JSONObject(userinfo);
                                if (UserInfoobj.has("cltitle01")) {
                                    cltitle01 = UserInfoobj.getString("cltitle01");
                                }

                                if (UserInfoobj.has("clquestioner01")) {
                                    //  String clquestioner01 = UserInfoobj.getString("clquestioner01");

                                    JSONArray json_question_array = null;
                                    if (UserInfoobj.has("clquestioner01"))
                                        json_question_array = UserInfoobj.getJSONArray("clquestioner01");

                                    for (int j = 0; j < json_question_array.length(); j++) {
                                        String type = "", lable = "", name = "", subtype = "", className = "", value_anser = "";
                                        JSONObject question_object = new JSONObject(json_question_array.getString(j));
                                        List<QuestionOptionModel> questionOptionModels = new ArrayList<>();

                                        if (question_object != null) {
                                            if (question_object.has("type")) {
                                                type = question_object.getString("type");
                                            }

                                            if (question_object.has("label")) {
                                                lable = question_object.getString("label");
                                                if(lable!= null)
                                                    lable = lable.trim();
                                            }

                                            if (question_object.has("name")) {
                                                name = question_object.getString("name");
                                            }
                                            if (question_object.has("value")) {
                                                value_anser = question_object.getString("value");
                                            }
                                            if (question_object.has("subtype")) {
                                                subtype = question_object.getString("subtype");
                                            }
                                            if (question_object.has("className")) {
                                                className = question_object.getString("className");
                                            }

                                            if (question_object.has("values")) {

                                                JSONArray json_option_array = null;
                                                if (question_object.has("values"))
                                                    json_option_array = question_object.getJSONArray("values");

                                                for (int k = 0; k < json_option_array.length(); k++) {
                                                    JSONObject option_object = new JSONObject(json_option_array.getString(k));

                                                    String option_lable = "", value = "";
                                                    boolean selected = false;
                                                    if (option_object != null) {
                                                        if (option_object.has("label")) {

                                                            option_lable = option_object.getString("label");
                                                        }


                                                        if (option_object.has("value")) {
                                                            value = option_object.getString("value");
                                                        }

                                                        if (option_object.has("selected")) {
                                                            selected = option_object.getBoolean("selected");
                                                        }

                                                    }

                                                    questionOptionModels.add(new QuestionOptionModel(option_lable, value, selected));
                                                }
                                            }

                                        }

                                        clquestioneranswer01.add(new QuestioniarModel(type, name, lable, className, subtype, questionOptionModels, value_anser));
                                    }

                                }


                                if (UserInfoobj.has("agency_unq_id")) {
                                    agency_unq_id = UserInfoobj.getString("agency_unq_id");
                                }

                                if (UserInfoobj.has("questioner_unq_id")) {
                                    questioner_unq_id = UserInfoobj.getString("questioner_unq_id");
                                }

                                String support_user_id = AppSession.getValue(ct, Constants.MEDIATOR_SUPPORT_WORKER_ID);
                                questioniarDTO = new QuestioniarDTO(clquestioneranswer01, cltitle01, MainActivity.enc_username, questioner_unq_id, agency_unq_id, support_user_id);

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (questioniarDTO != null)
                    {
                        not_found.setVisibility(View.GONE);
                        questionair_layout.setVisibility(View.VISIBLE);
                        setQuestionair();
                    }
                    else {
                        not_found.setVisibility(View.VISIBLE);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    private void setQuestionair()
    {
        try {
            main_layout.removeAllViews();


            int width = CommonFunction.getScreenWidth();
            for (int i = 0; i < questioniarDTO.getClquestioneranswer01().size(); i++) {
                RelativeLayout question_layout = new RelativeLayout(this);
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                question_layout.setLayoutParams(lp);
                if (questioniarDTO.getClquestioneranswer01().get(i).getType().equalsIgnoreCase(Constants.TEXT_AREA_TYPE)) {


                    TextView question = new TextView(this);
                    lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    question.setLayoutParams(lp);
                    question.setPadding(width / 50, width / 35, width / 50, width / 35);
                    question.setTextSize(textsize);
                    question.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/ubuntu.ttf"));
                    question.setText(questioniarDTO.getClquestioneranswer01().get(i).getLabel());
                    question.setBackgroundColor(Color.parseColor(Constants.emotion_color_array[i % 5]));
                    question.setTextColor(getResources().getColor(R.color.white));

                    question.setId(i + 1);

                    final EditText text_area = new EditText(this);
                    lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    lp.addRule(RelativeLayout.BELOW, question.getId());
                    text_area.setLayoutParams(lp);
                    text_area.setPadding(width / 50, width / 30, width / 50, width / 30);
                    text_area.setTextSize(smalltextsize);
                    text_area.setBackgroundColor(getResources().getColor(R.color.white));
                    text_area.setTextColor(getResources().getColor(R.color.black));
                    text_area.setHint(questioniarDTO.getClquestioneranswer01().get(i).getLabel());
                    if (questioniarDTO.getClquestioneranswer01().get(i).getValue() != null && !questioniarDTO.getClquestioneranswer01().get(i).getValue().equalsIgnoreCase(""))
                        text_area.setText(questioniarDTO.getClquestioneranswer01().get(i).getValue());
                    text_area.setId(i + 2);


                    TextWatcher textWatcher = new TextWatcher() {

                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                            //here, after we introduced something in the EditText we get the string from it
                            try {
                                int index = text_area.getId() - 2;
                                String answerString = text_area.getText().toString();
                                questioniarDTO.getClquestioneranswer01().get(index).setValue("" + answerString);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };


                    text_area.addTextChangedListener(textWatcher);

                    question_layout.addView(question);
                    question_layout.addView(text_area);
                } else if (questioniarDTO.getClquestioneranswer01().get(i).getType().equalsIgnoreCase(Constants.RADIO_GROUP_TYPE)) {
                    TextView question = new TextView(this);
                    lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    question.setLayoutParams(lp);
                    question.setPadding(width / 50, width / 35, width / 50, width / 35);
                    question.setTextSize(textsize);
                    question.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/ubuntu.ttf"));
                    question.setText(questioniarDTO.getClquestioneranswer01().get(i).getLabel());
                    //  question.setBackgroundColor(getResources().getColor(R.color.enter_color));
                    question.setBackgroundColor(Color.parseColor(Constants.emotion_color_array[i % 5]));
                    question.setTextColor(getResources().getColor(R.color.white));
                    question.setId(i + 1);

                    LinearLayout pre_meet_layout = new LinearLayout(this);
                    lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    lp.addRule(RelativeLayout.BELOW, question.getId());
                    pre_meet_layout.setOrientation(LinearLayout.VERTICAL);
                    pre_meet_layout.setLayoutParams(lp);
                    pre_meet_layout.setBackgroundColor(getResources().getColor(R.color.white));

                    final List<ImageView> radio_box = new ArrayList<>();

                    for (int j = 0; j < questioniarDTO.getClquestioneranswer01().get(i).getValues().size(); j++) {

                        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        LinearLayout mainlayout = new LinearLayout(ct);
                        mainlayout.setOrientation(LinearLayout.HORIZONTAL);
                        // lp.setMargins(width / 150, width / 100,width / 100, width / 100);
                        mainlayout.setLayoutParams(lp);
                        llp = new LinearLayout.LayoutParams(width / 10, width / 10);
                        llp.setMargins(width / 400, width / 100, 0, 0);
                        llp.gravity = Gravity.CENTER_VERTICAL;
                        ImageView dot = new ImageView(ct);
                        dot.setPadding(width / 50, width / 50, width / 50, width / 50);
                        dot.setLayoutParams(llp);


                        dot.setTag(i + "#" + j);


                        if (questioniarDTO.getClquestioneranswer01().get(i).getValues().get(j).isSelected()) {

                            dot.setImageResource(R.drawable.radio_questionire_sel);
                        } else {

                            dot.setImageResource(R.drawable.radio_questionire_unsel);
                        }


                        dot.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {

                                    String teg = (String) v.getTag();
                                    String[] array_index = teg.split("#");

                                    int i_value = Integer.parseInt(array_index[0]);
                                    int j_value = Integer.parseInt(array_index[1]);


                                    for (int i = 0; i < radio_box.size(); i++) {
                                        radio_box.get(i).setImageResource(R.drawable.radio_questionire_unsel);
                                        questioniarDTO.getClquestioneranswer01().get(i_value).getValues().get(i).setSelected(false);

                                    }
                                    questioniarDTO.getClquestioneranswer01().get(i_value).getValues().get(j_value).setSelected(true);

                                    ImageView imageView = (ImageView) v;
                                    imageView.setImageResource(R.drawable.radio_questionire_sel);


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                        radio_box.add(dot);
                        llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        llp.gravity = Gravity.CENTER_VERTICAL;
                        TextView text = new TextView(ct);

                        text.setLayoutParams(llp);
                        text.setTextSize(smalltextsize);
                        text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/ubuntu.ttf"));
                        text.setTextColor(getResources().getColor(R.color.black));
                        text.setGravity(Gravity.CENTER | Gravity.LEFT);
                        text.setText(questioniarDTO.getClquestioneranswer01().get(i).getValues().get(j).getLabel());

                        mainlayout.addView(dot);
                        mainlayout.addView(text);
                        pre_meet_layout.addView(mainlayout);

                    }


                    question_layout.addView(question);
                    question_layout.addView(pre_meet_layout);

                } else if (questioniarDTO.getClquestioneranswer01().get(i).getType().equalsIgnoreCase(Constants.CHECKBOX_GROUP_TYPE)) {
                    TextView question = new TextView(this);
                    lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    question.setLayoutParams(lp);
                    question.setPadding(width / 50, width / 35, width / 50, width / 35);
                    question.setTextSize(textsize);
                    question.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/ubuntu.ttf"));
                    question.setText(questioniarDTO.getClquestioneranswer01().get(i).getLabel());
                    //question.setBackgroundColor(getResources().getColor(R.color.enter_color));
                    question.setBackgroundColor(Color.parseColor(Constants.emotion_color_array[i % 5]));
                    question.setTextColor(getResources().getColor(R.color.white));
                    question.setId(i + 1);

                    LinearLayout pre_meet_layout = new LinearLayout(this);
                    lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    lp.addRule(RelativeLayout.BELOW, question.getId());
                    pre_meet_layout.setOrientation(LinearLayout.VERTICAL);
                    pre_meet_layout.setLayoutParams(lp);
                    pre_meet_layout.setBackgroundColor(getResources().getColor(R.color.white));

                    final List<ImageView> check_box_list = new ArrayList<>();
                    final List<Boolean> check_box_value = new ArrayList<>();

                    for (int j = 0; j < questioniarDTO.getClquestioneranswer01().get(i).getValues().size(); j++) {

                        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        LinearLayout mainlayout = new LinearLayout(ct);
                        mainlayout.setOrientation(LinearLayout.HORIZONTAL);
                        // lp.setMargins(width / 150, width / 100,width / 100, width / 100);
                        mainlayout.setLayoutParams(lp);
                        llp = new LinearLayout.LayoutParams(width / 10, width / 10);
                        llp.setMargins(width / 400, 0, 0, 0);
                        llp.gravity = Gravity.CENTER_VERTICAL;
                        ImageView dot = new ImageView(ct);
                        dot.setLayoutParams(llp);
                        // dot.setTextSize(13);
                        // dot.setGravity(Gravity.CENTER);
                        dot.setTag(i + "#" + j);
                        dot.setPadding(width / 100, width / 100, width / 100, width / 100);
                        //  dot.setId(j+1);
                        dot.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                try {
                                    ImageView imageView = (ImageView) v;
                                    String teg = (String) v.getTag();
                                    String[] array_index = teg.split("#");

                                    int i_value = Integer.parseInt(array_index[0]);
                                    int j_value = Integer.parseInt(array_index[1]);
                                    if (check_box_value.get(j_value)) {
                                        check_box_value.set(j_value, false);
                                        imageView.setImageResource(R.drawable.unmark_checkbox);
                                        questioniarDTO.getClquestioneranswer01().get(i_value).getValues().get(j_value).setSelected(false);
                                    } else {
                                        check_box_value.set(j_value, true);
                                        imageView.setImageResource(R.drawable.mark_check_box);
                                        questioniarDTO.getClquestioneranswer01().get(i_value).getValues().get(j_value).setSelected(true);

                                    }


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        });

                        check_box_list.add(dot);

                        if (questioniarDTO.getClquestioneranswer01().get(i).getValues().get(j).isSelected()) {
                            check_box_value.add(true);
                            dot.setImageResource(R.drawable.mark_check_box);
                        } else {
                            check_box_value.add(false);
                            dot.setImageResource(R.drawable.unmark_checkbox);
                        }


                        llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        llp.gravity = Gravity.CENTER_VERTICAL;
                        TextView text = new TextView(ct);
                        text.setLayoutParams(llp);
                        text.setTextSize(smalltextsize);
                        text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/ubuntu.ttf"));
                        text.setTextColor(getResources().getColor(R.color.homemenu));
                        // dot.setGravity(Gravity.CENTER|Gravity.LEFT);
                        text.setText(questioniarDTO.getClquestioneranswer01().get(i).getValues().get(j).getLabel());

                        mainlayout.addView(dot);
                        mainlayout.addView(text);
                        pre_meet_layout.addView(mainlayout);
                    }

                    question_layout.addView(question);
                    question_layout.addView(pre_meet_layout);

                } else if (questioniarDTO.getClquestioneranswer01().get(i).getType().equalsIgnoreCase(Constants.TEXT_TYPE)) {
                    TextView question = new TextView(this);
                    lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    question.setLayoutParams(lp);
                    question.setPadding(width / 50, width / 35, width / 50, width / 35);
                    question.setTextSize(textsize);
                    question.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/ubuntu.ttf"));
                    question.setText(questioniarDTO.getClquestioneranswer01().get(i).getLabel());
                    //question.setBackgroundColor(getResources().getColor(R.color.enter_color));
                    question.setBackgroundColor(Color.parseColor(Constants.emotion_color_array[i % 5]));
                    question.setTextColor(getResources().getColor(R.color.white));
                    question.setId(i + 1);

                    final EditText text_area = new EditText(this);
                    lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    lp.addRule(RelativeLayout.BELOW, question.getId());
                    text_area.setLayoutParams(lp);
                    text_area.setPadding(width / 50, width / 30, width / 50, width / 30);
                    text_area.setTextSize(smalltextsize);
                    text_area.setSingleLine();
                    text_area.setId(i + 2);
                    text_area.setBackgroundColor(getResources().getColor(R.color.white));
                    text_area.setText(questioniarDTO.getClquestioneranswer01().get(i).getValue());
                    text_area.setTextColor(getResources().getColor(R.color.black));
                    // text_area.setHint("Please enter your "+questioniarDTO.getClquestioneranswer01().get(i).getLabel());
                    text_area.setHint(questioniarDTO.getClquestioneranswer01().get(i).getLabel());

                    TextWatcher textWatcher = new TextWatcher() {

                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                            //here, after we introduced something in the EditText we get the string from it
                            try {
                                int index = text_area.getId() - 2;
                                String answerString = text_area.getText().toString();
                                questioniarDTO.getClquestioneranswer01().get(index).setValue("" + answerString);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };


                    text_area.addTextChangedListener(textWatcher);


                    question_layout.addView(question);
                    question_layout.addView(text_area);
                } else if (questioniarDTO.getClquestioneranswer01().get(i).getType().equalsIgnoreCase(Constants.NUMBER_TYPE)) {
                    TextView question = new TextView(this);
                    lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    question.setLayoutParams(lp);
                    question.setPadding(width / 50, width / 35, width / 50, width / 35);
                    question.setTextSize(textsize);
                    question.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/ubuntu.ttf"));
                    question.setText(questioniarDTO.getClquestioneranswer01().get(i).getLabel());
                    //  question.setBackgroundColor(getResources().getColor(R.color.enter_color));
                    question.setBackgroundColor(Color.parseColor(Constants.emotion_color_array[i % 5]));
                    question.setTextColor(getResources().getColor(R.color.white));
                    question.setId(i + 1);

                    final EditText text_area = new EditText(this);
                    lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    lp.addRule(RelativeLayout.BELOW, question.getId());
                    text_area.setLayoutParams(lp);
                    text_area.setPadding(width / 50, width / 30, width / 50, width / 30);
                    text_area.setTextSize(smalltextsize);
                    text_area.setSingleLine();
                    text_area.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                    text_area.setKeyListener(DigitsKeyListener.getInstance("0123456789."));
                    text_area.setBackgroundColor(getResources().getColor(R.color.white));
                    text_area.setTextColor(getResources().getColor(R.color.black));
                    text_area.setHint(questioniarDTO.getClquestioneranswer01().get(i).getLabel());
                    text_area.setText(questioniarDTO.getClquestioneranswer01().get(i).getValue());
                    // text_area.setHint("Please enter your "+questioniarDTO.getClquestioneranswer01().get(i).getLabel());
                    text_area.setId(i + 2);
                    TextWatcher textWatcher = new TextWatcher() {

                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                            //here, after we introduced something in the EditText we get the string from it
                            try {
                                int index = text_area.getId() - 2;
                                String answerString = text_area.getText().toString();
                                questioniarDTO.getClquestioneranswer01().get(index).setValue("" + answerString);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    };


                    text_area.addTextChangedListener(textWatcher);


                    question_layout.addView(question);
                    question_layout.addView(text_area);
                } else if (questioniarDTO.getClquestioneranswer01().get(i).getType().equalsIgnoreCase(Constants.PARAGRAPH)) {
                    TextView question = new TextView(this);
                    lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    question.setLayoutParams(lp);
                    question.setPadding(width / 50, width / 35, width / 50, width / 35);
                    question.setTextSize(textsize);
                    question.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/ubuntu.ttf"));
                    question.setText(questioniarDTO.getClquestioneranswer01().get(i).getLabel());
                    //  question.setBackgroundColor(getResources().getColor(R.color.white));

                    question.setBackgroundColor(Color.parseColor(Constants.emotion_color_array[i % 5]));

                    question.setTextColor(getResources().getColor(R.color.white));
                    question.setId(i + 1);
                    question_layout.addView(question);
                } else if (questioniarDTO.getClquestioneranswer01().get(i).getType().equalsIgnoreCase(Constants.DATE_TYPE)) {
                    TextView question = new TextView(this);
                    lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    question.setLayoutParams(lp);
                    question.setPadding(width / 50, width / 35, width / 50, width / 35);
                    question.setTextSize(textsize);
                    question.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/ubuntu.ttf"));
                    question.setText(questioniarDTO.getClquestioneranswer01().get(i).getLabel());
                    //    question.setBackgroundColor(getResources().getColor(R.color.enter_color));
                    question.setBackgroundColor(Color.parseColor(Constants.emotion_color_array[i % 5]));
                    question.setTextColor(getResources().getColor(R.color.white));
                    question.setId(i + 1);


                    final TextView text_area = new TextView(this);
                    lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    lp.addRule(RelativeLayout.BELOW, question.getId());
                    text_area.setLayoutParams(lp);
                    text_area.setPadding(width / 50, width / 30, width / 50, width / 30);
                    text_area.setTextSize(smalltextsize);
                    text_area.setSingleLine();
                    text_area.setBackgroundColor(getResources().getColor(R.color.white));
                    text_area.setTextColor(getResources().getColor(R.color.black));
                    // text_area.setHint("Please enter your "+questioniarDTO.getClquestioneranswer01().get(i).getLabel());
                    text_area.setHint(questioniarDTO.getClquestioneranswer01().get(i).getLabel());

                    if (questioniarDTO.getClquestioneranswer01().get(i).getValue() != null && !questioniarDTO.getClquestioneranswer01().get(i).getValue().equalsIgnoreCase("")) {
                        try {
                            String[] date_ar = questioniarDTO.getClquestioneranswer01().get(i).getValue().split("-");
                            text_area.setText(date_ar[2] + "/" + date_ar[1] + "/" + date_ar[0]);
                        } catch (Exception e) {
                            text_area.setText(questioniarDTO.getClquestioneranswer01().get(i).getValue());
                            e.printStackTrace();
                        }


                    }
                    text_area.setId(i + 2);

                    final Calendar myCalendar = Calendar.getInstance();


                    final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear,
                                              int dayOfMonth) {
                            // TODO Auto-generated method stub
                            myCalendar.set(Calendar.YEAR, year);
                            myCalendar.set(Calendar.MONTH, monthOfYear);
                            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            int index = text_area.getId() - 2;

                            String myFormat = "yyyy-MM-dd"; //In which you need put here
                            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


                            questioniarDTO.getClquestioneranswer01().get(index).setValue("" + sdf.format(myCalendar.getTime()));


                            updateLabel(text_area, myCalendar);
                        }

                    };

                    text_area.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            new DatePickerDialog(ct, date, myCalendar
                                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                        }
                    });

                    question_layout.addView(question);
                    question_layout.addView(text_area);

                } else if (questioniarDTO.getClquestioneranswer01().get(i).getType().equalsIgnoreCase(Constants.DROP_DOWN_TYPE)) {
                    TextView question = new TextView(this);
                    lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    question.setLayoutParams(lp);
                    question.setPadding(width / 50, width / 35, width / 50, width / 35);
                    question.setTextSize(textsize);
                    question.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/ubuntu.ttf"));
                    question.setText(questioniarDTO.getClquestioneranswer01().get(i).getLabel());
                    //question.setBackgroundColor(getResources().getColor(R.color.enter_color));
                    question.setBackgroundColor(Color.parseColor(Constants.emotion_color_array[i % 5]));
                    question.setTextColor(getResources().getColor(R.color.white));
                    question.setId(i + 1);


                    TextView text_area = new TextView(this);
                    lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    lp.addRule(RelativeLayout.BELOW, question.getId());
                    text_area.setLayoutParams(lp);
                    text_area.setPadding(width / 50, width / 30, width / 50, width / 30);
                    text_area.setTextSize(smalltextsize);
                    text_area.setSingleLine();
                    text_area.setBackgroundColor(getResources().getColor(R.color.white));
                    text_area.setTextColor(getResources().getColor(R.color.black));
                    text_area.setId(i + 2);

                    try
                    {
                        for(int k =0;  k <questioniarDTO.getClquestioneranswer01().get(i).getValues().size(); k++)
                        {
                            String value = questioniarDTO.getClquestioneranswer01().get(i).getValues().get(k).getValue();
                            if(value!= null && !value.equalsIgnoreCase(""))
                            {
                                text_area.setText(value);
                                break;
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    //text_area.setHint("Please enter your "+questioniarDTO.getClquestioneranswer01().get(i).getLabel());
                    text_area.setHint(questioniarDTO.getClquestioneranswer01().get(i).getLabel());


                    text_area.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int index = v.getId() - 2;
                            TextView textView = (TextView) v;
                            DropdownList(textView, index);
                        }
                    });


                    question_layout.addView(question);
                    question_layout.addView(text_area);

                }
                main_layout.addView(question_layout);
                TextView question = new TextView(this);
                lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, width / 40);
                question.setLayoutParams(lp);
                main_layout.addView(question);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DropdownList(final TextView tv, final int index) {
        try {
            AlertDialog.Builder builderSingle = new AlertDialog.Builder(ct);
            builderSingle.setIcon(R.drawable.ic_launcher);
            builderSingle.setTitle("");


           // final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ct, android.R.layout.select_dialog_singlechoice);
            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ct, android.R.layout.simple_list_item_1);
            for (int k = 0; k < questioniarDTO.getClquestioneranswer01().get(index).getValues().size(); k++) {
                arrayAdapter.add(questioniarDTO.getClquestioneranswer01().get(index).getValues().get(k).getLabel());
            }

            builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    tv.setText(questioniarDTO.getClquestioneranswer01().get(index).getValues().get(which).getLabel());

                    for(int k =0;  k <questioniarDTO.getClquestioneranswer01().get(index).getValues().size(); k++)
                    {
                        questioniarDTO.getClquestioneranswer01().get(index).getValues().get(k).setValue("");
                    }
                    questioniarDTO.getClquestioneranswer01().get(index).getValues().get(which).setValue(questioniarDTO.getClquestioneranswer01().get(index).getValues().get(which).getLabel());
                }
            });
            builderSingle.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void updateLabel(TextView edittext, Calendar myCalendar) {
        // String myFormat = "yyyy-MM-dd"; //In which you need put here
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edittext.setText(sdf.format(myCalendar.getTime()));
    }

    private class SubmitQuestionnair extends android.os.AsyncTask<String, String, Bitmap> {

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

                String mediator_support_uniq_id = AppSession.getValue(ct, Constants.MEDIATOR_SUPPORT_WORKER_ID);
                String agency_unq_id = AppSession.getValue(ct, Constants.MEDIATOR_AGENCY_ID);

                String jsonData1 = new Gson().toJson(questioniarDTO.getClquestioneranswer01());
                QuestioniarAnswerDTO questioniarAnswerDTO = new QuestioniarAnswerDTO(jsonData1, MainActivity.enc_username, mediator_support_uniq_id, agency_unq_id, mediator_support_uniq_id);


                CommonRequestTypeDTO commonRequestTypeDTO = new CommonRequestTypeDTO(questioniarAnswerDTO, "addQuestionerAnswer");

                try {
                    FinalObjectDTO finalObjectDTO = new FinalObjectDTO(commonRequestTypeDTO);
                    String jsonData = new Gson().toJson(finalObjectDTO);
                    response = httpRequest.Create_room(jsonData);
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

                if (response != null)
                {
                    countr =0;
                    is_from_appear = true;
                    progress_bar.setVisibility(View.GONE);
                    congrats_message.postDelayed(hide_shoe_ext , 100);
                   // new ToastUtil(ct, "Your assessment has been submited successfully.");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            is_submited = false;
        }
    }




    int countr =0;
    double alpha_value = 0.01;
    boolean is_from_appear = false;
    Runnable hide_shoe_ext = new Runnable() {
        @Override
        public void run()
        {
            congrats_message.removeCallbacks(hide_shoe_ext);

            if(countr<200 && is_from_appear)
            {


                double curr_val = alpha_value*countr;
                float currv = (float)curr_val;
                congrats_message.setAlpha(currv);
                congrats_message.postDelayed(hide_shoe_ext,10);
               /* if(countr < 16)
                {
                    congrats_message.setTextSize(8);
                }
                else {

                    if(countr < 50)
                        congrats_message.setTextSize(countr / 2);

                }*/
                congrats_message.setVisibility(View.VISIBLE);
                countr++;
            }else if(countr >0 )
            {
                is_from_appear = false;
                double curr_val = alpha_value*countr;
                float currv = (float)curr_val;
                congrats_message.setAlpha(currv);

                congrats_message.postDelayed(hide_shoe_ext,10);


               /* if(countr < 16)
                {
                    congrats_message.setTextSize(8);
                }
                else {
                    if(countr < 50 )
                        congrats_message.setTextSize(countr / 2);
                }
*/
                countr--;
            }
            else
            {
                congrats_message.setVisibility(View.GONE);
                finish();
            }

        }
    };

}
