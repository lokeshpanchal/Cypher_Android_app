package com.helio.silentsecret.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.helio.silentsecret.R;
import com.helio.silentsecret.application.SilentSecretApplication;
import com.helio.silentsecret.utils.Constants;
import com.helio.silentsecret.utils.Preference;

import java.util.regex.Pattern;

public abstract class BaseActivity extends FragmentActivity {

    private boolean isCautionShown = false;
    private View mCaution = null;
    private LinearLayout mCaution1 = null;
    private Handler mCautionHandler;
    private Runnable mCautionRunnable;

    public static boolean[] defaultRules = new boolean[3];

    static {
        defaultRules[0] = true;
        defaultRules[1] = true;
        defaultRules[2] = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LocalBroadcastManager.getInstance(this).registerReceiver(mCautionReceiver,
                new IntentFilter(Constants.ACTION_CAUTION));

    }

    private void showCaution() {

        try
        {
            Preference.saveCaution(false);

            mCautionHandler = new Handler(getMainLooper());
            mCautionRunnable = new Runnable() {
                @Override
                public void run() {
                    try
                    {
                        hideCaution();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }
            };

            isCautionShown = true;
            mCaution = findViewById(R.id.caution_view);

            mCaution.setVisibility(View.VISIBLE);

            mCaution.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            mCautionHandler.postDelayed(mCautionRunnable, 15000);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    private void hideCaution() {
        try
        {
            isCautionShown = false;
            if (mCaution != null) {
                mCaution.setVisibility(View.GONE);
            }

            if (mCautionHandler != null) {
                mCautionHandler.removeCallbacks(mCautionRunnable);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private BroadcastReceiver mCautionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try
            {
                showCaution();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    };

    @Override
    protected void onDestroy() {
        hideCaution();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mCautionReceiver);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (isCautionShown)
            return;
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Preference.getCaution()) {
            showCaution();
        }
    }

    protected void startTracking(String path) {
        Tracker t = ((SilentSecretApplication) getApplication()).getTracker(
                SilentSecretApplication.TrackerName.APP_TRACKER);
        t.setScreenName(path);
        t.send(new HitBuilders.AppViewBuilder().build());
    }

    protected boolean localResolve(String text) {
        for (String key : Constants.LOCAL_KEYS) {
            if (Pattern.compile(Pattern.quote(key), Pattern.CASE_INSENSITIVE).matcher(text).find()) {
                return false;
            }
        }
        return true;
    }

}
