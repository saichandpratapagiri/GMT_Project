package com.example.gmtandroid.postLogin.unconfirmed_funds;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.gmtandroid.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FundPagerAdapter extends PagerAdapter {

    Context context;
    List<Datum> pager;
    private TextView fundAmountTV, projectdateTV, descTV;
    private ImageView imageView;
    private UnconfirmedFunds unconfirmedFunds;

    public FundPagerAdapter(Context context, List<Datum> pager, UnconfirmedFunds unconfirmedFunds) {
        this.context = context;
        this.pager = pager;
        this.unconfirmedFunds = unconfirmedFunds;
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
    public Object instantiateItem(@NonNull final ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.funds_item, container, false);
        imageView =view.findViewById(R.id.funds_project_image);
        fundAmountTV = view.findViewById(R.id.fund_amount_tv);
        projectdateTV = view.findViewById(R.id.funds_project_date);
        descTV = view.findViewById(R.id.funds_project_desc);
        Button btn = view.findViewById(R.id.Confirm_btn);
        Datum data = pager.get(position);
        if (data.getPic() != null) Picasso.with(context).load(data.getPic()).into(imageView);
        if (data.getAmount() != null) fundAmountTV.setText("$ "+data.getAmount());
        if (data.getCreatedAt() != null) projectdateTV.setText(data.getCreatedAt().substring(0, 10));
        if (data.getProject().getName() != null) descTV.setText(data.getProject().getName());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, UploadRecieptActivity.class);
                i.putExtra("unconfirmed_funds", unconfirmedFunds);
                i.putExtra("page_position", position);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
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
