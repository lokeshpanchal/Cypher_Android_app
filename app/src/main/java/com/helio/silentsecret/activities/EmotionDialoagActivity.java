package com.helio.silentsecret.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.helio.silentsecret.R;
import com.helio.silentsecret.utils.AppSession;
import com.helio.silentsecret.utils.Constants;

public class EmotionDialoagActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView mFeelingAnxious;
    ImageView mFeelingFML;
    ImageView mFeelingFrustrated;
    ImageView mFeelingGreatful;
    ImageView mFeelingLOL;
    ImageView mFeelingLonely;
    ImageView mFeelingAngry;
    ImageView mFeelingHappy;
    ImageView mFeelingLove;
    ImageView mFeelingSad;
    ImageView mFeelingScared;
    ImageView mFeelingAshamed;
    ImageView post_emotion_close;


Context ct = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotion_dialoag);
        mFeelingAngry = (ImageView) findViewById(R.id.feeling_angry);
        mFeelingAnxious = (ImageView) findViewById(R.id.feeling_anxious);
        mFeelingAshamed = (ImageView) findViewById(R.id.feeling_ashamed);
        mFeelingFML = (ImageView) findViewById(R.id.feeling_fml);
        mFeelingFrustrated = (ImageView) findViewById(R.id.feeling_frustrated);
        mFeelingGreatful = (ImageView) findViewById(R.id.feeling_greatful);
        mFeelingHappy = (ImageView) findViewById(R.id.feeling_happy);
        mFeelingLOL = (ImageView) findViewById(R.id.feeling_lol);
        mFeelingLonely = (ImageView) findViewById(R.id.feeling_lonely);
        mFeelingLove = (ImageView) findViewById(R.id.feeling_love);
        mFeelingSad = (ImageView) findViewById(R.id.feeling_sad);
        mFeelingScared = (ImageView) findViewById(R.id.feeling_scared);
        post_emotion_close = (ImageView) findViewById(R.id.post_emotion_close);

        mFeelingAngry.setOnClickListener(this);
        mFeelingAnxious.setOnClickListener(this);
        mFeelingAshamed.setOnClickListener(this);
        mFeelingFML.setOnClickListener(this);
        mFeelingFrustrated.setOnClickListener(this);
        mFeelingGreatful.setOnClickListener(this);
        mFeelingHappy.setOnClickListener(this);
        mFeelingLOL.setOnClickListener(this);
        mFeelingLonely.setOnClickListener(this);
        mFeelingLove.setOnClickListener(this);
        mFeelingSad.setOnClickListener(this);
        mFeelingScared.setOnClickListener(this);


        post_emotion_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {


            case R.id.feeling_angry:
                AppSession.save(ct, Constants.TODAYS_EMOTION , Constants.emotion_name_array[Constants.ANGRY]);
                break;
            case R.id.feeling_anxious:
                AppSession.save(ct, Constants.TODAYS_EMOTION , Constants.emotion_name_array[Constants.ANXIOUS]);
                break;
            case R.id.feeling_ashamed:
                AppSession.save(ct, Constants.TODAYS_EMOTION , Constants.emotion_name_array[Constants.ASHAMED]);
                break;
            case R.id.feeling_fml:
                AppSession.save(ct, Constants.TODAYS_EMOTION , Constants.emotion_name_array[Constants.FML]);
                break;
            case R.id.feeling_frustrated:

                AppSession.save(ct, Constants.TODAYS_EMOTION , Constants.emotion_name_array[Constants.FRUSTRATED]);
                break;
            case R.id.feeling_greatful:
                AppSession.save(ct, Constants.TODAYS_EMOTION , Constants.emotion_name_array[Constants.GRATEFUL]);
                break;
            case R.id.feeling_happy:
                AppSession.save(ct, Constants.TODAYS_EMOTION , Constants.emotion_name_array[Constants.HAPPY]);
                break;
            case R.id.feeling_lol:
                AppSession.save(ct, Constants.TODAYS_EMOTION , Constants.emotion_name_array[Constants.LOL]);
                break;
            case R.id.feeling_lonely:
                AppSession.save(ct, Constants.TODAYS_EMOTION , Constants.emotion_name_array[Constants.LONELY]);

                break;
            case R.id.feeling_love:
                AppSession.save(ct, Constants.TODAYS_EMOTION , Constants.emotion_name_array[Constants.LOVE]);

                break;
            case R.id.feeling_sad:
                AppSession.save(ct, Constants.TODAYS_EMOTION , Constants.emotion_name_array[Constants.SAD]);
                break;
            case R.id.feeling_scared:
                AppSession.save(ct, Constants.TODAYS_EMOTION , Constants.emotion_name_array[Constants.SCARED]);
                break;


            default:
                break;
        }

        finish();
    }
}
