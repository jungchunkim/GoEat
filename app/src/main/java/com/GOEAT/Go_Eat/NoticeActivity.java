package com.GOEAT.Go_Eat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.GOEAT.Go_Eat.DataType.FoodInfo;
import com.GOEAT.Go_Eat.Server_Request.UserDB;
import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class NoticeActivity extends AppCompatActivity {

    ListView listview;
    private ImageView iv_back;
    private UserDB userDB = new UserDB();
    private ArrayList<String> title = new ArrayList<>();
    private ArrayList<String> change_date = new ArrayList<>();
    private ArrayList<String> content = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_real);

        listview = (ListView) findViewById(R.id.list);
        iv_back = findViewById(R.id.iv_back);

        final NoticeListViewAdapter adapter = new NoticeListViewAdapter();

        //2021-01-03 염상희
        //공지사항 데이터베이스내용으로 보여주기
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) { // 서버 응답 받아오는 부분
                try {
                    Log.e("notice 결과", response);
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        title.add(jsonObject.getString("title"));
                        change_date.add(jsonObject.getString("change_date"));
                        content.add(jsonObject.getString("content"));
                    }
                    // 이 부분을 서버에서 받아와서 서버에 저장된 개수만큼 for문으로 add하도록 수정해야함!
                    for (int i = 0; i < title.size(); i++)
                        adapter.addItem(title.get(i), makeStringToDate(change_date.get(i)), content.get(i));
                    //adapter.addItem("공지사항2", "20.12.02", "안녕하세요 Go Eat입니다.\n저희 어플을 다운해주셔서 감사합니다.\n앞으로 더 발전하겠습니다.\n고민하지말고 Go Eat!");
                    //adapter.addItem("공지사항1", "20.12.01", "안녕하세요 Go Eat입니다.\n이번에 저희 어플의 투게더 매칭은\n1월 중순에 활성화 예정입니다.\n많은 기대 부탁드려요!");

                    listview.setAdapter(adapter);
                } catch (JSONException e) {
                    Log.d("getNotice", e.toString());
                    e.printStackTrace();
                }
            }
        };
        userDB.getNotice(responseListener, NoticeActivity.this);     //음식 리스트 index 불러오는 부분

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

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

    public String makeStringToDate(String str){
        if(str.length()!=8) return "형식오류";
        String year = str.substring(0,4);
        String month = str.substring(4,6);
        String day = str.substring(6,8);
        return year+"."+month+"."+day;
    }
}