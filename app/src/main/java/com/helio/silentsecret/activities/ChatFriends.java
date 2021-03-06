package com.helio.silentsecret.activities;


import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.helio.silentsecret.R;
import com.helio.silentsecret.adapters.PagerAdapterChatFriends;
import com.helio.silentsecret.application.SilentSecretApplication;
import com.helio.silentsecret.fragments.IFriendsFragment;
import com.helio.silentsecret.fragments.PendingIFriendsFragment;

/**
 * Created by Maroof Ahmed Siddique on 8/2/2016.
 */
public class ChatFriends extends AppCompatActivity {



    TabLayout tabLayout;
    TextView help = null , back_iv;
    public static boolean is_frompush = false;
    TextView changetab = null;
    RelativeLayout time_lyout_bg = null;
    Context ct = null;
    TextView runAnime = null, cancel_help = null;

    private int width = 0, height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat_friends);


        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        ct = this;
        help = (TextView) findViewById(R.id.help);
        cancel_help = (TextView) findViewById(R.id.cancel_help);
        back_iv = (TextView) findViewById(R.id.back_iv);
        time_lyout_bg = (RelativeLayout) findViewById(R.id.time_lyout_bg);

        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        findViewById(R.id.ic_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.ifriend_plus_toogle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!MainActivity.is_flag)
                    startActivity(new Intent(ChatFriends.this, CreateSecretActivity.class));
                else
                    Toast.makeText(ct, "You are not permitted to share a secret.", Toast.LENGTH_SHORT).show();



            }
        });
        findViewById(R.id.ifriend_get_support).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ChatFriends.this, SupportActivity.class));
            }
        });


        cancel_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time_lyout_bg.setVisibility(View.GONE);
            }
        });
        time_lyout_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        runAnime = new TextView(this);

        try {
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            width = displayMetrics.widthPixels;
            height = displayMetrics.heightPixels;

        } catch (Exception e) {

            if (width <= 0) {
                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                width = size.x;
                height = size.y;
            }
            e.printStackTrace();
        }

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                time_lyout_bg.setVisibility(View.VISIBLE);
            }
        });
        changetab = new TextView(this);



    startTracking(getString(R.string.analytics_Ifriend));

        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.iFriendsText)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.pendingFriendsText)));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position == 0)
                {
                    IFriendsFragment.callWS();

                } else {
                    startTracking(getString(R.string.analytics_PendingFreind));
                    PendingIFriendsFragment.callPendingSending();
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        final PagerAdapterChatFriends adapter = new PagerAdapterChatFriends
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        if (is_frompush) {
            changetab.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try
                    {
                        viewPager.setCurrentItem(1);
                        PendingIFriendsFragment.callPendingSending();
                        is_frompush = false;
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }
            }, 200);
        }


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // heartAnimation();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }



    @Override
    public void onStart() {
        super.onStart();


    }


    @Override
    public void onStop() {
        super.onStop();


    }


    protected void startTracking(String path) {
        Tracker t = ((SilentSecretApplication) getApplication()).getTracker(
                SilentSecretApplication.TrackerName.APP_TRACKER);
        t.setScreenName(path);
        t.send(new HitBuilders.AppViewBuilder().build());
    }
}

