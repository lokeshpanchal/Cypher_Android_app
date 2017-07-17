package com.helio.silentsecret.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.helio.silentsecret.EncryptionDecryption.CryptLib;
import com.helio.silentsecret.R;
import com.helio.silentsecret.WebserviceDTO.LoginConditionDTO;
import com.helio.silentsecret.WebserviceDTO.LoginDTO;
import com.helio.silentsecret.WebserviceDTO.LoginbjectDTO;
import com.helio.silentsecret.connection.ConnectionDetector;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.dialogs.ForgotPinDialog;
import com.helio.silentsecret.dialogs.ProgressDialog;
import com.helio.silentsecret.utils.AppSession;
import com.helio.silentsecret.utils.Constants;
import com.helio.silentsecret.utils.KeyboardUtil;
import com.helio.silentsecret.utils.ToastUtil;
import com.nineoldandroids.animation.Animator;


public class LoginActivity extends FragmentActivity implements View.OnClickListener {

    private View startView;
    private View nextView;
    private View mOr;
    private EditText mUsername;
    private EditText mPin;

    Context ct = null;

    LoginConditionDTO loginConditionDTO = null;

    public static String CurrentUsername = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ct = this;
        initViews();
        MainActivity.is_from_login = true;
       // new Disclaimer(this);
    }

    private void initViews() {

        try
        {
            startView = findViewById(R.id.start_login_view);
            nextView = findViewById(R.id.next_login_view);
            mOr = findViewById(R.id.login_or_btn);
            mUsername = (EditText) findViewById(R.id.login_username_input);
            mPin = (EditText) findViewById(R.id.login_pin_input);

            InputFilter filter = new InputFilter() {
                public CharSequence filter(CharSequence source, int start, int end,
                                           Spanned dest, int dstart, int dend) {
                    for (int i = start; i < end; i++) {
                        if (!Character.isLetterOrDigit(source.charAt(i))) {
                            return "";
                        }
                    }
                    return null;
                }
            };

            if(LoginActivity.CurrentUsername!= null && !LoginActivity.CurrentUsername.equalsIgnoreCase(""))
                mUsername.setText(LoginActivity.CurrentUsername);

            mUsername.setFilters(new InputFilter[]{filter});

            findViewById(R.id.login_progress_bg_iv).setOnClickListener(this);

            findViewById(R.id.login_sign_in_btn).setOnClickListener(this);
            findViewById(R.id.login_sign_up_btn).setOnClickListener(this);
            findViewById(R.id.login_in_btn).setOnClickListener(this);
            findViewById(R.id.login_forgot_pin).setOnClickListener(this);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mUsername != null)
            KeyboardUtil.hideKeyBoard(mUsername, this);
    }

 /*   private void saveInstallation(ParseUser user)
    {
        Preference.saveUserAge(user.getString(Constants.USER_AGE));
        Preference.saveUserName(user.getUsername());

        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put(Constants.USER, ParseUser.getCurrentUser());
        ParsePush.subscribeInBackground(mUsername.getText().toString());

        installation.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {

                    Getpetname();


                } else {
                    new ToastUtil(LoginActivity.this, e.getMessage());
                }
            }
        });
    }*/


    private void Getpetname()
    {
        Intent intent = new Intent(LoginActivity.this, PetAvtarActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_sign_in_btn:
                makeNextView();
                break;
            case R.id.login_sign_up_btn:
                runSignUp();
                break;
            case R.id.login_in_btn:
                runLogin();
                break;
            case R.id.login_forgot_pin:
                forgotPin();
                break;
        }
    }

    private void forgotPin() {
        replaceFragment(new ForgotPinDialog());
    }

    public void replaceFragment(Fragment frag)
    {
        try {

        Fragment newFragment = frag;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_down, R.anim.exit_down, R.anim.pop_enter_down, R.anim.pop_exit_down);
        transaction.replace(R.id.login_content, newFragment);
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
        } catch (IllegalStateException e) {
        }
    }


    private void runLogin() {

        if (!ConnectionDetector.isNetworkAvailable(this))
        {
            new ToastUtil(this, "Please check your internet connection.");
        }
        else
        {

            if (mUsername.getText().toString().isEmpty())
                return;

            if (mPin.getText().toString().isEmpty())
                return;

            showProgress();

            try
            {
                loginConditionDTO = new LoginConditionDTO(CryptLib.encrypt(mUsername.getText().toString()),CryptLib.encrypt(mPin.getText().toString()));

            }
            catch (Exception e)
            {

            }

            new LoginUser().execute();

           /* ParseUser.logInInBackground(mUsername.getText().toString(), mPin.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser parseUser, ParseException e) {
                    hideProgress();
                    if (e == null)
                    {
                        saveInstallation(parseUser);
                    } else
                    {
                        new ToastUtil(LoginActivity.this, "Please Enter your Correct Username and PIN.");
                    }
                }
            });
*/

        }

    }



    private class LoginUser extends AsyncTask<String, String, Bitmap> {

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

                LoginDTO loginDTO = new LoginDTO(loginConditionDTO,Constants.ENCRYPT_USER_TABLE,Constants.ENCRYPT_FIND_METHOD);
                LoginbjectDTO loginbjectDTO = new LoginbjectDTO(loginDTO);

                status =  http.LoginUser(loginbjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image)
        {
            try
            {
                hideProgress();
                if(status!= null && status.equalsIgnoreCase("success"))
                {
                    Getpetname();
                }
                else
                {
                    Toast.makeText(ct, status ,Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }



    private void runMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void makeNextView() {
        YoYo.with(Techniques.FadeOutDown).withListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                YoYo.with(Techniques.FadeOutDown).duration(500).playOn(mOr);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                startView.setVisibility(View.GONE);
                nextView.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.FadeInUp).duration(500).playOn(nextView);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).duration(500).playOn(startView);
    }

    private void runSignUp() {
        startActivity(new Intent(this, SignUpActivity.class));
        finish();
    }

    private void showProgress() {
        ProgressDialog.showDialog(findViewById(R.id.login_progress_bg_iv), findViewById(R.id.login_progress_pb));
    }

    private void hideProgress() {
        ProgressDialog.hideDialog(findViewById(R.id.login_progress_bg_iv), findViewById(R.id.login_progress_pb));
    }

}
