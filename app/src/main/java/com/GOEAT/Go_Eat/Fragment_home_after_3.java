package com.GOEAT.Go_Eat;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class Fragment_home_after_3 extends Fragment {

    final int number = 10;
    private ArrayList<MainData> arrayList;  // 사진과 이름이 담긴 Data 만들어주고 선언!

    private MainAdapter mainAdapter;    //만들어줄 어뎁터
    private RecyclerView recyclerView;  //리사이클러뷰
    private LinearLayoutManager linearLayoutManager;    //리니어 레이아웃

    public Fragment_home_after_3() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView =(ViewGroup)inflater.inflate(R.layout.fragment_fragment_home_after_3, container, false);

        recyclerView=(RecyclerView)rootView.findViewById(R.id.rv_3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        arrayList=new ArrayList<>();
        //Data 정보들 넣어주는 곳 2020-09-23 김정천
        MainData[] Data_3= new MainData[number];
        Data_3[0]=new MainData(R.drawable.steak,"식당 이름","안녕하세요","2000","송파역");
        Data_3[1]=new MainData(R.drawable.steak,"식당 이름","안녕하세요","2000","송파역");
        Data_3[2]=new MainData(R.drawable.steak,"식당 이름","안녕하세요","2000","송파역");
        Data_3[3]=new MainData(R.drawable.steak,"식당 이름","안녕하세요","2000","송파역");
        Data_3[4]=new MainData(R.drawable.steak,"식당 이름","안녕하세요","2000","송파역");
        Data_3[5]=new MainData(R.drawable.steak,"식당 이름","안녕하세요","2000","송파역");
        Data_3[6]=new MainData(R.drawable.steak,"식당 이름","안녕하세요","2000","송파역");
        Data_3[7]=new MainData(R.drawable.steak,"식당 이름","안녕하세요","2000","송파역");
        Data_3[8]=new MainData(R.drawable.steak,"식당 이름","안녕하세요","2000","송파역");
        Data_3[9]=new MainData(R.drawable.steak,"식당 이름","안녕하세요","2000","송파역");

        for(int i=0;i<number;i++)
        {
            arrayList.add(Data_3[i]);
        }

        mainAdapter=new MainAdapter(arrayList);
        recyclerView.setAdapter(mainAdapter);

        mainAdapter.setOnItemClickListener(
                new MainAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Intent intent = new Intent(getActivity(), restuarent_detail.class);
                        startActivity(intent);
                    }
                });
        // Inflate the layout for this fragment
        return rootView;
    }
}