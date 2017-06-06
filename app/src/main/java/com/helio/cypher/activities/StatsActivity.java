package com.helio.cypher.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.helio.cypher.R;
import com.helio.cypher.WebserviceDTO.GetPetConditionDTO;
import com.helio.cypher.WebserviceDTO.GetPetInfoDTO;
import com.helio.cypher.WebserviceDTO.GetPetInfoObjectDTO;
import com.helio.cypher.WebserviceDTO.PetAvtarInfoDTO;
import com.helio.cypher.WebserviceDTO.SetDateOfBirthDTO;
import com.helio.cypher.WebserviceDTO.SetDateOfBitrhObjectDTO;
import com.helio.cypher.WebserviceDTO.SetDatetDataDTO;
import com.helio.cypher.WebserviceDTO.SetPetConditionDTO;
import com.helio.cypher.WebserviceDTO.SetPetInfoDTO;
import com.helio.cypher.WebserviceDTO.SetPetInfoObjectDTO;
import com.helio.cypher.connection.IfriendRequest;
import com.helio.cypher.dialogs.ProgressDialog;
import com.helio.cypher.fragments.DatePickerFragment;
import com.helio.cypher.utils.AppSession;
import com.helio.cypher.utils.Constants;
import com.helio.cypher.utils.ImageLoaderUtil;
import com.helio.cypher.utils.Preference;
import com.helio.cypher.utils.ToastUtil;
import com.helio.cypher.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StatsActivity extends BaseActivity implements View.OnClickListener {

    private ImageLoaderUtil mImageUtil;
    private TextView mYears;
    private TextView mMonth;

    LinearLayout doblayout = null;
    String[] petnamearray = {"Monkey", "Panda", "Horse", "Rabbit", "Donkey", "Sheep", "Deer", "Tiger", "Parrot", "Meerkat"};

    boolean is_selectable = true;
    ImageView petimage = null;
    ScrollView petscrollview = null;
    String petname = "";
    private TextView mDays, oxygenlevel, water_percent, food_percent, oxygen_percent, slecetpettext;
    ProgressBar water_progress, food_progress;
String Age = "";
  String[] colorsking = {"#aa222222", "#99222222", "#88222222", "#77222222", "#66222222", "#55222222", "#33222222", "#22222222", "#11222222", "#00222222"};
    Context ct = null;
    int width, height;
String dateofbirth = "";
    TextView skip_pet, done_pet = null;

    ImageView stats_progress_bg_iv = null;
    RelativeLayout petlayout = null, pet_select_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        try {
            ct = this;


            try {
                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                height = displayMetrics.widthPixels;
                width = displayMetrics.heightPixels;

            } catch (Exception e) {
                if (width <= 0) {
                    Display display = getWindowManager().getDefaultDisplay();
                    Point size = new Point();
                    display.getSize(size);
                    height = size.x;
                    width = size.y;
                }
                e.printStackTrace();
            }

            skip_pet = (TextView) findViewById(R.id.skip_pet);
            done_pet = (TextView) findViewById(R.id.done_pet);
            petimage = (ImageView) findViewById(R.id.petimage);
            petlayout = (RelativeLayout) findViewById(R.id.petlayout);
            pet_select_layout = (RelativeLayout) findViewById(R.id.pet_layout);
            doblayout = (LinearLayout) findViewById(R.id.doblayout);
            petscrollview = (ScrollView) findViewById(R.id.petscrollview);
            water_progress = (ProgressBar) findViewById(R.id.water_progress);
            food_progress = (ProgressBar) findViewById(R.id.food_progress);
            oxygenlevel = (TextView) findViewById(R.id.oxygenlevel);
            oxygen_percent = (TextView) findViewById(R.id.oxygen_percent);
            water_percent = (TextView) findViewById(R.id.water_percent);
            food_percent = (TextView) findViewById(R.id.food_percent);
            slecetpettext = (TextView) findViewById(R.id.slecetpettext);
            pet_main_layout = (LinearLayout) findViewById(R.id.pet_main_layout);

            initViews();

            Getpetname();

            startTracking(getString(R.string.analytics_PetAvtaar));

            pet_select_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            skip_pet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pet_select_layout.setVisibility(View.GONE);
                }
            });
            done_pet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (select_index >= 0) {
                        new SetPetInfo().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        pet_select_layout.setVisibility(View.GONE);

                        SpannableString text = new SpannableString("Feed your pet by giving out hugs, hearts, me2s and comments on posts");

                        text.setSpan(new ForegroundColorSpan(Color.parseColor("#333333")), 0, text.length(), 0);
                        text.setSpan(new ForegroundColorSpan(Color.BLACK), 26, 46, 0);
                        text.setSpan(new StyleSpan(Typeface.BOLD), 26, 46, 0);
                        text.setSpan(new ForegroundColorSpan(Color.BLACK), 51, 59, 0);
                        text.setSpan(new StyleSpan(Typeface.BOLD), 51, 59, 0);

                        AlertDialog.Builder updateAlert = new AlertDialog.Builder(ct);
                        updateAlert.setIcon(R.drawable.ic_launcher);
                        updateAlert.setTitle("Cypher");
                        updateAlert.setMessage(text);
                        updateAlert.setPositiveButton(
                                "Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alertUp = updateAlert.create();
                        alertUp.setCanceledOnTouchOutside(false);
                        alertUp.show();
                    } else {
                        Toast.makeText(ct, "Please select Your pet", Toast.LENGTH_SHORT).show();
                    }

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initViews() throws Exception {

        findViewById(R.id.stats_close).setOnClickListener(this);
        findViewById(R.id.stats_birthday).setOnClickListener(this);

        mImageUtil = new ImageLoaderUtil(this);
        //  mImageUtil.loadDrawable(R.drawable.ic_stats_bg, (ImageView) findViewById(R.id.stats_back));

        loadData();
    }

    private void loadData() {
        try {
            mYears = (TextView) findViewById(R.id.stats_years);
            mMonth = (TextView) findViewById(R.id.stats_month);
            mDays = (TextView) findViewById(R.id.stats_days);


            String DOB = AppSession.getValue(ct, Constants.USER_DATE_OF_BIRTH);
            if (DOB != null && !DOB.equalsIgnoreCase("")) {
                Date date = Utils.StringTodateyyyymmdd(DOB);
                if(date == null)
                    date  = Utils.StringTodateyyyymmddwithdiffformate(DOB);

                populateDate(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void populateDate(Date date) {
        if (date == null) return;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);



        Calendar today = Calendar.getInstance();

        long difference = Utils.DiffBetTwoDate(today,calendar);

        long days = difference / (24 * 60 * 60 * 1000);

        int years = (int) days/365;
        if(years >10)
            is_selectable = false;



        mYears.setText(String.valueOf(calendar.get(Calendar.YEAR)));
        mMonth.setText(String.valueOf(calendar.get(Calendar.MONTH) + 1));
        mDays.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
    }

    public void saveAndLoadDate(final Date date) {


        try {

            Calendar thatDay = Calendar.getInstance();
            thatDay.setTime(date);
            Calendar today = Calendar.getInstance();

            long difference = Utils.DiffBetTwoDate(today,thatDay);

            long days = difference / (24 * 60 * 60 * 1000);

            int years = (int) days/365;
            if(years>10)
            {
                Age = ""+years;


                dateofbirth = (String) android.text.format.DateFormat.format("yyyy/MM/dd", date);

                AppSession.save(ct, Constants.USER_DATE_OF_BIRTH, dateofbirth);

                populateDate(date);
                Preference.setHasDateChanged(true);

                new SetDOB().execute();
            }
            else
                new ToastUtil(ct,"You are too young to use this app.");

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {


            dateofbirth = (String) android.text.format.DateFormat.format("yyyy/MM/dd", date);

            AppSession.save(ct, Constants.USER_DATE_OF_BIRTH, dateofbirth);

            populateDate(date);
            Preference.setHasDateChanged(true);

            new SetDOB().execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.stats_close:
                finish();
                break;
            case R.id.stats_birthday:
                String DOB = AppSession.getValue(ct, Constants.USER_DATE_OF_BIRTH);
                if (DOB == null || DOB.equalsIgnoreCase("") || is_selectable)
                {
                    showDatePickerDialog();
                }
                break;
        }
    }

    public void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "DATE_PICKER");
    }

    public void showProgress() {
        ProgressDialog.showDialog(findViewById(R.id.stats_progress_bg_iv), findViewById(R.id.stats_progress_pb));
    }

    public void hideProgress() {
        ProgressDialog.hideDialog(findViewById(R.id.stats_progress_bg_iv), findViewById(R.id.stats_progress_pb));
    }

    private void Getpetname() {
        petname = AppSession.getValue(ct, Constants.USER_PET_NAME);
        if (petname != null && !petname.equalsIgnoreCase("") && !petname.equalsIgnoreCase("null")) {
           // new GetPetInfo().execute();

            new GetPetInfo().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
        else {

            petname = "";
            drawpetlist();

            String dobhidec = AppSession.getValue(ct, Constants.DOBHIDECOUNT);
            if (dobhidec != null && !dobhidec.equalsIgnoreCase("")) {
                int hidecount = Integer.parseInt(dobhidec);
                if (hidecount < 11) {
                    doblayout.setVisibility(View.VISIBLE);
                    hidecount = hidecount + 1;
                    AppSession.save(ct, Constants.DOBHIDECOUNT, "" + hidecount);

                } else {
                    petscrollview.setVisibility(View.GONE);
                    doblayout.setVisibility(View.GONE);
                    slecetpettext.setVisibility(View.VISIBLE);
                }
            } else {
                doblayout.setVisibility(View.VISIBLE);
                AppSession.save(ct, Constants.DOBHIDECOUNT, "" + 1);
            }
        }

    }


    int select_index = -1;
    LinearLayout pet_main_layout;

    private void drawpetlist() {

        pet_select_layout.setVisibility(View.VISIBLE);
        int[] petimagearray = {R.drawable.monkey_face, R.drawable.panda_face,
                R.drawable.horse_face, R.drawable.rabbit_face,
                R.drawable.donkey_face, R.drawable.sheep_face,
                R.drawable.deer_face, R.drawable.tiger_face,
                R.drawable.parrot_face, R.drawable.meerkat_face};

        final List<LinearLayout> petrowlayoutlist = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            LinearLayout petrowlayout = new LinearLayout(ct);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, width / 6);

            petrowlayout.setLayoutParams(lp);
            petrowlayout.setBackgroundColor(Color.parseColor("#e5e5e5"));
            petrowlayout.setId(i + 1);
            TextView petimage = new TextView(ct);
            lp = new LinearLayout.LayoutParams(width / 5, width / 8);
            lp.gravity = Gravity.CENTER_VERTICAL;
            lp.setMargins(width / 25, 0, 0, 0);
            petimage.setLayoutParams(lp);
            petimage.setBackgroundResource(petimagearray[i]);

            TextView setpetname = new TextView(ct);
            lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, width / 6);
            lp.setMargins(width / 10, 0, 0, 0);
            setpetname.setGravity(Gravity.CENTER_VERTICAL);
            setpetname.setLayoutParams(lp);
            setpetname.setTextColor(Color.parseColor("#222222"));
            setpetname.setTextSize(18);
            setpetname.setText(petnamearray[i]);


            TextView firgy = new TextView(ct);
            lp = new LinearLayout.LayoutParams(width, width / 300);
            firgy.setLayoutParams(lp);
            //firgy.setBackgroundColor(Color.parseColor("#555555"));

            petrowlayoutlist.add(petrowlayout);

            petrowlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    select_index = v.getId() - 1;
                    for (int i = 0; i < petrowlayoutlist.size(); i++) {
                        if (i == select_index)
                            petrowlayoutlist.get(i).setBackgroundColor(Color.parseColor("#36bcbc"));
                        else
                            petrowlayoutlist.get(i).setBackgroundColor(Color.parseColor("#e5e5e5"));

                    }
                }
            });
            petrowlayout.addView(petimage);
            petrowlayout.addView(setpetname);
            pet_main_layout.addView(petrowlayout);
            pet_main_layout.addView(firgy);

        }
    }


    private class GetPetInfo extends AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String status = "";
        Bitmap bitmap;


        @Override
        protected void onPreExecute() {
            showProgress();
            super.onPreExecute();
        }

        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest http = new IfriendRequest(ct);

                GetPetConditionDTO getPetConditionDTO = new GetPetConditionDTO(MainActivity.enc_username);
                GetPetInfoDTO getPetInfoDTO = new GetPetInfoDTO(Constants.ENCRYPT_MYVIRTUALSTATUS_TABLE, Constants.ENCRYPT_FIND_METHOD, getPetConditionDTO);

                GetPetInfoObjectDTO getPetInfoObjectDTO = new GetPetInfoObjectDTO(getPetInfoDTO);

                petAvtarInfoDTO = http.GetPetInfo(getPetInfoObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {
                if (petAvtarInfoDTO != null) {
                    ShowPetStatus();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            hideProgress();
        }
    }


    private class SetPetInfo extends AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String status = "";
        Bitmap bitmap;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress();
        }

        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest http = new IfriendRequest(ct);

                SetPetConditionDTO setPetConditionDTO = new SetPetConditionDTO(MainActivity.enc_username, petnamearray[select_index]);
                SetPetInfoDTO setPetInfoDTO = new SetPetInfoDTO(setPetConditionDTO);
                SetPetInfoObjectDTO setPetInfoObjectDTO = new SetPetInfoObjectDTO(setPetInfoDTO);
                petAvtarInfoDTO = http.SetPetInfo(setPetInfoObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {
                if (petAvtarInfoDTO != null) {
                    AppSession.save(ct, Constants.USER_PET_NAME, petnamearray[select_index]);
                    ShowPetStatus();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            hideProgress();
        }
    }

    int water_percentage;
    int food_percentage;
    int oxyzen;
    public PetAvtarInfoDTO petAvtarInfoDTO = null;

    private void ShowPetStatus() {

        try {
            petlayout.setVisibility(View.VISIBLE);

            petname = AppSession.getValue(ct, Constants.USER_PET_NAME);

            String hhug_food = petAvtarInfoDTO.getHug_oxygen();
            if (hhug_food != null && !hhug_food.equalsIgnoreCase("") && !hhug_food.equalsIgnoreCase("null")) {
                oxyzen = Integer.parseInt(hhug_food);
            }


            String hheart_food = petAvtarInfoDTO.getHeart_food();
            if (hheart_food != null && !hheart_food.equalsIgnoreCase("") && !hheart_food.equalsIgnoreCase("null")) {
                food_percentage = Integer.parseInt(hheart_food);
            }


            String hme_water = petAvtarInfoDTO.getM2_water();
            if (hme_water != null && !hme_water.equalsIgnoreCase("") && !hme_water.equalsIgnoreCase("null")) {
                water_percentage = Integer.parseInt(hme_water);
            }


            water_progress.setProgress(water_percentage);
            water_percent.setText("" + water_percentage + "%");


            food_progress.setProgress(food_percentage);
            food_percent.setText("" + food_percentage + "%");


            if (water_percentage == 100 && food_percentage == 100 && oxyzen == 100) {

                final int[] petimagearrayhappy = {R.drawable.monkeyhappy, R.drawable.pandahappy,
                        R.drawable.horsehappy, R.drawable.rabbithappy,
                        R.drawable.donkeyhappy, R.drawable.sheephappy,
                        R.drawable.deerhappy, R.drawable.tigerhappy,
                        R.drawable.parrothappy, R.drawable.meerkathappy};


                for (int i = 0; i < petnamearray.length; i++) {

                    if (petname.equalsIgnoreCase(petnamearray[i])) {
                        Glide.with(StatsActivity.this)
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
                                    Glide.with(StatsActivity.this)
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

            } else {
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


        try {
            String dobhidec = AppSession.getValue(ct, Constants.DOBHIDECOUNT);

            if (dobhidec != null && !dobhidec.equalsIgnoreCase("")) {
                int hidecount = Integer.parseInt(dobhidec);
                if (hidecount < 11) {
                    doblayout.setVisibility(View.VISIBLE);
                    hidecount = hidecount + 1;
                    AppSession.save(ct, Constants.DOBHIDECOUNT, "" + hidecount);

                } else {
                    doblayout.setVisibility(View.GONE);
                }
            } else {
                doblayout.setVisibility(View.VISIBLE);
                AppSession.save(ct, Constants.DOBHIDECOUNT, "" + 1);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }


    private class SetDOB extends android.os.AsyncTask<String, String, Bitmap> {

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
                SetDatetDataDTO findbyNameDTO = new SetDatetDataDTO(MainActivity.enc_username, dateofbirth,Age);
                SetDateOfBirthDTO findNameDTO = new SetDateOfBirthDTO(findbyNameDTO);
                  http.SetDAte(new SetDateOfBitrhObjectDTO(findNameDTO));

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {


        }
    }


}
