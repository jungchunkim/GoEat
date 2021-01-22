package com.GOEAT.Go_Eat;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class menulistRecyclerAdapter extends RecyclerView.Adapter<menulistRecyclerAdapter.ViewHolder> { // 음식점 메뉴 아이템 넣어주는 클래스 방진혁
    Context context;
    List<Analysis_menu_Item> items;
    //int item_layout;
    int num;

    /*public AnalysisHomeRecyclerAdapter(Context context, List<AnalysisItem> items, int item_layout, int num) {
        this.context = context;
        this.items = items;
        this.item_layout = item_layout;
        this.num = num;
    }*/

    public menulistRecyclerAdapter(Context context, List<Analysis_menu_Item> items, int num) {
        this.context = context;
        this.items = items;
        this.num = num;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menulist_cardview, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) { //마지막 사진 입력 오류 해결 필요
        final Analysis_menu_Item item = items.get(position);
        //Drawable drawable = ContextCompat.getDrawable(context, item.getImage());
        //holder.image.setBackground(item.getDrawable());

//        Glide.with(holder.itemView.getContext())
//                .load(item.getUrl())
//                .into(holder.image);
        Log.d("item put in ", "yes!");
        try {
                Picasso.get()
                        .load(item.url)
                        .fit()
                        .centerCrop()
                        .error(R.drawable.loading_fail)
                        .into(holder.image,new Callback() {
                @Override
                public void onSuccess() {
                }

                @Override
                public void onError(Exception e) {
                    holder.image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                }
            });
//            Picasso.get().load(item.url).error(R.drawable.go_logo1).into(holder.image);
            Log.d("image put in ", "yes!");
//            Log.d("image url is.. ", item.url);
        } catch (Exception e) { //[200210] fix: IllegalStateException: Unrecognized type of request
            holder.image.setImageResource(R.drawable.loading_fail);
            holder.image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            e.printStackTrace();
            Log.d("image put in ", "No!");
        }
        holder.title.setText(item.getTitle());
        holder.info.setText(item.getInfo());
//        holder.cardview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Toast.makeText(context, item.getTitle(), Toast.LENGTH_SHORT).show();
////                Intent intent = new Intent(context, Analysis_home_after.class);
////                // 탭정보 (메뉴1, 메뉴2, 메뉴3 구분)전송
////                intent.putExtra("position", position);
////                // 고잇추천인지, 비슷한사람 추천인지, 핫한 음식인기 구분 전송 (num : 0,1,2)
////                intent.putExtra("recommendType", num);
////                context.startActivity(intent.addFlags(FLAG_ACTIVITY_NEW_TASK));
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        TextView info;
        CardView cardview;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.iv_image1);
            title = (TextView) itemView.findViewById(R.id.tv_menu_title);
            info =  (TextView) itemView.findViewById(R.id.tv_menu_info);
            cardview = (CardView) itemView.findViewById(R.id.card_view1);

        }
    }






}