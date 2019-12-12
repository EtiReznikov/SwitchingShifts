package com.example.switchingshifts;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Login extends AppCompatActivity {

    private EditText loginWorkerNumber, loginPassNumber;
    private Button loginConnectionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginWorkerNumber = findViewById(R.id.loginWorkerNumber);
        loginPassNumber = findViewById(R.id.loginPassNumber);
        loginConnectionButton = findViewById(R.id.loginConnectionButton);

        loginConnectionButton.setEnabled(false);

        loginWorkerNumber.addTextChangedListener(loginTextWatcher);
        loginPassNumber.addTextChangedListener(loginTextWatcher);
        Toast.makeText(Login.this, "Firebase Connection Success", Toast.LENGTH_LONG).show();

    }

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String workerNumberInput = loginWorkerNumber.getText().toString().trim();
            String pessNumberInput = loginPassNumber.getText().toString().trim();
            if(!workerNumberInput.isEmpty() && !pessNumberInput.isEmpty())
            loginConnectionButton.setEnabled(true);
            loginConnectionButton.setOnClickListener(
                    new Button.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(Login.this, ChangePass.class));
                        }
                    }
            );
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}




//    Button loginConnectionButton = findViewById(R.id.loginConnectionButton);
//
//        loginConnectionButton.setOnClickListener(
//                new Button.OnClickListener(){
//@Override
//public void onClick(View v) {
//        startActivity(new Intent(Login.this, ChangePass.class));
//        }
//        }
//        );