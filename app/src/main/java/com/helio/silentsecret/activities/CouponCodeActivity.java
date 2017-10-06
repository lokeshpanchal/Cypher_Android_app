package com.helio.silentsecret.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.helio.silentsecret.R;
import com.helio.silentsecret.StaticObjectDTO.SchoolDTO;
import com.helio.silentsecret.WebserviceDTO.SeenbyCouponCodeDTO;
import com.helio.silentsecret.WebserviceDTO.SendCouponCodeObjectDTO;
import com.helio.silentsecret.WebserviceDTO.SendCouponDTO;
import com.helio.silentsecret.application.SilentSecretApplication;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.dialogs.ProgressDialog;
import com.helio.silentsecret.models.Notification;
import com.helio.silentsecret.models.School;
import com.helio.silentsecret.utils.AppSession;
import com.helio.silentsecret.utils.CommonFunction;
import com.helio.silentsecret.utils.Constants;
import com.helio.silentsecret.utils.KeyboardUtil;
import com.helio.silentsecret.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ABC on 10/19/2016.
 */
public class CouponCodeActivity extends Activity {


    TextView cancel_coupon_code = null;
    EditText couponCode_et = null, edt_search = null;
    TextView coupon_submit_btn = null;
    RelativeLayout main = null, Email_layout = null;
    LinearLayout ruby_code_layout = null;
    private List<School> schools;
    Context ct = null;
    String email = "", code = "";

    List<String> userara;
    SeenbyCouponCodeDTO seenbyCouponCodeDTO = null;
    TextView submit_button = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coupon_code);
        ct = this;
        cancel_coupon_code = (TextView) findViewById(R.id.cancel_coupon_code);
        couponCode_et = (EditText) findViewById(R.id.couponCode_et);
        edt_search = (EditText) findViewById(R.id.edt_search);
        coupon_submit_btn = (TextView) findViewById(R.id.coupon_submit_btn);
        submit_button = (TextView) findViewById(R.id.submit_button);
        main = (RelativeLayout) findViewById(R.id.main);
        Email_layout = (RelativeLayout) findViewById(R.id.Email_layout);
        ruby_code_layout = (LinearLayout) findViewById(R.id.ruby_code_layout);

        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //   loadSchools();

        findViewById(R.id.main_progress_bg_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        edt_search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() > 0) {
                    submit_button.setText("Submit");
                } else
                    submit_button.setText("Cancel");

            }
        });

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = edt_search.getText().toString().trim();
                if (email != null && !email.equalsIgnoreCase("")) {
                    if (CommonFunction.isValidEmail(email)) {
                        AppSession.save(ct, Constants.USER_EMAIL, email);

                        showProgress();
                        if (seenbyCouponCodeDTO != null)
                            seenbyCouponCodeDTO = null;
                        seenbyCouponCodeDTO = new SeenbyCouponCodeDTO(code, userara, email);

                        new SendCouponCode().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        Email_layout.setVisibility(View.GONE);
                    } else
                        Toast.makeText(ct, "Please enter valid email id.", Toast.LENGTH_SHORT).show();


                } else {
                    Email_layout.setVisibility(View.GONE);
                    showProgress();
                    new SendCouponCode().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    KeyboardUtil.hideKeyBoard(edt_search, ct);
                }


            }
        });


        startTracking(getString(R.string.analytics_CouponCode));

        cancel_coupon_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        coupon_submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text = couponCode_et.getText().toString().trim();
                if(text!= null && !text.equalsIgnoreCase(""))
                checkForSchool(couponCode_et.getText().toString());
                else
                    Toast.makeText(ct,"Please enter the code.", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void showProgress() {
        ProgressDialog.showDialog(findViewById(R.id.main_progress_bg_iv), findViewById(R.id.main_progress_pb));
    }

    private void stopProgress() {
        ProgressDialog.hideDialog(findViewById(R.id.main_progress_bg_iv), findViewById(R.id.main_progress_pb));
    }


    private String checkForSchool(String text) {

        boolean isMatch = false;
        if (MainActivity.stataicObjectDTO != null && MainActivity.stataicObjectDTO.getSchoolDTOs() != null) {
           /* if (schools == null || schools.isEmpty())
                return text;*/
            try {

                for (SchoolDTO item : MainActivity.stataicObjectDTO.getSchoolDTOs()) {

                    if (item.getCode() != null && !item.getCode().equalsIgnoreCase("")) {
                        // Pattern pattern = Pattern.compile(Pattern.quote(item.getCode()), Pattern.CASE_INSENSITIVE);
                        // Matcher match = pattern.matcher(text);
                        if (text.equalsIgnoreCase(item.getCode())) {
                            isMatch = true;
                            addSchool(item);
                          /*  String find = match.group();
                            text = text.replace(find, "");*/
                            return text;
                        }

                    }
                }

                if (!isMatch) {
                    stopProgress();
                    new ToastUtil(this, "Invalid Ruby Code.");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return text;
    }

    private void addSchool(final SchoolDTO item) {

        try {


            for (String userItem : item.getUsers()) {
                if (userItem.equals(MainActivity.enc_username)) {
                    stopProgress();
                    new ToastUtil(this, "Ruby Code already submitted by you");
                    couponCode_et.setText("");
                    return;
                }
            }

            userara = item.getUsers();
            if (userara != null && userara.size() > 0)
                userara.add(MainActivity.enc_username);
            else {
                userara = new ArrayList<>();
                userara.add(MainActivity.enc_username);
            }


            code = item.getCode();

            email = AppSession.getValue(ct, Constants.USER_EMAIL);
            if (seenbyCouponCodeDTO != null)
                seenbyCouponCodeDTO = null;
            seenbyCouponCodeDTO = new SeenbyCouponCodeDTO(code, userara, email);
            new SendCouponCode().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
              /*ruby_code_layout.setVisibility(View.GONE);

            AlertDialog.Builder updateAlert = new AlertDialog.Builder(ct);
            updateAlert.setIcon(R.drawable.ic_launcher);
            updateAlert.setTitle("Cypher");
            updateAlert.setMessage("Congratulation your code worked, you will now be in a prize draw! \n Please provide your email id.");
            updateAlert.setPositiveButton(
                    "Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        if(email == null || email.equalsIgnoreCase(""))
                        {
                            Email_layout.setVisibility(View.VISIBLE);
                        }
                            else
                            ruby_code_layout.setVisibility(View.VISIBLE);



                        }
                    });
            AlertDialog alertUp = updateAlert.create();
            alertUp.setCanceledOnTouchOutside(false);
            alertUp.show();*/



           /* schools.add(item.getObject());
            if (user.get(Constants.USER_SCHOOLS) == null) {
                user.put(Constants.USER_SCHOOLS, schools);
            } else

            {
                List<ParseObject> userSchools = (ArrayList<ParseObject>) user.get(Constants.USER_SCHOOLS);
                userSchools.add(item.getObject());
                user.put(Constants.USER_SCHOOLS, userSchools);
            }

            user.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    saveUserSchool(item);
                }
            });*/


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


/*
    private void saveUserSchool(School item) {
        try {
            ParseObject object = item.getObject();
            List<String> users = new ArrayList<>();
            users.add(Preference.getUserName());
            object.addAll(Constants.SCHOOL_USERS, users);
            object.saveInBackground();
            couponCode_et.setText("");
            new ToastUtil(this, "Thank you for using a coupon code");
            stopProgress();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/

    protected void startTracking(String path) {
        Tracker t = ((SilentSecretApplication) getApplication()).getTracker(
                SilentSecretApplication.TrackerName.APP_TRACKER);
        t.setScreenName(path);
        t.send(new HitBuilders.AppViewBuilder().build());
    }


    private class SendCouponCode extends android.os.AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        List<Notification> data;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            showProgress();
        }


        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest http = new IfriendRequest(ct);

                SendCouponCodeObjectDTO findbynameObjectDTO = new SendCouponCodeObjectDTO(new SendCouponDTO(seenbyCouponCodeDTO));
                http.SendCoupon(findbynameObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {

            stopProgress();
            couponCode_et.setText("");
            //  ruby_code_layout.setVisibility(View.VISIBLE);


            Toast.makeText(ct, "Congratulation your code worked, you will now be in a prize draw!", Toast.LENGTH_SHORT).show();


        }
    }


}
