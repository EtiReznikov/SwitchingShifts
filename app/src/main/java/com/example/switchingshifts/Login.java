package com.example.switchingshifts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;


public class Login extends AppCompatActivity {

    private EditText login_email_text, login_password_text;
    private Button login_connection_button;
    private String login_email;
    private String login_password;
    private FirebaseAuth firebase_auth;
    private FirebaseFirestore db;
    private String user_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /* Initialize Firebase Auth  and firestore*/
        firebase_auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        login_email_text = findViewById(R.id.login_email);
        login_password_text = findViewById(R.id.login_password);
        login_connection_button = findViewById(R.id.loginConnectionButton);

        login_connection_button.setEnabled(false);

        login_email_text.addTextChangedListener(loginTextWatcher);
        login_password_text.addTextChangedListener(loginTextWatcher);
        Toast.makeText(Login.this, "Firebase Connection Success", Toast.LENGTH_SHORT).show();

    }

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            login_email = login_email_text.getText().toString().trim();
            login_password = login_password_text.getText().toString().trim();
            if(!login_email.isEmpty() && !login_password.isEmpty())
                login_connection_button.setEnabled(true);
            login_connection_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    firebase_auth.signInWithEmailAndPassword(login_email, login_password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        user_id = firebase_auth.getCurrentUser().getUid();
////Change
                                        if(!user_id.equals("agRJExNLmWUhUdbosK7SgiSAKUA3")){
                                            DocumentReference documentReference = db.collection("workers").document(user_id);
                                            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                @Override
                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                    String first_name = documentSnapshot.getString("first_name");
                                                    String last_name = documentSnapshot.getString("last_name");
                                                    Toast.makeText(Login.this, " התחברת בהצלחה "+ first_name + " " + last_name, Toast.LENGTH_LONG).show();
                                                    startActivity(new Intent(Login.this, WorkerScreen.class));
                                                }
                                            });
                                        }else {
                                            Toast.makeText(Login.this, "מנהל, התחברת בהצלחה ", Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(Login.this, MangerScreen.class));
                                        }

                                    }else{
                                        Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            });


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