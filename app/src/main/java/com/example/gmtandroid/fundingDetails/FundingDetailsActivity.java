package com.example.gmtandroid.fundingDetails;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.gmtandroid.PostLogin.ProfileActivity;
import com.example.gmtandroid.R;
import com.example.gmtandroid.Utilities.FragmentUtils;
import com.example.gmtandroid.databinding.ActivityFundingDetailsBinding;
import com.google.android.material.tabs.TabLayout;

public class FundingDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFundingDetailsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_funding_details);
        prepareTabLayout(binding.tabLayout);
        prepareActionBar(binding.toolbar);
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
        getMenuInflater().inflate(R.menu.profile_menu, menu);
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
