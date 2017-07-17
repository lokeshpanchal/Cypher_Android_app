package com.helio.silentsecret.dialogs;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.helio.silentsecret.EncryptionDecryption.CryptLib;
import com.helio.silentsecret.R;
import com.helio.silentsecret.WebserviceDTO.LoginConditionDTO;
import com.helio.silentsecret.activities.ActionSecretActivity;
import com.helio.silentsecret.activities.CommentSecretActivity;
import com.helio.silentsecret.activities.MainActivity;
import com.helio.silentsecret.activities.MySecretsActivity;
import com.helio.silentsecret.appCounsellingDTO.CommonRequestTypeDTO;
import com.helio.silentsecret.appCounsellingDTO.FinalObjectDTO;
import com.helio.silentsecret.connection.ConnectionDetector;
import com.helio.silentsecret.connection.IfriendRequest;
import com.helio.silentsecret.utils.AppSession;
import com.helio.silentsecret.utils.Constants;
import com.helio.silentsecret.utils.KeyboardUtil;
import com.helio.silentsecret.utils.ToastUtil;

public class AccessDialog extends Fragment implements View.OnClickListener {

    private View mView;
    private View mOutView;

    private TextView mDone;
    private TextView mCancel;
    private TextView mForgotPin;
LoginConditionDTO loginConditionDTO = null;
    private TextView mHeader;
    private TextView mMessage;
    private EditText mInput;

    private final int STATE_ENTER = 0;
    private final int STATE_RESET = 1;
    private final int STATE_YOUR_PIN = 2;
    private final int STATE_AGAIN = 3;
    private int STATE = STATE_ENTER;

    private int current_state = 0;

    private Handler mMessageHandler;
    private Runnable mMessageRunnable;

    public static AccessDialog instance(String t) {
        AccessDialog access = new AccessDialog();
        Bundle data = new Bundle();
        data.putString(Constants.DIALOG_KEY, t);
        access.setArguments(data);
        return access;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow()
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().getWindow()
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.forgot_pin_dialog, null);

        mOutView = mView.findViewById(R.id.share_root);
        mOutView.setOnClickListener(this);

        YoYo.with(Techniques.FadeIn).duration(1500).playOn(mOutView);

        mDone = (TextView) mView.findViewById(R.id.dialog_done);
        mCancel = (TextView) mView.findViewById(R.id.dialog_cancel);
        mForgotPin = (TextView) mView.findViewById(R.id.dialog_forgot);

        mHeader = (TextView) mView.findViewById(R.id.dialog_header);
        mMessage = (TextView) mView.findViewById(R.id.dialog_message);
        mInput = (EditText) mView.findViewById(R.id.dialog_text);

        mInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                try {
                if(mInput.getText().toString()!= null && !mInput.getText().toString().equalsIgnoreCase(""))
                {
                    if (AppSession.getValue(getActivity(),Constants.USER_PIN).equals(mInput.getText().toString()))
                    {
                            if(current_state != STATE_AGAIN)
                            open();

                    }
                }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        setupState(STATE);

        mMessageHandler = new Handler();
        mMessageRunnable = new Runnable() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        YoYo.with(Techniques.FadeOutDown).playOn(mMessage);
                    }
                });
            }
        };

        return mView;
    }









    private void setupState(int state) {
        KeyboardUtil.hideKeyBoard(mInput, getActivity());
        current_state = state;
        switch (state) {
            case STATE_ENTER:
                try
                {
                    mMessage.setVisibility(View.INVISIBLE);
                    mCancel.setVisibility(View.VISIBLE);
                    mCancel.setText(getString(R.string.cancel));
                    mCancel.setOnClickListener(mCancelListener);
                    YoYo.with(Techniques.FadeInUp).duration(500).playOn(mCancel);
                    mForgotPin.setVisibility(View.VISIBLE);
                    mForgotPin.setText(getString(R.string.forgot_pin));
                    mForgotPin.setOnClickListener(mForgotPinListener);
                    YoYo.with(Techniques.FadeInUp).duration(500).playOn(mForgotPin);
                    mDone.setVisibility(View.VISIBLE);
                    mDone.setText(getString(R.string.done));
                    mDone.setOnClickListener(mDoneEnterPin);
                    YoYo.with(Techniques.FadeInUp).duration(500).playOn(mDone);
                    mHeader.setText(getString(R.string.enter_pin));
                    YoYo.with(Techniques.FadeInUp).duration(500).playOn(mHeader);
                    mInput.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                    mInput.setHint(getString(R.string._pin));
                    YoYo.with(Techniques.FadeInUp).duration(500).playOn(mInput);
                    KeyboardUtil.showKeyBoard(mInput, getActivity());
                    mInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                                doneClick();
                                //Toast.makeText(getActivity(),"Done call",Toast.LENGTH_LONG).show();
                            }
                            return false;
                        }
                    });
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }


                break;
            case STATE_RESET:

                try
                {
                    mMessage.setVisibility(View.INVISIBLE);
                    mCancel.setVisibility(View.VISIBLE);
                    mCancel.setText(getString(R.string.cancel));
                    mCancel.setOnClickListener(mCancelListener);

                    mForgotPin.setVisibility(View.GONE);

                    mDone.setVisibility(View.VISIBLE);
                    mDone.setText(getString(R.string.done));
                    mDone.setOnClickListener(mDoneAnswer);

                    if (AppSession.getValue(getActivity(),Constants.USER_SECURITY_QUESTION) != null)
                        mHeader.setText(AppSession.getValue(getActivity(),Constants.USER_SECURITY_QUESTION));

                    YoYo.with(Techniques.FadeInUp).duration(500).playOn(mHeader);

                    KeyboardUtil.hideKeyBoard(mInput, getActivity());
                    mInput.setText("");
                    mInput.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL);
                    mInput.setHint(getString(R.string.enter_your_answer));
                    YoYo.with(Techniques.FadeInUp).duration(500).playOn(mInput);

                    KeyboardUtil.showKeyBoard(mInput, getActivity());
                    mInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                                doneClick();
                                //Toast.makeText(getActivity(),"Done call",Toast.LENGTH_LONG).show();
                            }
                            return false;
                        }
                    });
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                break;
            case STATE_YOUR_PIN:
                try
                {
                    mMessage.setVisibility(View.INVISIBLE);
                    mCancel.setVisibility(View.VISIBLE);
                    mCancel.setText(getString(R.string.reset_pin));
                    mCancel.setOnClickListener(mResetPinListener);

                    mForgotPin.setVisibility(View.GONE);

                    mDone.setVisibility(View.VISIBLE);
                    mDone.setText(getString(R.string.cont));
                    mDone.setOnClickListener(mContinueListener);
                    mHeader.setText(getString(R.string.your_pin_is));
                    YoYo.with(Techniques.FadeInUp).duration(500).playOn(mHeader);
                    if (AppSession.getValue(getActivity(),Constants.USER_PIN) != null) {
                        mMessage.setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.FadeInUp).playOn(mMessage);
                        mMessage.setText(AppSession.getValue(getActivity(),Constants.USER_PIN));
                    }

                    mInput.setVisibility(View.GONE);
                    KeyboardUtil.hideKeyBoard(mInput, getActivity());
                    mInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                                doneClick();
                                //Toast.makeText(getActivity(),"Done call",Toast.LENGTH_LONG).show();
                            }
                            return false;
                        }
                    });
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }


                break;
            case STATE_AGAIN:

                try
                {
                    if (!ConnectionDetector.isNetworkAvailable(getActivity()))
                        return;

                    mMessage.setVisibility(View.INVISIBLE);
                    mCancel.setVisibility(View.VISIBLE);
                    mCancel.setText(getString(R.string.cancel));
                    mCancel.setOnClickListener(mCancelListener);

                    YoYo.with(Techniques.FadeInUp).duration(500).playOn(mCancel);

                    mForgotPin.setVisibility(View.GONE);

                    mDone.setVisibility(View.VISIBLE);
                    mDone.setText(getString(R.string.done));
                    mDone.setOnClickListener(mDoneNewPin);

                    YoYo.with(Techniques.FadeInUp).duration(500).playOn(mDone);

                    mHeader.setText(getString(R.string.enter_new_pin));
                    YoYo.with(Techniques.FadeInUp).duration(500).playOn(mHeader);

                    mInput.setVisibility(View.VISIBLE);
                    YoYo.with(Techniques.FadeInUp).duration(500).playOn(mInput);
                    mInput.setText("");
                    mInput.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                    mInput.setHint(getString(R.string._pin));

                    KeyboardUtil.showKeyBoard(mInput, getActivity());
                    mInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                                doneClick();
                                //Toast.makeText(getActivity(),"Done call",Toast.LENGTH_LONG).show();
                            }
                            return false;
                        }
                    });
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }


                break;
        }
    }









    private void doneClick() {

        try
        {
           // ParseUser user = ParseUser.getCurrentUser();

           /* if (user == null) {
                getActivity().finish();
            }*/

            if (AppSession.getValue(getActivity(),Constants.USER_PIN) == null) {
                getActivity().finish();
                return;
            }


            if (AppSession.getValue(getActivity(),Constants.USER_PIN).equals(mInput.getText().toString())) {
                try {
                    open();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                showMessage(getString(R.string.wrong_pin));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            if (getArguments().getString(Constants.DIALOG_KEY).equals(Constants.ACCESS_MOOD)
                    || getArguments().getString(Constants.DIALOG_KEY).equals(Constants.ACCESS_VERIFY)
                    || getArguments().getString(Constants.DIALOG_KEY).equals(Constants.ACCESS_MY_SECRETS)
                    || getArguments().getString(Constants.DIALOG_KEY).equals(Constants.ACCESS_STATS)
                    || getArguments().getString(Constants.DIALOG_KEY).equals(Constants.ACCESS_CLEAR_SECRET)
                    || getArguments().getString(Constants.DIALOG_KEY).equals(Constants.ACCESS_NOTIFICATIONS)
                    || getArguments().getString(Constants.DIALOG_KEY).equals(Constants.ACCESS_HIGHTLIGHTS)
                    || getArguments().getString(Constants.DIALOG_KEY).equals(Constants.ACCESS_YEAR_COMMENTS)
                    || getArguments().getString(Constants.DIALOG_KEY).equals(Constants.ACCESS_IFRIEND)
                    || getArguments().getString(Constants.DIALOG_KEY).equals(Constants.ACCESS_QR_READER)
                    ||  getArguments().getString(Constants.DIALOG_KEY).equals(Constants.LIVE_SCREEN)
                    ||  getArguments().getString(Constants.DIALOG_KEY).equals(Constants.ACCESS_PENDING_RATING)
                    || getArguments().getString(Constants.DIALOG_KEY).equals(Constants.ACCESS_BOOK_APPONTMENT)
                    ) {

                try {
                    ((MainActivity) getActivity()).hideSearchBar();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        try {
            if (getArguments().getString(Constants.DIALOG_KEY).equals(Constants.ACCESS_MOOD)
                    || getArguments().getString(Constants.DIALOG_KEY).equals(Constants.ACCESS_VERIFY)
                    || getArguments().getString(Constants.DIALOG_KEY).equals(Constants.ACCESS_MY_SECRETS)
                    || getArguments().getString(Constants.DIALOG_KEY).equals(Constants.ACCESS_STATS)
                    || getArguments().getString(Constants.DIALOG_KEY).equals(Constants.ACCESS_CLEAR_SECRET)
                    || getArguments().getString(Constants.DIALOG_KEY).equals(Constants.ACCESS_NOTIFICATIONS)
                    || getArguments().getString(Constants.DIALOG_KEY).equals(Constants.ACCESS_HIGHTLIGHTS)
                    || getArguments().getString(Constants.DIALOG_KEY).equals(Constants.ACCESS_YEAR_COMMENTS)
                    || getArguments().getString(Constants.DIALOG_KEY).equals(Constants.ACCESS_IFRIEND)
                    || getArguments().getString(Constants.DIALOG_KEY).equals(Constants.ACCESS_QR_READER)
                    || getArguments().getString(Constants.DIALOG_KEY).equals(Constants.LIVE_SCREEN)
                    || getArguments().getString(Constants.DIALOG_KEY).equals(Constants.ACCESS_PENDING_RATING)
                    || getArguments().getString(Constants.DIALOG_KEY).equals(Constants.ACCESS_BOOK_APPONTMENT))
                     {

                try{
                    ((MainActivity) getActivity()).showSearchBar();
                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        if (mMessageHandler != null && mMessageRunnable != null) {
            mMessageHandler.removeCallbacks(mMessageRunnable);
        }

        if (mInput != null && mInput.getVisibility() == View.VISIBLE) {
            KeyboardUtil.hideKeyBoard(mInput, getActivity());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.share_root:
//                cancelClick();
//                break;
        }
    }

    private void cancelClick() {
        if (getArguments().getString(Constants.DIALOG_KEY).equals(Constants.ACTION_SECRETS_ACCESS)) {
            ((ActionSecretActivity) getActivity()).runBack();
        } else if (getArguments().getString(Constants.DIALOG_KEY).equals(Constants.ACCESS_COMMENTS_SECRET)) {
            ((CommentSecretActivity) getActivity()).runBack();
        } else if (getArguments().getString(Constants.DIALOG_KEY).equals(Constants.ACCESS_MY_SECRETS_BACKEND)) {
            ((MySecretsActivity) getActivity()).runBack();
        }
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

    View.OnClickListener mCancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            cancelClick();
        }
    };

    View.OnClickListener mDoneNewPin = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try
            {
                if (mInput.getText().toString().isEmpty()) {
                    showMessage(getString(R.string._type_new));
                    return;
                } else
                {
                    if (mInput.getText().toString().length() < 4)
                    {
                        new ToastUtil(getActivity(), getString(R.string.your_pin_is_too_small));
                        return;
                    }

                    loginConditionDTO = new LoginConditionDTO(MainActivity.enc_username, CryptLib.encrypt(mInput.getText().toString()));
                    new LoginUser().execute();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();

            }

        }
    };

    View.OnClickListener mContinueListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                open();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    View.OnClickListener mResetPinListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setupState(STATE_AGAIN);
        }
    };

    View.OnClickListener mForgotPinListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setupState(STATE_RESET);
        }
    };

    View.OnClickListener mDoneEnterPin = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           /* ParseUser user = ParseUser.getCurrentUser();

            if (user == null)
            {
                getActivity().finish();
            }*/

            if (AppSession.getValue(getActivity(),Constants.USER_PIN) == null) {
                getActivity().finish();
                return;
            }


            if (AppSession.getValue(getActivity(),Constants.USER_PIN).equals(mInput.getText().toString())) {
                try {
                    open();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                showMessage(getString(R.string.wrong_pin));
            }

        }
    };

    View.OnClickListener mDoneAnswer = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            if (AppSession.getValue(getActivity(),Constants.USER_SECURITY_ANSWER) == null)
                return;

            if (AppSession.getValue(getActivity(),Constants.USER_SECURITY_ANSWER).equals(mInput.getText().toString())) {
                setupState(STATE_YOUR_PIN);
            } else {
                showMessage(getString(R.string.wrong_answer));
            }
        }
    };

    private void showMessage(String text) {
        YoYo.with(Techniques.Shake).playOn(mInput);
        mMessage.setText(text);
        mMessage.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeInUp).playOn(mMessage);
        mMessageHandler.removeCallbacks(mMessageRunnable);
        mMessageHandler.postDelayed(mMessageRunnable, 3000);
    }


    private void open() throws Exception {
        String type = getArguments().getString(Constants.DIALOG_KEY);

        if (type.equals(Constants.ACCESS_MY_SECRETS))
        {
            ((MainActivity) getActivity()).runMineSecrets(true);
        }
        else if (type.equals(Constants.ACCESS_QR_READER))
        {
            ((MainActivity) getActivity()).runqreader(false);
        }
        else if (type.equals(Constants.LIVE_SCREEN))
        {
            ((MainActivity) getActivity()).Golivescreen(false);
        }
        else if (type.equals(Constants.ACCESS_BOOK_APPONTMENT))
        {
            ((MainActivity) getActivity()).runbookappointment(false);
        }
        else if(type.equals(Constants.ACCESS_PENDING_RATING))
        {
            ((MainActivity) getActivity()).showratingpopup(false);

        }
        else if (type.equals(Constants.ACCESS_CLEAR_SECRET))
        {
            ((MainActivity) getActivity()).runMineSecrets(false);
        } else if (type.equals(Constants.ACTION_SECRETS_ACCESS))
        {
            //((ActionSecretActivity) getActivity()).loadData();
        } else if (type.equals(Constants.ACCESS_COMMENTS_SECRET))
        {
         //   ((CommentSecretActivity) getActivity()).loadData();
        } else if (type.equals(Constants.ACCESS_MY_SECRETS_BACKEND)) {
            ((MySecretsActivity) getActivity()).loadAccessData();
        } else if (type.equals(Constants.ACCESS_NOTIFICATIONS)) {
            ((MainActivity) getActivity()).runNotifications();
        } else if (type.equals(Constants.ACCESS_STATS)) {
            ((MainActivity) getActivity()).runStats();
        } else if (type.equals(Constants.ACCESS_VERIFY)) {
            ((MainActivity) getActivity()).unverifyUser();
        } else if (type.equals(Constants.ACCESS_MOOD)) {
            ((MainActivity) getActivity()).runMood();
        } else if (type.equals(Constants.ACCESS_HIGHTLIGHTS)) {
            ((MainActivity) getActivity()).runHighLights();
        } else if (type.equals(Constants.ACCESS_YEAR_COMMENTS)) {
            ((MainActivity) getActivity()).runYearComments();
        } else if (type.equals(Constants.ACCESS_IFRIEND)) {
            ((MainActivity) getActivity()).runIFriend();
        }

        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

    private class LoginUser extends AsyncTask<String, String, Bitmap> {

        android.app.ProgressDialog pDialog;
        String status = "";
        Bitmap bitmap;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected Bitmap doInBackground(String... args) {
            try {


                IfriendRequest http = new IfriendRequest(getActivity());

                CommonRequestTypeDTO commonRequestTypeDTO = new CommonRequestTypeDTO(loginConditionDTO,"resetPassword");
                FinalObjectDTO loginbjectDTO = new FinalObjectDTO(commonRequestTypeDTO);

                status =  http.ResetPassword(loginbjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image)
        {
            try
            {

                if(status!= null && status.contains("true"))
                {
                    AppSession.save(getActivity(),Constants.USER_PIN,mInput.getText().toString());
                    open();

                }
                else
                {
                    Toast.makeText(getActivity(), status ,Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }
}
