package com.GOEAT.Go_Eat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class TermsOfServices extends AppCompatActivity {

    private CheckBox cp_2, cp_3, cp_4, cp_up14, cp_all;
    private Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_of_services);

        cp_2 = findViewById(R.id.cp_2);
        cp_3 = findViewById(R.id.cp_3);
        cp_4 = findViewById(R.id.cp_4);

        cp_up14 = findViewById(R.id.cp_up14);
        cp_all = findViewById(R.id.cp_all);
        btn_next = findViewById(R.id.btn_next);

        cp_2.setOnCheckedChangeListener(itemCheckedChangeListener);
        cp_3.setOnCheckedChangeListener(itemCheckedChangeListener);
        cp_4.setOnCheckedChangeListener(itemCheckedChangeListener);
        cp_up14.setOnCheckedChangeListener(itemCheckedChangeListener);

        // 전체동의버튼 눌렀을때 모든 항목 체크됨
        cp_all.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllItemChecked(cp_all.isChecked());
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEssentialItemChecked()) {
                    Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                    startActivity(intent);
                    finish();
                }
            }


        });

        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void onTermsItemViewClick(@NonNull View v) {
        if (v.getTag() == null) return;

        final Intent intent;

        if (v.getTag().toString().equals("usage")) {
            intent = new Intent(this, Service_Detail_2.class);
        } else if (v.getTag().equals("privacy")) {
            intent = new Intent(this, Service_Detail_3.class);
        } else throw new UnknownError("Unknown view :: [" + v + "]");

        startActivity(intent);
    }

    private boolean isEssentialItemChecked() {
        return cp_2.isChecked() && cp_3.isChecked() && cp_up14.isChecked();
    }

    private boolean isAllItemChecked() {
        return isEssentialItemChecked() && cp_4.isChecked();
    }

    private void setAllItemChecked(boolean isChecked) {
        cp_2.setChecked(isChecked);
        cp_3.setChecked(isChecked);
        cp_4.setChecked(isChecked);
        cp_up14.setChecked(isChecked);
    }

    private final CompoundButton.OnCheckedChangeListener itemCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            btn_next.setEnabled(isEssentialItemChecked());
            cp_all.setChecked(isAllItemChecked());
        }
    };
}