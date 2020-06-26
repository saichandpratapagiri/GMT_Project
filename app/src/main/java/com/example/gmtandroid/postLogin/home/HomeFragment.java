package com.example.gmtandroid.postLogin.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gmtandroid.postLogin.PostLogin;
import com.example.gmtandroid.postLogin.fundingDetails.FundingDetailsActivity;
import com.example.gmtandroid.R;
import com.example.gmtandroid.utilities.Constant;
import com.example.gmtandroid.utilities.InternetConnection;
import com.example.gmtandroid.utilities.NoInternetActivity;

import java.util.Objects;

public class HomeFragment extends Fragment implements RecyclerItemClickListener {

    private HomeViewModel homeViewModel;
    private RecyclerView fundingRv;
    private RecyclerView managementRv;
    private Context ctx;
    private PostLogin postLogin;
    private TextView tv1, tv2, tv3;
    private Boolean isScreenLoaded = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        postLogin = (PostLogin) getActivity();
        tv1 = root.findViewById(R.id.projects_in_funding);
        fundingRv = root.findViewById(R.id.funding_rv);
        tv2 = root.findViewById(R.id.projects_in_management);
        managementRv = root.findViewById(R.id.management_rv);
        tv3 = root.findViewById(R.id.no_projects_found);
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        setAdapters();
        return root;
    }

    private void setAdapters(){
        try {
            if (InternetConnection.checkConnection(ctx)) {
                postLogin.showProgressView();
                homeViewModel.getProjectLists().observe(postLogin, new Observer<ProjectList>() {
                    @Override
                    public void onChanged(ProjectList projectList) {
                        postLogin.hideProgressView();
                        isScreenLoaded = true;
                        if (Objects.nonNull(projectList.getData())) {
                            homeViewModel.setFundList(projectList.getData().getACTIVEFUNDING());
                            homeViewModel.setManagementList(projectList.getData().getACTIVEMANAGEMENT());
                            Constant.shared.projectList.addAll(homeViewModel.getFundList());
                            Constant.shared.projectList.addAll(homeViewModel.getManagementList());
                            Log.i("lists", homeViewModel.getFundList().toString() + " " + homeViewModel.getManagementList().toString());
                            if (!homeViewModel.getFundList().isEmpty()) {
                                fundingRv.setVisibility(View.VISIBLE);
                                tv1.setVisibility(View.VISIBLE);
                            }
                            if (!homeViewModel.getManagementList().isEmpty()) {
                                tv2.setVisibility(View.VISIBLE);
                                managementRv.setVisibility(View.VISIBLE);
                            }
                            if (homeViewModel.getFundList().isEmpty() && homeViewModel.getManagementList().isEmpty()) {
                                tv3.setVisibility(View.VISIBLE);
                            }
                            //Setting Adapters
                            FundingRecyclerViewAdapter fundingRecyclerViewAdapter = new FundingRecyclerViewAdapter(homeViewModel.getFundList(), ctx, HomeFragment.this);
                            fundingRv.setAdapter(fundingRecyclerViewAdapter);
                            ManagementRecyclerViewAdapter managementRecyclerViewAdapter = new ManagementRecyclerViewAdapter(homeViewModel.getManagementList(), ctx, HomeFragment.this);
                            managementRv.setAdapter(managementRecyclerViewAdapter);
                            //Disabling scrolling
                            fundingRv.setNestedScrollingEnabled(false);
                            managementRv.setNestedScrollingEnabled(false);
                            //Setting Layout Managers
                            fundingRv.setLayoutManager(new LinearLayoutManager(ctx));
                            managementRv.setLayoutManager(new LinearLayoutManager(ctx));
                            //notify data changed
                            fundingRecyclerViewAdapter.notifyDataSetChanged();
                            managementRecyclerViewAdapter.notifyDataSetChanged();
                        } else {
                            tv3.setVisibility(View.VISIBLE);
                        }
                    }
                });
            } else {
                startActivity(new Intent(ctx, NoInternetActivity.class));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.ctx = context;
    }

    @Override
    public void onItemClick(int id) {
        Intent intent = new Intent(ctx, FundingDetailsActivity.class);
        intent.putExtra("ID", id);
        ctx.startActivity(intent);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isScreenLoaded) setAdapters();
    }
}
