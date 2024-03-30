package com.sergiu.libihb_java.presentation.fragment.add_memory;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private final OnDateSelectedListener onDateSelectedListener;
    public interface OnDateSelectedListener {
        void onDateSelected(int year, int month, int day);
    }

    public DatePicker(OnDateSelectedListener onDateSelectedListener){
        this.onDateSelectedListener = onDateSelectedListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(requireContext(), this, year, month, day);
    }

    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day) {
        onDateSelectedListener.onDateSelected(year, month, day);
    }
}