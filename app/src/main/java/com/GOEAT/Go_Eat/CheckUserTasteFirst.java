package com.GOEAT.Go_Eat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CheckUserTasteFirst extends AppCompatActivity {

    private Button btn_next;
    private Button btn_click1;
    private Button btn_click2;
    private Button btn_click3;
    private Button btn_click4;
    private View.OnClickListener m_listener1;
    private View.OnClickListener m_listener2;
    private View.OnClickListener m_listener3;
    private View.OnClickListener m_listener4;
    int num=0;
    int btn_num1;
    int btn_num2;
    int btn_num3;
    int btn_num4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_user_taste_first);

        btn_click1 = (Button)findViewById(R.id.button1);
        btn_click2 = (Button)findViewById(R.id.button2);
        btn_click3 = (Button)findViewById(R.id.button3);
        btn_click4 = (Button)findViewById(R.id.button4);

        btn_next = findViewById(R.id.btn_next);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CheckUserTasteSecond.class);
                startActivity(intent);
            }
        });

        m_listener1=new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_click1.setSelected(!view.isSelected());

                if(view.isSelected()) {
                    btn_num1=++num;
                    btn_click1.setSelected(true);
                    btn_click1.setText("#"+String.valueOf(btn_num1)+"                                 A. 칼로리                                      ");
                    btn_click1.setTextColor(Color.WHITE);

                }
                else
                {
                    num--;
                    btn_click1.setSelected(false);
                    btn_click1.setText("A. 칼로리");
                    btn_click1.setTextColor(Color.BLACK);
                }
            }
        };
        btn_click1.setOnClickListener(m_listener1);




        m_listener2=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_click2.setSelected(!view.isSelected());

                if(view.isSelected()) {
                    btn_num2=++num;
                    btn_click2.setSelected(true);
                    btn_click2.setText("#"+String.valueOf(btn_num2)+"                               B. 요리방식                                    ");
                    btn_click2.setTextColor(Color.WHITE);
                }
                else
                {
                    num--;
                    btn_click2.setSelected(false);
                    btn_click2.setText("B. 요리방식");
                    btn_click2.setTextColor(Color.BLACK);
                }
            }
        };

        btn_click2.setOnClickListener(m_listener2);


        m_listener3=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_click3.setSelected(!view.isSelected());

                if(view.isSelected()) {
                    btn_num3=++num;
                    btn_click3.setSelected(true);
                    btn_click3.setText("#"+String.valueOf(btn_num3)+"                           C. 누구랑 먹는지                                ");
                    btn_click3.setTextColor(Color.WHITE);
                }
                else
                {
                    num--;
                    btn_click3.setSelected(false);
                    btn_click3.setText("C. 누구랑 먹는지");
                    btn_click3.setTextColor(Color.BLACK);
                }
            }
        };

        btn_click3.setOnClickListener(m_listener3);

        m_listener4=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_click4.setSelected(!view.isSelected());

                if(view.isSelected()) {
                    btn_num4=++num;
                    btn_click4.setSelected(true);
                    btn_click4.setText("#"+String.valueOf(btn_num4)+"                                  D. 가격                                        ");
                    btn_click4.setTextColor(Color.WHITE);
                }
                else
                {
                    num--;
                    btn_click4.setSelected(false);
                    btn_click4.setText("D. 가격");
                    btn_click4.setTextColor(Color.BLACK);
                }
            }
        };

        btn_click4.setOnClickListener(m_listener4);
    }
}