package com.GOEAT.Go_Eat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.GOEAT.Go_Eat.DataType.SimpleFoodInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.GOEAT.Go_Eat.common.Values.SHOWING_FOOD_ITEM_COUNT;

public class AnalysisHomeRecyclerAdapter extends RecyclerView.Adapter<AnalysisHomeRecyclerAdapter.ViewHolder> {

    private final List<SimpleFoodInfo> dataSet;
    private ItemClickListener listener;

    public AnalysisHomeRecyclerAdapter(List<SimpleFoodInfo> dataSet) {
        this.dataSet = dataSet;
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // analysis_cardview.xml 에서의 CardView Top,Bottom Margin 값
        final int offset = parent.getResources().getDimensionPixelSize(R.dimen.food_list_top_bottom_margin);
        final int itemHeight = (parent.getHeight() / SHOWING_FOOD_ITEM_COUNT) - offset;

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.analysis_cardview, parent, false);
        return new ViewHolder(v, itemHeight);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final SimpleFoodInfo item = dataSet.get(position);
        final Context context = holder.itemView.getContext();

        if (item.imageUrl != null && !item.imageUrl.isEmpty()) {
            Picasso.get()
                    .load(item.imageUrl)
                    .error(R.drawable.error)
                    .into(holder.image);
        }

        // TODO: description 미구현
        holder.info.setText("");
        holder.title.setText(item.secondName);
        holder.kinds.setText(context.getString(R.string.food_kind_format, item.kind, item.firstName));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(dataSet.get(holder.getAdapterPosition()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return Math.min(SHOWING_FOOD_ITEM_COUNT, dataSet.size());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView image;
        private final TextView title;
        private final TextView kinds;
        private final TextView info;

        public ViewHolder(View itemView, int recommendHeight) {
            super(itemView);
            image = itemView.findViewById(R.id.iv_image);
            title = itemView.findViewById(R.id.tv_title);
            kinds = itemView.findViewById(R.id.tv_kinds);
            info = itemView.findViewById(R.id.tv_info);

            itemView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, recommendHeight));
        }
    }

    public interface ItemClickListener {
        void onItemClick(SimpleFoodInfo info);
    }
}