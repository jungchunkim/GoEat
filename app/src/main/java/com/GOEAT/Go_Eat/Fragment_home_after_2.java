package com.GOEAT.Go_Eat;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.GOEAT.Go_Eat.Server_Request.get_restaurantlist;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Fragment_home_after_2 extends Fragment {


    final int number = 10;
    private ArrayList<MainData> arrayList;  // 사진과 이름이 담긴 Data 만들어주고 선언!
    private String menu, main_menu,companion;
    private MainAdapter mainAdapter;    //만들어줄 어뎁터
    private RecyclerView recyclerView;  //리사이클러뷰
    private LinearLayoutManager linearLayoutManager;    //리니어 레이아웃

    public Fragment_home_after_2(String menu,String main_menu,String companion) {
        this.menu= menu;
        this.main_menu= main_menu;
        this.companion= companion;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView =(ViewGroup)inflater.inflate(R.layout.fragment_fragment_home_after_2, container, false);

        recyclerView=(RecyclerView)rootView.findViewById(R.id.rv_2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//서버에서 받은 Data 정보들 넣어주는 곳 2020-09-29 방진혁
        Response.Listener<String> responselistener2 = new Response.Listener<String>() {
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
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        get_restaurantlist get_restaurantlist = new get_restaurantlist(menu,main_menu,companion,responselistener2) ;
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(get_restaurantlist);

        arrayList=new ArrayList<>();
        //Data 정보들 넣어주는 곳 2020-09-23 김정천
        MainData[] Data_1= new MainData[number];
        Data_1[0]=new MainData(R.drawable.steak,"","","","");
        Data_1[1]=new MainData(R.drawable.steak,"","","","");
        Data_1[2]=new MainData(R.drawable.steak,"","","","");
        Data_1[3]=new MainData(R.drawable.steak,"","","","");
        Data_1[4]=new MainData(R.drawable.steak,"","","","");
        Data_1[5]=new MainData(R.drawable.steak,"","","","");
        Data_1[6]=new MainData(R.drawable.steak,"","","","");
        Data_1[7]=new MainData(R.drawable.steak,"","","","");
        Data_1[8]=new MainData(R.drawable.steak,"","","","");
        Data_1[9]=new MainData(R.drawable.steak,"","","","");
        for(int i=0;i<number;i++)
        {
            arrayList.add(Data_1[i]);
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