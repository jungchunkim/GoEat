package com.GOEAT.Go_Eat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class restuarent_detail extends AppCompatActivity implements OnMapReadyCallback {
    private ImageView booktag;
    private int number = 0;
    private TextView food_name_1, food_name_2, star_pt, phone_num, position_num, price_num, text_food, restaurant_txt_1, food_price_1, restaurant_txt_2, food_price_2, restaurant_txt_3, food_price_3, map_address;
    private ImageView restaurant_img_1, restaurant_img_2, restaurant_img_3, bt_phone_num, bt_share;
    private String restaurant_name, FirstFood, AssociateFood, menulist, pricelist;
    private String restaurant_main_image;
    private String restaurantLink;
    private Button naver_order, View_more, show_all_menu;
    private MapView mapView;

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restuarent_detail);

        food_name_1 = (TextView) findViewById(R.id.food_name_1);
        food_name_2 = (TextView) findViewById(R.id.food_name_2);
        star_pt = (TextView) findViewById(R.id.star_pt);
        phone_num = (TextView) findViewById(R.id.phone_num);
        position_num = (TextView) findViewById(R.id.position_num);
        price_num = (TextView) findViewById(R.id.price_num);
        text_food = (TextView) findViewById(R.id.text_food);
        restaurant_txt_1 = (TextView) findViewById(R.id.restaurant_txt_1);
        food_price_1 = (TextView) findViewById(R.id.food_price_1);
        restaurant_img_1 = (ImageView) findViewById(R.id.restaurant_img_1);
        restaurant_txt_2 = (TextView) findViewById(R.id.restaurant_txt_2);
        food_price_2 = (TextView) findViewById(R.id.food_price_2);
        restaurant_img_2 = (ImageView) findViewById(R.id.restaurant_img_2);
        restaurant_txt_3 = (TextView) findViewById(R.id.restaurant_txt_3);
        food_price_3 = (TextView) findViewById(R.id.food_price_3);
        map_address = (TextView) findViewById(R.id.map_address);
        restaurant_img_3 = (ImageView) findViewById(R.id.restaurant_img_3);
        naver_order = (Button) findViewById(R.id.naver_order);
        bt_phone_num = (ImageView) findViewById(R.id.bt_phone_num);
        View_more = (Button) findViewById(R.id.View_more);
        show_all_menu = (Button) findViewById(R.id.show_all_menu);
        bt_share = (ImageView) findViewById(R.id.bt_share);
        final Intent intent = getIntent();

        mapView = findViewById(R.id.map_view);

        mapView.onCreate(savedInstanceState);
        naverMapBasicSettings();

        restaurant_name = intent.getExtras().getString("restaurant_name");
        FirstFood = intent.getExtras().getString("FirstFood");
        AssociateFood = intent.getExtras().getString("AssociateFood");

        food_name_2.setText(intent.getExtras().getString("food_name_2"));
        star_pt.setText(intent.getExtras().getString("star_pt"));
        phone_num.setText(intent.getExtras().getString("phone_num"));
        position_num.setText(intent.getExtras().getString("position_num"));
        map_address.setText(intent.getExtras().getString("position_num"));
        text_food.setText(intent.getExtras().getString("text_food"));
        restaurant_txt_1.setText(intent.getExtras().getString("restaurant_txt_1"));
        food_price_1.setText(intent.getExtras().getString("food_price_1"));
        restaurant_txt_2.setText(intent.getExtras().getString("restaurant_txt_2"));
        food_price_2.setText(intent.getExtras().getString("food_price_2"));
        restaurant_txt_3.setText(intent.getExtras().getString("restaurant_txt_3"));
        food_price_3.setText(intent.getExtras().getString("food_price_3"));
        restaurant_main_image = intent.getExtras().getString("imageview1");
        menulist = intent.getExtras().getString("menulist");
        pricelist = intent.getExtras().getString("pricelist");
        restaurantLink = intent.getExtras().getString("restaurant_link");

        Log.d("restaurant_link", intent.getExtras().getString("restaurant_link"));

        final String priceRange = intent.getExtras().getString("price_num");
        price_num.setText(priceRange);

        //최소 최대 가격 계산 하여 넣는 부분 방진혁
//        String []tokensprice = pricelist.split(", ");
//        String maxprice, minprice;
//        for(int i = 0;i<tokensprice.length; i++ ){
//            tokensprice[i] = tokensprice[i].replace(",","");
//            tokensprice[i] = tokensprice[i].replace(" ","");
//            tokensprice[i] = tokensprice[i].replace("원","");
//        }
//        if(tokensprice.length == 0){
//            price_num.setText("메뉴 정보 없음");
//        }else if(tokensprice.length == 1){
//            price_num.setText(String.format("%,d",Integer.parseInt(tokensprice[0])));
//        }else if(tokensprice.length == 2){
//            if(Integer.parseInt(tokensprice[0]) < Integer.parseInt(tokensprice[1])){
//                price_num.setText(String.format("%,d",Integer.parseInt(tokensprice[0]))+" - "+String.format("%,d",Integer.parseInt(tokensprice[1])));
//            }else{
//                price_num.setText(String.format("%,d",Integer.parseInt(tokensprice[1]))+" - "+String.format("%,d",Integer.parseInt(tokensprice[0])));
//            }
//        }else{
//            if(Integer.parseInt(tokensprice[0]) < Integer.parseInt(tokensprice[1])){
//                maxprice = tokensprice[1];
//                minprice =  tokensprice[0];
//            }else{
//                maxprice = tokensprice[0];
//                minprice =  tokensprice[1];
//            }
//            for(int i = 2 ; i<tokensprice.length; i++){
//                if(Integer.parseInt(maxprice) < Integer.parseInt(tokensprice[i])){
//                    maxprice = tokensprice[i];
//                }
//                if(Integer.parseInt(minprice) > Integer.parseInt(tokensprice[i])){
//                    minprice = tokensprice[i];
//                }
//            }
//            price_num.setText(String.format("%,d",Integer.parseInt(minprice))+" - "+String.format("%,d",Integer.parseInt(maxprice)));
//        }
//        Log.d("restaurant_img_1",intent.getExtras().getString("menu_img_1"));
//        Log.d("restaurant_img_2",intent.getExtras().getString("menu_img_2"));
//        Log.d("restaurant_img_3",intent.getExtras().getString("menu_img_3"));
        try {
            Picasso.get().load(intent.getExtras().getString("menu_img_1")).fit().centerCrop().error(R.drawable.loading_fail).into(restaurant_img_1,new Callback() {
                @Override
                public void onSuccess() {
                }

                @Override
                public void onError(Exception e) {
                    restaurant_img_1.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                }
            });
            Log.d("restaurant_img_1", intent.getExtras().getString("menu_img_1"));
        } catch (Exception e) { //[200210] fix: IllegalStateException: Unrecognized type of request
            restaurant_img_1.setImageResource(R.drawable.loading_fail);
            restaurant_img_1.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            e.printStackTrace();
        }
        try {
            Picasso.get().load(intent.getExtras().getString("menu_img_2")).fit().centerCrop().error(R.drawable.loading_fail).into(restaurant_img_2,new Callback() {
                @Override
                public void onSuccess() {
                }

                @Override
                public void onError(Exception e) {
                    restaurant_img_2.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                }
            });
            Log.d("restaurant_img_1", intent.getExtras().getString("menu_img_2"));
        } catch (Exception e) { //[200210] fix: IllegalStateException: Unrecognized type of request
            restaurant_img_2.setImageResource(R.drawable.loading_fail);
            restaurant_img_2.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            e.printStackTrace();
        }
        try {
            Picasso.get().load(intent.getExtras().getString("menu_img_3")).error(R.drawable.loading_fail).into(restaurant_img_3,new Callback() {
                @Override
                public void onSuccess() {
                }

                @Override
                public void onError(Exception e) {
                    restaurant_img_3.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                }
            });
            Log.d("restaurant_img_1", intent.getExtras().getString("menu_img_3"));
        } catch (Exception e) { //[200210] fix: IllegalStateException: Unrecognized type of request
            restaurant_img_3.setImageResource(R.drawable.loading_fail);
            restaurant_img_3.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            e.printStackTrace();
        }
//        Picasso.get().load(intent.getExtras().getString("restaurant_img_1")).error(R.drawable.go).into(restaurant_img_1);
//        Picasso.get().load(intent.getExtras().getString("restaurant_img_2")).error(R.drawable.go).into(restaurant_img_2);
//        Picasso.get().load(intent.getExtras().getString("restaurant_img_3")).error(R.drawable.go).into(restaurant_img_3);
        //Glide.with(this).load(intent.getExtras().getString("restaurant_img_1")).centerCrop().into(restaurant_img_1);
//        Glide.with(this).load(intent.getExtras().getString("restaurant_img_2")).centerCrop().into(restaurant_img_2);
//        Glide.with(this).load(intent.getExtras().getString("restaurant_img_3")).centerCrop().into(restaurant_img_3);

//        price_num.setText(intent.getExtras().getString("price_num"));


        ViewPager viewpager1 = (ViewPager) findViewById(R.id.vpPager_1);
        MyPageAdapter adapter = new MyPageAdapter(this);
        viewpager1.setAdapter(adapter);

//        restaurant_link.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(intent.getExtras().getString("restaurant_link")));
//               startActivity(intent1);
//            }
//        });
        bt_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/html");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, "GoEat이 추천한 음식점!\n" + intent.getExtras().getString("restaurant_link"));
                startActivity(Intent.createChooser(sharingIntent, "Share using text"));

            }
        });
        naver_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //네이버 주문클릭시 사이트 이동
                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(intent.getExtras().getString("restaurant_link")));
                startActivity(intent1);
            }
        });
        show_all_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //메뉴 더보기 클릭시 화면 전환
                Intent intent2 = new Intent(getApplicationContext(), menu_detail_list.class);
                intent2.putExtra("menulist", menulist);
                intent2.putExtra("pricelist", pricelist);
                intent2.putExtra("restaurant_name", restaurant_name);
                for (int i = 0; i < (intent.getExtras().getInt("menu_length") + 2); i++) {
                    intent2.putExtra("menu_img_" + i, intent.getExtras().getString("menu_img_" + i));
                }
                startActivity(intent2);
            }
        });
        View_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 더보기 클릭시 확인하여 음식점 설명 레이아웃 크기 증가
                if (text_food.getLayout() != null) {
                    if (text_food.getLineCount() - 1>2) {
                        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
                        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, linearLayout2.getHeight());
                        int d = 90;
                        float mScale = getResources().getDisplayMetrics().density;
                        int calHeight = (int) (d * mScale);
                        params1.height = calHeight;
                        linearLayout2.setLayoutParams(params1);

                        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(

                                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                        d = 60;
                        mScale = getResources().getDisplayMetrics().density;
                        calHeight = (int) (d * mScale);
                        params2.height = calHeight;
                        text_food.setLayoutParams(params2);
                    }
                }

            }
        });
        bt_phone_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //전화 걸기
                Intent intent1 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + intent.getExtras().getString("phone_num")));
                startActivity(intent1);
            }
        });
//        booktag.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (number == 0) {
//                    number = 1;
//                    changeBtnBackground(booktag);
//                } else {
//                    reChangeBtnBackground(booktag);
//                    number = 0;
//                }
//            }
//        });

    }

    private void reChangeBtnBackground(ImageView btn) {
        btn.setBackgroundResource(R.drawable.ic_after_bookmark);
    }

    private void changeBtnBackground(ImageView btn) {
        btn.setBackgroundResource(R.drawable.ic_booktag_img);
    }

    public void naverMapBasicSettings() {
        mapView.getMapAsync(this);
    }

    // 네이버 지도 주소값 받아와서 지정
    @Override
    public void onMapReady(@NonNull final NaverMap naverMap) {
        Geocoder geocoder = new Geocoder(this);
        List<Address> list = null;
        double lat = 0;
        double lon = 0;

        String str = map_address.getText().toString();
        try {
            List<Address> addrList = geocoder.getFromLocationName(str, 3);
            Iterator<Address> addrs = addrList.iterator();

            String infoAddr = "";

            while (addrs.hasNext()) {
                Address loc = addrs.next();
                infoAddr += String.format("Coord : %f, %f", loc.getLatitude(), loc.getLongitude());
                lat = loc.getLatitude();
                lon = loc.getLongitude();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
//        try {
//            System.out.println(str);
//            list = geocoder.getFromLocationName
//                    ("str", // 지역 이름
//                            10); // 읽을 개수
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.e("test","입출력 오류 - 서버에서 주소변환시 에러발생");
//        }
//
//        if (list != null) {
//            if (list.size() == 0) {
//                Toast.makeText(this,
//                        "해당되는 주소 정보는 없습니다",
//                        Toast.LENGTH_SHORT).show();
//            } else {
//                // 해당되는 주소로 인텐트 날리기
//                Address addr = list.get(0);
//                lat = addr.getLatitude();
//                lon = addr.getLongitude();
//
//            }
//        }
        CameraPosition cameraPosition = new CameraPosition(
                new LatLng(lat, lon),  // 위치 지정
                16                      // 줌 레벨
        );
        naverMap.setCameraPosition(cameraPosition);
        LatLng coord = new LatLng(lat, lon);
        Marker marker = new Marker();
        marker.setPosition(new LatLng(lat, lon));
        marker.setMap(naverMap);
        //Toast.makeText(this,"위도: " + coord.latitude + ", 경도: " + coord.longitude,Toast.LENGTH_SHORT).show();
        // 현재 위치 버튼 안보이게 설정
        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setLocationButtonEnabled(false);

        naverMap.setOnMapClickListener(new NaverMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull PointF pointF, @NonNull LatLng latLng) {
                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(restaurantLink));
                startActivity(intent1);
            }
        });

        // 지도 유형 위성사진으로 설정
        naverMap.setMapType(NaverMap.MapType.Basic);
    }

    public class MyPageAdapter extends PagerAdapter {
        int[] images = {R.drawable.steak, R.drawable.bread, R.drawable.bread2};
        Context context;

        MyPageAdapter(Context context) {
            this.context = context;
        }

        @Override

        public int getCount() {
            return images.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = LayoutInflater.from(context).inflate(R.layout.viewpager_childview, container, false);
            ImageView imageview1 = (ImageView) view.findViewById(R.id.img_viewpager_childimage);
            try {
                Picasso.get().load(restaurant_main_image).fit().centerCrop().error(R.drawable.loadinf_fail_2).into(imageview1);

            } catch (Exception e) { //[200210] fix: IllegalStateException: Unrecognized type of request
                e.printStackTrace();
            }
//            imageview1.setImageResource(images[position]);
            Log.d("이미지 리소스", Integer.toString(images[position]));
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

            container.removeView((View) object);
        }
    }
}