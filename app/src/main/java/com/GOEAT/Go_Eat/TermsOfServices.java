package com.GOEAT.Go_Eat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.Arrays;

public class TermsOfServices extends AppCompatActivity {

    //cp_1,cp_5
    TextView click_see_2,click_see_3,click_see_4;
    CheckBox  cp_2 ,cp_3 ,cp_4,cp_up14 ,cp_all;
    int checkAll = 0;  // 전체동의 클릭 여부 담은 변수
    int[] agreement = {0, 0, 0, 0, 0};  //5개의 약관 동의 여부 담은 배열
    int up14 = 0;  // 만14세 이상 체크여부
    Button btn_next;
    int checkEssential = 0;  // 필수약관 동의 확인


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_of_services);

//      cp_1 = findViewById(R.id.cp_1);
//      cp_5 = findViewById(R.id.cp_5);
        cp_2 = findViewById(R.id.cp_2);
        cp_3 = findViewById(R.id.cp_3);
        cp_4 = findViewById(R.id.cp_4);
        click_see_2=findViewById(R.id.click_see_2);
        click_see_3=findViewById(R.id.click_see_3);
        click_see_4=findViewById(R.id.click_see_4);


        cp_up14 = findViewById(R.id.cp_up14);
        cp_all = findViewById(R.id.cp_all);
        btn_next = findViewById(R.id.btn_next);

        //2020-12-01 김정천 보기 버튼 눌렀을 때 이동
        click_see_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Service_Detail_2.class);
                startActivity(intent);
            }
        });

        click_see_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Service_Detail_3.class);
                startActivity(intent);
            }
        });

        click_see_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Service_Detail_4.class);
                startActivity(intent);
            }
        });

        // 전체동의버튼 눌렀을때 모든 항목 체크됨
        cp_all.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkAll==0){
//                  cp_1.setChecked(true);
                    cp_2.setChecked(true);
                    cp_3.setChecked(true);
                    cp_4.setChecked(true);
//                  cp_5.setChecked(true);
                    checkAll=1;
                }
                else{
//                  cp_1.setChecked(false);
                    cp_2.setChecked(false);
                    cp_3.setChecked(false);
                    cp_4.setChecked(false);
//                  cp_5.setChecked(false);
                    checkAll=0;
                }

            }
        });

//        cp_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked){
//                    agreement[0]=1;
//                    checkEssential();
//               }
//                else{
//                    agreement[0]=0;
//                    checkEssential();
//                }
//            }
//        });

        cp_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    agreement[1]=1;
                    checkEssential();
                }
                else{
                    agreement[1]=0;
                    checkEssential();
                }
            }
        });

        cp_3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    agreement[2]=1;
                    checkEssential();
                }
                else{
                    agreement[2]=0;
                    checkEssential();
                }
            }
        });

        cp_4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    agreement[3]=1;
                    checkEssential();
                }
                else{
                    agreement[3]=0;
                    checkEssential();
                }
            }
        });

//        cp_5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked){
//                    agreement[4]=1;
//                    checkEssential();
//               }
//                else{
//                    agreement[4]=0;
//                    checkEssential();
//               }
//            }
//        });

        cp_up14.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    up14=1;
                    checkEssential();
                }
                else{
                    up14=0;
                    checkEssential();
                }
            }
        });


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("동의 여부", Arrays.toString(agreement));
                Log.e("14세이상 여부", String.valueOf(up14));

                if(checkEssential==1){
                    Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                    startActivity(intent);
                }


            }




        });


    }

    //필수약관 동의여부 확인
    private void checkEssential() {
        // 3가지 약관에 모두 동의하고 14세 이상이라면
        if(agreement[0]==1&&agreement[1]==1&agreement[2]==1&&up14==1){
            // 다음 버튼 background 변경
            btn_next.setBackgroundResource(R.drawable.button_background);
            Log.e("필수약관 동의", "true");
            checkEssential = 1;
        }
        else{
            checkEssential=0;
            btn_next.setBackgroundResource(R.drawable.button_background2);
        }
    }


}