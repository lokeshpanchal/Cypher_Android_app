package com.helio.cypher.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.helio.cypher.R;

public class AttentionComments {

    private static AlertDialog warning;

    public AttentionComments(final Context context) {
        if (warning != null)
            if (warning.isShowing())
                return;

        warning = new AlertDialog.Builder(context).create();
        warning.setTitle(context.getString(R.string.attention));
        warning.setMessage(context.getString(R.string.create_warning));
        warning.setCancelable(false);

        warning.setButton(DialogInterface.BUTTON_POSITIVE, context.getString(android.R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        try {
            warning.show();
        } catch (Exception e) {
        }
}
}
