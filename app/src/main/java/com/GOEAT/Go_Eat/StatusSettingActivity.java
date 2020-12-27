package com.GOEAT.Go_Eat;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class StatusSettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_setting);

        final Bundle arguments = new Bundle();
        arguments.putBoolean("isEditMode", false);

        final StatusSettingFragment fragment = new StatusSettingFragment();
        fragment.setArguments(arguments);

        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
    }
}