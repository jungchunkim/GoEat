package com.GOEAT.Go_Eat;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class Fragment_2 extends Fragment {

    private Context context;
    private int num=0;

    // 위치 정보 선택 창 2020-09-20 김정천
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public Fragment_2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_fragment_2, container, false);
        context=container.getContext();
        final Button location_8 = (Button) view.findViewById(R.id.location_8);
        final Button location_9 = (Button) view.findViewById(R.id.location_9);
        final Button location_10 = (Button) view.findViewById(R.id.location_10);
        final Button location_11 = (Button) view.findViewById(R.id.location_11);
        final Button location_12= (Button) view.findViewById(R.id.location_12);


        location_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(num==0) {
                    num = 8;
                    changeBtnBackground(location_8);
                }else if(num==8){
                    num=0;
                    reChangeBtnBackground(location_8);
                }
                else {
                    Toast.makeText(context,"하나만 선택하세요",Toast.LENGTH_SHORT).show();
                }


            }
        });
        location_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(num==0) {
                    num = 9;
                    changeBtnBackground(location_9);
                }else if(num==9){
                    num=0;
                    reChangeBtnBackground(location_9);
                }
                else {
                    Toast.makeText(context,"하나만 선택하세요",Toast.LENGTH_SHORT).show();
                }


            }
        });
        location_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(num==0) {
                    num = 10;
                    changeBtnBackground(location_10);
                }else if(num==10){
                    num=0;
                    reChangeBtnBackground(location_10);
                }
                else {
                    Toast.makeText(context,"하나만 선택하세요",Toast.LENGTH_SHORT).show();
                }


            }
        });
        location_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(num==0) {
                    num = 11;
                    changeBtnBackground(location_11);
                }else if(num==11){
                    num=0;
                    reChangeBtnBackground(location_11);
                }
                else {
                    Toast.makeText(context,"하나만 선택하세요",Toast.LENGTH_SHORT).show();
                }


            }
        });
        location_12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(num==0) {
                    num = 12;
                    changeBtnBackground(location_12);
                }else if(num==12){
                    num=0;
                    reChangeBtnBackground(location_12);
                }
                else {
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