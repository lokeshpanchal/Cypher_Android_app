package com.helio.cypher.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.helio.cypher.R;
import com.helio.cypher.Services.SinchService;
import com.helio.cypher.appCounsellingDTO.CommonRequestTypeDTO;
import com.helio.cypher.appCounsellingDTO.FinalObjectDTO;
import com.helio.cypher.connection.IfriendRequest;
import com.helio.cypher.models.InserCallSessionDTO;

import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallEndCause;
import com.sinch.android.rtc.video.VideoCallListener;

import java.util.List;

public class IncomingCallScreenActivity extends SinchBaseActivity {

    static final String TAG = IncomingCallScreenActivity.class.getSimpleName();
    private String mCallId;
    private AudioPlayer mAudioPlayer;

    public static boolean is_incoming_call = false;
    public static boolean is_video_call = false;

    InserCallSessionDTO inserCallSessionDTO = null;

    int counter_connected = 0;

    Context ct = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.incoming);

        Button answer = (Button) findViewById(R.id.answerButton);
        answer.setOnClickListener(mClickListener);
        Button decline = (Button) findViewById(R.id.declineButton);
        decline.setOnClickListener(mClickListener);

        ct = this;
        mAudioPlayer = new AudioPlayer(this);
        mAudioPlayer.playRingtone();
        mCallId = getIntent().getStringExtra(SinchService.CALL_ID);
    }

    @Override
    protected void onServiceConnected() {
        counter_connected++;
        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            call.addCallListener(new SinchCallListener());
            TextView remoteUser = (TextView) findViewById(R.id.remoteUser);
            remoteUser.setText("Counsellor");

        } else {
            Log.e(TAG, "Started with invalid callId, aborting");
            finish();
        }
    }

    private void answerClicked()
    {
        mAudioPlayer.stopRingtone();
        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            call.answer();

            is_incoming_call = true;
            Intent intent = null;

            InsertAnswerCall();

            LiveCounselling.Attandance();
            LiveCounselling.UserAttandace();

            if(is_video_call)
             intent = new Intent(this, CallScreenActivity.class);
            else
             intent = new Intent(this, AudioCallScreenActivity.class);

            intent.putExtra(SinchService.CALL_ID, mCallId);
            startActivity(intent);
            finish();
        } else {
            finish();
        }
    }

    private void declineClicked() {

        try
        {
            for(int i=0; i < counter_connected; i++) {
                mAudioPlayer.stopRingtone();
                Call call = getSinchServiceInterface().getCall(mCallId);
                if (call != null) {
                    call.hangup();
                }

            }
            counter_connected = 0;
        }
        catch (Exception e)
        {

        }
        finish();
    }

    private class SinchCallListener implements VideoCallListener {

        @Override
        public void onCallEnded(Call call) {
            CallEndCause cause = call.getDetails().getEndCause();
            Log.d(TAG, "Call ended, cause: " + cause.toString());
            mAudioPlayer.stopRingtone();


            declineClicked();
        }

        @Override
        public void onCallEstablished(Call call) {
            Log.d(TAG, "Call established");
        }

        @Override
        public void onCallProgressing(Call call) {
            Log.d(TAG, "Call progressing");
        }

        @Override
        public void onShouldSendPushNotification(Call call, List<PushPair> pushPairs) {
            // Send a push through your push provider here, e.g. GCM
        }

        @Override
        public void onVideoTrackAdded(Call call) {
            // Display some kind of icon showing it's a video call
        }
    }

    private OnClickListener mClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.answerButton:
                    answerClicked();
                    break;
                case R.id.declineButton:
                    try
                    {
                        declineClicked();
                        InsertDecline();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    break;
            }
        }
    };




    private   void InsertAnswerCall()
    {
        if(inserCallSessionDTO!= null)
            inserCallSessionDTO = null;

        String userid = MainActivity.enc_username;

        inserCallSessionDTO = new InserCallSessionDTO( LiveCounselling.appointmentid, userid, LiveCounselling.counsellerid, "Answering",
                "Counsellor", "");
        new InsertSession().execute();

    }

    private   void InsertDecline()
    {
        if(inserCallSessionDTO!= null)
            inserCallSessionDTO = null;

        String userid = MainActivity.enc_username;
        inserCallSessionDTO = new InserCallSessionDTO( LiveCounselling.appointmentid, userid, LiveCounselling.counsellerid, "Decline",
                "Counsellor", "");
        new InsertSession().execute();

    }

    private class InsertSession extends android.os.AsyncTask<String, String, Bitmap>
    {

        protected Bitmap doInBackground(String... args) {
            try {


/*                IfriendRequest http = new IfriendRequest(ct);
                InsertCallSessionObjectDTO insertCallSessionObjectDTO = new InsertCallSessionObjectDTO(inserCallSessionDTO);
                    http.InsertSessopn(insertCallSessionObjectDTO);*/

                IfriendRequest http = new IfriendRequest(ct);
                CommonRequestTypeDTO commonRequestDTO = new CommonRequestTypeDTO(inserCallSessionDTO,"Callsession");
                FinalObjectDTO insertCallSessionObjectDTO = new FinalObjectDTO(commonRequestDTO);
                http.InsertSessopn(insertCallSessionObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    }


}



