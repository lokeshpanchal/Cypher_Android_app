package com.helio.silentsecret.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.helio.silentsecret.R;
import com.helio.silentsecret.connection.ConnectionDetector;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.utils.AppSession;
import com.helio.silentsecret.utils.CommonFunction;
import com.helio.silentsecret.utils.Constants;
import com.helio.silentsecret.utils.KeyboardUtil;
import com.helio.silentsecret.utils.ToastUtil;

import org.json.JSONObject;


public class OtherCountryUserActivity extends FragmentActivity implements View.OnClickListener {


    private EditText mUsername;
    private TextView mPin = null, back_iv = null;
    Context ct = this;

    String email = "", country = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_country_user);
        initViews();

    }

    private void initViews() {

        try {

            mUsername = (EditText) findViewById(R.id.login_username_input);
            mPin = (TextView) findViewById(R.id.login_pin_input);
            back_iv = (TextView) findViewById(R.id.back_iv);

            mPin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mUsername != null)
                        KeyboardUtil.hideKeyBoard(mUsername, ct);
                    Usercountylist();
                }
            });

            back_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            findViewById(R.id.login_in_btn).setOnClickListener(this);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mUsername != null)
            KeyboardUtil.hideKeyBoard(mUsername, this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.login_in_btn:
                runLogin();
                break;


        }
    }


    private void runLogin() {

        if (!ConnectionDetector.isNetworkAvailable(this)) {
            new ToastUtil(this, "Please check your internet connection.");
        } else {

            if (mUsername.getText().toString().isEmpty())
                return;

            email = mUsername.getText().toString().trim();
            if (!CommonFunction.isValidEmail(email)) {
                new ToastUtil(ct, "Please enter valid email.");
            } else if (country == null || country.equalsIgnoreCase("")) {
                new ToastUtil(ct, "Please choose your country");
            } else {
                new GetPrevMeeting().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }


        }

    }


    private class GetPrevMeeting extends AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;

        String data = "0";
        Bitmap bitmap;


        String response = "";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest httpRequest = new IfriendRequest(ct);
                JSONObject mJsonObjectSub = new JSONObject();
                JSONObject requestdata = new JSONObject();
                JSONObject main_object = new JSONObject();
                String mediat0r_name = AppSession.getValue(ct, Constants.MEDIATOR_SUPPORT_WORKER_NAME);
                mJsonObjectSub.put("email", email);
                mJsonObjectSub.put("country", country);
                mJsonObjectSub.put("clun01", MainActivity.enc_username);

                requestdata.put("apikey", "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R");
                requestdata.put("data", mJsonObjectSub);
                requestdata.put("requestType", "sendAppcounsellingRequest");

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

        protected void onPostExecute(Bitmap image)
        {
            new ToastUtil(ct, "Your appCounselling request has been submitted successfully.");
            back_iv.postDelayed(new Runnable() {
                @Override
                public void run() {
                    AppSession.save(ct,Constants.APPCOUNSELLING_REQUEST,"true");
                    finish();
                }
            }, 2500);


        }
    }


    private void Usercountylist() {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(ct);
        builderSingle.setTitle("Select Your country ");

        // final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ct, android.R.layout.select_dialog_singlechoice);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ct, android.R.layout.simple_list_item_single_choice);

        String[] arrContryCode = getResources().getStringArray(R.array.DialingCountryCode);
        for (int i = 0; i < arrContryCode.length; i++) {
            arrayAdapter.add(arrContryCode[i]);

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
                final String strName = arrayAdapter.getItem(which);
                AlertDialog.Builder builderInner = new AlertDialog.Builder(ct);
                builderInner.setMessage(strName);
                builderInner.setTitle("Your Selected Ccountry is");
                builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mPin.setText(strName);
                        country = strName;
                        //  Usercode = strName;
                        //  Usercode = Usercode.replaceAll("[^0-9]", "");
                        // usercountrycode.setText(strName);
                    }
                });
                builderInner.show();
            }
        });
        builderSingle.show();
    }


}
