package com.GOEAT.Go_Eat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.IOException;
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

        //holder.Image_1.setImageResource(arrayList.get(position).getImage_1());
        try {
            if(arrayList.get(position).getImage_1() != null && arrayList.get(position).getImage_1().length() > 0) { //java.lang.IllegalArgumentException: Path must not be empty.
                Picasso.get().load(arrayList.get(position).getImage_1()).fit().centerCrop().error(R.drawable.go_logo1).into(holder.Image_1);
            }
        } catch (Exception e){ //[200210] fix: IllegalStateException: Unrecognized type of request
            e.printStackTrace();
        }

        holder.shop_name_1.setText(arrayList.get(position).getShop_name_1());
//        holder.exp_1.setText(arrayList.get(position).getExp_1());
//        holder.price_1.setText(arrayList.get(position).getPrice_1());
        holder.place_1.setText(arrayList.get(position).getPlace_1()+", "+arrayList.get(position).getExp_1()+" "+arrayList.get(position).getPrice_1());

    }

    @Override
    public int getItemCount() {
        return this.arrayList.size();
    }
    //음식점 이름 가져오는 부분
    public String get_shop_name(int position){
            return arrayList.get(position).getShop_name_1();
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
//            this.exp_1=(TextView)itemView.findViewById(R.id.exp_1);
//            this.price_1=(TextView)itemView.findViewById(R.id.price_1);
            this.place_1=(TextView)itemView.findViewById(R.id.place_1);


           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   int pos = getAdapterPosition() ;
                   if (pos != RecyclerView.NO_POSITION) {
                       if (mListener != null) {
                           mListener.onItemClick(v, pos) ;
                       }
                   }
               }
           });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }

    // 리스너 객체 참조를 저장하는 변수
    private OnItemClickListener mListener = null ;

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;
    }
}