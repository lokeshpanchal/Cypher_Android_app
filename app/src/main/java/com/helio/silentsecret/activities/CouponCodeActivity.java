package com.helio.silentsecret.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.helio.silentsecret.utils.ToastUtil;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by ABC on 10/19/2016.
 */
public class CouponCodeActivity extends Activity {


    TextView cancel_coupon_code = null;
    EditText couponCode_et = null;
    TextView coupon_submit_btn = null;
    RelativeLayout main = null;
    private List<School> schools;
    Context ct = null;
    SeenbyCouponCodeDTO seenbyCouponCodeDTO = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coupon_code);
        ct = this;
        cancel_coupon_code = (TextView) findViewById(R.id.cancel_coupon_code);
        couponCode_et = (EditText) findViewById(R.id.couponCode_et);
        coupon_submit_btn = (TextView) findViewById(R.id.coupon_submit_btn);
        main = (RelativeLayout) findViewById(R.id.main);
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
                showProgress();

                checkForSchool(couponCode_et.getText().toString());

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

                for (SchoolDTO item : MainActivity.stataicObjectDTO.getSchoolDTOs())
                {

                    if (item.getCode() != null && !item.getCode().equalsIgnoreCase(""))
                    {
                       // Pattern pattern = Pattern.compile(Pattern.quote(item.getCode()), Pattern.CASE_INSENSITIVE);
                       // Matcher match = pattern.matcher(text);
                        if (text.equalsIgnoreCase(item.getCode()))
                        {
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

            List<String> userara = item.getUsers();
            if (userara != null && userara.size() > 0)
                userara.add(MainActivity.enc_username);
            else {
                userara = new ArrayList<>();
                userara.add(MainActivity.enc_username);
            }
            if (seenbyCouponCodeDTO != null)
                seenbyCouponCodeDTO = null;

            seenbyCouponCodeDTO = new SeenbyCouponCodeDTO(item.getCode(), userara);

            new SendCouponCode().execute();

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
            new ToastUtil(ct, "Congratulation your code worked, you will now be in a prize draw!");
        }
    }


}
