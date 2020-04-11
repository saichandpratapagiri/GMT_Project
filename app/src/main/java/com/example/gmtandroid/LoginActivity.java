package com.example.gmtandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEt, pswdEt;
    private String username, pswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        usernameEt = findViewById(R.id.usernameEt);
        pswdEt = findViewById(R.id.pswdEt);

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

    private void validateAndNavigate() {
        username = usernameEt.getText().toString();
        pswd = pswdEt.getText().toString();
        if(username != null && !username.equals("")){
            if(pswd != null && !pswd.equals("") ){
                startActivity(new Intent(LoginActivity.this, PostLogin.class));
            }
            else {
                pswdEt.setError("Password cannot be nil");
            }
        }else {
            usernameEt.setError("Username cannot be nil");
        }
    }

    public void toRegistraction(View view) {
    }
}
