package com.helio.silentsecret.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.helio.silentsecret.R;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.utils.AppSession;
import com.helio.silentsecret.utils.Constants;
import com.helio.silentsecret.utils.KeyboardUtil;
import com.helio.silentsecret.utils.ToastUtil;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class MediatorActivity extends AppCompatActivity {

    TextView back_iv = null, chat_icon = null;
    TextView submit_button = null;
    EditText edt_code = null;
    RelativeLayout mediator_option = null, progress_bar = null;
    Context ct = this;
boolean is_chat_clicked = false;
    String mediator_code = "";
    LinearLayout meeting_workshop = null, assignment = null, daily_emotion = null, raise_awareness = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mediator);
        back_iv = (TextView) findViewById(R.id.back_iv);
        edt_code = (EditText) findViewById(R.id.edt_code);
        submit_button = (TextView) findViewById(R.id.submit_button);
        chat_icon = (TextView) findViewById(R.id.chat_icon);
        mediator_option = (RelativeLayout) findViewById(R.id.mediator_option);
        progress_bar = (RelativeLayout) findViewById(R.id.progress_bar);
        meeting_workshop = (LinearLayout) findViewById(R.id.meeting_workshop);
        assignment = (LinearLayout) findViewById(R.id.assignment);
        daily_emotion = (LinearLayout) findViewById(R.id.daily_emotion);
        raise_awareness = (LinearLayout) findViewById(R.id.raise_awareness);
        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        meeting_workshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ct, Mediator_meeting.class);
                startActivity(intent);
            }
        });
        assignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ct, CompleteAssignment.class);
                startActivity(intent);
            }
        });
        daily_emotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ct, DailyEmotion.class);
                startActivity(intent);
            }
        });
        raise_awareness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ct, SetGoalsAwareness.class);
                startActivity(intent);
            }
        });


        chat_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(is_chat_clicked == false) {
                    is_chat_clicked = true;
                    new GetChatEnaleData().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }

            }
        });
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mediator_code = edt_code.getText().toString().trim();
                if (mediator_code != null && !mediator_code.equalsIgnoreCase("")) {
                    KeyboardUtil.hideKeyBoard(edt_code, ct);
                    new ValidateCode().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                } else
                    new ToastUtil(ct, "Please enter vaild code.");
            }
        });


        new GetMediatorData().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

       /* mediator_code = AppSession.getValue(ct, Constants.MEDIATOR_CODE);
        if (mediator_code == null || mediator_code.equalsIgnoreCase(""))
            new GetMediatorData().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            mediator_option.setVisibility(View.VISIBLE);*/
    }


    private class ValidateCode extends AsyncTask<String, String, Bitmap> {

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

                mJsonObjectSub.put("mediator_code", mediator_code);
                mJsonObjectSub.put("clun01", MainActivity.enc_username);

                requestdata.put("apikey", "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R");
                requestdata.put("data", mJsonObjectSub);
                requestdata.put("requestType", "checkMediatorcode");

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

                if (response != null) {
                    String status = "", agency_unq_id = "", created_by_user_id = "", created_by_user_un = "";
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


                                    JSONObject UserInfoobj = new JSONObject(jsonObject.getString("data"));


                                    if (UserInfoobj.has("mediator_code"))
                                        mediator_code = UserInfoobj.getString("mediator_code");
                                    if (UserInfoobj.has("agency_unq_id"))
                                        agency_unq_id = UserInfoobj.getString("agency_unq_id");
                                    if (UserInfoobj.has("swuser_unq_id"))
                                        created_by_user_id = UserInfoobj.getString("swuser_unq_id");
                                    if (UserInfoobj.has("clmediatorun01"))
                                        created_by_user_un = UserInfoobj.getString("clmediatorun01");


                                    AppSession.save(ct, Constants.MEDIATOR_CODE, mediator_code);
                                    AppSession.save(ct, Constants.MEDIATOR_AGENCY_ID, agency_unq_id);
                                    AppSession.save(ct, Constants.MEDIATOR_SUPPORT_WORKER_ID, created_by_user_id);
                                    AppSession.save(ct, Constants.MEDIATOR_SUPPORT_WORKER_NAME, created_by_user_un);

                                    mediator_option.setVisibility(View.VISIBLE);

                                } else
                                    new ToastUtil(ct, "Please enter valid code.");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }


    private class GetMediatorData extends AsyncTask<String, String, Bitmap> {

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


                mJsonObjectSub.put("clun01", MainActivity.enc_username);

                requestdata.put("apikey", "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R");
                requestdata.put("data", mJsonObjectSub);
                requestdata.put("requestType", "getUsersMediatorCode");

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

                String status = "", agency_unq_id = "", created_by_user_id = "", created_by_user_un = "";
                if (response != null) {
                    try {
                        Object object = new JSONTokener(response).nextValue();
                        if (object instanceof JSONObject) {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.has("status"))
                                status = jsonObject.getString("status");

                            if (status != null && status.equalsIgnoreCase("true"))
                            {
                                String userinfo = "";

                                if (jsonObject.has("data"))
                                    userinfo = jsonObject.getString("data");

                                JSONArray json_array = null;
                                if (jsonObject.has("data"))
                                    json_array = jsonObject.getJSONArray("data");

                                for (int i = 0; i < json_array.length(); i++) {


                                    JSONObject UserInfoobj = new JSONObject(json_array.getString(i));


                                    if (UserInfoobj.has("mediator_code"))
                                        mediator_code = UserInfoobj.getString("mediator_code");
                                    if (UserInfoobj.has("agency_unq_id"))
                                        agency_unq_id = UserInfoobj.getString("agency_unq_id");
                                    if (UserInfoobj.has("swuser_unq_id"))
                                        created_by_user_id = UserInfoobj.getString("swuser_unq_id");
                                    if (UserInfoobj.has("clmediatorun01"))
                                        created_by_user_un = UserInfoobj.getString("clmediatorun01");


                                    AppSession.save(ct, Constants.MEDIATOR_CODE, mediator_code);
                                    AppSession.save(ct, Constants.MEDIATOR_AGENCY_ID, agency_unq_id);
                                    AppSession.save(ct, Constants.MEDIATOR_SUPPORT_WORKER_ID, created_by_user_id);
                                    AppSession.save(ct, Constants.MEDIATOR_SUPPORT_WORKER_NAME, created_by_user_un);

                                    mediator_option.setVisibility(View.VISIBLE);
                                }
                            }
                            else
                            {

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }


    private class GetChatEnaleData extends AsyncTask<String, String, Bitmap> {

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

                String medcloun = AppSession.getValue(ct, Constants.MEDIATOR_SUPPORT_WORKER_NAME);
                mJsonObjectSub.put("clmediatorun01", medcloun);

                requestdata.put("apikey", "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R");
                requestdata.put("data", mJsonObjectSub);
                requestdata.put("requestType", "getMediatorChatFlag");

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

                is_chat_clicked = false;

                progress_bar.setVisibility(View.GONE);

                String status = "";
                boolean is_chatting = false;
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


                                    JSONObject UserInfoobj = new JSONObject(json_array.getString(i));


                                    if (UserInfoobj.has("is_chatting"))
                                        is_chatting = UserInfoobj.getBoolean("is_chatting");

                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                if (is_chatting) {
                    Intent intent = new Intent(ct, MediatorChating.class);
                    startActivity(intent);
                } else {

                    AlertDialog.Builder updateAlert = new AlertDialog.Builder(ct);
                    updateAlert.setIcon(R.drawable.ic_launcher);
                    updateAlert.setTitle("Cypher");
                    updateAlert.setMessage("Support worker is not available.");
                    updateAlert.setPositiveButton(
                            "Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertUp = updateAlert.create();
                    alertUp.setCanceledOnTouchOutside(false);

                    alertUp.show();


                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

}
