package com.helio.silentsecret.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.helio.silentsecret.R;
import com.helio.silentsecret.utils.CommonFunction;

public class Mediator_meeting extends AppCompatActivity {

    TextView back_iv = null , prev_back  = null;
    RelativeLayout prev_meet_layout = null;
    LinearLayout pre_meeting = null , new_meeting = null , prev_main_layout = null;
Context ct = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mediator_meeting);

        back_iv = (TextView) findViewById(R.id.back_iv);
        prev_back = (TextView) findViewById(R.id.prev_back);
        prev_meet_layout = (RelativeLayout) findViewById(R.id.prev_meet_layout);
        pre_meeting = (LinearLayout) findViewById(R.id.pre_meeting);
        new_meeting = (LinearLayout) findViewById(R.id.new_meeting);
        prev_main_layout = (LinearLayout) findViewById(R.id.prev_main_layout);

        pre_meeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prev_meet_layout.setVisibility(View.VISIBLE);
                showPrevMeeting();
            }
        });

        prev_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prev_meet_layout.setVisibility(View.GONE);
            }
        });

        new_meeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ct,PrepareMeeting.class);
                startActivity(intent);
            }
        });

        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void showPrevMeeting()
    {
        prev_main_layout.removeAllViews();
        int width = CommonFunction.getScreenWidth();
        int height = CommonFunction.getScreenHeight();
        for(int i=0 ; i<15; i++)
        {



            LinearLayout main_linear  = new LinearLayout(ct);
            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(width , LinearLayout.LayoutParams.WRAP_CONTENT);
            //llp.setMargins(0,width/60,0,width/60);
            main_linear.setOrientation(LinearLayout.HORIZONTAL);
            main_linear.setLayoutParams(llp);



            llp = new LinearLayout.LayoutParams(width, width/50);
            TextView margin2 = new TextView(ct);
            margin2.setLayoutParams(llp);

            prev_main_layout.addView(margin2);


            llp = new LinearLayout.LayoutParams(width/5  ,  width/4 + width/30);
            LinearLayout dates_leanir  = new LinearLayout(ct);
            dates_leanir.setLayoutParams(llp);
            dates_leanir.setOrientation(LinearLayout.VERTICAL);
            dates_leanir.setBackgroundResource(R.drawable.white_left_corner);


            llp = new LinearLayout.LayoutParams(width/5, width/12);
            TextView margin1 = new TextView(ct);
            margin1.setLayoutParams(llp);
            dates_leanir.addView(margin1);

            llp = new LinearLayout.LayoutParams(width/5 , LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView dates = new TextView(ct);
            dates.setLayoutParams(llp);

            dates.setText("12/07");
            dates.setTextSize(20);
            dates.setTextColor(Color.parseColor("#3daaa4"));
            dates.setGravity(Gravity.CENTER);

            llp = new LinearLayout.LayoutParams(width/5 , LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView last_edited = new TextView(ct);
            last_edited.setLayoutParams(llp);

            last_edited.setText("last edited");
            last_edited.setTextSize(12);
            last_edited.setGravity(Gravity.CENTER);
            last_edited.setTextColor(Color.parseColor("#3daaa4"));

            dates_leanir.addView(dates);
            dates_leanir.addView(last_edited);

            llp = new LinearLayout.LayoutParams(width/2 + width/4, width/4 + width/30);
            TextView meeting_title = new TextView(ct);
            meeting_title.setLayoutParams(llp);

            meeting_title.setText("Meeting title");
            meeting_title.setTextSize(15);
            meeting_title.setTextColor(Color.parseColor("#ffffff"));
            meeting_title.setGravity(Gravity.CENTER);
            meeting_title.setBackgroundResource(R.drawable.glimpse_button);

            main_linear.addView(dates_leanir);
            main_linear.addView(meeting_title);

            prev_main_layout.addView(main_linear);
            llp = new LinearLayout.LayoutParams(width, width/50);
            TextView margin = new TextView(ct);
            margin.setLayoutParams(llp);

            prev_main_layout.addView(margin);

        }
    }
}
