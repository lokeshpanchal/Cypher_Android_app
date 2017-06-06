package com.helio.cypher.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

    public ToastUtil(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
