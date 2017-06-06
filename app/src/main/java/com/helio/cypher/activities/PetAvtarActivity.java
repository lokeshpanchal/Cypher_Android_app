package com.helio.cypher.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.helio.cypher.EncryptionDecryption.CryptLib;
import com.helio.cypher.R;
import com.helio.cypher.WebserviceDTO.GetPetConditionDTO;
import com.helio.cypher.WebserviceDTO.GetPetInfoDTO;
import com.helio.cypher.WebserviceDTO.GetPetInfoObjectDTO;
import com.helio.cypher.WebserviceDTO.PetAvtarInfoDTO;
import com.helio.cypher.connection.IfriendRequest;
import com.helio.cypher.utils.AppSession;
import com.helio.cypher.utils.Constants;
import com.helio.cypher.utils.ImageLoaderUtil;

public class PetAvtarActivity extends BaseActivity implements View.OnClickListener {

    private ImageLoaderUtil mImageUtil;
    private TextView mYears;

    private TextView mMonth;

    LinearLayout doblayout = null;
    String[] petnamearray = {"Monkey", "Panda", "Horse", "Rabbit", "Donkey", "Sheep", "Deer", "Tiger", "Parrot", "Meerkat"};

    ImageView petimage = null;

    public  PetAvtarInfoDTO petAvtarInfoDTO = null;

    String petname = "";
    private TextView mDays, oxygenlevel, water_percent, food_percent, oxygen_percent;
    ProgressBar water_progress, food_progress;
    String[] colorsking = {"#aa222222", "#99222222", "#88222222", "#77222222", "#66222222", "#55222222", "#33222222", "#22222222", "#11222222", "#00222222"};
    Context ct = null;
    ImageView stats_progress_bg_iv = null;
    RelativeLayout petlayout = null,progress_bar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_activity);
        try {
            ct = this;
            initViews();


            petimage = (ImageView) findViewById(R.id.petimage);
            petlayout = (RelativeLayout) findViewById(R.id.petlayout);
            progress_bar  = (RelativeLayout) findViewById(R.id.progress_bar);
            progress_bar.setVisibility(View.VISIBLE);


            startTracking(getString(R.string.analytics_PetAvtaar));

            new GetPetInfo().execute();
            water_progress = (ProgressBar) findViewById(R.id.water_progress);
            food_progress = (ProgressBar) findViewById(R.id.food_progress);
            oxygenlevel = (TextView) findViewById(R.id.oxygenlevel);
            oxygen_percent = (TextView) findViewById(R.id.oxygen_percent);
            water_percent = (TextView) findViewById(R.id.water_percent);
            food_percent = (TextView) findViewById(R.id.food_percent);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initViews() throws Exception {

        findViewById(R.id.stats_close).setOnClickListener(this);

    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.stats_close:
                AppSession.save(this, Constants.COMMENT_SECRET_ID,"");
                Intent intent = new Intent(PetAvtarActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }



    private class GetPetInfo extends AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String status = "";
        Bitmap bitmap;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest http = new IfriendRequest(ct);
                GetPetConditionDTO getPetConditionDTO = new GetPetConditionDTO(CryptLib.encrypt(AppSession.getValue(ct,Constants.USER_NAME)));
                GetPetInfoDTO getPetInfoDTO = new GetPetInfoDTO(Constants.ENCRYPT_MYVIRTUALSTATUS_TABLE,Constants.ENCRYPT_FIND_METHOD,getPetConditionDTO);
                GetPetInfoObjectDTO getPetInfoObjectDTO = new GetPetInfoObjectDTO(getPetInfoDTO);
                petAvtarInfoDTO = http.GetPetInfo(getPetInfoObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image)
        {
            try {

                if(petAvtarInfoDTO!= null)
                {

                    ShowPetStatus();
                }
                else
                {
                    AppSession.save(ct, Constants.COMMENT_SECRET_ID,"");
                    Intent intent = new Intent(PetAvtarActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            findViewById(R.id.stats_close).setVisibility(View.VISIBLE);

            progress_bar.setVisibility(View.GONE);
        }
    }


    int water_percentage;
    int food_percentage ;
    int oxyzen  ;

    private void ShowPetStatus()
    {

        try {
            petlayout.setVisibility(View.VISIBLE);

            petname = AppSession.getValue(ct, Constants.USER_PET_NAME);

            String hhug_food =  petAvtarInfoDTO.getHug_oxygen();
            if(hhug_food!= null && !hhug_food.equalsIgnoreCase("") && !hhug_food.equalsIgnoreCase("null")) {
                oxyzen = Integer.parseInt(hhug_food);
            }


            String hheart_food =   petAvtarInfoDTO.getHeart_food();
            if(hheart_food!= null && !hheart_food.equalsIgnoreCase("") && !hheart_food.equalsIgnoreCase("null")) {
                food_percentage = Integer.parseInt(hheart_food);
            }


            String hme_water =   petAvtarInfoDTO.getM2_water();
            if(hme_water!= null && !hme_water.equalsIgnoreCase("") && !hme_water.equalsIgnoreCase("null")) {
                water_percentage = Integer.parseInt(hme_water);
            }


            water_progress.setProgress(water_percentage);
            water_percent.setText("" + water_percentage + "%");


            food_progress.setProgress(food_percentage);
            food_percent.setText("" + food_percentage + "%");


            if (water_percentage == 100 && food_percentage == 100 && oxyzen == 100)
            {

                final int[] petimagearrayhappy = {R.drawable.monkeyhappy, R.drawable.pandahappy,
                        R.drawable.horsehappy, R.drawable.rabbithappy,
                        R.drawable.donkeyhappy, R.drawable.sheephappy,
                        R.drawable.deerhappy, R.drawable.tigerhappy,
                        R.drawable.parrothappy, R.drawable.meerkathappy};


                for (int i = 0; i < petnamearray.length; i++) {

                    if (petname.equalsIgnoreCase(petnamearray[i])) {
                        Glide.with(PetAvtarActivity.this)
                                .load(petimagearrayhappy[i])
                                .into(petimage);
                        break;
                    }
                }

                petimage.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            for (int i = 0; i < petnamearray.length; i++) {
                                if (petname.equalsIgnoreCase(petnamearray[i])) {
                                    Glide.with(PetAvtarActivity.this)
                                            .load(petimagearrayhappy[i])
                                            .into(petimage);


                                    break;
                                }
                            }


                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                }, 1000);

            }else
            {
                int[] petimagearray = {R.drawable.monkey, R.drawable.panda,
                        R.drawable.horse, R.drawable.rabbit,
                        R.drawable.donkey, R.drawable.sheep,
                        R.drawable.deer, R.drawable.tiger,
                        R.drawable.parrot, R.drawable.meerkat};


                for (int i = 0; i < petnamearray.length; i++) {
                    if (petname.equalsIgnoreCase(petnamearray[i])) {
                        petimage.setImageResource(petimagearray[i]);
                        break;
                    }
                }
            }


            oxygen_percent.setText("Oxygen : " + oxyzen + "%");
            int oxyzen1 = (int) oxyzen / 10;
            oxygenlevel.setBackgroundColor(Color.parseColor(colorsking[oxyzen1 - 1]));

        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }



}
