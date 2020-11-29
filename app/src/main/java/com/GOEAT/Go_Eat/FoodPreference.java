package com.GOEAT.Go_Eat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FoodPreference extends AppCompatActivity implements View.OnClickListener {

    ImageView food_img;
    Button btn_no, btn_nomatter,btn_like;
    TextView food_name, num_count;
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
    }
    public void onClick(View view) {
        switch (view.getId())
        {
            //클릭했을 때 image, text, 숫자 바뀐다.
            case R.id.btn_no:
                //싫어요 클릭했을 때
                if(reference<=full_count)
                {
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

