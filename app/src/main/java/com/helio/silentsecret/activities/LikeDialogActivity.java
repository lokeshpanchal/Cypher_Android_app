package com.helio.silentsecret.activities;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.helio.silentsecret.R;

public class LikeDialogActivity extends BaseActivity
{


    TextView  cancel_button = null;
    TextView  see_all_filter = null;
    ImageView selected_see_all = null;
    ImageView selected_light_filter = null;
    ImageView selected_tense_filter = null;
LinearLayout filter_layout = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likebutton_dialog);

        cancel_button = (TextView) findViewById(R.id.cancel_button);
        see_all_filter = (TextView) findViewById(R.id.see_all_filter);
        selected_see_all = (ImageView) findViewById(R.id.selected_see_all);
        selected_light_filter = (ImageView) findViewById(R.id.selected_light_filter);
        selected_tense_filter = (ImageView) findViewById(R.id.selected_tense_filter);
        filter_layout = (LinearLayout) findViewById(R.id.filter_layout);

        filter_layout.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {

                if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    selected_see_all.setImageResource(R.drawable.seeall_icon);
                    selected_see_all.setVisibility(View.GONE);
                    see_all_filter.setVisibility(View.VISIBLE);
                }

                if (event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    see_all_filter.setVisibility(View.GONE);
                    selected_see_all.setImageResource(R.drawable.seeall_icon);
                    selected_see_all.setVisibility(View.VISIBLE);
                }

                return false;
            }
        });


        see_all_filter.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {

                if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    selected_see_all.setImageResource(R.drawable.seeall_icon);
                    selected_see_all.setVisibility(View.GONE);
                    see_all_filter.setVisibility(View.VISIBLE);
                }

                if (event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    see_all_filter.setVisibility(View.GONE);
                    selected_see_all.setImageResource(R.drawable.seeall_icon);
                    selected_see_all.setVisibility(View.VISIBLE);
                }

                return false;
            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }



}
