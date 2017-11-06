package com.helio.silentsecret.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.helio.silentsecret.R;
import com.helio.silentsecret.activities.MainActivity;
import com.helio.silentsecret.activities.MoodActivity;
import com.helio.silentsecret.activities.RoomsActivity;
import com.helio.silentsecret.activities.SignUpDialogActivity;
import com.helio.silentsecret.application.SilentSecretApplication;
import com.helio.silentsecret.connection.ConnectionDetector;
import com.helio.silentsecret.controller.Controller;
import com.helio.silentsecret.utils.AppSession;
import com.helio.silentsecret.utils.Constants;
import com.helio.silentsecret.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import static com.helio.silentsecret.activities.MainActivity.ct;

public class GlimpseFragment extends Fragment implements MainActivity.OnReplace, View.OnClickListener {

    private View mView;
    private View moodView;
    private View bookview;




    private List<View> moods = new ArrayList<>();


    public static int count;






    public static int sesstion_time = 0;

    @Override
    public void onClick(View view)
    {
        String userna = AppSession.getValue(getActivity(), Constants.USER_NAME);
        switch (view.getId())
        {


            case R.id.glimpse_mood_clickable:

                if (userna == null || userna.equalsIgnoreCase(""))
                {
                    Intent intent = new Intent(getActivity(), SignUpDialogActivity.class);
                    startActivity(intent);
                }
                else {
                    if (ConnectionDetector.isNetworkAvailable(getActivity()))
                    {
                        Controller.runGlimpse(Controller.MOOD, getActivity(), true);
                    }
                    else new ToastUtil(getActivity(),Constants.NETWORK_FAILER);

                }


                break;

            case R.id.glimpse_counsell_clickable:

                if (userna == null || userna.equalsIgnoreCase(""))
                {
                    Intent intent = new Intent(getActivity(), SignUpDialogActivity.class);
                    startActivity(intent);
                }
                else {

                    if (ConnectionDetector.isNetworkAvailable(getActivity()))
                    {
                        Controller.runGlimpse(Controller.COUNSELL, getActivity(), true);
                    }
                    else new ToastUtil(getActivity(),Constants.NETWORK_FAILER);

                }


                break;
            case R.id.glimpse_mediator:

                if (userna == null || userna.equalsIgnoreCase(""))
                {
                    Intent intent = new Intent(getActivity(), SignUpDialogActivity.class);
                    startActivity(intent);
                }
                else {
                    if (ConnectionDetector.isNetworkAvailable(getActivity()))
                    {
                        ((MainActivity) getActivity()).AccessMediator();
                    }
                    else new ToastUtil(getActivity(),Constants.NETWORK_FAILER);
                }



                break;
            case R.id.glimpse_rooms:

                if (userna == null || userna.equalsIgnoreCase(""))
                {
                    Intent intent = new Intent(getActivity(), SignUpDialogActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(getActivity(), RoomsActivity.class);
                    startActivity(intent);
                   // Controller.runGlimpse(Controller.ROOM, getActivity(), true);
                }





                break;
            case R.id.glimpse_mysecret_clickable:
                Controller.runGlimpse(Controller.ACCESS_MY_SECRET, getActivity(), true);
                break;
        }
    }

    private enum StyleType {
        BUBBLES,
        SQUARES
    }

    @Override
    public void onResume() {
        super.onResume();
        if(count > 0)
        {

        }
    }

    private StyleType styleType = StyleType.BUBBLES;

    @Override
    public void runMood() {
        startActivity(new Intent(getActivity(), MoodActivity.class));
    }


    @Override
    public void runRoom() {
        startActivity(new Intent(getActivity(), RoomsActivity.class));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_glimpse, null);

        ((MainActivity) getActivity()).mMoodRunner = this;



        attachListeners();


        return mView;
    }




    private void attachListeners() {
        mView.findViewById(R.id.glimpse_mood_clickable).setOnClickListener(this);
        mView.findViewById(R.id.glimpse_counsell_clickable).setOnClickListener(this);
        mView.findViewById(R.id.glimpse_rooms).setOnClickListener(this);
        mView.findViewById(R.id.glimpse_mysecret_clickable).setOnClickListener(this);
        mView.findViewById(R.id.glimpse_mediator).setOnClickListener(this);


    }



    protected void startTracking(String path) {
        Tracker t = ((SilentSecretApplication) ct.getApplicationContext()).getTracker(
                SilentSecretApplication.TrackerName.APP_TRACKER);
        t.setScreenName(path);
        t.send(new HitBuilders.AppViewBuilder().build());
    }
}
