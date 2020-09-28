package com.GOEAT.Go_Eat;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    Context context;
    List<Item> items;
    int item_layout;
    int num;

    public RecyclerAdapter(Context context, List<Item> items, int item_layout, int num) {
        this.context = context;
        this.items = items;
        this.item_layout = item_layout;
        this.num = num;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Item item = items.get(position);
        Drawable drawable = ContextCompat.getDrawable(context, item.getImage());
        holder.image.setBackground(drawable);
        holder.title.setText(item.getTitle());
        holder.kinds.setText(item.getKinds());
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, item.getTitle(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, Analysis_home_after.class);
                // 탭정보 (메뉴1, 메뉴2, 메뉴3 구분)전송
                intent.putExtra("position", position);
                // 고잇추천인지, 비슷한사람 추천인지, 핫한 음식인기 구분 전송 (num : 0,1,2)
                intent.putExtra("recommendType", num);
                context.startActivity(intent.addFlags(FLAG_ACTIVITY_NEW_TASK));
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        TextView kinds;
        CardView cardview;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
            kinds = itemView.findViewById(R.id.kinds);
            cardview = (CardView) itemView.findViewById(R.id.cardview);



        }
    }






}