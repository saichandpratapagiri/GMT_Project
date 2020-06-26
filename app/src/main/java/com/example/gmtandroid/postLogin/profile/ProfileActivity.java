package com.example.gmtandroid.postLogin.profile;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gmtandroid.BaseActivity;
import com.example.gmtandroid.R;
import com.example.gmtandroid.utilities.InternetConnection;
import com.example.gmtandroid.utilities.NoInternetActivity;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class ProfileActivity extends BaseActivity {

    private static final int PICK_IMAGE = 100;
    ProfileViewModel profileViewModel;
    ImageView profileImageview;
    TextView fundsTV;
    EditText nameTV, phoneTV, addressTV, bankNameTV, bankAccTV, bankBranchTV;
    EditText[] textViews;
    Button exitButton, editButton;
    Boolean isInEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setViews();
        setUpTextViewArray();
        makeEditTextsNonEditable();
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        loadProfile();
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
        if (InternetConnection.checkConnection(this)) {
            showProgressView();
            profileViewModel.getProfile().observe(this, new Observer<ProfileModel>() {
                @Override
                public void onChanged(ProfileModel profileModel) {
                    hideProgressView();
                    if (Objects.nonNull(profileModel.getData().getPic()))
                       Picasso.with(ProfileActivity.this).load(profileModel.getData().getPic()).fit().into(profileImageview);
                    if (Objects.nonNull(profileModel.getData().getTotalFunds()))
                        fundsTV.setText("$ "+profileModel.getData().getTotalFunds());
                    if (Objects.nonNull(profileModel.getData().getFirstName()) && Objects.nonNull(profileModel.getData().getLastName()))
                        nameTV.setText(profileModel.getData().getFirstName()+" "+profileModel.getData().getLastName());
                    if (Objects.nonNull(profileModel.getData().getPhoneNo()))
                        phoneTV.setText(profileModel.getData().getPhoneNo());
                    if (Objects.nonNull(profileModel.getData().getAddr()))
                        addressTV.setText(profileModel.getData().getAddr());
                    if (Objects.nonNull(profileModel.getData().getBankDetails())) {
                        BankDetailsItem bankDetailsItem = profileModel.getData().getBankDetails().get(0);
                        if (Objects.nonNull(bankDetailsItem.getBankName()))
                            bankNameTV.setText(bankDetailsItem.getBankName());
                        if (Objects.nonNull(bankDetailsItem.getAccNo()))
                            bankAccTV.setText(String.valueOf(bankDetailsItem.getAccNo()));
                        if (Objects.nonNull(bankDetailsItem.getBranch()))
                            bankBranchTV.setText(bankDetailsItem.getBranch());
                    }
                }
            });
        } else {
            startActivity(new Intent(ProfileActivity.this, NoInternetActivity.class));
        }
    }

    public void changeProfillePic(View view) {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
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
                        hideProgressView();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            Uri imageUri = data.getData();
            profileImageview.setImageURI(imageUri);
        }
    }
}
