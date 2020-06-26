package com.example.gmtandroid.postLogin.fundingDetails;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.gmtandroid.BaseActivity;
import com.example.gmtandroid.postLogin.home.ACTIVEFUNDINGItem;
import com.example.gmtandroid.postLogin.profile.ProfileActivity;
import com.example.gmtandroid.R;
import com.example.gmtandroid.utilities.FragmentUtils;
import com.example.gmtandroid.databinding.ActivityFundingDetailsBinding;
import com.example.gmtandroid.utilities.InternetConnection;
import com.example.gmtandroid.utilities.NoInternetActivity;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.io.Serializable;

public class FundingDetailsActivity extends BaseActivity  {

    private int id;
    private FundDetailsViewModel viewModel;
    private ActivityFundingDetailsBinding binding;
    private ImageView headerImageView;
    private TextView projectTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getIntExtra("ID", 0);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_funding_details);
        prepareActionBar(binding.toolbar);
        prepareTabLayout(binding.tabLayout);
        viewModel = ViewModelProviders.of(this).get(FundDetailsViewModel.class);
        headerImageView = findViewById(R.id.header);
        projectTV = findViewById(R.id.header_text);
        getFundingForProject();
    }

    @Override
    protected void onResume() {
        getFundingForProject();
        super.onResume();
    }

    private void getFundingForProject() {
        if (InternetConnection.checkConnection(this)) {
            showProgressView();
            viewModel.getFundingDetails(id).observe(this, new Observer<FundingDetailsModel>() {
                @Override
                public void onChanged(FundingDetailsModel fundingDetailsModel) {
                    hideProgressView();
                    Picasso.with(FundingDetailsActivity.this).load(fundingDetailsModel.getData().getThumbnailUrl()).into(headerImageView);
                    projectTV.setText(fundingDetailsModel.getData().getName());
                }
            });
        } else {
            startActivity(new Intent(FundingDetailsActivity.this, NoInternetActivity.class));
        }
    }

    public FundDetailsViewModel getViewModel() {
        return viewModel;
    }

    private void prepareActionBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
    }

    private void prepareTabLayout(TabLayout tabLayout) {
        if (tabLayout != null) {
            tabLayout.addTab(tabLayout.newTab().setText("Project Details"));
            tabLayout.addTab(tabLayout.newTab().setText("Location Map"));
        }
        if (tabLayout != null) {
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    selectFragment(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

            //set default tab selection
            TabLayout.Tab tab = tabLayout.getTabAt(0);
            if (tab != null) {
                tab.select();
                selectFragment(0);
            }
        }
    }

    private void selectFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = ProjectDetailsFragment.newInstance();
                break;
            case 1:
                fragment = LocationMapFragment.newInstance();
                break;
        }
        if (fragment != null) {
            FragmentUtils.addFragment(this, fragment, R.id.fund_fragment_container);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu,menu);
        ImageView imageView = new ImageView(FundingDetailsActivity.this);
        imageView.setImageResource(R.drawable.ic_person_outline);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.OVAL);
        gradientDrawable.setColor(Color.parseColor("#ffffff"));
        imageView.setBackground(gradientDrawable);
        menu.getItem(1).setActionView(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FundingDetailsActivity.this, ProfileActivity.class));
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(FundingDetailsActivity.this, ProfileActivity.class));
        return super.onOptionsItemSelected(item);
    }

    public void onBackButtonClick(View view) {
        super.onBackPressed();
    }
}
