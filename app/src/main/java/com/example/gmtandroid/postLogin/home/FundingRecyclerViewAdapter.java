package com.example.gmtandroid.postLogin.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gmtandroid.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FundingRecyclerViewAdapter extends RecyclerView.Adapter<FundingRecyclerViewAdapter.ViewHolder> {

    private List<ACTIVEFUNDINGItem> fundList;
    private Context context;
    private RecyclerItemClickListener listener;

    public FundingRecyclerViewAdapter(List<ACTIVEFUNDINGItem> fundingList, Context context,RecyclerItemClickListener listener) {
        this.fundList = fundingList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FundingRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_home, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FundingRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.card_tv.setText(fundList.get(position).getName());
        Picasso.with(context).load(fundList.get(position).getThumbnailUrl()).fit().into(holder.card_image);
    }

    @Override
    public int getItemCount() {
        return fundList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView card_tv;
        ImageView card_image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            card_tv = itemView.findViewById(R.id.card_text);
            card_image = itemView.findViewById(R.id.card_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(fundList.get(getAdapterPosition()).getId());
                }
            });
        }
    }
}
