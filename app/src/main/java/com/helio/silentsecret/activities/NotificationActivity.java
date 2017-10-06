package com.helio.silentsecret.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.helio.silentsecret.R;
import com.helio.silentsecret.WebserviceDTO.FindbyNameDTO;
import com.helio.silentsecret.WebserviceDTO.GetAllNotificationObjecttDTO;
import com.helio.silentsecret.WebserviceDTO.GetAllNotificationjListDTO;
import com.helio.silentsecret.adapters.NotificationAdapter;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.dialogs.ProgressDialog;
import com.helio.silentsecret.dialogs.VerifyDialog;
import com.helio.silentsecret.models.Notification;
import com.helio.silentsecret.utils.Preference;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends BaseActivity implements View.OnClickListener {

    private ListView listView;
    private NotificationAdapter adapter;
    private List<Notification> mList;
    TextView back = null;
    Context ct = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        ct = this;
        initArray();
        initViews();
        new GetNotifications().execute();
      //  loadNotifications();

        startTracking(getString(R.string.analytics_Notification));
    }

    private void initArray() {
        if (Preference.getNotificationCheckout() == null)
            Preference.saveNotificationList(new JSONArray());
    }

    private void initViews() {

        listView = (ListView) findViewById(R.id.notifications_list_view);
        back = (TextView) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mList = new ArrayList<>();
        adapter = new NotificationAdapter(LayoutInflater.from(this), mList, this);
        listView.setAdapter(adapter);

        findViewById(R.id.notifications_home).setOnClickListener(this);
        findViewById(R.id.notifications_plus_toogle).setOnClickListener(this);
        findViewById(R.id.notifications_get_support).setOnClickListener(this);

    }

    /*private void loadNotifications() {
        showProgress();
        new NotificationsLoader(new NotificationsCallback() {
            @Override
            public void onReceive(List<Notification> data) {
                hideProgress();
                mList.clear();
                mList.addAll(data);
                adapter.notifyDataSetChanged();
            }
        }).execute();
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.notifications_home:
                finish();
                break;
            case R.id.notifications_get_support:
                startActivity(new Intent(NotificationActivity.this, SupportActivity.class));
                break;
            case R.id.notifications_plus_toogle:
                if(!MainActivity.is_flag)
                    startActivity(new Intent(this, CreateSecretActivity.class));
                else
                    Toast.makeText(ct, "You are not permitted to share a secret.", Toast.LENGTH_SHORT).show();

                break;
        }
    }

    public void showProgress() {
        ProgressDialog.showDialog(findViewById(R.id.notifications_progress_bg_iv), findViewById(R.id.notifications_progress_pb));
    }

    public void hideProgress() {
        ProgressDialog.hideDialog(findViewById(R.id.notifications_progress_bg_iv), findViewById(R.id.notifications_progress_pb));
    }

    public void replacePrompt() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        try {
            transaction.replace(R.id.notification_frame, new VerifyDialog());
        } catch (IllegalStateException e) {
        }
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }


    private class GetNotifications extends android.os.AsyncTask<String, String, Bitmap> {

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

                GetAllNotificationObjecttDTO findbynameObjectDTO = new GetAllNotificationObjecttDTO(new GetAllNotificationjListDTO(new FindbyNameDTO(MainActivity.enc_username)));
                data = http.GetAllNotificationList(findbynameObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {

            hideProgress();

            try {
                mList.clear();
                mList.addAll(data);
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


}
