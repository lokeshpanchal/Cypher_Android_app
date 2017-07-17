package com.helio.silentsecret.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.helio.silentsecret.EncryptionDecryption.CryptLib;
import com.helio.silentsecret.R;
import com.helio.silentsecret.WebserviceDTO.SecretDTO;
import com.helio.silentsecret.WebserviceDTO.SecretDataDTO;
import com.helio.silentsecret.WebserviceDTO.SecretObjectDTO;
import com.helio.silentsecret.activities.MainActivity;
import com.helio.silentsecret.checkers.CategoryHelper;
import com.helio.silentsecret.connection.ConnectionDetector;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.utils.AppSession;
import com.helio.silentsecret.utils.Constants;
import com.helio.silentsecret.utils.ToastUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.fabric.sdk.android.services.concurrency.AsyncTask;

public class CustomDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;

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
    SecretDataDTO secretDataDTO = null;
    public static RelativeLayout progress_bar;

    private String currentBackground;
    private ArrayList<String> imageList;
    private int imagePosition = 0;

    private String currentMood = null;
    private String currentText = null;
    private String text = null;
int secrets_count = 0;

    private int n = 0;

    public CustomDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.post_emotions);

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
        progress_bar = (RelativeLayout) findViewById(R.id.progress_bar);

        secrets_count = 0;
        updateImageList();

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
                MainActivity.cd.hide();
            }
        });

        Random rand = new Random();
        n = rand.nextInt(13) + 1;
        imagePosition = n;
        currentBackground = imageList.get(imagePosition);
    }

    public void updateImageList() {
        try {


            imageList = new ArrayList<>();
           /* ParseUser user = ParseUser.getCurrentUser();

            int invites = user.get(Constants.USER_INVITES) != null
                    ? user.getInt(Constants.USER_INVITES) : 0;

            int backgroundsCount = invites / Constants.INVITE_THRESHOLD;
*/
            int count = 92;
            try {
                AssetManager assetManager = c.getAssets();
                String[] list = assetManager.list("backs");

                if (count > list.length)
                    count = list.length;

                for (int i = 0; i < count; i++) {
                    for (String item : list) {
                        if (item.equals(new StringBuilder().append("bg").append(i).append(".jpg").toString())) {
                            imageList.add(item);
                            break;
                        }
                    }
                }

                // Free items
                for (String item : Constants.FREE_BG) {
                    if (!imageList.contains(item))
                        imageList.add(item);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.feeling_angry:
                if (ConnectionDetector.isNetworkAvailable(c)) {
                    progress_bar.setVisibility(View.VISIBLE);
                    startPosting("angry","Feeling Angry");
                } else {
                    new ToastUtil(c, "Please check your internet connection");
                }

                break;
            case R.id.feeling_anxious:
                if (ConnectionDetector.isNetworkAvailable(c)) {
                    progress_bar.setVisibility(View.VISIBLE);
                    startPosting("anxious","Feeling Anxious");
                } else {
                    new ToastUtil(c, "Please check your internet connection");
                }

                break;
            case R.id.feeling_ashamed:
                if (ConnectionDetector.isNetworkAvailable(c)) {
                    progress_bar.setVisibility(View.VISIBLE);
                    startPosting("ashamed","Feeling Ashamed");
                } else {
                    new ToastUtil(c, "Please check your internet connection");
                }

                break;
            case R.id.feeling_fml:
                if (ConnectionDetector.isNetworkAvailable(c)) {
                    progress_bar.setVisibility(View.VISIBLE);
                    startPosting("FML","Feeling F*ck My Life");
                } else {
                    new ToastUtil(c, "Please check your internet connection");
                }

                break;
            case R.id.feeling_frustrated:
                if (ConnectionDetector.isNetworkAvailable(c)) {
                    progress_bar.setVisibility(View.VISIBLE);
                    startPosting("frustrated","Feeling Frustrated");
                } else {
                    new ToastUtil(c, "Please check your internet connection");
                }

                break;
            case R.id.feeling_greatful:
                if (ConnectionDetector.isNetworkAvailable(c)) {
                    progress_bar.setVisibility(View.VISIBLE);
                    startPosting("grateful","Feeling Grateful");
                } else {
                    new ToastUtil(c, "Please check your internet connection");
                }

                break;
            case R.id.feeling_happy:
                if (ConnectionDetector.isNetworkAvailable(c)) {
                    progress_bar.setVisibility(View.VISIBLE);
                    startPosting("happy","Feeling Happy");
                } else {
                    new ToastUtil(c, "Please check your internet connection");
                }

                break;
            case R.id.feeling_lol:
                if (ConnectionDetector.isNetworkAvailable(c)) {
                    progress_bar.setVisibility(View.VISIBLE);
                    startPosting("lol","Feeling lol");
                } else {
                    new ToastUtil(c, "Please check your internet connection");
                }

                break;
            case R.id.feeling_lonely:
                if (ConnectionDetector.isNetworkAvailable(c)) {
                    progress_bar.setVisibility(View.VISIBLE);
                    startPosting("lonely","Feeling Lonely");
                } else {
                    new ToastUtil(c, "Please check your internet connection");
                }

                break;
            case R.id.feeling_love:
                if (ConnectionDetector.isNetworkAvailable(c)) {
                    progress_bar.setVisibility(View.VISIBLE);
                    startPosting("love","Feeling Love");
                } else {
                    new ToastUtil(c, "Please check your internet connection");
                }

                break;
            case R.id.feeling_sad:
                if (ConnectionDetector.isNetworkAvailable(c)) {
                    progress_bar.setVisibility(View.VISIBLE);
                    startPosting("sad","Feeling Sad");
                } else {
                    new ToastUtil(c, "Please check your internet connection");
                }

                break;
            case R.id.feeling_scared:
                if (ConnectionDetector.isNetworkAvailable(c)) {
                    progress_bar.setVisibility(View.VISIBLE);
                    startPosting("scared","Feeling Scared");
                } else {
                    new ToastUtil(c, "Please check your internet connection");
                }

                break;
            default:
                break;
        }
    }


    private void finishCreating()
    {
        try
        {

            MainActivity.is_From_feeling_post = true;
            MainActivity.updateTitleBanner(1);

            //Intent data = new Intent();
           // data.putExtra(Constants.SECRET_POST_KEY, 1);
           // c.setResult(Constants.SECRET_POST, data);
           // c.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

/*    SecretPushNotificayionDTO secretPushNotificayionDTO = null;
    private void load(final int state, boolean showProgress)
    {
        new CreateMainActivityMineLoader(c, new UpdateCallback()
        {
            @Override
            public void onUpdate(List<Secret> list) {

               *//* try
                {
                    if(list!= null && list.size()>0)
                    {
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }*//*


            }
        }).mysecretexecute(state);

    }*/




    private void startPosting(final String mood, final String text) {

        currentMood = mood;

        currentText = text;

        try {

                new AsyncTask<Void, Void, Integer>() {

                    @Override
                    protected Integer doInBackground(Void... voids) {
                        return 0;
                    }

                    @Override
                    protected void onPostExecute(Integer integer) {
                        super.onPostExecute(integer);



                        try {
                            if (secretDataDTO != null)
                                secretDataDTO = null;

                            long mseconds = System.currentTimeMillis();

                            String user_id = CryptLib.encrypt(AppSession.getValue(getContext(), Constants.USER_NAME)) + mseconds;

                            String secrets = AppSession.getValue(getContext(), Constants.USER_SECRETS);
                            try {
                                if (secrets != null && !secrets.equalsIgnoreCase("")) {
                                    secrets_count = Integer.parseInt(secrets);
                                    secrets_count = secrets_count + 1;
                                } else {
                                    secrets_count = 0;
                                    AppSession.save(getContext(), Constants.USER_SECRETS, "" + secrets_count);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            String addrs = "";
                           /* if (address != null && !address.equalsIgnoreCase(""))
                                addrs = CryptLib.encrypt(address);*/

                            List<String> school_users = null;
                            secretDataDTO = new SecretDataDTO(user_id, CryptLib.encrypt(AppSession.getValue(getContext(), Constants.USER_NAME)), AppSession.getValue(getContext(), Constants.USER_GENDER), AppSession.getValue(getContext(), Constants.USER_AGE), addrs, CategoryHelper.returnCategory(currentMood), null, "" + integer,
                                    currentMood, 1, currentBackground, CryptLib.encrypt(text), null, 0, "", school_users,
                                    "", "","");
                            new CreatSecret().execute();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }




                       /* final ParseObject send = ObjectMaker.formNewSecret(null, currentBackground, CategoryHelper.returnCategory( currentMood), currentMood, integer, null, null, 1, text,null);


                        ParseACL acl = new ParseACL();
                        acl.setPublicReadAccess(true);
                        acl.setPublicWriteAccess(true);
                        send.setACL(acl);

                        send.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e)
                            {

                                try
                                {
                                    load(0,false);
                                    if (e == null) {
                                        finishCreating();
                                        progress_bar.setVisibility(View.GONE);
                                        MainActivity.cd.hide();
                                    } else {
                                        finishCreating();
                                        progress_bar.setVisibility(View.GONE);
                                        e.printStackTrace();
                                    }
                                }
                                catch (Exception e1)
                                {
                                    e1.printStackTrace();
                                }

                            }
                        });*/
                    }
                }.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private class CreatSecret extends android.os.AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String status = "";
        Bitmap bitmap;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress_bar.setVisibility(View.VISIBLE);
        }

        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest http = new IfriendRequest(getContext());

                SecretDTO secretDTO = new SecretDTO(secretDataDTO, Constants.ENCRYPT_SECRET_TABLE, Constants.ENCRYPT_ADD_METHOD);
                SecretObjectDTO loginbjectDTO = new SecretObjectDTO(secretDTO);
                status = http.AddSecret(loginbjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {

            progress_bar.setVisibility(View.GONE);
            try
            {
                try
                {
                      //  load(0,false);
                        finishCreating();
                        progress_bar.setVisibility(View.GONE);
                        MainActivity.cd.hide();

                    /* else
                    {
                        finishCreating();
                        progress_bar.setVisibility(View.GONE);

                    }*/
                }
                catch (Exception e1)
                {
                    e1.printStackTrace();
                }

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

}
