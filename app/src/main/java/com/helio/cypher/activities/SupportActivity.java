package com.helio.cypher.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.helio.cypher.R;
import com.helio.cypher.WebserviceDTO.GetSupportListDTO;
import com.helio.cypher.WebserviceDTO.GetSupportObjectDTO;
import com.helio.cypher.WebserviceDTO.SeenSupportRatingDataDTO;
import com.helio.cypher.WebserviceDTO.SendSupportRatingObjectDTO;
import com.helio.cypher.WebserviceDTO.SendUpoortRatingDTO;
import com.helio.cypher.adapters.SupportAdapter;
import com.helio.cypher.callbacks.GPSCallback;
import com.helio.cypher.connection.ConnectionDetector;
import com.helio.cypher.connection.IfriendRequest;
import com.helio.cypher.dialogs.RateDialog;
import com.helio.cypher.dialogs.WebViewDialog;
import com.helio.cypher.location.GPSCoordinateReceiver;
import com.helio.cypher.location.GeoHelper;
import com.helio.cypher.models.SupportOrganisation;
import com.helio.cypher.utils.Constants;
import com.helio.cypher.utils.ImageLoaderUtil;
import com.helio.cypher.utils.Preference;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SupportActivity extends BaseActivity implements View.OnClickListener, GPSCallback {

    private GPSCoordinateReceiver GPS;
    private ListView listView;
    private SupportAdapter adapter;
    Context ct = null;
    private List<SupportOrganisation> mList;
    private SupportOrganisation currentOrganisation;
    private ImageLoaderUtil mImageUtil;

    SeenSupportRatingDataDTO seenSupportRatingDataDTO = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            ct = this;
            setContentView(R.layout.activity_support);
            initViews();
            startTracking(Constants.TRACK_SUPPORT);
            GPS = new GPSCoordinateReceiver(this, this);
            showProgress();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initViews() {

        try {
            listView = (ListView) findViewById(R.id.support_list_view);

            findViewById(R.id.support_close).setOnClickListener(this);

            mImageUtil = new ImageLoaderUtil(this);
//        mImageUtil.loadDrawable(R.drawable.ic_support_back, (ImageView) findViewById(R.id.support_back));

            mList = new ArrayList<>();
            adapter = new SupportAdapter(LayoutInflater.from(this), mList, this);

            listView.setAdapter(adapter);
            new GetSupportList().execute();
            /*int age = Integer.parseInt(Preference.getUserAge());

            if (age == 11 || age == 12 || age == 13) {
                loadAdditionalFreeSupport();
            }
            else
            {
                loadSupportOrg();
            }*/


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

  /*  private void loadAdditionalFreeSupport() {
        if (!ConnectionDetector.isNetworkAvailable(this))
            return;
        new SupportLoader(new SupportFreeCallback() {
            @Override
            public void onUpdate(SupportOrganisation data) {
                try {
                    mList.add(data);
                    adapter.notifyDataSetChanged();
                } catch (IndexOutOfBoundsException e) {
                } catch (NullPointerException nullExe) {
                }
            }
        }).executeForFree();
    }*/

/*    private void loadSupportOrg() {
        if (!ConnectionDetector.isNetworkAvailable(this))
            return;
        new SupportLoader(new SupportCallback() {
            @Override
            public void onUpdate(List<SupportOrganisation> data)
            {
                hideProgress();
                mList.addAll(data);
                adapter.notifyDataSetChanged();
                if (mList.size() > 0) {
                    listView.setVisibility(View.VISIBLE);
                    findViewById(R.id.support_not_found).setVisibility(View.GONE);
                    listView.setSelection(0);
                } else {
                    listView.setVisibility(View.INVISIBLE);
                    findViewById(R.id.support_not_found).setVisibility(View.VISIBLE);
                }
            }
        }).executeForUser();
    }*/

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {

        Fragment frag = getSupportFragmentManager().findFragmentById(R.id.support_frame);
        if (frag instanceof WebViewDialog || frag instanceof RateDialog) {

        } else {
            super.onBackPressed();
        }
    }

    private void replaceWeb(String url) {


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        try {
            if (url.contains("https")) {
                url = url.replace("https", "http");
            }
            transaction.replace(R.id.support_frame, WebViewDialog.instance(url, Constants.SUPPORT_WEB_VIEW));
        } catch (IllegalStateException e) {
        }
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void replaceRate() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        try {
            transaction.replace(R.id.support_frame, RateDialog.instance(Constants.SUPPORT_WEB_VIEW));
        } catch (IllegalStateException e) {
        }
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.support_close:
                finish();
                break;
        }
    }
    public void showProgress() {
        try {
            findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideProgress() {

        try {
            findViewById(R.id.progress_bar).setVisibility(View.GONE);
         //   ProgressDialog.hideDialog(findViewById(R.id.support_progress_bg_iv), findViewById(R.id.support_progress_pb));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void runOrganisation(SupportOrganisation item) {
        currentOrganisation = item;
        try {
            if (item.getUrl() != null && !item.getUrl().equalsIgnoreCase("")) {
                replaceWeb(item.getUrl());
            } else if (item.getPhoneNo() != null) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + item.getPhoneNo()));
                startActivity(callIntent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void webViewClosed()
    {
        if (!ConnectionDetector.isNetworkAvailable(this))
            return;

        if (currentOrganisation == null)
            return;

        if (currentOrganisation.getUsersClicked() == null)
            return;

        if (!currentOrganisation.getUsersClicked().contains(MainActivity.enc_username))
        {
            try {
                final List<String> rateUsers = currentOrganisation.getUsersClicked();
                rateUsers.add(MainActivity.enc_username);

                if(seenSupportRatingDataDTO!= null)
                    seenSupportRatingDataDTO = null;
                seenSupportRatingDataDTO = new SeenSupportRatingDataDTO(currentOrganisation.getOrg(), rateUsers, 0,"clicked");
                currentOrganisation.setUsersClicked(rateUsers);
                new SaveClicked().execute();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        if (!currentOrganisation.getUserRated().contains(MainActivity.enc_username))
            replaceRate();
    }

    public void makeRate(final int rate) {

        if (!ConnectionDetector.isNetworkAvailable(this))
            return;

        showProgress();
        try {
            final List<String> rateUsers = currentOrganisation.getUserRated();
            rateUsers.add(MainActivity.enc_username);

    if(seenSupportRatingDataDTO!= null)
    seenSupportRatingDataDTO = null;
            seenSupportRatingDataDTO = new SeenSupportRatingDataDTO(currentOrganisation.getOrg(), rateUsers, rate,"ratted");
            currentOrganisation.setUserRated(rateUsers);
            new Saverating().execute();
        } catch (Exception e) {
            e.printStackTrace();
        }


        /*final ParseObject biteObject = new ParseObject(Constants.SUPPORT_ORGANISATIONS_CLASS);
        biteObject.setObjectId(currentOrganisation.getObjectId());



        biteObject.put(Constants.SUPPORT_ORGANISATIONS_USERS_RATED, rateUsers);

        ParseACL acl = new ParseACL();
        acl.setPublicReadAccess(true);
        acl.setPublicWriteAccess(true);
        biteObject.setACL(acl);
        biteObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    try {
                        currentOrganisation.setUserRated(rateUsers);
                        saveRate(rate);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }


                } else {
                    hideProgress();
                }


            }
        });*/
    }

    private void saveRate(int rate) {
       /* ObjectMaker.formRate(currentOrganisation.getOrg(), rate, ParseUser.getCurrentUser()).saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                hideProgress();
            }
        });*/
    }

    @Override
    public void onReceive(final Location location) {

        try {

            if (location != null) {

                Preference.saveUserLat(String.valueOf(location.getLatitude()));
                Preference.saveUserLon(String.valueOf(location.getLongitude()));

                final String userLocation = GeoHelper.returnAddressString(this);

                if (userLocation == null) {
                    GPS.removeCallback();
                    return;
                }


            } else
            {

                GPS.removeCallback();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

   /* private void finishExecution(String userLocation, List<SupportOrganisation> list, ParseUser user) {
        try {
            List<String> ids = filterOrg(list, userLocation);

            if (ids.size() > 0) {
                user.addAllUnique(Constants.USER_SUPPORT_ORGANISATION, ids);
                user.saveInBackground();
            }

            loadSupportOrg();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/

    private List<String> filterOrg(List<SupportOrganisation> data, String userLocation) {
        List<String> result = new ArrayList<>();

        try {
            for (SupportOrganisation item : data) {
                if (item.getTriggers() == null) {
                    for (String location : item.getLocation()) {
                        if (Pattern.compile(Pattern.quote(location), Pattern.CASE_INSENSITIVE).matcher(userLocation).find()) {
                            result.add(item.getObjectId());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }


    private class GetSupportList extends android.os.AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        List<SupportOrganisation> data;

        String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            showProgress();
        }


        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest http = new IfriendRequest(ct);

                GetSupportObjectDTO findbynameObjectDTO = new GetSupportObjectDTO(new GetSupportListDTO());
                data = http.GetSupportlist(findbynameObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {

                hideProgress();
                mList.addAll(data);
                adapter.notifyDataSetChanged();
                if (mList.size() > 0) {
                    listView.setVisibility(View.VISIBLE);
                    findViewById(R.id.support_not_found).setVisibility(View.GONE);
                    listView.setSelection(0);
                } else {
                    listView.setVisibility(View.INVISIBLE);
                    findViewById(R.id.support_not_found).setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    private class Saverating extends android.os.AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String data;

        String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            showProgress();
        }


        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest http = new IfriendRequest(ct);

                SendSupportRatingObjectDTO findbynameObjectDTO = new SendSupportRatingObjectDTO(new SendUpoortRatingDTO(seenSupportRatingDataDTO,"sendSupportRating"));
                data = http.SendSupportRating(findbynameObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {

            hideProgress();
        }
    }



    private class SaveClicked extends android.os.AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String data;

        String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            showProgress();
        }


        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest http = new IfriendRequest(ct);

                SendSupportRatingObjectDTO findbynameObjectDTO = new SendSupportRatingObjectDTO(new SendUpoortRatingDTO(seenSupportRatingDataDTO,"sendSupportClicked"));
                data = http.SendSupportRating(findbynameObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {

            hideProgress();
        }
    }


}
