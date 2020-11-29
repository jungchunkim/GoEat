package com.GOEAT.Go_Eat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FoodPreference extends AppCompatActivity implements View.OnClickListener {

    ImageView food_img;
    Button btn_no, btn_nomatter,btn_like;
    TextView food_name, num_count;
    private int[] prefer_food = new int[24];    //싫어요 그냥그래요 좋아요 담은 것
    private int reference=1;
    private int full_count=24;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_preference);

        food_img=findViewById(R.id.food_img);
        btn_no=findViewById(R.id.btn_no);
        btn_nomatter=findViewById(R.id.btn_nomatter);
        btn_like=findViewById(R.id.btn_like);
        food_name=findViewById(R.id.food_name);
        num_count=findViewById(R.id.num_count);

        btn_no.setOnClickListener(this);
        btn_nomatter.setOnClickListener(this);
        btn_like.setOnClickListener(this);


        //처음 나오는 그림, text이름, num_count
        food_img.setImageResource(R.drawable.p_food1);
        food_name.setText("떡볶이");
        num_count.setText(reference+"/24");
        reference++;

        //레이아웃을 위에 겹쳐서 올리는 부분
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //레이아웃 객체생성
        LinearLayout ll = (LinearLayout)inflater.inflate(R.layout.food_preference_up, null);
        //레이아웃 배경 투명도 주기
        ll.setBackgroundColor(Color.parseColor("#99000000"));
        //레이아웃 위에 겹치기
        LinearLayout.LayoutParams paramll = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT);
        addContentView(ll, paramll);

        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout lk = (LinearLayout)findViewById(R.id.ll_writeChoice);
                ((ViewManager) lk.getParent()).removeView(lk);
            }
        });


    }







    public void onClick(View view) {
        switch (view.getId())
        {
            //클릭했을 때 image, text, 숫자 바뀐다.
            case R.id.btn_no:
                //싫어요 클릭했을 때
                if(reference<=full_count)
                {
                    prefer_food[reference-1]=-2;
                    food_img.setImageResource(R.drawable.p_food1);
                    food_name.setText("음식이름");
                    num_count.setText(reference+"/24");
                    reference++;
                }
                else
                {// count가 25됐을 때
                    Intent intent = new Intent(getApplicationContext(), before_emotion_check.class);
                    startActivity(intent);
                }
                break;
            case R.id.btn_nomatter:
                //그냥 그래요 클릭했을 때
                if(reference<=full_count)
                {
                    prefer_food[reference-1]=-1;
                    food_img.setImageResource(R.drawable.p_food1);
                    food_name.setText("음식이름");
                    num_count.setText(reference+"/24");
                    reference++;
                }
                else
                {// count가 25됐을 때
                    Intent intent = new Intent(getApplicationContext(), before_emotion_check.class);
                    startActivity(intent);
                }
                break;
            case R.id.btn_like:
                //좋아요 클릭했을 때
                if(reference<=full_count)
                {
                    prefer_food[reference-1]=1;
                    food_img.setImageResource(R.drawable.p_food1);
                    food_name.setText("음식이름");
                    num_count.setText(reference+"/24");
                    reference++;
                }
                else
                {// count가 25됐을 때
                    Intent intent = new Intent(getApplicationContext(), before_emotion_check.class);
                    startActivity(intent);
                }
                break;
        }
    }

}

