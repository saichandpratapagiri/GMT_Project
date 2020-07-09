package com.example.gmtandroid.postLogin;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.gmtandroid.BaseActivity;
import com.example.gmtandroid.R;
import com.example.gmtandroid.postLogin.profile.ProfileActivity;
import com.example.gmtandroid.postLogin.profile.ProfileModel;
import com.example.gmtandroid.postLogin.profile.ProfileViewModel;
import com.example.gmtandroid.postLogin.project_story.ProjectStoryFragment;
import com.example.gmtandroid.utilities.Constant;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class PostLogin extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_login);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setItemIconTintList(null);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu,menu);
        ImageView imageView = new ImageView(PostLogin.this);
        Gson gson = new Gson();
        ProfileModel profileModel = gson.fromJson(Constant.shared.gsonProfile, ProfileModel.class);
        if (profileModel.getData().getPic() == null || profileModel.getData().getPic().equals("")) {
            imageView.setImageResource(R.drawable.ic_person_outline);
        } else
            Picasso.with(this).load(profileModel.getData().getPic()).into(imageView);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.OVAL);
        gradientDrawable.setColor(Color.parseColor("#ffffff"));
        imageView.setBackground(gradientDrawable);
        menu.getItem(1).setActionView(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PostLogin.this, ProfileActivity.class));
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {


        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(PostLogin.this, ProfileActivity.class));
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100){
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.navigation_dashboard);
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

}
