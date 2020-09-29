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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.GOEAT.Go_Eat.Server_Request.get_restaurantdetail;
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

    int number = 10;
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
                        String[] url_1 = new String[10];
                        url_1[0] = jsonObject.getString("image0");
                        url_1[1] = jsonObject.getString("image1");
                        url_1[2] = jsonObject.getString("image2");
                        url_1[3] = jsonObject.getString("image3");
                        url_1[4] = jsonObject.getString("image4");
                        url_1[5] = jsonObject.getString("image5");
                        url_1[6] = jsonObject.getString("image6");
                        url_1[7] = jsonObject.getString("image7");
                        url_1[8] = jsonObject.getString("image8");
                        url_1[9] = jsonObject.getString("image9");


                        for(int i=0;i<number;i++)
                        {
                           String name = "name"+i;
                           String recommendmenu = "recommendmenu"+i;
                           String recommendprice = "recommendprice"+i;
                           String address = "address"+i;
                            if (jsonObject.getString(name).length()>1) {
                                Data_1[i] = new MainData(url_1[i], jsonObject.getString(name), jsonObject.getString(recommendmenu), jsonObject.getString(recommendprice), jsonObject.getString(address));
                            }else{
                                number = i;
                                break;

                            }
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
                                    public void onItemClick(View v, final int position) {
                                        final Intent intent = new Intent(getActivity(), restuarent_detail.class);
                                        //음식점에 맞는 정보를 여기에 입력해주면됨!, id값에 맞게 정보 입력!
                                        intent.putExtra("restaurant_name", mainAdapter.get_shop_name(position)); /*송신*/
                                        intent.putExtra("FirstFood",menu);
                                        intent.putExtra("AssociateFood",main_menu);
                                        Log.d("shop_name",mainAdapter.get_shop_name(position));
                                    Response.Listener<String> responselistener = new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                String success = jsonObject.getString("success");
                                                System.out.println(success);
                                                if (success.equals("true")){
                                                    intent.putExtra("food_name_2", jsonObject.getString("name"));
                                                    intent.putExtra("star_pt", jsonObject.getString("score"));
                                                    intent.putExtra("phone_num", jsonObject.getString("telephone"));
                                                    intent.putExtra("position_num", jsonObject.getString("address"));
                                                    intent.putExtra("text_food", jsonObject.getString("explain"));
                                                    intent.putExtra("food_name_1", jsonObject.getString("category"));
                                                    intent.putExtra("imageview1", jsonObject.getString("image"));
                                                    intent.putExtra("restaurant_img_1", jsonObject.getString("menuimage1"));
                                                    intent.putExtra("restaurant_img_2", jsonObject.getString("menuimage2"));
                                                    intent.putExtra("restaurant_img_3", jsonObject.getString("menuimage3"));
                                                    intent.putExtra("restaurant_img_4", jsonObject.getString("menuimage4"));
                                                    intent.putExtra("restaurant_img_5", jsonObject.getString("menuimage5"));

                                                    String []tokensmenu = jsonObject.getString("menulist").split(", ");
                                                    String []tokensprice = jsonObject.getString("pricelist").split(", ");

                                                    intent.putExtra("restaurant_txt_1", tokensmenu[0]);
                                                    intent.putExtra("food_price_1", tokensprice[0]);
                                                    intent.putExtra("restaurant_txt_2", tokensmenu[1]);
                                                    intent.putExtra("food_price_2", tokensprice[1]);
                                                    intent.putExtra("restaurant_txt_3", tokensmenu[2]);
                                                    intent.putExtra("food_price_3", tokensprice[2]);
                                                    intent.putExtra("price_num", jsonObject.getString("up_down_price"));
                                                    startActivity(intent);
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    };
                                    get_restaurantdetail get_restaurantdetail = new get_restaurantdetail(mainAdapter.get_shop_name(position),menu,main_menu,responselistener);
                                    RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                                    queue.add(get_restaurantdetail);

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