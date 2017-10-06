package com.helio.silentsecret.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.helio.silentsecret.R;
import com.helio.silentsecret.WebserviceDTO.GetSecretbyIdDataDTO;
import com.helio.silentsecret.WebserviceDTO.GetSecretbyidDTO;
import com.helio.silentsecret.WebserviceDTO.GetSecretbyidObjectDTO;
import com.helio.silentsecret.adapters.NotificationSecretAdapter;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.dialogs.AccessDialog;
import com.helio.silentsecret.models.Secret;
import com.helio.silentsecret.utils.Constants;
import com.helio.silentsecret.utils.ImageLoaderUtil;
import com.helio.silentsecret.utils.ToastUtil;
import com.nirhart.parallaxscroll.views.ParallaxListView;

import java.util.List;


public class ActionSecretActivity extends BaseActivity {
    private ImageLoaderUtil mImageLoader;
    private String secretId;
    NotificationSecretAdapter adapter;
    private ParallaxListView mListView;
    RelativeLayout progress_bar = null;
Context ct = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_secret);
        ct = this;
        mListView = (ParallaxListView) findViewById(R.id.feed_list_view);

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
      //  ((TextView) findViewById(R.id.action_secret_text)).setText("");

        findViewById(R.id.action_secret_done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runBack();
            }
        });

        if(secretId!= null)
            new GetSecretByID().execute();

    }


    private void loadImage(String back, ImageView view) {
        mImageLoader.loadSimpleDraw(back, view);
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

    public void showprogress()
    {
        progress_bar.setVisibility(View.VISIBLE);
    }

    public void hideprogress()
    {
        progress_bar.setVisibility(View.GONE);
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
                {
                    adapter = new NotificationSecretAdapter(LayoutInflater.from(ct), list, ct);
                    mListView.setAdapter(adapter);
                }

                findViewById(R.id.action_secret_done).setVisibility(View.VISIBLE);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


        }
    }

}
