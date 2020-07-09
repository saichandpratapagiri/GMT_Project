package com.example.gmtandroid.utilities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.gmtandroid.R;

import uk.co.senab.photoview.PhotoViewAttacher;

public class PhotoViewerActivity extends AppCompatActivity {

    private ImageView photoImageView;
    private View backgroundView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_viewer);
        photoImageView = findViewById(R.id.attach_photo);
        backgroundView = findViewById(R.id.background);
        if (getIntent().getExtras() != null) {
            photoImageView.setImageURI(Uri.parse(getIntent().getStringExtra("imageUri")));
            PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(photoImageView);
            photoViewAttacher.update();
        }
    }

    public void goBack(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
