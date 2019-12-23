package com.example.switchingshifts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class PersonalDetails extends AppCompatActivity implements View.OnClickListener {

    private EditText first_name, last_name, email, birthday;
    private Button save_button;
    private FirebaseAuth firebase_auth;
    private FirebaseFirestore db;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Initialize Firebase Auth  and firestore*/
        firebase_auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        setContentView(R.layout.activity_personal_details);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        Button button = findViewById(R.id.buttonNewPess);
        button.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View view){
                        startActivity(new Intent(PersonalDetails.this, ChangePass.class));
                    }
                }
        );

        first_name = findViewById(R.id.personalName);
        last_name = findViewById(R.id.lastlName);
        email = findViewById(R.id.mail);
        birthday = findViewById(R.id.birthday);
        save_button = findViewById(R.id.saveButton);
        save_button.setOnClickListener(this);
        user_id = firebase_auth.getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("workers").document(user_id);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                first_name.setText(documentSnapshot.getString("first_name"));
                last_name.setText(documentSnapshot.getString("last_name"));
                email.setText(documentSnapshot.getString("mail"));
                birthday.setText(documentSnapshot.getString("birthday"));

            }
        });
    }
    @Override
    public void onClick(View view){
        if(view.getId() == R.id.saveButton){
            String f_name = first_name.getText().toString().trim();
            String l_name = last_name.getText().toString().trim();
            String mail_string = email.getText().toString().trim();
            String birthday_string = birthday.getText().toString().trim();
            boolean flag = false;
            if(TextUtils.isEmpty(f_name)) {
                first_name.setError("חובה למלא שדה זה");
                flag = true;
            }
            if(TextUtils.isEmpty(l_name)) {
                last_name.setError("חובה למלא שדה זה");
                flag = true;
            }
            if(TextUtils.isEmpty(mail_string)) {
                email.setError("חובה למלא שדה זה");
                flag = true;
            }
            else if(!Patterns.EMAIL_ADDRESS.matcher(mail_string).matches()){
                email.setError("כתובת המייל לא תקינה");
            }
            if(TextUtils.isEmpty(birthday_string)) {
                birthday.setError("חובה למלא שדה זה");
                flag = true;
            }
            if(!flag){
                Toast.makeText(PersonalDetails.this,"הנתונים נשמרו", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PersonalDetails.this, WorkerScreen.class));
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.my_shift){
            startActivity(new Intent(PersonalDetails.this, MyShifts.class));
        }
        if(id == R.id.messages){
            startActivity(new Intent(PersonalDetails.this, Messages.class));
        }
        if(id == R.id.personal_info){
            startActivity(new Intent(PersonalDetails.this, PersonalDetails.class));
        }
        if(id == R.id.home_page){
            /* if the current unique id equal to maneger unique id go to maneger else worker */
            if(firebase_auth.getCurrentUser().getUid().equals("agRJExNLmWUhUdbosK7SgiSAKUA3")){
                startActivity(new Intent(PersonalDetails.this, MangerScreen.class));
            }
            else {
                startActivity(new Intent(PersonalDetails.this, WorkerScreen.class));
            }
        }
        return true;
    }
}
