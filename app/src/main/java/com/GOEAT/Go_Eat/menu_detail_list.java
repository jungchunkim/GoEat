package com.GOEAT.Go_Eat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class menu_detail_list extends AppCompatActivity { // 음식점 메뉴 리스트 클래스 방진혁
    String menulist, pricelist;
    TextView tv_res_name;
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
        menulist = intent.getExtras().getString("menulist");
        pricelist = intent.getExtras().getString("pricelist");
        final List<Analysis_menu_Item> items = new ArrayList<>();
        final Analysis_menu_Item[] item = new Analysis_menu_Item[15];
        Log.d("menulist", menulist);
        Log.d("pricelist", pricelist);
        String []tokensmenu = menulist.split(", ");
        String []tokensprice = pricelist.split(", ");
        Log.d("menulist", tokensmenu[0]);
        Log.d("pricelist", tokensprice[2]);
        Log.d("foodArray", tokensmenu.length +"<>"+ tokensprice.length);

        //혹시모를 디비 개수 차이 확인
        int arraylength;
        if (tokensmenu.length>=tokensprice.length){
            arraylength = tokensprice.length;
        }else{
            arraylength = tokensmenu.length;
        }

        for(i = 0 ;i<arraylength; i++){ //아이템에 넣어주는 부분 => 사진 부분 수정 해야함
            System.out.println(i);
            if(tokensprice[i].contains("원")) {
                item[i] = new Analysis_menu_Item("https://i.pinimg.com/originals/48/01/a7/4801a73cdbf6c59e6cad5c7033104be8.png", tokensmenu[i], tokensprice[i]);
            }else{
                item[i] = new Analysis_menu_Item("https://i.pinimg.com/originals/48/01/a7/4801a73cdbf6c59e6cad5c7033104be8.png", tokensmenu[i], tokensprice[i]+"원");
            }

        }
//        //recyclerView.scrollToPosition(items.size() - 1);
//
        for (j = 0; j < i ; j++) {
            items.add(item[j]);
        }

        final menulistRecyclerAdapter menulistRecyclerAdapter = new menulistRecyclerAdapter(this, items, 0);

        recyclerView.setAdapter(menulistRecyclerAdapter);
    }
}