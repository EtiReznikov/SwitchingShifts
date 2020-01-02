package com.example.switchingshifts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.CollectionReference;

import org.w3c.dom.Document;

import backend.Request;
import backend.Shift;


/*The worker main screen */
public class WorkerScreen extends AppCompatActivity {
    private FirebaseAuth firebase_auth;
    private FirebaseFirestore db;
    private String user_id;
    private String worker_role;
    private List<String> shifts_reg = new ArrayList<>();
    private List<String> id_shifts_reg = new ArrayList<>();
    private List<String> shifts_wanted = new ArrayList<>();
    private List<String> id_shifts_wanted = new ArrayList<>();
    private Spinner s_shift_reg, s_shift_wanted;
    private ArrayAdapter<CharSequence> adapter_shift_reg, adapter_shift_wanted;
    private String shift_reg_selcted, shift_wanted_selcted, shift_reg_id, shift_wanted_id;
    private String request_id;

    private Button ok_button;
    private Request request;

    SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy");
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
                worker_role = documentSnapshot.getString("role");
            }
        });

        db.collection("workers").document(user_id).collection("shifts").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            shifts_reg.clear();
                            id_shifts_reg.clear();
                            shifts_reg.add("");
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                id_shifts_reg.add(d.getId());
                                Date shift_date = d.getDate("date");
                                shifts_reg.add(sfd.format(shift_date) + "  " + d.getString("type"));
                                // shifts_reg.add(d.getId());
                            }
                        }
                    }
                });
        shifts_reg.add("");
        s_shift_reg = findViewById(R.id.spinner_shifts_reg);
        adapter_shift_reg = new ArrayAdapter(this, android.R.layout.simple_spinner_item, shifts_reg);
        adapter_shift_reg.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_shift_reg.setAdapter(adapter_shift_reg);

        db.collection("workers")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        shifts_wanted.clear();
                        id_shifts_wanted.clear();
                        shifts_wanted.add("");
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult()) {
                                if (doc.getId() != user_id) {
                                    db.collection("workers").document(doc.getId()).collection("shifts").get()
                                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                //add Equal to role
                                                @Override
                                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                    if (!queryDocumentSnapshots.isEmpty()) {
                                                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                                        for (DocumentSnapshot d : list) {
                                                            if (!shifts_reg.contains(d.getId())) {
                                                                id_shifts_wanted.add(d.getId());
                                                                Date shift_date = d.getDate("date");
                                                                // shifts_wanted.add(d.getId());
                                                                shifts_wanted.add(sfd.format(shift_date) + "  " + d.getString("type"));
                                                            }
                                                        }

                                                    }
                                                }

                                            });
                                }

                            }
                        }

                    }
                });


        shifts_wanted.add("");
        s_shift_wanted = findViewById(R.id.spinner_shifts_wanted);
        adapter_shift_wanted = new ArrayAdapter(this, android.R.layout.simple_spinner_item, shifts_wanted);
        adapter_shift_wanted.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_shift_wanted.setAdapter(adapter_shift_wanted);

        s_shift_reg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                shift_reg_selcted = parent.getItemAtPosition(pos).toString();
                shift_reg_id = id_shifts_reg.get(pos);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        s_shift_reg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (!parentView.getItemAtPosition(position).equals("")) {
                    Toast.makeText(getBaseContext(), ("selected " + parentView.getItemAtPosition(position)), Toast.LENGTH_LONG).show();
                    shift_reg_selcted = parentView.getItemAtPosition(position).toString();
                    shift_reg_id = id_shifts_reg.get(position - 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

        s_shift_wanted.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (!parentView.getItemAtPosition(position).equals("")) {
                    Toast.makeText(getBaseContext(), ("selected " + parentView.getItemAtPosition(position)), Toast.LENGTH_LONG).show();
                    shift_wanted_selcted = parentView.getItemAtPosition(position).toString();
                    shift_wanted_id = id_shifts_wanted.get(position - 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

        ok_button = findViewById(R.id.button_ok_worker_screen);
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = false;
                if(TextUtils.isEmpty(shift_reg_selcted)){
                    ((TextView)s_shift_reg.getSelectedView()).setError("חובה למלא שדה זה");
                    flag = true;
                }
                if(TextUtils.isEmpty(shift_wanted_selcted)){
                    ((TextView)s_shift_wanted.getSelectedView()).setError("חובה למלא שדה זה");
                    flag = true;
                }
                if(!flag){
                    request = new Request( shift_reg_id, shift_wanted_id);
                    request_id = shift_reg_id+"_"+shift_wanted_id;
                    db.collection("workers").document(user_id).collection("requests").document(request_id).set(request)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getBaseContext(), " בקשתך לחילוף התקבלה" , Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(WorkerScreen.this, WorkerScreen.class));
                                }
                            });
                }
            }
        });


    }





    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /* When press one of the items in the toolbar we will go to the required screen. */
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.my_shift) {
            Intent intent = new Intent(WorkerScreen.this, WorkerShifts.class);
            startActivity(intent);
        }
        if (id == R.id.personal_info) {
            Intent intent = new Intent(WorkerScreen.this, PersonalDetails.class);
            startActivity(intent);
        }
        if (id == R.id.home_page) {
            Intent intent = new Intent(WorkerScreen.this, WorkerScreen.class);
            startActivity(intent);
        }
        if (id == R.id.logout) {
            Intent intent = new Intent(WorkerScreen.this, Login.class);
            startActivity(intent);
        }
        return true;
    }
}
