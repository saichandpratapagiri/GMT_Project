package com.example.gmtandroid.PostLogin.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gmtandroid.fundingDetails.FundingDetailsActivity;
import com.example.gmtandroid.R;

public class HomeFragment extends Fragment implements RecyclerItemClickListener {

    private HomeViewModel homeViewModel;
    private RecyclerView fundingRv;
    private RecyclerView managementRv;
    private Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        fundingRv = root.findViewById(R.id.funding_rv);
        managementRv = root.findViewById(R.id.management_rv);
        //Setting Layout Managers
        fundingRv.setLayoutManager(new LinearLayoutManager(context));
        managementRv.setLayoutManager(new LinearLayoutManager(context));
        //Setting Adapters
        fundingRv.setAdapter(new FundingRecyclerViewAdapter(homeViewModel.getFundList(), context, this));
        managementRv.setAdapter(new ManagementRecyclerViewAdapter(homeViewModel.getManagementList(), context, this));
        //Disabling scrolling
        fundingRv.setNestedScrollingEnabled(false);
        managementRv.setNestedScrollingEnabled(false);
        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onItemClick(String string) {
        Intent intent = new Intent(context, FundingDetailsActivity.class);
        context.startActivity(intent);
    }
}
