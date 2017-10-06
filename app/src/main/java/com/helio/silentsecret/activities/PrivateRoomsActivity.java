package com.helio.silentsecret.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.helio.silentsecret.R;
import com.helio.silentsecret.adapters.RoomListAdapter;
import com.helio.silentsecret.application.SilentSecretApplication;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.dialogs.ProgressDialog;
import com.helio.silentsecret.models.RoomsInfoDTO;
import com.helio.silentsecret.models.School;
import com.helio.silentsecret.utils.AppSession;
import com.helio.silentsecret.utils.Constants;
import com.helio.silentsecret.utils.KeyboardUtil;
import com.helio.silentsecret.utils.ToastUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ABC on 10/19/2016.
 */
public class PrivateRoomsActivity extends BaseActivity {


    TextView cancel_coupon_code = null , add_room = null;
    EditText couponCode_et = null;
    TextView room_submit_btn = null, cancel_code = null;
    //RelativeLayout main = null;
    private List<School> schools;
    Context ct = null;
String room_code = "";
    List<RoomsInfoDTO> roomsInfoDTOs = new ArrayList<>();
    ListView private_room_list = null;

List<String> room_code_list = new ArrayList<>();
    RelativeLayout private_room_layout = null;
    LinearLayout enter_code_layout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.private_room_activity);
        ct = this;
        cancel_coupon_code = (TextView) findViewById(R.id.cancel_coupon_code);
        couponCode_et = (EditText) findViewById(R.id.couponCode_et);
        room_submit_btn = (TextView) findViewById(R.id.room_submit_btn);
        cancel_code = (TextView) findViewById(R.id.cancel_code);
        add_room = (TextView) findViewById(R.id.add_room);
        enter_code_layout = (LinearLayout) findViewById(R.id.enter_code_layout);
        private_room_list = (ListView) findViewById(R.id.private_room_list);
        private_room_layout = (RelativeLayout) findViewById(R.id.private_room_layout);
        findViewById(R.id.main_progress_bg_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cancel_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enter_code_layout.setVisibility(View.GONE);
            }
        });

        add_room.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                enter_code_layout.setVisibility(View.VISIBLE);
            }
        });

        startTracking(getString(R.string.analytics_CouponCode));

        cancel_coupon_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        room_submit_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                //  showProgress();

                room_code = couponCode_et.getText().toString();

                if (!room_code.isEmpty())
                {

                    if(room_code_list!= null && room_code_list.size()>0)
                    {
                        if(room_code_list.contains(room_code))
                        {
                            new ToastUtil(ct,"Code already used.");
                        }
                        else
                        {
                            couponCode_et.setText("");
                            enter_code_layout.setVisibility(View.GONE);
                            KeyboardUtil.hideKeyBoard(room_submit_btn, ct);
                            new Checkroomconde().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        }
                    }
                    else {
                        couponCode_et.setText("");
                        enter_code_layout.setVisibility(View.GONE);
                        KeyboardUtil.hideKeyBoard(room_submit_btn, ct);
                        new Checkroomconde().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    }
                }
                else
                    new ToastUtil(ct,"Please enter room code");
            }
        });


        private_room_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    RoomDetailActivity.roomsInfoDTO = roomsInfoDTOs.get(i);
                    Intent intent = new Intent(ct, RoomDetailActivity.class);
                    startActivity(intent);
                } catch (Exception e)

                {
                    e.printStackTrace();

                }
            }
        });
        new GetPrivateRooms().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void showProgress() {
        ProgressDialog.showDialog(findViewById(R.id.main_progress_bg_iv), findViewById(R.id.main_progress_pb));
    }

    private void stopProgress() {
        ProgressDialog.hideDialog(findViewById(R.id.main_progress_bg_iv), findViewById(R.id.main_progress_pb));
    }


    protected void startTracking(String path) {
        Tracker t = ((SilentSecretApplication) getApplication()).getTracker(
                SilentSecretApplication.TrackerName.APP_TRACKER);
        t.setScreenName(path);
        t.send(new HitBuilders.AppViewBuilder().build());
    }


    private class Checkroomconde extends android.os.AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String data = "0";
        Bitmap bitmap;


        String response = "";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress();

        }

        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest httpRequest = new IfriendRequest(ct);

                String age = AppSession.getValue(ct, Constants.USER_AGE);


                JSONObject mJsonObjectSub = new JSONObject();
                JSONObject requestdata = new JSONObject();
                JSONObject main_object = new JSONObject();


                mJsonObjectSub.put("ruby_code", room_code);
                mJsonObjectSub.put("clun01", MainActivity.enc_username);
                requestdata.put("apikey", "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R");
                requestdata.put("data", mJsonObjectSub);
                requestdata.put("requestType", "checkRoomCode");

                main_object.put("requestData", requestdata);
                try {
                    roomsInfoDTOs = httpRequest.GetRooms(main_object.toString());
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
                stopProgress();

                try {
                    if(roomsInfoDTOs!= null && roomsInfoDTOs.size()>0)
                    {

                       new GetPrivateRooms().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    }
                    else {
                        enter_code_layout.setVisibility(View.VISIBLE);
                        new ToastUtil(ct,"Please enter valid room code");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }




    private class GetPrivateRooms extends android.os.AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String data = "0";
        Bitmap bitmap;


        String response = "";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress();

        }

        protected Bitmap doInBackground(String... args) {
            try {

                room_code_list.clear();
                IfriendRequest httpRequest = new IfriendRequest(ct);

                String age = AppSession.getValue(ct, Constants.USER_AGE);


                JSONObject mJsonObjectSub = new JSONObject();
                JSONObject requestdata = new JSONObject();
                JSONObject main_object = new JSONObject();

                mJsonObjectSub.put("clun01", MainActivity.enc_username);
                requestdata.put("apikey", "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R");
                requestdata.put("data", mJsonObjectSub);
                requestdata.put("requestType", "getUserPrivateRoom");

                main_object.put("requestData", requestdata);
                try {
                    roomsInfoDTOs = httpRequest.GetRooms(main_object.toString());
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
                stopProgress();

                try {
                    if(roomsInfoDTOs!= null && roomsInfoDTOs.size()>0)
                    {
                        for(int i=0; i < roomsInfoDTOs.size(); i++)
                        {
                            room_code_list.add(roomsInfoDTOs.get(i).getRoom_code());
                        }

                        RoomListAdapter roomListAdapter = new RoomListAdapter(ct, roomsInfoDTOs);
                        private_room_list.setAdapter(roomListAdapter);
                        private_room_layout.setVisibility(View.VISIBLE);

                    }
                    else {
                        enter_code_layout.setVisibility(View.VISIBLE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }


}
