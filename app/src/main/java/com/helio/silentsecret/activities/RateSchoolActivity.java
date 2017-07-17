package com.helio.silentsecret.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.helio.silentsecret.R;
import com.helio.silentsecret.WebserviceDTO.SchoolRatingConditionDTO;
import com.helio.silentsecret.WebserviceDTO.SchoolRatingObjectDTO;
import com.helio.silentsecret.WebserviceDTO.ScoolratingDTO;
import com.helio.silentsecret.adapters.SchoolRateAdapter;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.dialogs.ProgressDialog;
import com.helio.silentsecret.models.School;
import com.helio.silentsecret.utils.AppSession;
import com.helio.silentsecret.utils.Constants;
import com.helio.silentsecret.utils.Preference;

public class RateSchoolActivity extends BaseActivity implements View.OnClickListener {

    private ListView listView;
    private SchoolRateAdapter adapter;

    private EditText mSchoolEditText;
    private School currentSchool;
    private TextView rate_header = null;
    Context ct = null;

    SchoolRatingConditionDTO schoolRatingConditionDTO = null;
    String[] ratearray = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_school);
        ct = this;
        initViews();
        startTracking(getString(R.string.analytics_RateSchool));
    }

    private void initViews() {
        mSchoolEditText = (EditText) findViewById(R.id.rate_school_et);
        rate_header = (TextView) findViewById(R.id.rate_header);
        if (Integer.parseInt(Preference.getUserAge()) > 15)
        {
            mSchoolEditText.setHint(getString(R.string.name_of_college));
            rate_header.setText("Rate your college");
        }

        listView = (ListView) findViewById(R.id.rate_school_lv);

        adapter = new SchoolRateAdapter(LayoutInflater.from(this), this);
        listView.setAdapter(adapter);
        findViewById(R.id.rate_school_submit).setOnClickListener(this);
        findViewById(R.id.rate_school_close).setOnClickListener(this);
      //  querySchools();
    }

    /*private void querySchools() {
        showProgress();
        new SchoolLoader(new SchoolCallback() {
            @Override
            public void onUpdate(List<School> data) {
                hideProgress();
                if (data != null && !data.isEmpty())
                    currentSchool = data.get(0);
                else
                    currentSchool = null;
            }
        }).execute();
    }*/

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rate_school_submit:
                sendRateData();
                break;
            case R.id.rate_school_close:
                hideProgress();
                finish();
                break;
        }
    }

    private void sendRateData() {

        if (adapter.retrieveRateMap().size() != Constants.rateKeySet.length) {
            Toast.makeText(this, getString(R.string.please_fill_out_fields), Toast.LENGTH_SHORT).show();
            return;
        }

        if (mSchoolEditText.getText().toString().isEmpty()) {
            Toast.makeText(this, getString(R.string.please_enter_name), Toast.LENGTH_SHORT).show();
            return;
        }

        showProgress();

        ratearray = new String[ adapter.retrieveRateMap().size()];
        for (int i=0; i<adapter.retrieveRateMap().size(); i++)
        {
            ratearray[i] = ""+adapter.retrieveRateMap().get(Constants.rateKeySet[i]);
        }


        String address = AppSession.getValue(ct, Constants.USER_LOCATION);
        String age =  AppSession.getValue(ct, Constants.USER_AGE);
        String gender = AppSession.getValue(ct, Constants.USER_GENDER);
        schoolRatingConditionDTO = new SchoolRatingConditionDTO(MainActivity.enc_username,mSchoolEditText.getText().toString(),address, age,gender, ratearray);

        new RateSchool().execute();
       /* ObjectMaker.formSchoolRate(adapter.retrieveRateMap(), ParseUser.getCurrentUser(), currentSchool).saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                hideProgress();
                Preference.getSharedPreferences().edit().putLong(Constants.RATE_SCHOOL_CLASS, System.currentTimeMillis()).commit();
                finish();
            }
        });*/
    }

    public void showProgress() {
        ProgressDialog.showDialog(findViewById(R.id.rate_school_progress_bg_iv), findViewById(R.id.rate_school_progress_pb));
    }

    public void hideProgress() {
        ProgressDialog.hideDialog(findViewById(R.id.rate_school_progress_bg_iv), findViewById(R.id.rate_school_progress_pb));
    }




    private class RateSchool extends android.os.AsyncTask<String, String, Bitmap> {

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
                ScoolratingDTO scoolratingDTO = new ScoolratingDTO(schoolRatingConditionDTO);
                SchoolRatingObjectDTO schoolRatingObjectDTO = new SchoolRatingObjectDTO(scoolratingDTO);
                http.Schoolrating(schoolRatingObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {

                hideProgress();
                Preference.getSharedPreferences().edit().putLong(Constants.RATE_SCHOOL_CLASS, System.currentTimeMillis()).commit();
                finish();


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }



}
