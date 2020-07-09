package com.example.gmtandroid.login;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gmtandroid.BaseActivity;
import com.example.gmtandroid.postLogin.profile.ProfileModel;
import com.example.gmtandroid.postLogin.profile.ProfileViewModel;
import com.example.gmtandroid.utilities.Constant;
import com.example.gmtandroid.postLogin.unconfirmed_funds.FundsActivity;
import com.example.gmtandroid.R;
import com.example.gmtandroid.utilities.FingerprintHandler;
import com.example.gmtandroid.utilities.InternetConnection;
import com.example.gmtandroid.utilities.NoInternetActivity;
import com.google.gson.Gson;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class LoginActivity extends BaseActivity {

    private EditText usernameEt, pswdEt;
    private String username, pswd;
    private KeyStore keyStore;
    private KeyGenerator keyGenerator;
    private String KEY_NAME = "my_key";
    private Cipher cipher;
    private KeyguardManager keyguardManager;
    private FingerprintManager fingerprintManager;
    private FingerprintManager.CryptoObject cryptoObject;
    private LoginViewModel viewModel;
    private ProfileViewModel profileViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameEt = findViewById(R.id.usernameEt);
        pswdEt = findViewById(R.id.pswdEt);
        //MARK:- Remove while production
        usernameEt.setText("mastermindankur@gmail.com"); //"niranjan.tdp@gmail.com"
        pswdEt.setText("happy2020"); //"Sep@2020"
        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);

//        if(checkLockScreen()){
//            generateKey();
//            if (cipherInit()) {
//                FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);
//                FingerprintHandler helper = new FingerprintHandler(this);
//                helper.startAuth(fingerprintManager, cryptoObject);
//            }
//        }

        usernameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().equals(" ")){
                    usernameEt.setText("");
                }
            }
        });

        pswdEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().equals(" ")){
                    pswdEt.setText("");
                }
            }
        });

        pswdEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_GO){
                    validateAndNavigate();
                }
                return false;
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private Boolean checkLockScreen() {
         keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
         fingerprintManager = (FingerprintManager) getSystemService(Context.FINGERPRINT_SERVICE);

        if (!keyguardManager.isKeyguardSecure()) {

            Toast.makeText(this,
                    "Lock screen security not enabled",
                    Toast.LENGTH_LONG).show();
            return false;
        }

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.USE_FINGERPRINT) !=
                PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this,
                    "Permission not enabled (Fingerprint)",
                    Toast.LENGTH_LONG).show();

            return false;
        }

        if (!fingerprintManager.hasEnrolledFingerprints()) {
            Toast.makeText(this,
                    "No fingerprint registered, please register",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void generateKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            keyGenerator = KeyGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES,
                    "AndroidKeyStore");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }

        try {
            keyStore.load(null);
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean cipherInit(){
        try {
            cipher = Cipher.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES + "/"
                            + KeyProperties.BLOCK_MODE_CBC + "/"
                            + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME, null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return  false;
    }



    private void validateAndNavigate() {
        username = usernameEt.getText().toString();
        pswd = pswdEt.getText().toString();
        if(username != null && !username.equals("")){
            if(pswd != null && !pswd.equals("") ){
                viewModel.setLoginRequestBody(username, pswd);
                requestAndNavigate();
            }
            else {
                pswdEt.setError("Password cannot be nil");
            }
        }else {
            usernameEt.setError("Username cannot be nil");
        }
    }

    private void requestAndNavigate() {
        if (InternetConnection.checkConnection(this)) {
            showProgressView();
            viewModel.getUser().observe(this, new Observer<User>() {
                @Override
                public void onChanged(User user) {
                    if (user != null) {
                        Constant.shared.access_token = "Bearer " + user.getData().getToken();
                        getProfile();
                    } else {
                        String errorMsg = viewModel.getErrorMessage();
                        Toast.makeText(LoginActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            startActivity(new Intent(LoginActivity.this, NoInternetActivity.class));
        }

    }

    private void getProfile() {
        profileViewModel.getProfile().observe(this, new Observer<ProfileModel>() {
            @Override
            public void onChanged(ProfileModel profileModel) {
                hideProgressView();
                finish();
                startActivity(new Intent(LoginActivity.this, FundsActivity.class));
            }
        });
    }

    public void toRegistraction(View view) {
        Intent httpIntent = new Intent(Intent.ACTION_VIEW);
        httpIntent.setData(Uri.parse("https://grovedev.globalmangrove.org/congrats-funding"));
        startActivity(httpIntent);
    }
}
