package com.example.gmtandroid.preLogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gmtandroid.BaseActivity;
import com.example.gmtandroid.login.LoginActivity;
import com.example.gmtandroid.R;

import java.util.ArrayList;

public class PageActivity extends BaseActivity {

    private ViewPager viewPager;
    private ArrayList<Integer> arrayList;
    private LinearLayout layout_dot;
    private TextView[] dot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        viewPager = findViewById(R.id.page_view);
        layout_dot = (LinearLayout) findViewById(R.id.dot_layout);
        arrayList = new ArrayList<>();

        arrayList.add(R.color.colorAccent);
        arrayList.add(R.color.colorPrimary);
        arrayList.add(R.color.colorPrimaryDark);
        arrayList.add(R.color.colorAccent);

        CustomPagerAdapter pagerAdapter = new CustomPagerAdapter(getApplicationContext(), arrayList);
        viewPager.setAdapter(pagerAdapter);
        addDot(0);

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

    public void getStarted(View view) {
        startActivity(new Intent(PageActivity.this, LoginActivity.class));
        finish();
    }
}
