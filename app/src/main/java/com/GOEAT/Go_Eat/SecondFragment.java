package com.GOEAT.Go_Eat;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
//Onboarding2 화면
public class SecondFragment  extends Fragment {
    // Store instance variables
    private String title;
    private int page;

    // newInstance constructor for creating fragment with arguments
    public static SecondFragment newInstance(int page, String title) {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragment.setArguments(args);
        return fragment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Inflate the view for the fragment based on layout XML
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        TextView tvLabel = (TextView) view.findViewById(R.id.textview);
        tvLabel.setText("회원가입을 하시고,\n취향 분석을 한번만\n해주시면");
        tvLabel.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 28);
        tvLabel.setGravity(Gravity.CENTER_HORIZONTAL);
        tvLabel.setTextColor(Color.parseColor("#222B45"));
        Typeface typeface = getResources().getFont(R.font.pureunjeonnam);
        tvLabel.setTypeface(typeface);
        return view;
    }
}