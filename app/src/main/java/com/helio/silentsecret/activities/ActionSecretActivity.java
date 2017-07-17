package com.helio.silentsecret.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.helio.silentsecret.R;
import com.helio.silentsecret.WebserviceDTO.GetSecretbyIdDataDTO;
import com.helio.silentsecret.WebserviceDTO.GetSecretbyidDTO;
import com.helio.silentsecret.WebserviceDTO.GetSecretbyidObjectDTO;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.dialogs.AccessDialog;
import com.helio.silentsecret.models.Secret;
import com.helio.silentsecret.utils.Constants;
import com.helio.silentsecret.utils.ImageLoaderUtil;
import com.helio.silentsecret.utils.ToastUtil;

import java.util.List;


public class ActionSecretActivity extends BaseActivity {
    private ImageLoaderUtil mImageLoader;
    private String secretId;
    RelativeLayout progress_bar = null;
Context ct = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_secret);
        ct = this;
        mImageLoader = new ImageLoaderUtil(this);
        Bundle extras = getIntent().getExtras();
        progress_bar = (RelativeLayout) findViewById(R.id.progress_bar);
        if (extras != null)
            secretId = getIntent().getExtras().getString(Constants.PUSH_SECRET_ID);
        else
        {
            new ToastUtil(this, getString(R.string._error));
            runBack();
            return;
        }

        /*if (extras != null
                && extras.containsKey(Constants.PUSH_SECRET_ID)
                && !extras.getString(Constants.PUSH_SECRET_ID)
                .equals(ObjectMaker.EMPTY)) {
            secretId = getIntent().getExtras().getString(Constants.PUSH_SECRET_ID);
        } else {
            new ToastUtil(this, getString(R.string._error));
            runBack();
            return;
        }*/


      //  ((TextView) findViewById(R.id.action_secret_text)).setText(getString(R.string.access_required));
        ((TextView) findViewById(R.id.action_secret_text)).setText("");

        findViewById(R.id.action_secret_done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runBack();
            }
        });

        if(secretId!= null)
            new GetSecretByID().execute();
        /*if (getIntent().getExtras().getBoolean(Constants.ACCESS_SECRET_SHOW_DIALOG)) {
            replaceDialog();
        } else {
            loadData();
        }*/
    }

    private void loadImage(String back, ImageView view) {
        mImageLoader.loadSimpleDraw(back, view);
    }

    private void initViews(Secret object) {

        ((TextView) findViewById(R.id.action_secret_text)).setText(object.getText());
        ((TextView) findViewById(R.id.action_secret_hugs_count)).setText(String.valueOf(object.getHugs()));
        ((TextView) findViewById(R.id.action_secret_hearts_count)).setText(String.valueOf(object.getHearts()));
        ((TextView) findViewById(R.id.action_secret_me2s_count)).setText(String.valueOf(object.getMe2s()));
        loadImage(object.getBgImageName(), ((ImageView) findViewById(R.id.action_secret_image_back)));
    }

    @Override
    public void onBackPressed() {
        Fragment frag = getSupportFragmentManager().findFragmentById(R.id.action_content);
        if (frag instanceof AccessDialog) {
            super.onBackPressed();
        } else {
            runBack();
        }
    }

    private void replaceDialog() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        try {
            transaction.replace(R.id.action_content, AccessDialog.instance(Constants.ACTION_SECRETS_ACCESS));
        } catch (IllegalStateException e) {
        }
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

/*
    public void loadData() {
        new AccessSecretLoader(new AccessSecretCallback() {
            @Override
            public void onReceive(Secret item) {
                if (item != null)
                    initViews(item);
            }
        }).execute(secretId);
    }
*/

    public void runBack() {
        if (getIntent().getExtras() != null && getIntent().getExtras().getBoolean(Constants.ACCESS_SECRET_SHOW_DIALOG)) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        finish();
    }


    private class GetSecretByID extends android.os.AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String data;
        List<Secret> list;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress_bar.setVisibility(View.VISIBLE);

        }


        protected Bitmap doInBackground(String... args) {
            try {


                  IfriendRequest http = new IfriendRequest(ct);

                GetSecretbyidObjectDTO searchSecretObjectDTO = new GetSecretbyidObjectDTO(new GetSecretbyidDTO(new GetSecretbyIdDataDTO(secretId)));
                list = http.GetSecretbyID(searchSecretObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image)
        {
            try
            {
                progress_bar.setVisibility(View.GONE);
                if(list!= null && list.size()>0)
                    initViews(list.get(0));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


        }
    }

}
