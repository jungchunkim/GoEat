package com.GOEAT.Go_Eat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PwdAuthNumberActivity extends AppCompatActivity {

    EditText et_1, et_2, et_3, et_4;
    ImageView iv_back;
    Button btn_next;
    private String check;
    private TextView tv_check;
    private TextView tv_resend;
    private String AuthNum;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd_auth_number);


        et_1 = findViewById(R.id.et_1);
        et_2 = findViewById(R.id.et_2);
        et_3 = findViewById(R.id.et_3);
        et_4 = findViewById(R.id.et_4);
        iv_back = findViewById(R.id.iv_back);
        btn_next = findViewById(R.id.btn_next);
        tv_check = findViewById(R.id.tv_check);
        tv_resend = findViewById(R.id.tv_resend);
        layout = findViewById(R.id.layout);

        Intent getIntent = getIntent();
        check = getIntent.getStringExtra("check");

        if (check.equals("phone"))
            tv_check.setText("핸드폰 번호로 인증번호를 보냈습니다.");
        else
            tv_check.setText("이메일로 인증번호를 보냈습니다.");

        // 다음 버튼 클릭
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AuthNum = et_1.getText().toString()+et_2.getText().toString()+et_3.getText().toString()+et_4.getText().toString();

                // 인증번호 맞는지 확인하는 코드 ( 작성해야함!! )


                // 인증번호 틀렸을때의 코드 ( 작성해야함!! )

                //만약, 인증번호틀리다면 아래의코드 반드시 넣어야함!
                //layout.setVisibility(View.VISIBLE);

                // 액티비티 이동
                Intent intent = new Intent(getApplicationContext(), ResetPwdActivity.class);
                startActivity(intent);
            }
        });

        // 뒤로가기 버튼 클릭
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // 재전송 버튼 눌렀을 때
        tv_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 인증번호 다시 보내는 코드 작성하기 ( 작성해야함!! )
            }
        });

        et_1.requestFocus();


        et_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(et_1.getText().toString().length()==1)
                    et_2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(et_2.getText().toString().length()==1)
                    et_3.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(et_3.getText().toString().length()==1)
                    et_4.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}