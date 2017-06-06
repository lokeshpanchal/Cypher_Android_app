package com.helio.cypher.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.helio.cypher.activities.CreateSecretActivity;
import com.helio.cypher.activities.StatsActivity;

import java.util.Calendar;

public class CreateSecretDatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog =    new DatePickerDialog(getActivity(), this, year, month, day);
        Calendar c2 = Calendar.getInstance();
        c2.set(2006, month, day);

        datePickerDialog.getDatePicker().setMaxDate(c2.getTimeInMillis());
        Calendar c1 = Calendar.getInstance();
        c1.set(1943, month, day);

        datePickerDialog.getDatePicker().setMinDate(c1.getTimeInMillis());

        return datePickerDialog;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        dismiss();
        ((CreateSecretActivity) getActivity()).saveAndLoadDate(cal.getTime());
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }
}
