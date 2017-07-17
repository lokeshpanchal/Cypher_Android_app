package com.helio.silentsecret.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.helio.silentsecret.EncryptionDecryption.CryptLib;
import com.helio.silentsecret.R;
import com.helio.silentsecret.WebserviceDTO.GetPetConditionDTO;
import com.helio.silentsecret.WebserviceDTO.GetPetInfoDTO;
import com.helio.silentsecret.WebserviceDTO.GetPetInfoObjectDTO;
import com.helio.silentsecret.WebserviceDTO.PetAvtarInfoDTO;
import com.helio.silentsecret.WebserviceDTO.StaticDataDTO;
import com.helio.silentsecret.WebserviceDTO.StaticObjectDTO;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.utils.AppSession;
import com.helio.silentsecret.utils.Constants;
import com.helio.silentsecret.utils.ImageLoaderUtil;

public class PetAvtarActivity extends BaseActivity implements View.OnClickListener {

    private ImageLoaderUtil mImageUtil;
    private TextView mYears;

    private TextView mMonth , quotoes= null,autohor_name = null;

    LinearLayout doblayout = null;
    RelativeLayout progress_bar = null;
    Context ct = null;
    ImageView stats_progress_bg_iv = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_activity);
        try {
            ct = this;
            initViews();

            quotoes = (TextView) findViewById(R.id.quotoes);
            autohor_name = (TextView) findViewById(R.id.autohor_name);
            progress_bar  = (RelativeLayout) findViewById(R.id.progress_bar);
            progress_bar.setVisibility(View.VISIBLE);


            startTracking(getString(R.string.analytics_PetAvtaar));

            new GetPetInfo().execute();



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initViews() throws Exception {

        findViewById(R.id.stats_close).setOnClickListener(this);

    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.stats_close:
                AppSession.save(this, Constants.COMMENT_SECRET_ID,"");
                Intent intent = new Intent(PetAvtarActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }



    private class GetPetInfo extends AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String status = "";
        Bitmap bitmap;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Bitmap doInBackground(String... args) {
            try {

                if(MainActivity.stataicObjectDTO!= null)
                    MainActivity.stataicObjectDTO = null;

                IfriendRequest http = new IfriendRequest(ct);
                StaticDataDTO staticDataDTO = new StaticDataDTO();
                StaticObjectDTO staticObjectDTO = new StaticObjectDTO(staticDataDTO);
                MainActivity.stataicObjectDTO = http.GetstaticData(staticObjectDTO);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image)
        {
            try {

                if(  MainActivity.stataicObjectDTO!= null)
                {
                    ShowPetStatus();
                    findViewById(R.id.stats_close).setVisibility(View.VISIBLE);

                    progress_bar.setVisibility(View.GONE);
                }
                else
                {
                    AppSession.save(ct, Constants.COMMENT_SECRET_ID,"");
                    Intent intent = new Intent(PetAvtarActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }




    private void ShowPetStatus()
    {

        try
        {
            quotoes.setText( "\"" + MainActivity.stataicObjectDTO.getiFriendSettingDTO().getQuotes() +  "\"");
            autohor_name.setText(MainActivity.stataicObjectDTO.getiFriendSettingDTO().getAuthor_name());
        }
        catch (Exception e)
        {

        }


    }



}
