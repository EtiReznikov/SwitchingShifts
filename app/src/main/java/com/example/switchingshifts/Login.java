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
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import backend.Worker;


public class Login extends AppCompatActivity {
    /* private data members */
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
            /* if the login button pressed */
            login_connection_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /* checking with firebase auth if this email and password registered already */
                    firebase_auth.signInWithEmailAndPassword(login_email, login_password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        /* gets the auth unique id */
                                        user_id = firebase_auth.getCurrentUser().getUid();
                                        DocumentReference documentReference = db.collection("workers").document(user_id);
                                        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                                /* Gets worker role, first name and last name */
                                                String worker_role = documentSnapshot.getString("role");
                                                String first_name = documentSnapshot.getString("first_name");
                                                String last_name = documentSnapshot.getString("last_name");
                                                boolean first_login = documentSnapshot.getBoolean("first_login");
                                                /* if the current worker isn't the manager */
                                                if (worker_role.equals("Manager")) {

                                                    if(first_login){
                                                        /* print welcome message to the manager, change first_login to false and go to ChangePass activity */
                                                        Toast.makeText(Login.this, "מנהל, התחברת בהצלחה ", Toast.LENGTH_LONG).show();
                                                        Map<String, Object> data = new HashMap<>();
                                                        data.put("first_login", false);

                                                        db.collection("workers").document(user_id)
                                                                .set(data, SetOptions.merge());
                                                        startActivity(new Intent(Login.this, ChangePass.class));
                                                    }
                                                    else{
                                                        /* print welcome message to the manager and go the the manager screen */
                                                        Toast.makeText(Login.this, "מנהל, התחברת בהצלחה ", Toast.LENGTH_LONG).show();
                                                        startActivity(new Intent(Login.this, MangerScreen.class));
                                                    }



                                                }
                                                else if(worker_role.equals("פוטר")) {//fired
                                                    Toast.makeText(Login.this, "העובד לא קיים במערכת, אנא נסה שנית ", Toast.LENGTH_LONG).show();
                                                    startActivity(new Intent(Login.this, Login.class));
                                                }
                                                else{//regular worker


                                                    if(first_login){
                                                        /* print welcome message to the worker, change first_login to false and go to ChangePass activity */
                                                        Toast.makeText(Login.this, " התחברת בהצלחה " + first_name + " " + last_name, Toast.LENGTH_LONG).show();
                                                        Map<String, Object> data = new HashMap<>();
                                                        data.put("first_login", false);

                                                        db.collection("workers").document(user_id)
                                                                .set(data, SetOptions.merge());
                                                        startActivity(new Intent(Login.this, ChangePass.class));
                                                    }

                                                    else{//first_login = false
                                                        /* print welcome message and go to the regular worker screen */
                                                        Toast.makeText(Login.this, " התחברת בהצלחה " + first_name + " " + last_name, Toast.LENGTH_LONG).show();
                                                        startActivity(new Intent(Login.this, WorkerScreen.class));
                                                    }

                                                }
                                            }
                                        });





                                    }else{ /* invalid email or password */
                                        Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
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

