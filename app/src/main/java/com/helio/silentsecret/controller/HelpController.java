package com.helio.silentsecret.controller;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.widget.Toast;

import com.helio.silentsecret.R;
import com.helio.silentsecret.activities.MainActivity;
import com.helio.silentsecret.utils.ToastUtil;


import java.util.List;

public class HelpController {





    public static void shareHelpResults(Context context) {


        if (MainActivity.users == null || MainActivity.users.isEmpty()) {
            new ToastUtil(context, context.getString(R.string.you_cant_help_zero));
            return;
        }

        if (shareHelp(context, MainActivity.users.size()))
        {
            MainActivity.users = null;
        }
    }

    private static void openTwitter(Context cntx) {
        try {
            cntx.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.twitter.android")));
        } catch (android.content.ActivityNotFoundException anfe) {

        }
    }

    private static boolean shareHelp(Context context, int count) {
        final Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        String text;
        if (count == 1)
        {
            text = context.getString(R.string.help_one_person);
        } else
        {
            text = String.format(context.getString(R.string.help_share_text), count);
        }

        Intent twitterIntent = getShareIntent("twitter", context, text);
        if (twitterIntent == null)
        {
            openTwitter(context);
            Toast.makeText(context, "Twitter application not found", Toast.LENGTH_SHORT).show();
            return false;
        }
        context.startActivity(twitterIntent);
        return true;
    }

    private static Intent getShareIntent(String type, Context context, String text) {
        boolean found = false;
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");


        List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(share, 0);
        System.out.println("resinfo: " + resInfo);
        if (!resInfo.isEmpty()) {
            for (ResolveInfo info : resInfo) {
                if (info.activityInfo.packageName.toLowerCase().contains(type) ||
                        info.activityInfo.name.toLowerCase().contains(type)) {
                    share.putExtra(Intent.EXTRA_TEXT, text);


                    Uri uri = Uri.parse("android.resource://com.helio.silentsecret/drawable/ic_launcher");
                    share.putExtra(Intent.EXTRA_STREAM, uri);
                    share.setType("image/png");

                    share.setPackage(info.activityInfo.packageName);
                    found = true;
                    break;
                }
            }
            if (!found)
                return null;

            return share;
        }
        return null;
    }


}
