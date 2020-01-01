package com.example.switchingshifts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;



import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.CollectionReference;

import org.w3c.dom.Document;


/*The worker main screen */
public class WorkerScreen extends AppCompatActivity {
    private FirebaseAuth firebase_auth;
    private FirebaseFirestore db;
    private String user_id;
    private String role;
    private List<String> shifts_reg = new ArrayList<>();
    private List<String> id_shifts_reg = new ArrayList<>();
    private List<String> shifts_wanted = new ArrayList<>();
    private List<String> id_shifts_wanted = new ArrayList<>();
    private Spinner s_shift_reg, s_shift_wanted;
    private ArrayAdapter<CharSequence> adapter_shift_reg, adapter_shift_wanted;
    private String  shift_reg_selcted, shift_wanted_selcted, shift_reg_id, shift_wanted_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_screen);

        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        /* Initialize Firebase Auth  and firestore*/
        firebase_auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user_id = firebase_auth.getCurrentUser().getUid();
        DocumentReference documentReference = db.collection("workers").document(user_id);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                         @Override
                                                         public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                             role = documentSnapshot.getString("role");
                                                         }
                                                     });

        db.collection("workers").document(user_id).collection("shifts").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            shifts_reg.clear();
                            id_shifts_reg.clear();
                            shifts_reg.add("");
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for(DocumentSnapshot d : list){
                                id_shifts_reg.add(d.getId());
                                shifts_reg.add(d.getString("date")+"  "+d.getString("role"));
                            }
                        }
                    }
                });
        shifts_reg.add("");
        s_shift_reg = findViewById(R.id.spinner_shifts_reg);
        adapter_shift_reg = new ArrayAdapter(this, android.R.layout.simple_spinner_item, shifts_reg);
        adapter_shift_reg.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_shift_reg.setAdapter(adapter_shift_reg);
        s_shift_reg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("בחר שם")){}
                else {
                    Toast.makeText(getBaseContext(), ("selected " + parent.getItemAtPosition(position)), Toast.LENGTH_LONG).show();
//                    shift_reg_selcted = parent.getItemAtPosition(position).toString();
//                    shift_reg_id = id_shifts_reg.get(position-1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
//        db.collection("workers")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (DocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
//                            }
//                        } else {
//                            Log.w(TAG, "Error getting documents.", task.getException());
//                        }
//                    }
//                });

//        db.collection("workers").get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        if(!queryDocumentSnapshots.isEmpty()){
//
//                            shifts_wanted.clear();
//                            id_shifts_wanted.clear();
//                            shifts_wanted.add("");
//                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
//                            for(DocumentSnapshot d : list){
//                                CollectionReference c=d.get("shidts");
//
//                                String id_shift=d.getId();
//                              //  if (!id_shifts_reg.contains(id_shift)) {
//                                    id_shifts_wanted.add(id_shift);
//                                    shifts_wanted.add(d.getString("date") + "  " + d.getString("role"));
//                               // }
//                            }
//                        }
//                    }
//                });

//        db.collection("workers").get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot documentSnapshots) {
//                        if (!documentSnapshots.isEmpty()) {
//
//                            List<DocumentSnapshot> list = documentSnapshots.getDocuments();
//                            for (DocumentSnapshot d : list) {
//
//
//                                String id_shift = d.getId();
//                                //  if (!id_shifts_reg.contains(id_shift)) {
//                                id_shifts_wanted.add(id_shift);
//                                shifts_wanted.add(d.getString("date") + "  " + d.getString("role"));
//                                // }
//                            }
//
//
//                        }
//                    }
//                    });
//        db.getInstance()
//                .collection("workers")
//                .get()
//                .addOnCompleteListener(WorkerScreen.this, new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
//                        }
//                    }
//                });
//
//        db.collection("workers").
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot documentSnapshots) {
//                        if (!documentSnapshots.isEmpty()) {
//
//                            List<DocumentSnapshot> list = documentSnapshots.getDocuments();
//                            for (DocumentSnapshot d : list) {
//                                DocumentReference d1= new DocumentReference(d.getData());
//
//                                String id_shift = d.getId();
//                                //  if (!id_shifts_reg.contains(id_shift)) {
//                                id_shifts_wanted.add(id_shift);
//                                shifts_wanted.add(d.getString("date") + "  " + d.getString("role"));
//                                // }
//                            }
//
//
//                        }
//                    }
//                    });
//



//        db.collection("workers").document().collection("shifts").get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        if(!queryDocumentSnapshots.isEmpty()){
//
//                            shifts_wanted.clear();
//                            id_shifts_wanted.clear();
//                            shifts_wanted.add("");
//                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
//                            for(DocumentSnapshot d : list){
//                                String id_shift=d.getId();
//                              //  if (!id_shifts_reg.contains(id_shift)) {
//                                    id_shifts_wanted.add(id_shift);
//                                    shifts_wanted.add(d.getString("date") + "  " + d.getString("role"));
//                               // }
//                            }
//                        }
//                    }
//                });
        shifts_wanted.add("");
        s_shift_wanted = findViewById(R.id.spinner_shifts_wanted);
        adapter_shift_wanted = new ArrayAdapter(this, android.R.layout.simple_spinner_item, shifts_wanted);
        adapter_shift_wanted.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_shift_wanted.setAdapter(adapter_shift_wanted);

        s_shift_wanted.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("בחר שם")){}
                else {
                    Toast.makeText(getBaseContext(), ("selected " + parent.getItemAtPosition(position)), Toast.LENGTH_LONG).show();
//                    shift_wanted_selcted = parent.getItemAtPosition(position).toString();
//                   shift_wanted_id = id_shifts_wanted.get(position-1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    /* When press one of the items in the toolbar we will go to the required screen. */
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.my_shift){
            Intent intent = new Intent(WorkerScreen.this, WorkerShifts.class);
            startActivity(intent);
        }
        if(id == R.id.personal_info){
            Intent intent = new Intent(WorkerScreen.this, PersonalDetails.class);
            startActivity(intent);
        }
        if(id == R.id.home_page){
            Intent intent = new Intent(WorkerScreen.this, WorkerScreen.class);
            startActivity(intent);
        }
        if(id == R.id.logout) {
            Intent intent = new Intent(WorkerScreen.this, Login.class);
            startActivity(intent);
        }
        return true;
    }
}
