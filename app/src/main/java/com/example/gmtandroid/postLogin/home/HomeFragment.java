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
    private RecyclerView notYetDeployedRv;
    private RecyclerView fundingRv;
    private RecyclerView managementRv;
    private Context ctx;
    private PostLogin postLogin;
    private TextView tv, tv1, tv2, tv3;
    private Boolean isScreenLoaded = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        postLogin = (PostLogin) getActivity();
        tv = root.findViewById(R.id.projects_not_yet_deployed);
        notYetDeployedRv = root.findViewById(R.id.funding_nyd);
        tv1 = root.findViewById(R.id.projects_in_funding);
        fundingRv = root.findViewById(R.id.funding_rv);
        tv2 = root.findViewById(R.id.projects_in_management);
        managementRv = root.findViewById(R.id.management_rv);
        tv3 = root.findViewById(R.id.no_projects_found);
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
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
                        if (projectList.getData() != null) {
                            homeViewModel.setNotYetDeployedList(projectList.getData().getNOTYETDEPLOYED());
                            homeViewModel.setFundList(projectList.getData().getACTIVEFUNDING());
                            homeViewModel.setManagementList(projectList.getData().getACTIVEMANAGEMENT());
                            Log.i("lists", homeViewModel.getFundList().toString() + " " + homeViewModel.getManagementList().toString());
                            if (!homeViewModel.getNotYetDeployedList().isEmpty()) {
                                Constant.shared.projectList.addAll(homeViewModel.getNotYetDeployedList());
                                notYetDeployedRv.setVisibility(View.VISIBLE);
                                tv.setVisibility(View.VISIBLE);
                            }
                            if (!homeViewModel.getFundList().isEmpty()) {
                                Constant.shared.projectList.addAll(homeViewModel.getFundList());
                                fundingRv.setVisibility(View.VISIBLE);
                                tv1.setVisibility(View.VISIBLE);
                            }
                            if (!homeViewModel.getManagementList().isEmpty()) {
                                Constant.shared.projectList.addAll(homeViewModel.getManagementList());
                                tv2.setVisibility(View.VISIBLE);
                                managementRv.setVisibility(View.VISIBLE);
                            }
                            if (homeViewModel.getNotYetDeployedList().isEmpty() && homeViewModel.getFundList().isEmpty() && homeViewModel.getManagementList().isEmpty()) {
                                tv3.setVisibility(View.VISIBLE);
                            }
                            //Disabling scrolling
                            notYetDeployedRv.setNestedScrollingEnabled(false);
                            fundingRv.setNestedScrollingEnabled(false);
                            managementRv.setNestedScrollingEnabled(false);
                            //Setting Layout Managers
                            notYetDeployedRv.setLayoutManager(new LinearLayoutManager(ctx));
                            fundingRv.setLayoutManager(new LinearLayoutManager(ctx));
                            managementRv.setLayoutManager(new LinearLayoutManager(ctx));
                            //Setting Adapters
                            FundingRecyclerViewAdapter notYetDeployedRecyclerViewAdapter = new FundingRecyclerViewAdapter(homeViewModel.getNotYetDeployedList(), ctx, HomeFragment.this);
                            notYetDeployedRv.setAdapter(notYetDeployedRecyclerViewAdapter);
                            FundingRecyclerViewAdapter fundingRecyclerViewAdapter = new FundingRecyclerViewAdapter(homeViewModel.getFundList(), ctx, HomeFragment.this);
                            fundingRv.setAdapter(fundingRecyclerViewAdapter);
                            FundingRecyclerViewAdapter managementRecyclerViewAdapter = new FundingRecyclerViewAdapter(homeViewModel.getManagementList(), ctx, HomeFragment.this);
                            managementRv.setAdapter(managementRecyclerViewAdapter);
                            //notify data changed
                            notYetDeployedRecyclerViewAdapter.notifyDataSetChanged();
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
        if (!isScreenLoaded) {
            Constant.shared.projectList.clear();
            setAdapters();
        }
    }
}
