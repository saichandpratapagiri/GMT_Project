package com.example.gmtandroid.postLogin.unconfirmed_funds;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gmtandroid.BaseActivity;
import com.example.gmtandroid.R;
import com.example.gmtandroid.postLogin.photo_upload_models.GetUploadUrlResponse;
import com.example.gmtandroid.postLogin.photo_upload_models.PhotoUploadRequest;
import com.example.gmtandroid.utilities.InternetConnection;
import com.example.gmtandroid.utilities.NoInternetActivity;

import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class UploadRecieptActivity extends BaseActivity {

    private ImageView logo_image, upload_image;
    private TextView dateTV, fund_amount_tv;
    private EditText editText_with_funds;
    private UnconfirmedFunds unconfirmedFunds;
    private int currentPage = 0, funds = 0;
    private Uri imageUri;
    private PhotoUploadRequest photoUploadRequest;
    private UnconfirmedFundsviewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_reciept);
        setViewsId();
        viewModel = ViewModelProviders.of(this).get(UnconfirmedFundsviewModel.class);
        unconfirmedFunds = (UnconfirmedFunds) getIntent().getExtras().getSerializable("unconfirmed_funds");
        currentPage = getIntent().getIntExtra("page_position", 0);
        setViews();
        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (upload_image.getDrawable() == null) {
                    if (ContextCompat.checkSelfPermission(UploadRecieptActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(UploadRecieptActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                1);
                    } else {
                        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        startActivityForResult(gallery, 100);
                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(UploadRecieptActivity.this).setTitle("Select Action")
                            .setNeutralButton("View photo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    attachPhoto(UploadRecieptActivity.this, imageUri);
                                }
                            })
                            .setPositiveButton("Add another", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                                    startActivityForResult(gallery, 100);
                                }
                            });
                    builder.create().show();
                }
            }
        });

        editText_with_funds.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals("")) {
                    int value = Integer.valueOf(s.toString());
                    if (value <= funds) {
                        editText_with_funds.removeTextChangedListener(this);
                        editText_with_funds.setText(String.valueOf(value)+"");
                        editText_with_funds.setSelection(String.valueOf(value).length());
                        editText_with_funds.addTextChangedListener(this);
                    } else {
                        editText_with_funds.setError("Amount shouldn't be greater than fund received");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void setViewsId() {
        logo_image = findViewById(R.id.reciept_project_image);
        upload_image = findViewById(R.id.upload_reciept_image);
        dateTV = findViewById(R.id.reciept_project_date);
        fund_amount_tv = findViewById(R.id.reciept_amount_tv);
        editText_with_funds = findViewById(R.id.reciept_enter_amount);
    }

    private void setViews() {
        Picasso.with(this).load(unconfirmedFunds.getData().get(currentPage).getPic()).into(logo_image);
        dateTV.setText(unconfirmedFunds.getData().get(currentPage).getCreatedAt().substring(0, 10));
        fund_amount_tv.setText(unconfirmedFunds.getData().get(currentPage).getAmount());
        funds = Integer.parseInt(fund_amount_tv.getText().toString());
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(data != null) {
            imageUri = data.getData();
            String extension = getFileExtension(imageUri);
            photoUploadRequest = new PhotoUploadRequest();
            photoUploadRequest.setExtension(extension);
            upload_image.setImageURI(imageUri);
        }
    }

    public void returnToFundActivity(View view) {
        if (photoUploadRequest != null && InternetConnection.checkConnection(this) && !editText_with_funds.getText().toString().isEmpty() && editText_with_funds.getError() == null) {
            showProgressView();
            viewModel.getUploadImageUrl(photoUploadRequest).observe(this, new Observer<GetUploadUrlResponse>() {
                @Override
                public void onChanged(GetUploadUrlResponse getUploadUrlResponse) {
                    try {
                        hideProgressView();
                        if (getUploadUrlResponse != null) setUpMultiPartBody(imageUri, getUploadUrlResponse);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            if (photoUploadRequest == null) Toast.makeText(this, "Attach a reciept", Toast.LENGTH_SHORT).show();
            else if (editText_with_funds.getError() != null) Toast.makeText(this, "Enter Valid Amount", Toast.LENGTH_SHORT).show();
            else if (editText_with_funds.getText().toString().equals("")) Toast.makeText(this, "Enter Amount", Toast.LENGTH_SHORT).show();
            else startActivity(new Intent(this, NoInternetActivity.class));
        }
    }

    private void setUpMultiPartBody(Uri imageUri, GetUploadUrlResponse getUploadUrlResponse) {
        try {
            String path = getFilePath(imageUri);
            File file = new File(path);
            RequestBody requestFile = RequestBody.create(
                    MediaType.parse(getContentResolver().getType(imageUri)),
                    file
            );
            MultipartBody.Part body = MultipartBody.Part.createFormData("picture", file.getName(), requestFile);
            String uploadUrl = getUploadUrlResponse.getUploadurl();
            showProgressView();
            viewModel.uploadImage(body, uploadUrl).observe(this, new Observer<ResponseBody>() {
                @Override
                public void onChanged(ResponseBody responseBody) {
                    hideProgressView();
                    if (responseBody != null) {
                        UnconfirmedFundsRequest request = new UnconfirmedFundsRequest();
                        request.setAttributes(new Attributes());
                        request.getAttributes().setAmount(fund_amount_tv.getText().toString());
                        request.getAttributes().setFundId(unconfirmedFunds.getData().get(currentPage).getId());
                        request.getAttributes().setProjectId(unconfirmedFunds.getData().get(currentPage).getProjectId());
                        request.getAttributes().setPic(getUploadUrlResponse.getData().get(0).getImageUrl());
                        showProgressView();
                        viewModel.uploadReciept(request).observe(UploadRecieptActivity.this, new Observer<ResponseBody>() {
                            @Override
                            public void onChanged(ResponseBody responseBody) {
                                hideProgressView();
                                Toast.makeText(UploadRecieptActivity.this, "Upload Success", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }
                    else {
                        Toast.makeText(UploadRecieptActivity.this, "Failed try again later", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
