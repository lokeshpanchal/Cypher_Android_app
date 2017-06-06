package com.helio.cypher.dialogs;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.helio.cypher.EncryptionDecryption.CryptLib;
import com.helio.cypher.R;
import com.helio.cypher.WebserviceDTO.ForgetDTO;
import com.helio.cypher.WebserviceDTO.ForgetObjectDTO;
import com.helio.cypher.WebserviceDTO.ForgetConditionDTO;
import com.helio.cypher.connection.IfriendRequest;
import com.helio.cypher.utils.AppSession;
import com.helio.cypher.utils.Constants;
import com.helio.cypher.utils.KeyboardUtil;

public class ForgotPinDialog extends Fragment implements View.OnClickListener {

    private View mView;
    private View mOutView;

    Context ct ;
    private TextView mDone;
    private TextView mCancel;

    private TextView mHeader;
    private TextView mMessage;
    private EditText mInput;

    ForgetConditionDTO forgetConditionDTO = null;
    private final int STATE_ENTER = 0;
    private final int STATE_ANSWER = 1;
    private final int STATE_YOUR_PIN = 2;

    private int STATE = STATE_ENTER;

    private Handler mMessageHandler;
    private Runnable mMessageRunnable;




    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ct = getActivity();
        mView = inflater.inflate(R.layout.forgot_pin_dialog, null);

        mOutView = mView.findViewById(R.id.share_root);
        mOutView.setOnClickListener(this);

        YoYo.with(Techniques.FadeIn).duration(1500).playOn(mOutView);

        mDone = (TextView) mView.findViewById(R.id.dialog_done);
        mCancel = (TextView) mView.findViewById(R.id.dialog_cancel);

        mHeader = (TextView) mView.findViewById(R.id.dialog_header);
        mMessage = (TextView) mView.findViewById(R.id.dialog_message);
        mInput = (EditText) mView.findViewById(R.id.dialog_text);

        mView.findViewById(R.id.dialog_forgot).setVisibility(View.GONE);

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

        mCancel.setVisibility(View.VISIBLE);
        mCancel.setText(getString(R.string.cancel));
        mCancel.setOnClickListener(mCancelListener);

        mDone.setVisibility(View.VISIBLE);
        mDone.setText(getString(R.string.done));
        mDone.setOnClickListener(mDoneUsername);

        setupState(STATE);
        return mView;
    }

    private void setupState(int state) {
        KeyboardUtil.hideKeyBoard(mInput, getActivity());

        switch (state) {
            case STATE_ENTER:
                try
                {
                    mMessage.setVisibility(View.INVISIBLE);
                    YoYo.with(Techniques.FadeInUp).duration(500).playOn(mCancel);
                    YoYo.with(Techniques.FadeInUp).duration(500).playOn(mDone);
                    mHeader.setText(getString(R.string.enter_username));
                    YoYo.with(Techniques.FadeInUp).duration(500).playOn(mHeader);
                    mInput.setText("");
                    mInput.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL);
                    mInput.setHint(getString(R.string.username_hint));
                    YoYo.with(Techniques.FadeInUp).duration(500).playOn(mInput);
                    KeyboardUtil.showKeyBoard(mInput, getActivity());
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                break;
            case STATE_ANSWER:
                try
                {
                    mMessage.setVisibility(View.INVISIBLE);

                    /*if (user.getString(Constants.USER_SECURITY_QUESTION) != null)
                        mHeader.setText(user.getString(Constants.USER_SECURITY_QUESTION));
*/


                    mHeader.setText(AppSession.getValue(ct,Constants.USER_SECURITY_QUESTION));
                    YoYo.with(Techniques.FadeInUp).duration(500).playOn(mHeader);

                    mDone.setOnClickListener(mDoneAnswer);

                    KeyboardUtil.hideKeyBoard(mInput, getActivity());
                    mInput.setText("");
                    mInput.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL);
                    mInput.setHint(getString(R.string.enter_your_answer));
                    YoYo.with(Techniques.FadeInUp).duration(500).playOn(mInput);

                    KeyboardUtil.showKeyBoard(mInput, getActivity());
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

                    mHeader.setText(getString(R.string.your_pin_is));
                    YoYo.with(Techniques.FadeInUp).duration(500).playOn(mHeader);
                 //   if (user.getString(Constants.USER_PIN) != null)
                  //  {
                        mMessage.setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.FadeInUp).playOn(mMessage);
                        mMessage.setText(AppSession.getValue(ct,Constants.USER_PIN));
                    //}
                    mInput.setVisibility(View.GONE);
                    KeyboardUtil.hideKeyBoard(mInput, getActivity());

                    mDone.setOnClickListener(mCancelListener);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                break;

        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

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
            case R.id.share_root:
                cancelClick();
                break;
        }
    }

    private void cancelClick() {
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

    View.OnClickListener mCancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            cancelClick();
        }
    };


    View.OnClickListener mDoneUsername = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (mInput.getText().toString().isEmpty())
                return;

            try
            {
                forgetConditionDTO = new ForgetConditionDTO(CryptLib.encrypt(mInput.getText().toString()));

            }
            catch (Exception e)
            {

            }

            new ForgetPasswordUser().execute();

           // findUserCallback();
        }
    };



    private class ForgetPasswordUser extends AsyncTask<String, String, Bitmap> {

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

                ForgetDTO loginDTO = new ForgetDTO(forgetConditionDTO,Constants.ENCRYPT_USER_TABLE,Constants.ENCRYPT_FIND_METHOD);
                ForgetObjectDTO loginbjectDTO = new ForgetObjectDTO(loginDTO);

                status =  http.ForgetUser(loginbjectDTO);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap image)
        {

           if(status!= null && status.equalsIgnoreCase("success"))
           {
               setupState(STATE_ANSWER);
           }
            else
               Toast.makeText(ct, status, Toast.LENGTH_SHORT).show();
        }
    }



    private void findUserCallback()
    {

        /*ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereMatches(Constants.USER_NAME, mInput.getText().toString(), "i");
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> objects, ParseException e)
            {
                if (e == null && objects != null && objects.size() > 0) {
                    user = objects.get(0);
                    setupState(STATE_ANSWER);
                }
            }
        });*/
    }

    View.OnClickListener mDoneAnswer = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try
            {
                if (mInput.getText().toString().isEmpty())
                    return;

                if (AppSession.getValue(ct,Constants.USER_SECURITY_ANSWER).equals(mInput.getText().toString())) {
                    setupState(STATE_YOUR_PIN);
                } else {
                    showMessage(getString(R.string.wrong_answer));
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
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




}
