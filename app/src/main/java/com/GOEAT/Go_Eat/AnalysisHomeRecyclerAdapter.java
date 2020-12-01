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

import com.GOEAT.Go_Eat.Server_Request.get_restaurantlist;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class AnalysisHomeRecyclerAdapter extends RecyclerView.Adapter<AnalysisHomeRecyclerAdapter.ViewHolder> {
    Context context;
    List<AnalysisItem> items;
    //int item_layout;
    int num;

    /*public AnalysisHomeRecyclerAdapter(Context context, List<AnalysisItem> items, int item_layout, int num) {
        this.context = context;
        this.items = items;
        this.item_layout = item_layout;
        this.num = num;
    }*/

    public AnalysisHomeRecyclerAdapter(Context context, List<AnalysisItem> items,  int num) {
        this.context = context;
        this.items = items;
        this.num = num;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.analysis_cardview, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final AnalysisItem item = items.get(position);
        //Drawable drawable = ContextCompat.getDrawable(context, item.getImage());
        //holder.image.setBackground(item.getDrawable());

//        Glide.with(holder.itemView.getContext())
//                .load(item.getUrl())
//                .into(holder.image);

        Response.Listener<String> responselistener1 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success0");
                    System.out.println(success);
                    if (success.equals("true")){
                        int number = 20;
                        for(int i=0;i<number;i++)
                        {
                            String name = "name"+i;
                            if (jsonObject.getString(name).length()>1) {
                                Log.d(position+"res_name", jsonObject.getString(name));
                            }else{
                                number = i;
                                break;
                            }
                        }
                        holder.info.setText("");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        get_restaurantlist get_restaurantlist = new get_restaurantlist(item.getTitle(),item.getKinds(), item.getwho(),responselistener1) ;
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(get_restaurantlist);


        if(item.url != null) Picasso.get().load(item.url).into(holder.image);
        holder.title.setText(item.getTitle());
        holder.kinds.setText(item.getKinds());

        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, item.getTitle(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, Analysis_home_after.class);
                // 탭정보 (메뉴1, 메뉴2, 메뉴3 구분)전송 // 없어졌으므로 무의미
                intent.putExtra("position", position);
                //음식 종류 전송
                intent.putExtra("kinds", item.getKinds());
                intent.putExtra("title", item.getTitle());

                intent.putExtra("place", item.getplace());
                intent.putExtra("who", item.getwho());
                intent.putExtra("weather", item.getweather());
                intent.putExtra("name", item.getname());
                intent.putExtra("emotion", item.getemotion());
                intent.putExtra("calorie", item.getcalorie());
                // 고잇추천인지, 비슷한사람 추천인지, 핫한 음식인기 구분 전송 (num : 0,1,2) // 없어졌으므로 무의미
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
        TextView info;
        CardView cardview;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.iv_image);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            kinds = (TextView) itemView.findViewById(R.id.tv_kinds);
            info =  (TextView) itemView.findViewById(R.id.tv_info);
            cardview = (CardView) itemView.findViewById(R.id.card_view);

        }
    }






}