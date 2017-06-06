/*
 * Basic no frills app which integrates the ZBar barcode scanner with
 * the camera.
 * 
 * Created by lisah0 on 2012-02-24
 */
package com.helio.cypher.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.helio.cypher.R;
import com.helio.cypher.appCounsellingDTO.FinalObjectDTO;
import com.helio.cypher.appCounsellingDTO.RedeemCounsellingCodeDTO;
import com.helio.cypher.appCounsellingDTO.RedeemCounsellingCodeeDataDTO;
import com.helio.cypher.connection.ConnectionDetector;
import com.helio.cypher.connection.IfriendRequest;
import com.helio.cypher.utils.AppSession;
import com.helio.cypher.utils.Constants;
import com.helio.cypher.utils.ToastUtil;


import net.sourceforge.zbar.ImageScanner;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/* Import ZBar Class files */

public class CameraTestActivity extends Activity {


    int qr_code = 0;
    boolean is_click = false;
    TextView scanText,qrstart,cancel_book;
    Button scanButton;
    Context ct = null;
    ImageScanner scanner;
    RedeemCounsellingCodeeDataDTO redeemCounsellingCodeeDataDTO = null;
    String agencyID = "";
int qrcout = 0;
    EditText edtcounselcode = null;
    private boolean barcodeScanned = false;
    private boolean previewing = true;
RelativeLayout qrstart_layout = null;

    String appcounsellcode = "";

  /*  static {

        System.loadLibrary("iconv");
        //System.load("/data/app/com.helio.silentsecret/lib/armeabi-v7a/iconv.so");
    }*/

    RelativeLayout progress_bar = null;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        qrstart_layout = (RelativeLayout)findViewById(R.id.qrstart_layout);
        qrstart = (TextView) findViewById(R.id.qrstart);
        edtcounselcode  = (EditText) findViewById(R.id.edtcounselcode);
        cancel_book  = (TextView) findViewById(R.id.cancel_book);
        cancel_book.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ct = this;

        qrstart.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                appcounsellcode = edtcounselcode.getText().toString();


                if(appcounsellcode!= null && !appcounsellcode.equalsIgnoreCase(""))
                {

                    try {
                        if (ConnectionDetector.isNetworkAvailable(ct) && is_click == false)
                        {
                            is_click = true;
                            appcounsellcode = appcounsellcode.trim();
                            redeemCounsellingCodeeDataDTO = new RedeemCounsellingCodeeDataDTO(MainActivity.enc_username,appcounsellcode);
                            new ReddemCounsellingCode().execute();

                        } else {
                            new ToastUtil(ct, "Please check your internet connection.");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else
                    new ToastUtil(ct,"Please enter code first.");

               // qrstart_layout.setVisibility(View.GONE);


            }
        });

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        try {


            progress_bar   = (RelativeLayout) findViewById(R.id.progress_bar);

            progress_bar.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private class ReddemCounsellingCode extends android.os.AsyncTask<String, String, Bitmap> {

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
                IfriendRequest http = new IfriendRequest(ct);

                RedeemCounsellingCodeDTO checkUserSessionDTO = new RedeemCounsellingCodeDTO(redeemCounsellingCodeeDataDTO);
                FinalObjectDTO finalObjectDTO = new FinalObjectDTO(checkUserSessionDTO);
                response =    http.RedeemCounsellingCode(finalObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image)
        {
            try {
                is_click = false;
                progress_bar.setVisibility(View.GONE);
                String status = "",qid = "", agency_unq_id = "", agent_unq_id = "", Session_left = "";
                Object object = new JSONTokener(response).nextValue();
                if (object instanceof JSONObject)
                {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.has("status"))
                        status = jsonObject.getString("status");
                    if(status != null && status.equalsIgnoreCase("true"))
                    {
                        JSONArray json_array = null;
                        if (jsonObject.has("data"))
                            json_array = jsonObject.getJSONArray("data");

                        for (int i = 0; i < json_array.length(); i++)
                        {

                            JSONObject UserInfoobj = new JSONObject(json_array.getString(i));

                            if (UserInfoobj.has("qid"))
                                qid = UserInfoobj.getString("qid");

                            if (UserInfoobj.has("agency_unq_id"))
                                agency_unq_id = UserInfoobj.getString("agency_unq_id");

                            if (UserInfoobj.has("agent_unq_id"))
                                agent_unq_id = UserInfoobj.getString("agent_unq_id");

                            if (UserInfoobj.has("session"))
                                Session_left = UserInfoobj.getString("session");


                            AppSession.save(ct, Constants.USED_QR_CODE, qid);
                            AppSession.save(ct, Constants.AGENCY_ID,agency_unq_id );
                            AppSession.save(ct, Constants.AGENT_UNIQUE_ID,agent_unq_id );
                            AppSession.save(ct, Constants.USER_SESSION_LEFT,Session_left );

                            try
                            {
                                qrcout = Integer.parseInt(Session_left);
                                MainActivity.session_left = qrcout;
                            }
                            catch (Exception e)
                            {

                            }

                            AlertDialog.Builder senderFriends = new AlertDialog.Builder(ct);
                            if(qrcout>1)
                                senderFriends.setMessage("Congratulations! You have credited "+qrcout + " sessions");
                            else
                                senderFriends.setMessage("Congratulations! You have credited "+qrcout + " session");

                            senderFriends.setTitle("Cypher");
                            senderFriends.setIcon(R.drawable.ic_launcher);
                            senderFriends.setCancelable(true);
                            senderFriends.setPositiveButton(
                                    "Ok",
                                    new DialogInterface.OnClickListener()
                                    {
                                        public void onClick(DialogInterface dialog, int id)
                                        {
                                            Intent intent = new Intent(ct, CounsellerBooking.class);
                                            startActivity(intent);
                                            finish();
                                            dialog.cancel();
                                        }
                                    });

                            AlertDialog alert1senderFriends = senderFriends.create();
                            alert1senderFriends.show();


                        }
                    }
                    else
                    {
                        new ToastUtil(ct, jsonObject.getString("error"));
                    }
                }

            } catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }

}
