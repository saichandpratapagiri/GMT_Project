package com.example.gmtandroid.postLogin.unconfirmed_funds;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.gmtandroid.BaseActivity;
import com.example.gmtandroid.postLogin.PostLogin;
import com.example.gmtandroid.R;
import com.example.gmtandroid.postLogin.profile.ProfileActivity;
import com.example.gmtandroid.utilities.InternetConnection;
import com.example.gmtandroid.utilities.NoInternetActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FundsActivity extends BaseActivity {

    private ViewPager viewPager;
    private List<Datum> arrayList;
    private LinearLayout layout_dot;
    private TextView[] dot;
    private UnconfirmedFundsviewModel viewModel;
    private UnconfirmedFunds unconfdFunds;
    private int currentpage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funds);
        viewPager = findViewById(R.id.funds_page_view);
        layout_dot = (LinearLayout) findViewById(R.id.funds_dot_layout);
        arrayList = new ArrayList<>();
        viewModel = ViewModelProviders.of(this).get(UnconfirmedFundsviewModel.class);

    }

    private void getUnconfirmedFunds() {
        if (InternetConnection.checkConnection(this)) {
            showProgressView();
            viewModel.getUnconfirmedFunds().observe(this, new Observer<UnconfirmedFunds>() {
                @Override
                public void onChanged(UnconfirmedFunds unconfirmedFunds) {
                    hideProgressView();
                    if (unconfirmedFunds.getData() != null) {
                        if (unconfirmedFunds.getData().size() == 0) navigate();
                        unconfdFunds = unconfirmedFunds;
                        arrayList = unconfirmedFunds.getData();
                        setPageAdapter();
                    } else {
                        navigate();
                    }
                }
            });
        } else {
            startActivity(new Intent(FundsActivity.this, NoInternetActivity.class));
        }
    }

    private void setPageAdapter() {
        FundPagerAdapter pagerAdapter = new FundPagerAdapter(getApplicationContext(), arrayList, unconfdFunds);
        viewPager.setAdapter(pagerAdapter);
        if (arrayList.size() > 1) addDot(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }
            @Override
            public void onPageSelected(int i) {
                addDot(i);
            }
            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void addDot(int page_position) {
        dot = new TextView[arrayList.size()];
        currentpage = page_position;
        layout_dot.removeAllViews();

        for (int i = 0; i < dot.length; i++) {;
            dot[i] = new TextView(this);
            dot[i].setText(Html.fromHtml("&#9679;"));
            dot[i].setTextSize(25);
            dot[i].setTextColor(getResources().getColor(R.color.darker_gray));
            layout_dot.addView(dot[i]);
        }

        dot[page_position].setTextColor(getResources().getColor(R.color.colorPrimary));
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUnconfirmedFunds();
    }

    public void closeFunds(View view) {
        navigate();
    }

    private void navigate() {
        Intent intent = new Intent(FundsActivity.this, PostLogin.class);
        startActivity(intent);
        finish();
    }

}
