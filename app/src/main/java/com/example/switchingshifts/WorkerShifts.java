package com.example.switchingshifts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import backend.Worker;


public class WorkerShifts extends AppCompatActivity {
    private Worker worker;
    private ScrollView shifts;
    private FirebaseAuth firebase_auth;
    private FirebaseFirestore db;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_shifts);

        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        shifts= findViewById(R.id.my_shifts);

        firebase_auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        user_id = firebase_auth.getCurrentUser().getUid();
        DocumentReference documentReference = db.collection("ShiftAndWorker").document(user_id);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

            }
        });




    }

    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.my_shift){
            startActivity(new Intent(WorkerShifts.this, WorkerShifts.class));
        }
        if(id == R.id.personal_info){
            startActivity(new Intent(WorkerShifts.this, PersonalDetails.class));
        }
        if(id == R.id.home_page){
            if(worker.getEmail().equals("admin@gmail.com")){
                startActivity(new Intent(WorkerShifts.this, MangerScreen.class));
            }
            else {
                startActivity(new Intent(WorkerShifts.this, WorkerScreen.class));
            }
        }
        if(id == R.id.logout){
            Intent intent = new Intent(WorkerShifts.this, Login.class);
            startActivity(intent);
        }
        return true;
    }
}
