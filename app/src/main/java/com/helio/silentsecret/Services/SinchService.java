package com.helio.silentsecret.Services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.helio.silentsecret.activities.IncomingCallScreenActivity;
import com.sinch.android.rtc.AudioController;
import com.sinch.android.rtc.ClientRegistration;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.SinchClientListener;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;
import com.sinch.android.rtc.video.VideoController;

public class SinchService extends Service {

/*    private static final String APP_KEY = "enter-application-key";
    private static final String APP_SECRET = "enter-application-secret";
    private static final String ENVIRONMENT = "sandbox.sinch.com";*/


    private static final String APP_KEY = "3596f6bc-11c9-4a68-8e4e-cba4e25fc8d3";
    private static final String APP_SECRET = "/wbSHBIMKEGEkHpxlK0lZQ==";
    private static final String ENVIRONMENT = "clientapi.sinch.com";
    //private static final String ENVIRONMENT = "sandbox.sinch.com";


    public static final String CALL_ID = "CALL_ID";
    static final String TAG = SinchService.class.getSimpleName();

    private SinchServiceInterface mSinchServiceInterface = new SinchServiceInterface();
    private SinchClient mSinchClient;
    private String mUserId;

    private StartFailedListener mListener;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        if (mSinchClient != null && mSinchClient.isStarted()) {
            mSinchClient.terminate();
        }
        super.onDestroy();
    }

    private void start(String userName)
    {
        if(mSinchClient!= null)
            mSinchClient = null;
        if (mSinchClient == null) {
            mUserId = userName;
            try
            {
                mSinchClient = Sinch.getSinchClientBuilder().context(getApplicationContext()).userId(userName)
                        .applicationKey(APP_KEY)
                        .applicationSecret(APP_SECRET)
                        .environmentHost(ENVIRONMENT).build();

                mSinchClient.setSupportCalling(true);
                mSinchClient.startListeningOnActiveConnection();

                mSinchClient.addSinchClientListener(new MySinchClientListener());
                mSinchClient.getCallClient().addCallClientListener(new SinchCallClientListener());
                mSinchClient.start();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


        }
    }

    private void stop()
    {
        try
        {
            if (mSinchClient != null)
            {
                try {
                  //  mSinchClient.terminate();
                    mSinchClient.stopListeningOnActiveConnection();

                    mSinchClient = null;
                } catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private boolean isStarted() {
        return (mSinchClient != null && mSinchClient.isStarted());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mSinchServiceInterface;
    }

    public class SinchServiceInterface extends Binder {

        public Call callUserVideo(String userId) {
            return mSinchClient.getCallClient().callUserVideo(userId);
        }

        public Call callUser(String userId) {
            if (mSinchClient == null) {
                return null;
            }
            return mSinchClient.getCallClient().callUser(userId);
        }

        public String getUserName() {
            return mUserId;
        }

        public boolean isStarted() {
            return SinchService.this.isStarted();
        }

        public void startClient(String userName) {
            start(userName);
        }

        public void stopClient() {
            stop();
        }

        public void setStartListener(StartFailedListener listener) {
            mListener = listener;
        }

        public Call getCall(String callId) {
            return mSinchClient.getCallClient().getCall(callId);
        }

        public VideoController getVideoController() {
            if (!isStarted()) {
                return null;
            }
            return mSinchClient.getVideoController();
        }

        public AudioController getAudioController() {
            if (!isStarted()) {
                return null;
            }
            return mSinchClient.getAudioController();
        }
    }

    public interface StartFailedListener {

        void onStartFailed(SinchError error);

        void onStarted();
    }

    private class MySinchClientListener implements SinchClientListener {

        @Override
        public void onClientFailed(SinchClient client, SinchError error) {
            if (mListener != null) {
                mListener.onStartFailed(error);
            }
            mSinchClient.terminate();
            mSinchClient = null;


        }

        @Override
        public void onClientStarted(SinchClient client) {
            Log.d(TAG, "SinchClient started");
            if (mListener != null) {
                mListener.onStarted();
            }
        }

        @Override
        public void onClientStopped(SinchClient client) {
            Log.d(TAG, "SinchClient stopped");
        }

        @Override
        public void onLogMessage(int level, String area, String message) {
            switch (level) {
                case Log.DEBUG:
                    Log.d(area, message);
                    break;
                case Log.ERROR:
                    Log.e(area, message);
                    break;
                case Log.INFO:
                    Log.i(area, message);
                    break;
                case Log.VERBOSE:
                    Log.v(area, message);
                    break;
                case Log.WARN:
                    Log.w(area, message);
                    break;
            }
        }

        @Override
        public void onRegistrationCredentialsRequired(SinchClient client,
                ClientRegistration clientRegistration) {
        }
    }

    private class SinchCallClientListener implements CallClientListener {

        @Override
        public void onIncomingCall(CallClient callClient, Call call) {
            Log.d(TAG, "Incoming call");
            Intent intent = new Intent(SinchService.this, IncomingCallScreenActivity.class);
            intent.putExtra(CALL_ID, call.getCallId());

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            SinchService.this.startActivity(intent);
        }
    }

}
