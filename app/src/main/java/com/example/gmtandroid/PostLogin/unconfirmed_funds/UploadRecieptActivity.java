package com.example.gmtandroid.PostLogin.unconfirmed_funds;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.gmtandroid.R;

public class UploadRecieptActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_reciept);
    }

    public void returnToFundActivity(View view) {
        finish();
    }
}
