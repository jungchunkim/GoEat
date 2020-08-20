package com.GOEAT.Go_Eat;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class FindPasswordActivity extends AppCompatActivity {

    private EditText et_email;
    private Button btn_email_send;
    private Button btn_phone_send;
    private String email;
    private ImageView iv_back;
    private String check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);

        btn_email_send = (Button)findViewById(R.id.btn_email_send);
        btn_phone_send = findViewById(R.id.btn_phone_send);
        et_email  = (EditText)findViewById(R.id.et_email);
        iv_back = findViewById(R.id.iv_back);

        // 뒤로가기 버튼 클릭
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        et_email.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) { // 엔터시 키보드 내리는 부분
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow( et_email.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        btn_email_send.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                //final String receive_email = et_email.getText().toString();

                // 이메일 유효성 확인하는 부분 (작성해야함!!)


                // 이메일로 인증번호 보내는 부분 ( 작성해야함!!)


                // 화면전환(임시)
                Intent intent = new Intent(FindPasswordActivity.this, PwdAuthNumberActivity.class);
                check = "email";
                intent.putExtra("check", check);
                startActivity(intent);


            }
        });

        btn_phone_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 등록된 핸드폰으로 인증번호 보내는 부분 ( 작성해야함!!)


                // 화면전환
                Intent intent = new Intent(FindPasswordActivity.this, PwdAuthNumberActivity.class);
                check = "phone";
                intent.putExtra("check", check);
                startActivity(intent);

            }
        });
    }
}