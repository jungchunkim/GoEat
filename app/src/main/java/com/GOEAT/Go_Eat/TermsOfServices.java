package com.GOEAT.Go_Eat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.Arrays;

public class TermsOfServices extends AppCompatActivity {

    CheckBox cp_1, cp_2 ,cp_3 ,cp_4 ,cp_5 ,cp_up14 ,cp_all;
    int checkAll = 0;
    int[] agreement = {0, 0, 0, 0, 0};  //5개의 약관 동의 여부 담은 배열
    int up14 = 0;  // 만14세 이상 체크여부
    Button btn_next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_of_services);

        cp_1 = findViewById(R.id.cp_1);
        cp_2 = findViewById(R.id.cp_2);
        cp_3 = findViewById(R.id.cp_3);
        cp_4 = findViewById(R.id.cp_4);
        cp_5 = findViewById(R.id.cp_5);
        cp_up14 = findViewById(R.id.cp_up14);
        cp_all = findViewById(R.id.cp_all);
        btn_next = findViewById(R.id.btn_next);

        // 전체동의버튼 눌렀을때 모든 항목 체크됨
        cp_all.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkAll==0){
                    cp_1.setChecked(true);
                    cp_2.setChecked(true);
                    cp_3.setChecked(true);
                    cp_4.setChecked(true);
                    cp_5.setChecked(true);
                    checkAll=1;
                }
                else{
                    cp_1.setChecked(false);
                    cp_2.setChecked(false);
                    cp_3.setChecked(false);
                    cp_4.setChecked(false);
                    cp_5.setChecked(false);
                    checkAll=0;
                }

            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cp_1.isChecked())
                    agreement[0]=1;
                if(cp_2.isChecked())
                    agreement[1]=1;
                if(cp_3.isChecked())
                    agreement[2]=1;
                if(cp_4.isChecked())
                    agreement[3]=1;
                if(cp_5.isChecked())
                    agreement[4]=1;

                if(cp_up14.isChecked())
                    up14=1;

                Log.e("동의 여부", Arrays.toString(agreement));
                Log.e("14세이상 여부", String.valueOf(up14));

            }
        });

    }


}