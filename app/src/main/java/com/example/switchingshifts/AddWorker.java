package com.example.switchingshifts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import io.opencensus.tags.Tag;


public class AddWorker extends AppCompatActivity {
    private String first_name, last_name, email, role, password;
    private EditText textInputEmail;
    private EditText textInputFirstName;
    private EditText textInputLastName;
    private Button ok;
    private TextView error;
    Spinner s_worker_type;
    ArrayAdapter<CharSequence> adapter_worker_type;
    private Worker worker;
    private FirebaseAuth firebase_auth;
    private FirebaseFirestore db;
    private String user_id;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* Initialize Firebase Auth */
        firebase_auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_add_worker);

        error=(TextView)findViewById(R.id.textError);

        s_worker_type = (Spinner) findViewById(R.id.spinner_worker_type);
        adapter_worker_type = ArrayAdapter.createFromResource(this, R.array.role_type, android.R.layout.simple_spinner_item);
        adapter_worker_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_worker_type.setAdapter(adapter_worker_type);
        s_worker_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("בחר תפקיד")) {
                } else {
                    role = parent.getItemAtPosition(position).toString();
                    Toast.makeText(getBaseContext(), parent.getItemAtPosition(position) + "selected", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        textInputFirstName = (EditText) findViewById(R.id.Etext_first_name);
        textInputLastName = (EditText) findViewById(R.id.Etext_last_name);
        textInputEmail = (EditText) findViewById(R.id.Etext_mail);
        ok = (Button) findViewById(R.id.button_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                first_name = textInputFirstName.getText().toString().trim();
                last_name = textInputLastName.getText().toString().trim();
                email = textInputEmail.getText().toString().trim();
                password = new String(email);
                boolean flag = false;

                if(TextUtils.isEmpty(first_name)){
                    textInputFirstName.setError("חובה למלא שדה זה");
                    flag = true;
                }
                if(TextUtils.isEmpty(last_name)){
                    textInputLastName.setError("חובה למלא שדה זה");
                    flag = true;
                }
                if(TextUtils.isEmpty(email)){
                    textInputEmail.setError("חובה למלא שדה זה");
                    flag = true;
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    textInputEmail.setError("כתובת המייל לא תקינה");
                    flag = true;
                }
                if(TextUtils.isEmpty(role)){
                    ((TextView)s_worker_type.getSelectedView()).setError("חובה למלא שדה זה");
                    flag = true;
                }

                if(!flag){

                    /* register the worker to firebase */
                  firebase_auth.createUserWithEmailAndPassword(email, password)
                          .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                              @Override
                              public void onComplete(@NonNull Task<AuthResult> task) {
                                  if(task.isSuccessful()){
                                      user_id = firebase_auth.getCurrentUser().getUid();
                                      worker = new Worker(first_name, last_name, role, email);
                                      db.collection("workers").document(user_id).set(worker).addOnSuccessListener(new OnSuccessListener<Void>() {
                                          @Override
                                          public void onSuccess(Void aVoid) {
                                              Toast.makeText(AddWorker.this, worker.getFirst_name() + " " + worker.getLast_name() + " נוסף בהצלחה למערכת ", Toast.LENGTH_LONG).show();

                                          }
                                      }).addOnFailureListener(new OnFailureListener() {
                                          @Override
                                          public void onFailure(@NonNull Exception e) {
                                              Toast.makeText(AddWorker.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                          }
                                      });


                                      startActivity(new Intent(getApplicationContext(), MangerScreen.class));
                                  }else{
                                      Toast.makeText(AddWorker.this, task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                  }
                              }
                          });
                }




            }
        });


    }



    private boolean validateEmail(String mail) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private boolean vaildateText(String text){
        if (text.isEmpty())
            return false;
        return true;
    }
}
