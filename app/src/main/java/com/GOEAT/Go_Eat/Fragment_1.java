package com.GOEAT.Go_Eat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;


public class Fragment_1 extends Fragment {

    private Context context;
    private int num=0;

    // 위치 정보 선택 창 2020-09-20 김정천
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public Fragment_1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_1, container, false);

        context=container.getContext();
        final Button location_1 = (Button) view.findViewById(R.id.location_1);
        final Button location_2 = (Button) view.findViewById(R.id.location_2);
        final Button location_3 = (Button) view.findViewById(R.id.location_3);
        final Button location_4 = (Button) view.findViewById(R.id.location_4);
        final Button location_5 = (Button) view.findViewById(R.id.location_5);
        final Button location_6 = (Button) view.findViewById(R.id.location_6);
        final Button location_7 = (Button) view.findViewById(R.id.location_7);


        location_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(num==0) {
                    num = 1;
                    changeBtnBackground(location_1);
                }else if(num==1){
                    num=0;
                    reChangeBtnBackground(location_1);
                }
                else {
                    Toast.makeText(context,"하나만 선택하세요",Toast.LENGTH_SHORT).show();
                }


            }
        });
        location_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(num==0) {
                    num = 2;
                    changeBtnBackground(location_2);
                }else if(num==2){
                    num=0;
                    reChangeBtnBackground(location_2);
                }
                else{
                    Toast.makeText(context,"하나만 선택하세요",Toast.LENGTH_SHORT).show();
                }
            }
        });
        location_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(num==0) {
                    num = 3;
                    changeBtnBackground(location_3);
                }else if(num==3){
                    num=0;
                    reChangeBtnBackground(location_3);
                }
                else{
                    Toast.makeText(context,"하나만 선택하세요",Toast.LENGTH_SHORT).show();
                }
            }
        });
        location_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(num==0) {
                    num = 4;
                    changeBtnBackground(location_4);
                }else if(num==4){
                    num=0;
                    reChangeBtnBackground(location_4);
                }else{
                    Toast.makeText(context,"하나만 선택하세요",Toast.LENGTH_SHORT).show();
                }
            }
        });
        location_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(num==0) {
                    num = 5;
                    changeBtnBackground(location_5);
                }else if(num==5){
                    num=0;
                    reChangeBtnBackground(location_5);
                }else {
                    Toast.makeText(context,"하나만 선택하세요",Toast.LENGTH_SHORT).show();
                }
            }
        });
        location_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(num==0) {
                    num = 6;
                    changeBtnBackground(location_6);
                }else if(num==6){
                    num=0;
                    reChangeBtnBackground(location_6);
                }else{
                    Toast.makeText(context,"하나만 선택하세요",Toast.LENGTH_SHORT).show();
                }
            }
        });
        location_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(num==0) {
                    num = 7;
                    changeBtnBackground(location_7);
                }else if(num==7){
                    num=0;
                    reChangeBtnBackground(location_7);
                }else{
                    Toast.makeText(context,"하나만 선택하세요",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }


    private void reChangeBtnBackground(Button btn) {

        btn.setBackgroundResource(R.drawable.location_button);
        btn.setTextColor(getResources().getColorStateList(R.color.black));

    }

    private void changeBtnBackground(Button btn) {
        btn.setBackgroundResource(R.drawable.button_background);
        btn.setTextColor(getResources().getColorStateList(R.color.white));
    }

}