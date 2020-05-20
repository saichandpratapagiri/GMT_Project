package com.example.gmtandroid.PreLogin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.gmtandroid.R;

import java.util.ArrayList;

public class CustomPagerAdapter extends PagerAdapter {

    Context context;
    ArrayList<Integer> pager;

    public CustomPagerAdapter(Context context, ArrayList<Integer> pager) {
        this.context = context;
        this.pager = pager;
    }

    @Override
    public int getCount() {
        return pager.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.page_item, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.prelogin_images);
        imageView.setBackgroundResource(pager.get(position));
        container.addView(view);
        return view;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
