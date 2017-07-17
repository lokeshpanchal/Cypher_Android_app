package com.helio.silentsecret.connection;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

import com.helio.silentsecret.R;

public class ConnectionDetector {

    private static AlertDialog connectDialog;

    public static boolean isNetworkAvailable123(Context context) {
        ConnectivityManager check = (ConnectivityManager) context.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        if (check != null) {
            NetworkInfo[] info = check.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                showErrorDialog(context);
                return false;
            } else {
                showErrorDialog(context);
                return false;
            }
        } else {
            showErrorDialog(context);
            return false;
        }
    }

    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager check = (ConnectivityManager) context.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        if (check != null) {
            NetworkInfo[] info = check.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                return false;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    private static void showErrorDialog(final Context context) {

        if (connectDialog != null)
            if (connectDialog.isShowing())
                return;

        connectDialog = new AlertDialog.Builder(context).create();
        connectDialog.setTitle(context.getString(R.string.warning));
        connectDialog.setMessage(context.getString(R.string.missing));

        connectDialog.setButton(DialogInterface.BUTTON_POSITIVE, context.getString(R.string.settings), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent myIntent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                context.startActivity(myIntent);
            }
        });

        connectDialog.setButton(DialogInterface.BUTTON_NEGATIVE, context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        try {

        } catch (Exception e) {
            connectDialog.show();
        }
    }



    public static boolean isNetworkAvailable(Context context) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            android.net.NetworkInfo networkinfo = cm.getActiveNetworkInfo();
            if (networkinfo != null && networkinfo.isConnected()) {
                return true;
            }
            return false;
        }

}
