package com.helio.cypher.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.helio.cypher.R;
import com.helio.cypher.utils.Preference;

public class CreateDisclaimer {

    private static AlertDialog disclaimer;

    public CreateDisclaimer(final Context context) {
        if (Preference.getUserDisclaimer())
            return;

        if (disclaimer != null)
            if (disclaimer.isShowing())
                return;

        disclaimer = new AlertDialog.Builder(context).create();
        disclaimer.setTitle(context.getString(R.string.disclaimer_title));
        disclaimer.setMessage(context.getString(R.string.disclaimer_text));
        disclaimer.setCancelable(false);

        disclaimer.setButton(DialogInterface.BUTTON_POSITIVE, context.getString(android.R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Preference.saveUserDisclaimer(true);
            }
        });

        disclaimer.setButton(DialogInterface.BUTTON_NEUTRAL, context.getString(android.R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                disclaimer.dismiss();
              /*  new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        new CreateDisclaimer(context);
                    }
                }, 500);*/
            }
        });

        try {
            disclaimer.show();
        } catch (Exception e) {
        }

    }
}
