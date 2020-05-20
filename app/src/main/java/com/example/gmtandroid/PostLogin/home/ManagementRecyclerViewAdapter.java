package com.example.gmtandroid.PostLogin.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gmtandroid.R;

import java.util.ArrayList;

public class ManagementRecyclerViewAdapter  extends RecyclerView.Adapter<ManagementRecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> managementList;
    private Context context;

    public ManagementRecyclerViewAdapter(ArrayList<String> managementList, Context context) {
        this.managementList = managementList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ManagementRecyclerViewAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_home, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.card_tv.setText(managementList.get(position));
    }

    @Override
    public int getItemCount() {
        return managementList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView card_tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            card_tv = itemView.findViewById(R.id.card_text);
        }
    }
}
