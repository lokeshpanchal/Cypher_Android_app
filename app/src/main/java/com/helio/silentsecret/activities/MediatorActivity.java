package com.helio.silentsecret.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.helio.silentsecret.R;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.utils.KeyboardUtil;
import com.helio.silentsecret.utils.ToastUtil;

import org.json.JSONObject;

public class MediatorActivity extends AppCompatActivity {

    TextView back_iv = null;
    TextView submit_button = null;
    EditText edt_code = null;
    RelativeLayout mediator_option = null , progress_bar = null;
    Context ct = this;

    String mediator_code = "";
LinearLayout meeting_workshop = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mediator);
        back_iv = (TextView) findViewById(R.id.back_iv);
        edt_code = (EditText) findViewById(R.id.edt_code);
        submit_button = (TextView) findViewById(R.id.submit_button);
        mediator_option = (RelativeLayout) findViewById(R.id.mediator_option);
        progress_bar = (RelativeLayout) findViewById(R.id.progress_bar);
        meeting_workshop = (LinearLayout) findViewById(R.id.meeting_workshop);
        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        meeting_workshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ct,Mediator_meeting.class);
                startActivity(intent);
            }
        });
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mediator_code = edt_code.getText().toString().trim();
                if(mediator_code!= null && !mediator_code.equalsIgnoreCase("")) {
                    KeyboardUtil.hideKeyBoard(edt_code, ct);

                    new ValidateCode().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
                else
                    new ToastUtil(ct,"Please enter vaild code.");
            }
        });
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

                requestdata.put("apikey", "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R");
                requestdata.put("data", mJsonObjectSub);
                requestdata.put("requestType", "checkMediatorcode");

                main_object.put("requestData", requestdata);
                try {
                    httpRequest.UpdateUserFirstName(main_object.toString());
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
                mediator_option.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

}
