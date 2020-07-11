package com.example.gmtandroid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;


import com.example.gmtandroid.utilities.PhotoViewerActivity;
import com.victor.loading.rotate.RotateLoading;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;


public class BaseActivity extends AppCompatActivity {

    private RotateLoading loader;
    ViewGroup progressView;
    protected boolean isProgressShowing = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("SFCompactDisplay-Regular.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    public void showProgressView() {
        if(!isProgressShowing) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            progressView = (ViewGroup) getLayoutInflater().inflate(R.layout.progressbar_layout, null);
            loader = progressView.findViewById(R.id.newton_cradle_loading);
            loader.setLoadingColor(R.color.red);
            loader.start();
            View v = this.findViewById(android.R.id.content).getRootView();
            ViewGroup viewGroup = (ViewGroup) v;
            viewGroup.addView(progressView);
            isProgressShowing = true;
        }
    }

    public void hideProgressView() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        loader.stop();
        View v = this.findViewById(android.R.id.content).getRootView();
        ViewGroup viewGroup = (ViewGroup) v;
        viewGroup.removeView(progressView);
        isProgressShowing = false;
    }

    public void showOkAlertMessage(Context context, String message) {
        AlertDialog.Builder alertDialog  = new AlertDialog.Builder(context);
        alertDialog.setMessage(message)
        .setCancelable(false)
        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        })
        ;
    }

    public String getFileExtension(Uri uri)
    {
//        ContentResolver contentResolver=getContentResolver();
//        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
//
//        // Return file Extension
//        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
        String filePath = getFilePath(uri);
        return filePath.substring(filePath.lastIndexOf(".") + 1);
    }

    public String getFilePath(Uri contentURI) {
        try {
            String result;
            Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
            if (cursor == null) { // Source is Dropbox or other similar local file path
                result = contentURI.getPath();
            } else {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                result = cursor.getString(idx);
                cursor.close();
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void attachPhoto(Context context, Uri imageUri) {
        Intent intent = new Intent(context, PhotoViewerActivity.class);
        intent.putExtra("imageUri", imageUri.toString());
        startActivity(intent);
    }

}
