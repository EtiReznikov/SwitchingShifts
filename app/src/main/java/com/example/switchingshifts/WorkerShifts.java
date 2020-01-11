package com.example.switchingshifts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.TextView;
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

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import backend.Worker;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;import android.widget.ListView;

import static java.util.Objects.*;


public class WorkerShifts extends AppCompatActivity  {
//    private Worker worker;
//    private ScrollView shifts;
//    private FirebaseAuth firebase_auth;
//    private FirebaseFirestore db;
//    private String user_id;
//    private String shifts_to_show = "";
//    private SimpleDateFormat sdf;


    private TextView shifts_list;
    private String shifts_list_intent = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_shifts);

        shifts_list_intent = getIntent().getStringExtra("shifts_to_show");


        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
//        shifts= findViewById(R.id.my_shifts);

//        /* Initialize Firebase Auth  and firestore*/
//        firebase_auth = FirebaseAuth.getInstance();
//        db = FirebaseFirestore.getInstance();

        shifts_list = findViewById(R.id.my_text_view);
        shifts_list.setMovementMethod(new ScrollingMovementMethod());

//        sdf = new SimpleDateFormat("dd/MM/yyyy");
//
//        /* get user id */
//        user_id = firebase_auth.getCurrentUser().getUid();

//        db.collection("workers").document(user_id)
//                .collection("shifts")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//
//                        if(task.isSuccessful()){
//                            /* go over all document in shifts collection */
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                /* save id's of all documents */
////                                shift_ids.add(document.getId());
//                                DocumentReference documentReference = db
//                                        .collection("workers")
//                                        .document(user_id).collection("shifts")
//                                        .document(document.getId());
//                                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                                    @Override
//                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                        /* Gets worker date and type of shift */
//                                        Date shift_date = documentSnapshot.getTimestamp("date").toDate();
//                                        String shift_type = documentSnapshot.getString("type");
//
//                                        String date_str = sdf.format(shift_date);
//                                        String shift_str = date_str + ", " + shift_type + "\n";
//                                        shifts_to_show +=shift_str ;
//
//                                    }
//                                });
//                            }
//
//                        }else{
//                            Toast.makeText(WorkerShifts.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
//                        }
//
//
//                    }
//                });



       shifts_list.setText(shifts_list_intent);






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

            startActivity(new Intent(WorkerShifts.this, WorkerScreen.class));

        }
        if(id == R.id.logout){
            Intent intent = new Intent(WorkerShifts.this, Login.class);
            startActivity(intent);
        }
        return true;
    }
}
