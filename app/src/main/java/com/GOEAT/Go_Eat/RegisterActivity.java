package com.GOEAT.Go_Eat;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    final int DIALOG_DATE = 1;
    EditText et_name, et_email, et_pwd1, et_pwd2, et_phoneNum;
    Button btn_female, btn_male, btn_send;
    TextView tv_birth;
    private String usergender = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        et_pwd1 = findViewById(R.id.et_pwd1);
        et_pwd2 = findViewById(R.id.et_pwd2);
        et_phoneNum = findViewById(R.id.et_phoneNum);
        btn_female = findViewById(R.id.btn_female);
        btn_male = findViewById(R.id.btn_male);
        btn_send = findViewById(R.id.btn_send);
        tv_birth = findViewById(R.id.tv_birth);

        tv_birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DIALOG_DATE);
            }
        });

        btn_female.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                btn_female.setBackgroundColor(Color.rgb(224,39,74));
                btn_male.setBackgroundColor(Color.WHITE);
                usergender = "여";
            }
        });

        btn_male.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                btn_male.setBackgroundColor(Color.rgb(224,39,74));
                btn_female.setBackgroundColor(Color.WHITE);
                usergender = "남";
            }
        });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 비밀번호 일치
                if(et_pwd1.getText().toString().equals(et_pwd2.getText().toString())){

                    // 비밀번호가 8자리 미만인 경우
                    if (!(et_pwd1.getText().toString().length() >= 8))
                        Toast.makeText(getApplicationContext(),"비밀번호는 8자 이상이어야 합니다",Toast.LENGTH_LONG).show();
                    else{

                        // 서버에 저장하는 코드 (작성해야함!!)

                        

                        // 액티비티 이동
                        Intent intent = new Intent(RegisterActivity.this, RegAuthActivity.class);
                        startActivity(intent);
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    @Override
    protected Dialog onCreateDialog(int id) {

        switch (id){
            case DIALOG_DATE:
                DatePickerDialog dpd = new DatePickerDialog
                        (RegisterActivity.this, // 현재화면의 제어권자
                                new DatePickerDialog.OnDateSetListener() {
                                    public void onDateSet(DatePicker view,
                                                          int year, int monthOfYear, int dayOfMonth) {

                                        tv_birth.setText(year+"년 " + (monthOfYear+1) + "월 " + dayOfMonth + "일");
                                    }
                                }
                                , // 사용자가 날짜설정 후 다이얼로그 빠져나올때
                                //    호출할 리스너 등록
                                2000, 1, 1); // 기본값 연월일
                return dpd;


        }

        return super.onCreateDialog(id);
    }
}