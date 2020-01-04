package com.example.switchingshifts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import backend.Worker;


public class WorkerShifts extends AppCompatActivity {
    private Worker worker;
    private ScrollView shifts;
    private FirebaseAuth firebase_auth;
    private FirebaseFirestore db;
    private String user_id;
    private List<String> shift_ids = new ArrayList<>();
    private List<String> shifts_to_show = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_shifts);

        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        shifts= findViewById(R.id.my_shifts);

        /* Initialize Firebase Auth  and firestore*/
        firebase_auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        /* get user id */
        user_id = firebase_auth.getCurrentUser().getUid();

        db.collection("workers").document(user_id)
                .collection("shifts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful()){
                            /* go over all document in shifts collection */
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                /* save id's of all documents */
                                shift_ids.add(document.getId());
                            }
                        }else{
                            Toast.makeText(WorkerShifts.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

        DocumentReference documentReference = db.collection("workers").document(user_id);

//        for(String shift_id: shift_ids){
//            db.collection("workers").document(user_id)
//                    .collection("shifts").document(shift_id)
//        }






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
