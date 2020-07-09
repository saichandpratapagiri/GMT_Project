package com.example.gmtandroid.postLogin.profile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gmtandroid.BaseActivity;
import com.example.gmtandroid.R;
import com.example.gmtandroid.postLogin.photo_upload_models.GetUploadUrlResponse;
import com.example.gmtandroid.postLogin.photo_upload_models.PhotoUploadRequest;
import com.example.gmtandroid.postLogin.unconfirmed_funds.UploadRecieptActivity;
import com.example.gmtandroid.utilities.Constant;
import com.example.gmtandroid.utilities.InternetConnection;
import com.example.gmtandroid.utilities.NoInternetActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class ProfileActivity extends BaseActivity {

    private static final int PICK_IMAGE = 100;
    ProfileViewModel profileViewModel;
    ImageView profileImageview;
    TextView fundsTV;
    EditText nameTV, phoneTV, addressTV, bankNameTV, bankAccTV, bankBranchTV;
    EditText[] textViews;
    Button exitButton, editButton;
    FloatingActionButton floatingActionButton;
    Boolean isInEditMode = false;
    String uploadImageUrl = "";
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setViews();
        setUpTextViewArray();
        makeEditTextsNonEditable();
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        loadProfile();
        profileImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profileImageview.getDrawable() != null && imageUri != null)
                     attachPhoto(ProfileActivity.this, imageUri);
            }
        });
    }

    private void setViews() {
        profileImageview = findViewById(R.id.profile_image);
        fundsTV = findViewById(R.id.funds);
        nameTV = findViewById(R.id.profile_name);
        phoneTV = findViewById(R.id.profile_phNum);
        addressTV = findViewById(R.id.profile_address);
        bankNameTV = findViewById(R.id.bank_name);
        bankAccTV = findViewById(R.id.bank_acc_no);
        bankBranchTV = findViewById(R.id.bank_branch);
        exitButton = findViewById(R.id.nav_back_btn);
        editButton = findViewById(R.id.edit_personal_details_btn);
        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.hide();

        addressTV.setImeOptions(EditorInfo.IME_ACTION_DONE);
        addressTV.setRawInputType(InputType.TYPE_CLASS_TEXT);
    }

    private  void setUpTextViewArray() {
        textViews = new EditText[6];
        textViews[0] = nameTV;
        textViews[1] = phoneTV;
        textViews[2] = addressTV;
        textViews[3] = bankNameTV;
        textViews[4] = bankAccTV;
        textViews[5] = bankBranchTV;
    }

    private void loadProfile() {
//        if (InternetConnection.checkConnection(this)) {
//            showProgressView();
//            profileViewModel.getProfile().observe(this, new Observer<ProfileModel>() {
//                @Override
//                public void onChanged(ProfileModel profileModel) {
//                    hideProgressView();
        Gson gson = new Gson();
        ProfileModel profileModel = gson.fromJson(Constant.shared.gsonProfile, ProfileModel.class);
                    if (profileModel.getData().getPic() != null && !profileModel.getData().getPic().equals(""))
                       Picasso.with(ProfileActivity.this).load(profileModel.getData().getPic()).fit().into(profileImageview);
                    if (profileModel.getData().getTotalFunds() !=null)
                        fundsTV.setText("$ "+profileModel.getData().getTotalFunds());
                    if (profileModel.getData().getFirstName()!= null && profileModel.getData().getLastName() != null)
                        nameTV.setText(profileModel.getData().getFirstName()+" "+profileModel.getData().getLastName());
                    if (profileModel.getData().getPhoneNo() !=null)
                        phoneTV.setText(profileModel.getData().getPhoneNo());
                    if (profileModel.getData().getAddr() != null)
                        addressTV.setText(profileModel.getData().getAddr());
                    if (profileModel.getData().getBankDetails() != null && ! profileModel.getData().getBankDetails().isEmpty()) {
                        BankDetailsItem bankDetailsItem = profileModel.getData().getBankDetails().get(0);
                        if (bankDetailsItem.getBankName() != null)
                            bankNameTV.setText(bankDetailsItem.getBankName());
                        if (bankDetailsItem.getAccNo() != null)
                            bankAccTV.setText(String.valueOf(bankDetailsItem.getAccNo()));
                        if (bankDetailsItem.getBranch() != null)
                            bankBranchTV.setText(bankDetailsItem.getBranch());
                    }
//                }
//            });
//        } else {
//            startActivity(new Intent(ProfileActivity.this, NoInternetActivity.class));
//        }
    }

    public void changeProfillePic(View view) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
        } else {
            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(gallery, 100);
        }
    }

    public void editProfileDetails(View view) {
        makeEditTextsEditable();
    }

    private void makeEditTextsNonEditable() {
        for (EditText tv: textViews) {
            tv.setFocusable(false);
            tv.setEnabled(false);
            tv.setClickable(false);
            tv.setFocusableInTouchMode(false);
            tv.setBackground(new ColorDrawable(Color.TRANSPARENT));
        }
        exitButton.setText(R.string.back);
        editButton.setVisibility(View.VISIBLE);
        floatingActionButton.hide();
        isInEditMode = false;
    }

    private void makeEditTextsEditable() {
        for (EditText tv: textViews) {
            tv.setFocusable(true);
            tv.setEnabled(true);
            tv.setClickable(true);
            tv.setFocusableInTouchMode(true);
            tv.setBackground(getDrawable(R.color.screen_background));
        }
        exitButton.setText(R.string.save_details);
        editButton.setVisibility(View.GONE);
        floatingActionButton.show();
        isInEditMode = true;
    }

    public void nav_back(View view) {
        if (isInEditMode) {
            if (InternetConnection.checkConnection(this)) {
                showProgressView();
                ProfileRequestBody requestBody = new ProfileRequestBody();
                Attributes attributes = new Attributes();
                attributes.setAddr(addressTV.getText().toString());
                BankDetailsItem bankDetailsItem = new BankDetailsItem();
                bankDetailsItem.setAccNo(Integer.parseInt(bankAccTV.getText().toString()));
                bankDetailsItem.setBankName(bankNameTV.getText().toString());
                bankDetailsItem.setBranch(bankBranchTV.getText().toString());
                attributes.setBankDetails(bankDetailsItem);
                attributes.setPhoneNo(phoneTV.getText().toString());
                attributes.setPic(uploadImageUrl);
                String name = nameTV.getText().toString();
                if (name.split(" ").length > 1) {
                    attributes.setFirstName(name.substring(0,name.indexOf(' ')));
                    attributes.setLastName(name.substring(name.indexOf(' ') + 1));
                } else {
                    attributes.setFirstName(name);
                }
                requestBody.setAttributes(attributes);
                profileViewModel.setProfileRequestBody(requestBody);
                profileViewModel.setProfile().observe(this, new Observer<ProfileRequestBody>() {
                    @Override
                    public void onChanged(ProfileRequestBody profileRequestBody) {
                        profileViewModel.getProfile().observe(ProfileActivity.this, new Observer<ProfileModel>() {
                            @Override
                            public void onChanged(ProfileModel profileModel) {
                                hideProgressView();
                            }
                        });
                    }
                });
            } else {
                startActivity(new Intent(ProfileActivity.this, NoInternetActivity.class));
            }
            makeEditTextsNonEditable();
        } else {
            finish();
        }
    }

    private void setGsonProfile(ProfileRequestBody profileRequestBody) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE && InternetConnection.checkConnection(this)){
            try {
                imageUri = data.getData();
                String extension = getFileExtension(imageUri);
                PhotoUploadRequest request = new PhotoUploadRequest();
                request.setExtension(extension);
                showProgressView();
                profileViewModel.getUploadImageUrl(request).observe(this, new Observer<GetUploadUrlResponse>() {
                    @Override
                    public void onChanged(GetUploadUrlResponse getUploadUrlResponse) {
                        try {
                            hideProgressView();
                            setUpMultiPartBody(imageUri, getUploadUrlResponse);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }catch (Exception e) {
                e.printStackTrace();
            }
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
            profileViewModel.uploadImage(body, uploadUrl).observe(this, new Observer<ResponseBody>() {
                @Override
                public void onChanged(ResponseBody responseBody) {
                    hideProgressView();
                    if (responseBody != null) {
                        profileImageview.setImageURI(imageUri);
                        uploadImageUrl = getUploadUrlResponse.getImageurl();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
