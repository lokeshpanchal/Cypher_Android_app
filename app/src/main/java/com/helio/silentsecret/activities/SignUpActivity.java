package com.helio.silentsecret.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.helio.silentsecret.EncryptionDecryption.CryptLib;
import com.helio.silentsecret.R;
import com.helio.silentsecret.WebserviceDTO.RegistrationDTO;
import com.helio.silentsecret.WebserviceDTO.RegistrationDataDTO;
import com.helio.silentsecret.WebserviceDTO.RegistrationObjectDTO;
import com.helio.silentsecret.WebserviceDTO.SetDateOfBirthDTO;
import com.helio.silentsecret.WebserviceDTO.SetDateOfBitrhObjectDTO;
import com.helio.silentsecret.WebserviceDTO.SetDatetDataDTO;
import com.helio.silentsecret.connection.ConnectionDetector;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.creator.ObjectMaker;
import com.helio.silentsecret.dialogs.ProgressDialog;
import com.helio.silentsecret.fragments.SignupDatePickerFragment;
import com.helio.silentsecret.location.GeoHelper;
import com.helio.silentsecret.utils.AppSession;
import com.helio.silentsecret.utils.Constants;
import com.helio.silentsecret.utils.KeyboardUtil;
import com.helio.silentsecret.utils.Preference;
import com.helio.silentsecret.utils.ToastUtil;
import com.helio.silentsecret.utils.Utils;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SignUpActivity extends FragmentActivity implements View.OnClickListener {

    private EditText mUsername;
    private EditText mPin;
    private EditText mAnswer;
    private EditText edt_search;
    private TextView mQuestionTextView, privacy_policy, close_web, date_of_birth ,special_code , submit_button;
    ImageView male_gender_radio = null, female_gender_radio = null ,other_gender_radio = null;

    private PopupMenu popupMenu;

    private String username;
    private String Pin;
    private String Answer;
    private String Question;
    private String Gender = "Male";
    private String Age = "";
    boolean is_processing = false;
    Context ct = null;

    WebView webview = null;
    String address = "";
    String imei_numer = "";
    RelativeLayout webview_layout = null,ruby_code_layout = null;
    RegistrationDataDTO registrationDataDTO = null ;
String code = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ct = this;
        initViews();
    }


    private void initViews() {

        try {


            mUsername = (EditText) findViewById(R.id.sign_up_username_input);
            edt_search = (EditText) findViewById(R.id.edt_search);
            mPin = (EditText) findViewById(R.id.sign_up_pin_input);
            mAnswer = (EditText) findViewById(R.id.sign_up_answer);

            mQuestionTextView = (TextView) findViewById(R.id.sign_up_question_tv);
            submit_button = (TextView) findViewById(R.id.submit_button);
            privacy_policy = (TextView) findViewById(R.id.privacy_policy);
            special_code = (TextView) findViewById(R.id.special_code);
            close_web = (TextView) findViewById(R.id.close_web);
            webview_layout = (RelativeLayout) findViewById(R.id.webview_layout);
            ruby_code_layout = (RelativeLayout) findViewById(R.id.ruby_code_layout);
            webview = (WebView) findViewById(R.id.webview);
            date_of_birth = (TextView) findViewById(R.id.date_of_birth);
            male_gender_radio = (ImageView) findViewById(R.id.male_gender_radio);
            female_gender_radio = (ImageView) findViewById(R.id.female_gender_radio);
            other_gender_radio = (ImageView) findViewById(R.id.other_gender_radio);

            date_of_birth.setOnClickListener(this);
            male_gender_radio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    male_gender_radio.setImageResource(R.drawable.gender_sel_radio);
                    female_gender_radio.setImageResource(R.drawable.gender_unsel_radio);
                    other_gender_radio.setImageResource(R.drawable.gender_unsel_radio);
                    Gender = "Male";
                }
            });

            female_gender_radio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    male_gender_radio.setImageResource(R.drawable.gender_unsel_radio);
                    other_gender_radio.setImageResource(R.drawable.gender_unsel_radio);
                    female_gender_radio.setImageResource(R.drawable.gender_sel_radio);
                    Gender = "Female";
                }
            });

            other_gender_radio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    male_gender_radio.setImageResource(R.drawable.gender_unsel_radio);
                    female_gender_radio.setImageResource(R.drawable.gender_unsel_radio);
                    other_gender_radio.setImageResource(R.drawable.gender_sel_radio);

                    Gender = "Other";
                }
            });



            special_code.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ruby_code_layout.setVisibility(View.VISIBLE);

                    edt_search.setFocusable(true);
                    edt_search.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(edt_search, InputMethodManager.SHOW_IMPLICIT);
                }
            });

            edt_search.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                }

                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    if (s.length() > 0) {
                        submit_button.setText("Submit");
                    } else
                        submit_button.setText("Cancel");

                }
            });

            submit_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    code = edt_search.getText().toString().trim();
                    if (code != null && !code.equalsIgnoreCase("")) {
                        new CHeckRubyCode().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    } else
                        ruby_code_layout.setVisibility(View.GONE);

                    KeyboardUtil.hideKeyBoard(edt_search, ct);

                }
            });



            InputFilter filter = new InputFilter() {
                public CharSequence filter(CharSequence source, int start, int end,
                                           Spanned dest, int dstart, int dend) {
                    for (int i = start; i < end; i++) {
                        if (!Character.isLetterOrDigit(source.charAt(i))) {
                            return ObjectMaker.EMPTY;
                        }
                    }
                    return null;
                }
            };

            close_web.postDelayed(new Runnable() {
                @Override
                public void run() {
                    address = GeoHelper.returnAddressString(SignUpActivity.this);

                    TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    imei_numer = telephonyManager.getDeviceId();
                }
            }, 500);

            initWebView(Constants.PRIVACY_POLICY_URL);
            mUsername.setFilters(new InputFilter[]{filter});
            findViewById(R.id.sign_up_btn).setOnClickListener(this);
            findViewById(R.id.sign_up_close).setOnClickListener(this);
            findViewById(R.id.sign_up_question_view).setOnClickListener(this);
            //findViewById(R.id.sign_up_age_view).setOnClickListener(this);
            findViewById(R.id.privacy_policy).setOnClickListener(this);
            //  findViewById(R.id.stats_birthday).setOnClickListener(this);
            findViewById(R.id.close_web).setOnClickListener(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mUsername != null)
            KeyboardUtil.hideKeyBoard(mUsername, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_up_btn:
                if (!is_processing)
                    makeSignUp();
                break;
            case R.id.sign_up_close:
                runLoginActivity();
                break;
            case R.id.sign_up_question_view:
                KeyboardUtil.hideKeyBoard(mUsername, ct);
                showPopupMenu(mQuestionTextView, R.menu.sign_up_queries);
                break;
           /* case R.id.sign_up_gender_view:
                showPopupMenu(mGenderTextView, R.menu.sign_up_gender);
                break;*/
            /*case R.id.sign_up_age_view:
                showPopupMenu(mAgeTextView, R.menu.sign_up_age);
                break;*/
            case R.id.date_of_birth:
                KeyboardUtil.hideKeyBoard(mUsername, ct);
                showDatePickerDialog();
                break;
            case R.id.privacy_policy:
                webview_layout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        runPolicy();
                    }
                }, 500);
                break;
            case R.id.close_web:
                webview_layout.setVisibility(View.GONE);
                break;

        }
    }

    @Override
    public void onBackPressed() {
        is_processing = false;
        runLoginActivity();
    }


    public void showDatePickerDialog() {
        SignupDatePickerFragment newFragment = new SignupDatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "DATE_PICKER");
    }

    private void makeSignUp() {
        if (!ConnectionDetector.isNetworkAvailable(this)) {

            new ToastUtil(this, "Please check your internet connection.");
        } else {
            is_processing = true;
            if (mUsername.getText().toString().isEmpty()) {
                new ToastUtil(this, getString(R.string.username_missed));
                is_processing = false;
                return;
            }

            if (mPin.getText().toString().isEmpty()) {
                new ToastUtil(this, getString(R.string.pin_missed));
                is_processing = false;
                return;
            }

            if (mPin.getText().toString().length() < 4) {
                new ToastUtil(this, getString(R.string.your_pin_is_too_small));
                is_processing = false;
                return;
            }

            if (mQuestionTextView.getText().toString().isEmpty() || mQuestionTextView.getText().toString().equals(getString(R.string.questions_prompt))) {
                new ToastUtil(this, getString(R.string.choose_your_secret_query));
                is_processing = false;
                return;
            }

            if (mAnswer.getText().toString().isEmpty()) {
                new ToastUtil(this, getString(R.string.answer_the_secret_query));
                is_processing = false;
                return;
            }

           /* if (mGenderTextView.getText().toString().isEmpty() || mGenderTextView.getText().toString().equals(getString(R.string.gender_prompt)))
            {
                new ToastUtil(this, getString(R.string.select_your_gender));
                is_processing = false;
                return;
            }*/

            if (Age.isEmpty() || Age.equals(getString(R.string.age_prompt))) {
                new ToastUtil(this, getString(R.string.select_your_age));
                is_processing = false;
                return;
            }


            username = mUsername.getText().toString();
            Pin = mPin.getText().toString();
            Question = mQuestionTextView.getText().toString();
            Answer = mAnswer.getText().toString();
            //Gender = mGenderTextView.getText().toString();


            String code = AppSession.getValue(ct, Constants.USER_CODE);
            String code_type = AppSession.getValue(ct, Constants.USER_CODE_TYPE);
            String guest_clun01 = AppSession.getValue(ct, Constants.GUEST_USER_NAME);
            if (registrationDataDTO != null)
                registrationDataDTO = null;

            try {
                registrationDataDTO = new RegistrationDataDTO(CryptLib.encrypt(username), CryptLib.encrypt(Pin),
                        CryptLib.encrypt(Question), CryptLib.encrypt(Answer), Gender, address, Preference.getUserLat() + ", " + Preference.getUserLon(), Age, imei_numer, safeguard_date, address, Preference.getUserLat() + ", " + Preference.getUserLon(), code, code_type ,guest_clun01);

            } catch (Exception e) {
                is_processing = false;
                e.printStackTrace();

            }

            if (Preference.getUserDisclaimer())
                new RegisterUser().execute();
            else
                showDisclaimer();

        }

    }


    private class RegisterUser extends AsyncTask<String, String, Bitmap> {

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
                RegistrationDTO registrationDTO = new RegistrationDTO(registrationDataDTO, Constants.ENCRYPT_USER_TABLE, Constants.ENCRYPT_ADD_METHOD);
                RegistrationObjectDTO registrationObjectDTO = new RegistrationObjectDTO(registrationDTO);

                status = http.RegistraterUser(registrationObjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {


            if (status != null && status.equalsIgnoreCase("success")) {
                Preference.saveUserAge(Age);
                Preference.saveUserName(username);

                AppSession.save(ct, Constants.USER_NAME, username);
                AppSession.save(ct, Constants.USER_PIN, Pin);
                AppSession.save(ct, Constants.USER_SECURITY_QUESTION, Question);
                AppSession.save(ct, Constants.USER_SECURITY_ANSWER, Answer);
                AppSession.save(ct, Constants.USER_GENDER, Gender);
                AppSession.save(ct, Constants.USER_AGE, Age);
                AppSession.save(ct, Constants.USER_ME2S, "0");
                AppSession.save(ct, Constants.USER_HEARTS, "0");
                AppSession.save(ct, Constants.USER_HUGS, "0");
                AppSession.save(ct, Constants.USER_FLAG, "false");
                AppSession.save(ct, Constants.USER_VERIFIED, "false");
                AppSession.save(ct, Constants.USER_SECRETS, "0");
                AppSession.save(ct, Constants.USER_DATE_OF_BIRTH, dateofbirth);
                new SetDOB().execute();
            } else {

                Toast.makeText(ct, status, Toast.LENGTH_SHORT).show();
            }

            hideProgress();
            is_processing = false;
        }
    }


    private void runMainActivity() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void runLoginActivity() {
        // startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void showPopupMenu(final View v, int id) {
        if (popupMenu != null) {
            popupMenu.dismiss();
        }

        popupMenu = new PopupMenu(this, v);
        popupMenu.inflate(id);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ((TextView) v).setText(item.getTitle());
                return false;
            }
        });

        popupMenu.show();
    }

    private void showProgress() {
        ProgressDialog.showDialog(findViewById(R.id.sign_up_progress_bg_iv), findViewById(R.id.sign_up_progress_pb));
    }

    private void hideProgress() {
        ProgressDialog.hideDialog(findViewById(R.id.sign_up_progress_bg_iv), findViewById(R.id.sign_up_progress_pb));
    }

    String dateofbirth = "";

    public void saveAndLoadDate(final Date date) {
        try {

            Calendar thatDay = Calendar.getInstance();
            thatDay.setTime(date);
            Calendar today = Calendar.getInstance();

            long difference = Utils.DiffBetTwoDate(today, thatDay);

            long days = difference / (24 * 60 * 60 * 1000);

            int years = (int) days / 365;
            if (years > 10) {
                Age = "" + years;
                dateofbirth = (String) android.text.format.DateFormat.format("yyyy/MM/dd", date);
                populateDate(date);
            } else
                new ToastUtil(ct, "You are too young to use this app.");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void populateDate(Date date) {
        if (date == null) return;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        date_of_birth.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) + "/" + String.valueOf(calendar.get(Calendar.MONTH) + 1) + "/" + String.valueOf(calendar.get(Calendar.YEAR)));

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
                SetDatetDataDTO findbyNameDTO = new SetDatetDataDTO(CryptLib.encrypt(username), dateofbirth, Age);
                SetDateOfBirthDTO findNameDTO = new SetDateOfBirthDTO(findbyNameDTO);
                http.SetDAte(new SetDateOfBitrhObjectDTO(findNameDTO));

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            runMainActivity();
        }
    }


    private void showDisclaimer() {


        AlertDialog disclaimer = new AlertDialog.Builder(this).create();
        disclaimer.setTitle(getString(R.string.disclaimer_title));
        disclaimer.setMessage(getString(R.string.disclaimer_text));

        disclaimer.setButton(DialogInterface.BUTTON_POSITIVE, getString(android.R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Preference.saveUserDisclaimer(true);
                new RegisterUser().execute();
            }
        });

        disclaimer.setButton(DialogInterface.BUTTON_NEGATIVE, getString(android.R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                is_processing = false;
            }
        });


        disclaimer.show();

    }

    String safeguard_date = "";

    public void runPolicy() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        safeguard_date = df.format(c.getTime());

        AppSession.save(ct, Constants.USER_SAFE_GUARD, safeguard_date);

        webview_layout.setVisibility(View.VISIBLE);
    }

    public void initWebView(String url) {

        webview.getSettings().setJavaScriptEnabled(true);


        webview.loadUrl(url);

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if(url.contains("team@"))
                {
                    url  = url.replace("mailto:","");

                    Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                    emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    emailIntent.setType("vnd.android.cursor.item/email");
                    emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{url});
                    startActivity(Intent.createChooser(emailIntent, "Send mail using..."));
                    return true;
                }
                else
                    webview.loadUrl(url);
                return false;

              /*  webview.loadUrl(url);
                return false;*/

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

            }

            @Override
            public void onPageFinished(WebView view, final String url) {

            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);

            }
        });
    }

    @Override
    protected void onResume() {
        is_processing = false;
        super.onResume();
    }




    private class CHeckRubyCode extends AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String response = "";
        Bitmap bitmap;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress();
        }

        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest http = new IfriendRequest(ct);


                String userna = AppSession.getValue(ct, Constants.USER_NAME);
                if (userna == null || userna.equalsIgnoreCase("")) {
                    JSONObject requestdata = new JSONObject();
                    JSONObject condition = new JSONObject();
                    JSONObject main_object = new JSONObject();
                    requestdata.put("apikey", "KhOSpc4cf67AkbRpq1hkq5O3LPlwU9IAtILaL27EPMlYr27zipbNCsQaeXkSeK3R");
                    condition.put("code", code);
                    requestdata.put("requestType", "checkUsercode");
                    requestdata.put("data", condition);
                    main_object.put("requestData", requestdata);
                    response = http.flagRoomComment(main_object.toString());
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image) {
            try {
                hideProgress();
                String status = "", guest_clun01 = "";
                Object object = new JSONTokener(response).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(response);


                    if (jsonObject.has("status"))
                        status = jsonObject.getString("status");

                    if (status != null && status.equalsIgnoreCase("true")) {
                        String codetype = "";
                        if (jsonObject.has("codetype"))
                            codetype = jsonObject.getString("codetype");


                        AppSession.save(ct, Constants.USER_CODE, code);
                        AppSession.save(ct, Constants.USER_CODE_TYPE, codetype);

                        edt_search.setText("");
                        ruby_code_layout.setVisibility(View.GONE);
                        Toast.makeText(ct, "Thanks for submiting the code.", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(ct, "Please enter a valid code.", Toast.LENGTH_SHORT).show();
                    }


                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }


}
