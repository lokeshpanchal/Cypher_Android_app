package com.helio.silentsecret.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.helio.silentsecret.R;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.models.QuestionOptionModel;
import com.helio.silentsecret.models.QuestioniarDTO;
import com.helio.silentsecret.models.QuestioniarModel;
import com.helio.silentsecret.utils.AppSession;
import com.helio.silentsecret.utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

public class CompleteAssignment extends AppCompatActivity {
    TextView back_iv = null;
    Context ct = this;
    RelativeLayout progress_bar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_assignment);
        back_iv = (TextView) findViewById(R.id.back_iv);
        progress_bar = (RelativeLayout) findViewById(R.id.progress_bar);
        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                QuestioniarDTO questioniarDTO = null;
                if (response != null)
                {
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
                                    if (UserInfoobj.has("cltitle01"))
                                    {
                                        cltitle01 = UserInfoobj.getString("cltitle01");
                                    }

                                    if (UserInfoobj.has("clquestioner01"))
                                    {
                                      //  String clquestioner01 = UserInfoobj.getString("clquestioner01");

                                            JSONArray json_question_array = null;
                                            if (UserInfoobj.has("clquestioner01"))
                                                json_question_array = UserInfoobj.getJSONArray("clquestioner01");

                                            for (int j = 0; j < json_question_array.length(); j++)
                                            {
                                                String type = "", lable = "", name = "", subtype = "", className = "";
                                                JSONObject question_object = new JSONObject(json_question_array.getString(j));
                                                List<QuestionOptionModel> questionOptionModels = new ArrayList<>();

                                                if (question_object != null)
                                                {
                                                    if (question_object.has("type")) {
                                                        type = question_object.getString("type");
                                                    }

                                                    if (question_object.has("label")) {
                                                        lable = question_object.getString("label");
                                                    }

                                                    if (question_object.has("name")) {
                                                        name = question_object.getString("name");
                                                    }
                                                    if (question_object.has("subtype")) {
                                                        subtype = question_object.getString("subtype");
                                                    }
                                                    if (question_object.has("className")) {
                                                        className = question_object.getString("className");
                                                    }

                                                    if (question_object.has("values"))
                                                    {

                                                        JSONArray json_option_array = null;
                                                        if (question_object.has("values"))
                                                            json_option_array = question_object.getJSONArray("values");

                                                        for (int k = 0; k < json_option_array.length(); k++)
                                                        {
                                                            JSONObject option_object = new JSONObject(json_option_array.getString(k));

                                                            String  option_lable = "", value = "";
                                                            if(option_object!= null)
                                                            {
                                                                if(option_object.has("label"))
                                                                {

                                                                            option_lable = option_object.getString("label");
                                                                }


                                                                if(option_object.has("value"))
                                                                {
                                                                    value = option_object.getString("value");
                                                                }

                                                            }

                                                            questionOptionModels.add(new QuestionOptionModel(option_lable,value));
                                                        }
                                                    }

                                                }

                                                clquestioneranswer01.add(new QuestioniarModel(type, name, lable, className, subtype, questionOptionModels));
                                            }

                                    }


                                    if (UserInfoobj.has("agency_unq_id")) {
                                        agency_unq_id = UserInfoobj.getString("agency_unq_id");
                                    }

                                    if (UserInfoobj.has("questioner_unq_id")) {
                                        questioner_unq_id = UserInfoobj.getString("questioner_unq_id");
                                    }

                                    String support_user_id = AppSession.getValue(ct,Constants.MEDIATOR_SUPPORT_WORKER_ID);
                                    questioniarDTO = new QuestioniarDTO(clquestioneranswer01 ,cltitle01 , MainActivity.enc_username ,questioner_unq_id,agency_unq_id,  support_user_id);

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if(questioniarDTO!= null)
                    {

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }


}
