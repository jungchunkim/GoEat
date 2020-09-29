package com.GOEAT.Go_Eat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.GOEAT.Go_Eat.Server_Request.get_restaurantlist;
import com.GOEAT.Go_Eat.Server_Request.login_request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Fragment_home_after_1 extends Fragment {

    final int number = 10;
    private String menu, main_menu,companion;
    private Bitmap bitmaps;
    private ArrayList<MainData> arrayList;  // 사진과 이름이 담긴 Data 만들어주고 선언!

    private MainAdapter mainAdapter;    //만들어줄 어뎁터
    private RecyclerView recyclerView;  //리사이클러뷰
    private LinearLayoutManager linearLayoutManager;    //리니어 레이아웃

    public Fragment_home_after_1(String menu,String main_menu,String companion) {
       this.menu= menu;
       this.main_menu= main_menu;
       this.companion= companion;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView =(ViewGroup)inflater.inflate(R.layout.fragment_fragment_home_after_1, container, false);

        recyclerView=(RecyclerView)rootView.findViewById(R.id.rv_1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //서버에서 받은 Data 정보들 넣어주는 곳 2020-09-29 방진혁
        Response.Listener<String> responselistener1 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success0");
                    System.out.println(success);
                    if (success.equals("true")){
                        System.out.println( jsonObject.getString("name8"));

                        arrayList=new ArrayList<>();
                        MainData[] Data_1= new MainData[number];
                        String url0 = jsonObject.getString("image0");
                        String url1 = jsonObject.getString("image1");
                        String url2 = jsonObject.getString("image2");
                        String url3 = jsonObject.getString("image3");
                        String url4 = jsonObject.getString("image4");
                        String url5 = jsonObject.getString("image5");
                        String url6 = jsonObject.getString("image6");
                        String url7 = jsonObject.getString("image7");
                        String url8 = jsonObject.getString("image8");
                        String url9 = jsonObject.getString("image9");


                        for(int i=0;i<number;i++)
                        {
                           String name = "name"+i;
                           String recommendmenu = "recommendmenu"+i;
                           String recommendprice = "recommendprice"+i;
                           String address = "address"+i;
                           Data_1[i] = new MainData(R.drawable.steak, jsonObject.getString(name), jsonObject.getString(recommendmenu), jsonObject.getString(recommendprice), jsonObject.getString(address));

                        }
                        for(int i=0;i<number;i++)
                        {
                            arrayList.add(Data_1[i]);
                        }

                        mainAdapter=new MainAdapter(arrayList);
                        recyclerView.setAdapter(mainAdapter);

                        //2020-09-29 김정천 클릭 이벤트 추가
                        mainAdapter.setOnItemClickListener(
                                new MainAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View v, int position) {
                                        Intent intent = new Intent(getActivity(), restuarent_detail.class);
                                        //음식점에 맞는 정보를 여기에 입력해주면됨!, id값에 맞게 정보 입력!

                                        startActivity(intent);
                                    }
                                });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        get_restaurantlist get_restaurantlist = new get_restaurantlist(menu,main_menu,companion,responselistener1) ;
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(get_restaurantlist);

                // Inflate the layout for this fragment
        return rootView;
    }
}