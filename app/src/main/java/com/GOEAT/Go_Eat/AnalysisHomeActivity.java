package com.GOEAT.Go_Eat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AnalysisHomeActivity extends AppCompatActivity {

    final int ITEM_SIZE = 5;
    private TextView tv_similar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis_home);

        tv_similar = findViewById(R.id.tv_similar);

        // 사용자의 이름 넣는 부분 (서버관련코드 구현해야함!)
        //tv_similar.setText(name + tv_similar.getText() );

        // 2020.09.07 임민영

        // 이벤트 부분

        //임시로..
        /*TextView example=(TextView)findViewById(R.id.example);
        example.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Analysis_home_after.class);
                startActivity(intent);
            }
        });
*/



        // Event Fragment로 넘길 Image Resource
        ArrayList<Integer> listImage = new ArrayList<>();
        listImage.add(R.drawable.edit_text_background_dark);
        listImage.add(R.drawable.edit_text_background_dark);
        listImage.add(R.drawable.edit_text_background_dark);
        listImage.add(R.drawable.edit_text_background_dark);

        ViewPager vp_event = findViewById(R.id.vp_event);
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        // ViewPager와  FragmentAdapter 연결
        vp_event.setAdapter(fragmentAdapter);

        vp_event.setClipToPadding(false);
        int dpValue = 16;
        float d = getResources().getDisplayMetrics().density;
        int margin = (int) (dpValue * d);
        vp_event.setPadding(margin, 0, margin, 0);
        vp_event.setPageMargin(margin/2);

        // FragmentAdapter에 Fragment 추가, Image 개수만큼 추가
        for (int i = 0; i < listImage.size(); i++) {
            ImageFragment imageFragment = new ImageFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("imgRes", listImage.get(i));
            imageFragment.setArguments(bundle);
            fragmentAdapter.addItem(imageFragment);
        }

        fragmentAdapter.notifyDataSetChanged();



        // 고잇 알고리즘이 추천한 메뉴

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        List<Item> items = new ArrayList<>();
        Item[] item = new Item[ITEM_SIZE];
        item[0] = new Item(R.drawable.steak, "현명식탁", "양식");
        item[1] = new Item(R.drawable.noodle, "정육면체", "국수");
        item[2] = new Item(R.drawable.pasta, "라구식당", "파스타");
        item[3] = new Item(R.drawable.button_background, "홍콩반점", "중식");
        item[4] = new Item(R.drawable.button_background,"일식집", "일식");

        //recyclerView.scrollToPosition(items.size() - 1);

        for (int i = 0; i < ITEM_SIZE; i++) {
            items.add(item[i]);
        }

        recyclerView.setAdapter(new RecyclerAdapter(getApplicationContext(), items, R.layout.activity_analysis_home));


        // 비슷한 사람들이 먹은 음식

        RecyclerView recyclerView2 = (RecyclerView) findViewById(R.id.recyclerview2);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(layoutManager2);

        List<Item> items2 = new ArrayList<>();
        Item[] item2 = new Item[ITEM_SIZE];
        item2[0] = new Item(R.drawable.bread, "만동제과", "베이커리");
        item2[1] = new Item(R.drawable.rice, "마도인신촌", "대창덮밥");
        item2[2] = new Item(R.drawable.salad, "보울룸", "샐러드");
        item2[3] = new Item(R.drawable.button_background, "홍콩반점", "중식");
        item2[4] = new Item(R.drawable.button_background,"일식집", "일식");

        //recyclerView.scrollToPosition(items.size() - 1);

        for (int i = 0; i < ITEM_SIZE; i++) {
            items2.add(item2[i]);
        }

        RecyclerAdapter adapter2 = new RecyclerAdapter(getApplicationContext(), items2, R.layout.activity_analysis_home);
        recyclerView2.setAdapter(adapter2);



        // 신촌에서 핫한 음식

        RecyclerView recyclerView3 = (RecyclerView) findViewById(R.id.recyclerview3);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(layoutManager3);

        List<Item> items3 = new ArrayList<>();
        Item[] item3 = new Item[ITEM_SIZE];
        item3[0] = new Item(R.drawable.ramen, "카라멘야", "일식");
        item3[1] = new Item(R.drawable.bread2, "파이홀", "베이커리");
        item3[2] = new Item(R.drawable.ricecake, "품어떡", "분식");
        item3[3] = new Item(R.drawable.button_background, "홍콩반점", "중식");
        item3[4] = new Item(R.drawable.button_background,"일식집", "일식");

        //recyclerView.scrollToPosition(items.size() - 1);

        for (int i = 0; i < ITEM_SIZE; i++) {
            items3.add(item3[i]);
        }


        // 음식 선택하면 분석홈 상세로 넘어가는 부분 (adapter1처럼 adapter2, adapter3도 구현하면 됨)



    }

    class FragmentAdapter extends FragmentPagerAdapter {

        // ViewPager에 들어갈 Fragment들을 담을 리스트
        private ArrayList<Fragment> fragments = new ArrayList<>();

        // 필수 생성자
        FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        // List에 Fragment를 담을 함수
        void addItem(Fragment fragment) {
            fragments.add(fragment);
        }
    }

}

