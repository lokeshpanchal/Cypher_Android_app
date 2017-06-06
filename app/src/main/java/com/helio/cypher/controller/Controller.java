package com.helio.cypher.controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.helio.cypher.R;
import com.helio.cypher.activities.BaseActivity;
import com.helio.cypher.activities.MainActivity;
import com.helio.cypher.connection.ConnectionDetector;
import com.helio.cypher.fragments.FeeListFragment;
import com.helio.cypher.utils.Constants;
import com.helio.cypher.utils.Preference;
import com.helio.cypher.utils.ToastUtil;


public class Controller {

    public static final int MOOD = 0;
    public static final int ENTER = 1;
    public static final int LIFE = 2;
    public static final int NEWS = 3;
    public static final int HELP = 4;
    public static final int COUNSELL = 5;

    public static void runGlimpse(int action, Context context, boolean fromGlimpse) {
        String title = null;

        switch (action) {
            case MOOD:
                ((MainActivity) context).moodAccess();
                break;
            case ENTER:
                try
                {
                    title = context.getString(R.string.enter_track);
                    ((MainActivity) context).replaceFragmentInside(
                            FeeListFragment.instance(Constants.ENTERTAINMENT, BaseActivity.defaultRules, 0, fromGlimpse), fromGlimpse);

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                    break;
            case NEWS:
                title = context.getString(R.string.news_track);
                ((MainActivity) context).replaceFragmentInside(
                        FeeListFragment.instance(Constants.NEWS, BaseActivity.defaultRules, 0, fromGlimpse), fromGlimpse);
                break;
            case LIFE:
                title = context.getString(R.string.life_track);
                ((MainActivity) context).replaceFragmentInside(
                        FeeListFragment.instance(Constants.LIFESTYLE, BaseActivity.defaultRules, 0, fromGlimpse), fromGlimpse);
                break;
            case COUNSELL:

                if (ConnectionDetector.isNetworkAvailable(context))
                {
                    ((MainActivity) context).QRAccess();
                } else {
                    new ToastUtil(context, "Please check your internet connection.");
                }

           /*     Intent intent = new Intent(context,CameraTestActivity.class);
                context.startActivity(intent);*/
                  break;
            case HELP:
               if (!Preference.hasHelped())
                {
                    showBanner(context);
                } else {
                     proceedHelp(context);
                }
                break;
        }

        if (title != null) {
          /*  ObjectMaker.formTrack(ParseUser.getCurrentUser(),
                    title, null).saveInBackground();*/
        }
    }

    private static void showBanner(final Context context) {
        AlertDialog banner = new AlertDialog.Builder(context).create();
        banner.setMessage(context.getString(R.string.help_alert_text));

        banner.setButton(DialogInterface.BUTTON_POSITIVE, context.getString(android.R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Preference.setHasHelped(true);
                proceedHelp(context);
                dialog.dismiss();
            }
        });

        try {
            banner.setCanceledOnTouchOutside(false);
            banner.show();
        } catch (Exception e) {
        }
    }

    private static void proceedHelp(Context context)
    {

      /*  if (ActionChecker.checkDailyHelp())
        {
            HelpController.shareHelpResults(context);
        } else {
            new ToastUtil(context, context.getString(R.string.check_again_tomorrow));
        }*/
    }
}
