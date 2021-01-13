package com.GOEAT.Go_Eat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class menu_detail_list extends AppCompatActivity { // 음식점 메뉴 리스트 클래스 방진혁
    String menulist, pricelist;
    TextView tv_res_name;
    ImageView btn_back_menulist;
    int i, j;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_detail_list);
        RecyclerView recyclerView = findViewById(R.id.recyclerviewmenu);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        final Intent intent = getIntent();
        tv_res_name = findViewById(R.id.tv_res_name);
        tv_res_name.setText(intent.getExtras().getString("restaurant_name")+" 메뉴판");
        btn_back_menulist = findViewById(R.id.btn_back_menulist);
        menulist = intent.getExtras().getString("menulist");
        pricelist = intent.getExtras().getString("pricelist");
        final List<Analysis_menu_Item> items = new ArrayList<>();
        final Analysis_menu_Item[] item = new Analysis_menu_Item[50];
        Log.d("menulist", menulist);
        Log.d("pricelist", pricelist);
        String []tokensmenu = menulist.split(", ");
        String []tokensprice = pricelist.split(", ");
        Log.d("menulist", tokensmenu[0]);
        Log.d("pricelist", tokensprice[2]);
        Log.d("foodArray", tokensmenu.length +"<>"+ tokensprice.length);

        btn_back_menulist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //혹시모를 디비 개수 차이 확인
        int arraylength;
        if (tokensmenu.length>=tokensprice.length){
            arraylength = tokensprice.length;
        }else{
            arraylength = tokensmenu.length;
        }

        for(i = 0 ;i<arraylength; i++){ //아이템에 넣어주는 부분 => 사진 부분 수정 해야함 //완료
            System.out.println(i);
            if(tokensprice[i].contains("원")) {
                item[i] = new Analysis_menu_Item(intent.getExtras().getString("menu_img_"+(i+1)), tokensmenu[i], tokensprice[i]);
            }else{
                item[i] = new Analysis_menu_Item(intent.getExtras().getString("menu_img_"+(i+1)), tokensmenu[i], tokensprice[i]+"원");
            }


        }
//        //recyclerView.scrollToPosition(items.size() - 1);
//
        for (j = 0; j < i ; j++) {
            items.add(item[j]);
        }
        Log.d("item_length", ""+j);

        final menulistRecyclerAdapter menulistRecyclerAdapter = new menulistRecyclerAdapter(this, items, 0);

        recyclerView.setAdapter(menulistRecyclerAdapter);
    }

    @Override
    public void onBackPressed()
    {
        //super.onBackPressed();
    }

}