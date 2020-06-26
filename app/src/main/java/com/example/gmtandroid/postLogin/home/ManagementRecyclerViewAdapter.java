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

public class ManagementRecyclerViewAdapter  extends RecyclerView.Adapter<ManagementRecyclerViewAdapter.ViewHolder> {

    private List<ACTIVEFUNDINGItem> managementList;
    private Context context;
    private RecyclerItemClickListener listener;

    public ManagementRecyclerViewAdapter(List<ACTIVEFUNDINGItem> managementList, Context context,RecyclerItemClickListener listener) {
        this.managementList = managementList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ManagementRecyclerViewAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_home, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.card_tv.setText(managementList.get(position).getName());
        Picasso.with(context).load(managementList.get(position).getThumbnailUrl()).fit().into(holder.card_image);
    }

    @Override
    public int getItemCount() {
        return managementList.size();
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
                    listener.onItemClick(managementList.get(getAdapterPosition()).getId());
                }
            });
        }
    }
}
