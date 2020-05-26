package com.example.gmtandroid.PostLogin.home;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gmtandroid.R;

import java.util.ArrayList;

public class FundingRecyclerViewAdapter extends RecyclerView.Adapter<FundingRecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> fundList;
    private Context context;
    private RecyclerItemClickListener listener;

    public FundingRecyclerViewAdapter(ArrayList<String> fundingList, Context context,RecyclerItemClickListener listener) {
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
        holder.card_tv.setText(fundList.get(position));
    }

    @Override
    public int getItemCount() {
        return fundList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView card_tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            card_tv = itemView.findViewById(R.id.card_text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(fundList.get(getAdapterPosition()));
                }
            });
        }
    }
}
