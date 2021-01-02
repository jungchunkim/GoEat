package com.GOEAT.Go_Eat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class NoticeActivity extends AppCompatActivity {

    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_real);

        listview = (ListView)findViewById(R.id.list);

        NoticeListViewAdapter adapter = new NoticeListViewAdapter();


        // 이 부분을 서버에서 받아와서 서버에 저장된 개수만큼 for문으로 add하도록 수정해야함!
        adapter.addItem("공지사항2", "20.12.02", "안녕하세요 Go Eat입니다.\n저희 어플을 다운해주셔서 감사합니다.\n앞으로 더 발전하겠습니다.\n고민하지말고 Go Eat!");
        adapter.addItem("공지사항1", "20.12.01", "안녕하세요 Go Eat입니다.\n이번에 저희 어플의 투게더 매칭은\n1월 중순에 활성화 예정입니다.\n많은 기대 부탁드려요!");

        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NoticeListViewItem item = (NoticeListViewItem) parent.getItemAtPosition(position);

                String titleStr = item.getTitle();
                String dateStr = item.getDate();
                String textStr = item.getText();

                Intent intent = new Intent(getApplicationContext(), Notice_detail1.class);
                intent.putExtra("title", titleStr);
                intent.putExtra("date", dateStr);
                intent.putExtra("text", textStr);
                startActivity(intent);

            }
        });


    }
}