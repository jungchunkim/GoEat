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
import android.widget.Toast;

import java.util.Calendar;

public class gender_year_picker extends DialogFragment { // 생년월일 팝업 창

    private static final int MAX_YEAR = 2099;
    private static final int MIN_YEAR = 1980;

    private DatePickerDialog.OnDateSetListener listener;
    public Calendar cal = Calendar.getInstance();



    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }


    Button btn_pop_confirm;
    Button btn_pop_cancel;
    private TextView tv_pop_men, tv_pop_women;
    private int gender = 0;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialog = inflater.inflate(R.layout.activity_gender_year_picker, null);

        btn_pop_confirm = dialog.findViewById(R.id.btn_pop_confirm);
        btn_pop_cancel = dialog.findViewById(R.id.btn_pop_cancel);
        tv_pop_men =(TextView) dialog.findViewById(R.id.tv_pop_men);
        tv_pop_women =(TextView) dialog.findViewById(R.id.tv_pop_women);
        final NumberPicker picker_pop_month = (NumberPicker) dialog.findViewById(R.id.picker_pop_month);
        final NumberPicker picker_pop_year = (NumberPicker) dialog.findViewById(R.id.picker_pop_year);
        final NumberPicker picker_pop_date = (NumberPicker) dialog.findViewById(R.id.picker_pop_date);

        tv_pop_women.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {  // 여자 버튼 클릭
                tv_pop_women.setBackground(getResources().getDrawable(R.drawable.round_button));
                tv_pop_men.setBackground(getResources().getDrawable(R.drawable.edge));
                gender=1;
            }
        });

        tv_pop_men.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 남자 버튼 클릭
                tv_pop_men.setBackground(getResources().getDrawable(R.drawable.round_button));
                tv_pop_women.setBackground(getResources().getDrawable(R.drawable.edge));
                gender=2;
            }
        });
        btn_pop_cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                gender_year_picker.this.getDialog().cancel();
            }
        });

        btn_pop_confirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(gender == 0){
                    Toast.makeText(getActivity(),"성별을 선택해 주세요",Toast.LENGTH_LONG).show();
                }else {
                    listener.onDateSet(null, Integer.parseInt(picker_pop_year.getValue()+""+gender), picker_pop_month.getValue() , picker_pop_date.getValue());
                }
            }
        });

        picker_pop_month.setMinValue(1);
        picker_pop_month.setMaxValue(12);
        picker_pop_month.setValue(cal.get(Calendar.MONTH) + 1);

        picker_pop_date.setMinValue(1);
        picker_pop_date.setMaxValue(31);
        picker_pop_date.setValue(cal.get(Calendar.DATE));



        int year = cal.get(Calendar.YEAR);
        picker_pop_year.setMinValue(MIN_YEAR);
        picker_pop_year.setMaxValue(MAX_YEAR);
        picker_pop_year.setValue(year);

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