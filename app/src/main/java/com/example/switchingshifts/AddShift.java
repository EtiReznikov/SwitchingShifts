package com.example.switchingshifts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View;
import android.app.DatePickerDialog;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.DocumentSnapshot;
import backend.Shift;
import backend.Worker;

public class AddShift extends AppCompatActivity {
    /* private data members */
    private FirebaseAuth firebase_auth;
    private FirebaseFirestore db;
    private TextView date;
    private Button select_date, ok_button;
    private Calendar calendar;
    private DatePickerDialog dpd;
    private Shift shift;
    private Spinner s_shift_type, s_worker_type, s_workers_names;
    private ArrayAdapter<CharSequence> adapter_shift_type, adapter_worker_type;
    Worker worker;
    private List<String> names = new ArrayList<>();
    private List<String> id_names = new ArrayList<>();
    Map<String,String> worker_and_shift =  new HashMap<>();
    private String shift_role, shift_date, shift_type, worker_name, worker_id, shift_id;
    private ArrayAdapter<String> adapter_workers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shift);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        /* Initialize Firebase Auth  and firestore*/
        firebase_auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        s_shift_type= (Spinner)findViewById(R.id.spinner_shift_type);
        adapter_shift_type= ArrayAdapter.createFromResource(this,R.array.shift_type,android.R.layout.simple_spinner_item);
        adapter_shift_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_shift_type.setAdapter(adapter_shift_type);
        s_shift_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("בחר משמרת")) {}
                else {
                    Toast.makeText(getBaseContext(), parent.getItemAtPosition(position) + "selected ", Toast.LENGTH_LONG).show();
                    shift_type = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        s_worker_type= findViewById(R.id.spinner_worker_type);
        adapter_worker_type= ArrayAdapter.createFromResource(this,R.array.role_type,android.R.layout.simple_spinner_item);
        adapter_worker_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_worker_type.setAdapter(adapter_worker_type);
        s_worker_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("בחר תפקיד")) {}
                else {
                    Toast.makeText(getBaseContext(), parent.getItemAtPosition(position) + "selected ", Toast.LENGTH_LONG).show();
                    shift_role = parent.getItemAtPosition(position).toString();
                    db.collection("workers").whereEqualTo("role",shift_role).get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    if(!queryDocumentSnapshots.isEmpty()){
                                        names.clear();
                                        id_names.clear();
                                        names.add("בחר שם");
                                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                        for(DocumentSnapshot d : list){
                                            id_names.add(d.getId());
                                            names.add(d.getString("first_name"));
                                        }
                                    }
                                }
                            });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        names.add("בחר שם");
        s_workers_names = findViewById(R.id.spinner_workers_names);
        adapter_workers = new ArrayAdapter(this, android.R.layout.simple_spinner_item, names);
        adapter_workers.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_workers_names.setAdapter(adapter_workers);
        s_workers_names.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("בחר שם")){}
                else {
                    Toast.makeText(getBaseContext(), ("selected " + parent.getItemAtPosition(position)), Toast.LENGTH_LONG).show();
                    worker_name = parent.getItemAtPosition(position).toString();
                    worker_id = id_names.get(position-1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        select_date = findViewById(R.id.button_day);
        date = findViewById(R.id.txt_date);


        select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                dpd=new DatePickerDialog(AddShift.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mDay, int mMonth, int mYear) {
                        date.setText(mYear + "/" + (mMonth+1) + "/"+ mDay);
                        shift_date = mYear + "/" + (mMonth+1) + "/" + mDay;
                    }
                },year, month, day);;
                dpd.getDatePicker().setMinDate(System.currentTimeMillis());
                dpd.show();
            }
        });

        ok_button = findViewById(R.id.button2);
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = false;
                if(TextUtils.isEmpty(shift_date)){
                    select_date.setError("חובה למלא שדה זה");
                    flag = true;
                }
                if(TextUtils.isEmpty(shift_type)){
                    ((TextView)s_shift_type.getSelectedView()).setError("חובה למלא שדה זה");
                    flag = true;
                }
                if(TextUtils.isEmpty(shift_role)){
                    ((TextView)s_worker_type.getSelectedView()).setError("חובה למלא שדה זה");
                    flag = true;
                }
                if(TextUtils.isEmpty(worker_name)){
                    ((TextView)s_workers_names.getSelectedView()).setError("חובה למלא שדה זה");
                    flag = true;
                }
                if(!flag){
                    shift = new Shift(shift_date, shift_type);
                    shift_id = db.collection("Shift").document().getId();
                    db.collection("Shift").document(shift_id).set(shift).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getBaseContext(), " נוספה משמרת חדשה ל" + worker_name , Toast.LENGTH_LONG).show();
                        }
                    });
                    worker_and_shift.put("shift_id", shift_id);
                    db.collection("ShiftAndWorker").document(worker_id).set(worker_and_shift).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            startActivity(new Intent(AddShift.this, AddShift.class));
                        }
                    });
                }
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
            Intent intent = new Intent(AddShift.this, MyShifts.class);
            startActivity(intent);
        }
        if(id == R.id.messages){
            Intent intent = new Intent(AddShift.this, Messages.class);
            startActivity(intent);
        }
        if(id == R.id.personal_info){
            Intent intent = new Intent(AddShift.this, PersonalDetails.class);
            startActivity(intent);
        }
        if(id == R.id.home_page){
            Intent intent = new Intent(AddShift.this, MangerScreen.class);
            startActivity(intent);
        }
        if(id == R.id.logout){
            Intent intent = new Intent(AddShift.this, Login.class);
            startActivity(intent);
        }
        return true;
    }
}
