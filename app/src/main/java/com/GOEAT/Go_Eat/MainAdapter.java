package com.GOEAT.Go_Eat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.CustomViewHolder> {

    private ArrayList<MainData> arrayList;


    public MainAdapter( ArrayList<MainData> arrayList) {
        this.arrayList = arrayList;
    }

    //생성주기
    @NonNull
    @Override
    public MainAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        CustomViewHolder holder=new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.CustomViewHolder holder, int position) {

        holder.Image_1.setImageResource(arrayList.get(position).getImage_1());
        holder.shop_name_1.setText(arrayList.get(position).getShop_name_1());
        holder.exp_1.setText(arrayList.get(position).getExp_1());
        holder.price_1.setText(arrayList.get(position).getPrice_1());
        holder.place_1.setText(arrayList.get(position).getPlace_1());

    }

    @Override
    public int getItemCount() {
        return this.arrayList.size();
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView Image_1;
        protected TextView shop_name_1;
        protected TextView exp_1;
        protected TextView price_1;
        protected TextView place_1;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.Image_1=(ImageView)itemView.findViewById(R.id.Image_1);
            this.shop_name_1=(TextView)itemView.findViewById(R.id.shop_name_1);
            this.exp_1=(TextView)itemView.findViewById(R.id.exp_1);
            this.price_1=(TextView)itemView.findViewById(R.id.price_1);
            this.place_1=(TextView)itemView.findViewById(R.id.place_1);
        }
    }
}