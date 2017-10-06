package com.helio.silentsecret.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.helio.silentsecret.R;
import com.helio.silentsecret.WebserviceDTO.SetVerifyFlagConditionDTO;
import com.helio.silentsecret.WebserviceDTO.SetVerifyFlagDTO;
import com.helio.silentsecret.WebserviceDTO.SetVerifyFlagObjectDTO;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.dialogs.AccessDialog;
import com.helio.silentsecret.utils.AppSession;
import com.helio.silentsecret.utils.Constants;

public class SettingsActivity extends AppCompatActivity {
    TextView back = null , stay_safe = null, clear_post = null,unverify_me = null,invite_friends = null,ruby_code = null,
            rate_your_school = null;
    Context ct = null;
    FrameLayout main_content = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ct = this;
        back = (TextView) findViewById(R.id.back);
        stay_safe = (TextView) findViewById(R.id.stay_safe);
        clear_post = (TextView) findViewById(R.id.clear_post);
        unverify_me = (TextView) findViewById(R.id.unverify_me);
        invite_friends = (TextView) findViewById(R.id.invite_friends);
        ruby_code = (TextView) findViewById(R.id.ruby_code);
        rate_your_school = (TextView) findViewById(R.id.rate_your_school);
        main_content  = (FrameLayout) findViewById(R.id.main_content);
        stay_safe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ct,PrivatePolicyActivity.class));
            }
        });
        clear_post.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(ct,ClearMySecretsActivity.class));
            }
        });
        unverify_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_content.setVisibility(View.VISIBLE);
                UnverifyMeDialog();

            }
        });
        invite_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ct,ReferFriendActivity.class));
            }
        });
        ruby_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ct,CouponCodeActivity.class));
            }
        });
        rate_your_school.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ct,RateSchoolActivity.class));
            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void clearDataDialog() {
        replaceDialog(Constants.ACCESS_CLEAR_SECRET);
    }

    private void UnverifyMeDialog() {
        replaceDialog(Constants.ACCESS_UNVERIFY_ME);
    }


    public void replaceDialog(String frag) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        try {
            transaction.replace(R.id.main_content, AccessDialog.instance(frag));
            transaction.addToBackStack(null);
            transaction.commitAllowingStateLoss();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        main_content.setVisibility(View.GONE);
    }


    public void runMineSecrets(boolean flag) {
        Intent intent = new Intent(this, MySecretsActivity.class);

        if (!flag)
            intent.putExtra(Constants.ACCESS_CLEAR_SECRET, true);

        startActivity(intent);
    }

    public void unverifyUser() {

        if (MainActivity.setVerifyFlagConditionDTO != null)
            MainActivity.setVerifyFlagConditionDTO = null;
        MainActivity.setVerifyFlagConditionDTO = new SetVerifyFlagConditionDTO(MainActivity.enc_username, "verified", "false");
        new UpdateVeryfiFlag().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        AppSession.save(ct, Constants.USER_VERIFIED, "false");

    }



    private  class UpdateVeryfiFlag extends AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String status = "";
        Bitmap bitmap;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest http = new IfriendRequest(ct);

                SetVerifyFlagDTO setVerifyFlagDTO = new SetVerifyFlagDTO(MainActivity.setVerifyFlagConditionDTO);
                SetVerifyFlagObjectDTO setVerifyFlagObjectDTO = new SetVerifyFlagObjectDTO(setVerifyFlagDTO);
                http.UpdateFlagVeryfi(setVerifyFlagObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


}
