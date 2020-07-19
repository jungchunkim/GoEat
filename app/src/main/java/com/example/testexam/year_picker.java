package com.example.testexam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.Calendar;

public class year_picker extends DialogFragment { // 생년월일 팝업 창

    private static final int MAX_YEAR = 2099;
    private static final int MIN_YEAR = 1980;

    private DatePickerDialog.OnDateSetListener listener;
    public Calendar cal = Calendar.getInstance();

    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }


    Button btnConfirm;
    Button btnCancel;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialog = inflater.inflate(R.layout.activity_year_picker, null);

        btnConfirm = dialog.findViewById(R.id.btn_confirm);
        btnCancel = dialog.findViewById(R.id.btn_cancel);

        final NumberPicker picker_month = (NumberPicker) dialog.findViewById(R.id.picker_month);
        final NumberPicker picker_year = (NumberPicker) dialog.findViewById(R.id.picker_year);
        final NumberPicker picker_date = (NumberPicker) dialog.findViewById(R.id.picker_date);


        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                year_picker.this.getDialog().cancel();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                listener.onDateSet(null, picker_year.getValue(), picker_month.getValue(), picker_date.getValue());
                year_picker.this.getDialog().cancel();
            }
        });

        picker_month.setMinValue(1);
        picker_month.setMaxValue(12);
        picker_month.setValue(cal.get(Calendar.MONTH) + 1);

        picker_date.setMinValue(1);
        picker_date.setMaxValue(31);
        picker_date.setValue(cal.get(Calendar.DATE));



        int year = cal.get(Calendar.YEAR);
        picker_year.setMinValue(MIN_YEAR);
        picker_year.setMaxValue(MAX_YEAR);
        picker_year.setValue(year);

        builder.setView(dialog);
        // Add action buttons
        /*
        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                listener.onDateSet(null, yearPicker.getValue(), monthPicker.getValue(), 0);
            }
        })
        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                MyYearMonthPickerDialog.this.getDialog().cancel();
            }
        })
        */


        return builder.create();
    }
}