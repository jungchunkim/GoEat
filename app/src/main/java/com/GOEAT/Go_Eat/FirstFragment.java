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

//Onboarding1 화면
public class FirstFragment extends Fragment {
    // Store instance variables
    private String title;
    private int page;

    // newInstance constructor for creating fragment with arguments
    public static FirstFragment newInstance(int page, String title) {
        FirstFragment fragment = new FirstFragment();
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
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");

    }

    // Inflate the view for the fragment based on layout XML
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        TextView tvLabel = (TextView) view.findViewById(R.id.textview);
        tvLabel.setText("안녕하세요!\n우리는 외식 비서 어플\nGo eat이에요!");
        tvLabel.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
        tvLabel.setGravity(Gravity.CENTER_HORIZONTAL);
        tvLabel.setTextColor(Color.parseColor("#222B45"));
        Typeface typeface = getResources().getFont(R.font.pureunjeonnam);
        tvLabel.setTypeface(typeface);
        return view;
    }
}