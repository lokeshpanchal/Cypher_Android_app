package com.helio.silentsecret.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.helio.silentsecret.EncryptionDecryption.CryptLib;
import com.helio.silentsecret.R;
import com.helio.silentsecret.Services.NotificationService;
import com.helio.silentsecret.Services.SinchService;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.fragments.GlimpseFragment;
import com.helio.silentsecret.models.GetCurrentDateObjectDTO;
import com.helio.silentsecret.models.GetCurrentDateTimeDTO;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallEndCause;
import com.sinch.android.rtc.calling.CallListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class AudioCallScreenActivity extends SinchBaseActivity {

    static final String TAG = CallScreenActivity.class.getSimpleName();

    private AudioPlayer mAudioPlayer;
    private Timer mTimer;
    private UpdateCallDurationTask mDurationTask;
    Context ct = null;
    Date currentdatetime = null;
    private String mCallId;
    private long mCallStart = 0;
    public static long remain_minute = 100, remain_second = 60;
    private TextView mCallDuration, show_counsel_timer = null;
    private TextView mCallState;
    private TextView mCallerName;

    private class UpdateCallDurationTask extends TimerTask {

        @Override
        public void run() {
            AudioCallScreenActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateCallDuration();
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_callscreen);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mAudioPlayer = new AudioPlayer(this);
        mCallDuration = (TextView) findViewById(R.id.callDuration);
        mCallerName = (TextView) findViewById(R.id.remoteUser);
        mCallState = (TextView) findViewById(R.id.callState);
        show_counsel_timer = (TextView) findViewById(R.id.show_counsel_timer);
        Button endCallButton = (Button) findViewById(R.id.hangupButton);
        ct = this;
        mCallDuration.setVisibility(View.GONE);
        show_counsel_timer.setVisibility(View.GONE);
        endCallButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                endCall();
            }
        });
      // mCallStart = System.currentTimeMillis();
        mCallId = getIntent().getStringExtra(SinchService.CALL_ID);

      //  new GetCurrentDateTime().execute();

        if(IncomingCallScreenActivity.is_incoming_call)
        {

        }
    }

    @Override
    public void onServiceConnected() {
        try
        {
            Call call = getSinchServiceInterface().getCall(mCallId);
            if (call != null) {
                NotificationService.runningcall = call;
                call.addCallListener(new SinchCallListener());
                if(LiveCounselling.counsellor_firstname!= null && !LiveCounselling.counsellor_firstname.equalsIgnoreCase(""))
                    mCallerName.setText(CryptLib.decrypt(LiveCounselling.counsellor_firstname));
                else
                    mCallerName.setText("Counsellor");
                mCallState.setText("Calling...");
            } else {
                Log.e(TAG, "Started with invalid callId, aborting.");
                finish();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        mDurationTask.cancel();
        mTimer.cancel();
    }

    @Override
    public void onResume() {
        super.onResume();
        mTimer = new Timer();
        mDurationTask = new UpdateCallDurationTask();
        mTimer.schedule(mDurationTask, 0, 1000);
        mCallStart =0;
        new GetCurrentDateTime().execute();
    }

    @Override
    public void onBackPressed() {
        // User should exit activity by ending call, not by going back.
    }

    private void endCall() {
        mAudioPlayer.stopProgressTone();
        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            call.hangup();
        }
        finish();
    }

    long showmin = 0,showsec =0,facsec =0,connectingcounter = 0;
boolean drc_min = false;
    private String formatTimespan(long timespan)
    {

        long totalSeconds = timespan / 1000;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;

        if(remain_second - seconds >= 0 )
        {
            showsec = remain_second - seconds;
        }
        else if( seconds <= 59)
        {
            showsec = (60 + remain_second) - seconds;
        }

        if(showsec!=0)
            drc_min = false;

        if(showsec <=0 && drc_min == false)
        {
            drc_min = true;
            if(remain_minute !=0) {
                remain_minute = remain_minute - 1;
                showsec = 59;
            }
        }
        if(remain_minute ==0 && showsec == 0)
            endCall();

        if(remain_minute<0)
            endCall();

        show_counsel_timer.setText(String.format(Locale.US, "%02d:%02d", remain_minute, showsec));



        return String.format(Locale.US, "%02d:%02d", minutes, seconds);
    }

    private void updateCallDuration()
    {
        if (mCallStart > 0)
        {
            mCallDuration.setText(formatTimespan(System.currentTimeMillis() - mCallStart));
        }
    }

    private class SinchCallListener implements CallListener {

        @Override
        public void onCallEnded(Call call) {
            CallEndCause cause = call.getDetails().getEndCause();
            Log.d(TAG, "Call ended. Reason: " + cause.toString());
            mAudioPlayer.stopProgressTone();
            setVolumeControlStream(AudioManager.USE_DEFAULT_STREAM_TYPE);
            String endMsg = "Call ended: " + call.getDetails().toString();
          //  Toast.makeText(AudioCallScreenActivity.this, endMsg, Toast.LENGTH_LONG).show();
            endCall();
        }

        @Override
        public void onCallEstablished(Call call) {
            Log.d(TAG, "Call established");
            mAudioPlayer.stopProgressTone();
           // mCallState.setText(call.getState().toString());
            setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
            mCallState.setText("Voice Call");
            LiveCounselling.Attandance();

          //  mCallDuration.setVisibility(View.VISIBLE);
           // is_connected = true;
        }

        @Override
        public void onCallProgressing(Call call) {
            Log.d(TAG, "Call progressing");
            mAudioPlayer.playProgressTone();
        }

        @Override
        public void onShouldSendPushNotification(Call call, List<PushPair> pushPairs) {
            // Send a push through your push provider here, e.g. GCM
        }

    }




    private class GetCurrentDateTime extends android.os.AsyncTask<String, String, Bitmap> {

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


                IfriendRequest http = new IfriendRequest(ct);

                GetCurrentDateTimeDTO allIfriendDTO = new GetCurrentDateTimeDTO("currentTime");

                GetCurrentDateObjectDTO getCurrentDateObjectDTO = new GetCurrentDateObjectDTO(allIfriendDTO);


                response = http.GetDateTime(getCurrentDateObjectDTO);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            if (response != null && !response.equalsIgnoreCase("")) {
                try {
                     response =  getLocalTime(response);

                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                            "MM/dd/yyyy HH:mm:ss");
                    try {
                        currentdatetime = dateFormat.parse(response);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    setdate_time();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //currentdatetime = token.getUpdatedAt();


            }

        }
    }


    private void setdate_time() {

        try {
            int intHour, intMinute, second, Mins;

            String Hour, Minute, Second;
            intHour = currentdatetime.getHours();
            intMinute = currentdatetime.getMinutes();
            second = currentdatetime.getSeconds();

            remain_second = 59 - second;

            if (intHour < 10) {
                Hour = "0" + intHour;
            } else {
                Hour = "" + intHour;
            }

            if (intMinute < 10) {
                Minute = "0" + intMinute;
            } else {
                Minute = "" + intMinute;
            }

           /* if (second < 10) {
                Second = "0" + second;
            } else {
                Second = "" + second;
            }*/

            String Mytime = Hour + ":" + Minute;

           /* if (bookingtime.length() <= 5)
            {
                bookingtime = bookingtime + ":00";
            }*/

            SimpleDateFormat format = new SimpleDateFormat("HH:mm");

            Date Date2 = format.parse(Mytime);

            Date Date1 = format.parse(LiveCounselling.bookingtime);
            long differenceInMins = Math.abs(Date2.getTime() - Date1.getTime()) / 1000;
            remain_minute = (int) differenceInMins / 60;

            remain_minute = GlimpseFragment.sesstion_time - remain_minute-1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        show_counsel_timer.setVisibility(View.VISIBLE);
        mCallStart = System.currentTimeMillis();
        /*Calendar cal = Calendar.getInstance();
        int intsecond = cal.get(Calendar.SECOND);
        seconcount = 59 - intsecond;
*/
    }



    private String getLocalTime(String datetime)
    {
        String result ="";
        try
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "MM/dd/yyyy HH:mm:ss");
            Date myDate = null;
            try {
                myDate = dateFormat.parse(datetime);

            } catch (Exception e) {
                e.printStackTrace();
            }
            Calendar   current = Calendar.getInstance();


            TimeZone tzCurrent = current.getTimeZone();
            int offset = tzCurrent.getRawOffset();
            if (tzCurrent.inDaylightTime(new Date())) {
                offset = offset + tzCurrent.getDSTSavings();
            }

            Calendar   current1 = Calendar.getInstance();

            current1.setTime(myDate);
          long  miliSeconds = current1.getTimeInMillis();
            miliSeconds = miliSeconds + offset;
            Date  resultdate = new Date(miliSeconds);
            SimpleDateFormat destFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            TimeZone london = TimeZone.getTimeZone("Europe/London");
            long now = resultdate.getTime();
            Date resultdate1 = new Date(  miliSeconds - london.getOffset(now));
            result = destFormat.format(resultdate1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        return  result;
    }


}
