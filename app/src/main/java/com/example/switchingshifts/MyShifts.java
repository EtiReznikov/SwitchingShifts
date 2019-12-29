package com.example.switchingshifts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import backend.Worker;


public class MyShifts extends AppCompatActivity {
    private Worker worker;
    private TextView shifts;
    private FirebaseAuth firebase_auth;
    private FirebaseFirestore db;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shifts);

        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        shifts= findViewById(R.id.textView_my_shifts);

        firebase_auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        user_id = firebase_auth.getCurrentUser().getUid();



        String text="";
        shifts.setText(text);

        shifts.setMovementMethod(new ScrollingMovementMethod());



    }

    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.my_shift){
            startActivity(new Intent(MyShifts.this, MyShifts.class));
        }
        if(id == R.id.messages){
            startActivity(new Intent(MyShifts.this, Messages.class));
        }
        if(id == R.id.personal_info){
            startActivity(new Intent(MyShifts.this, PersonalDetails.class));
        }
        if(id == R.id.home_page){
            if(worker.getEmail().equals("admin@gmail.com")){
                startActivity(new Intent(MyShifts.this, MangerScreen.class));
            }
            else {
                startActivity(new Intent(MyShifts.this, WorkerScreen.class));
            }
        }
        return true;
    }
}
