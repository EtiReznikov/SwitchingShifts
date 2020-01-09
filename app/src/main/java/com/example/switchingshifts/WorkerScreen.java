package com.example.switchingshifts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import android.view.Menu;
import android.view.MenuItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.QuerySnapshot;

import backend.Graph;
import backend.DFS;
import backend.Request;

import backend.Shift;
import backend.Vetrex;


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
    private Graph graph;
    private DFS dfs;
    private Vetrex v_worker_id, v_wanted_shift, v_reg_shift;
    private Shift new_shift;
    private int size;
    private boolean reads_data;
    private Button ok_button;
    private Request request;
    private int num_of_requests;
    private Stack<Vetrex> path;
    private String u_id;
    private String current_reg_id, curent_wanted_id;
    private String current_id_user;
    private String current_id_shift_reg;
    private String current_id_shift_wanted;
    private String next_id_user;
    private List<String> requests = new ArrayList();
    private List<String> shifts_to_deltete = new ArrayList();
    SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_screen);

        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        graph = new Graph();

        /* Initialize Firebase Auth  and firestore*/
        firebase_auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user_id = firebase_auth.getCurrentUser().getUid();

        final TextView textViewToChange = (TextView) findViewById(R.id.Worker_Screen_title);

        final DocumentReference documentReference = db.collection("workers").document(user_id);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                worker_role = documentSnapshot.getString("role");
                textViewToChange.setText(
                        "Hello " + documentSnapshot.get("first_name"));
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
                                //add date condition
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
                //.whereEqualTo("role", worker_role)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        shifts_wanted.clear();
                        id_shifts_wanted.clear();
                        shifts_wanted.add("");
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult()) {
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
                                                            //add date condition
                                                        }
                                                    }

                                                }
                                            }

                                        });
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
                if (TextUtils.isEmpty(shift_reg_selcted)) {
                    ((TextView) s_shift_reg.getSelectedView()).setError("חובה למלא שדה זה");
                    flag = true;
                }
                if (TextUtils.isEmpty(shift_wanted_selcted)) {
                    ((TextView) s_shift_wanted.getSelectedView()).setError("חובה למלא שדה זה");
                    flag = true;
                }
                if (!flag) {
                    request = new Request(shift_reg_id, shift_wanted_id, user_id);
                    request_id = shift_reg_id + "_" + shift_wanted_id;
                    db.collection("workers").document(user_id).collection("requests").document(request_id).set(request)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getBaseContext(), " בקשתך לחילוף התקבלה", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(WorkerScreen.this, WorkerScreen.class));
                                    read_requests_from_data();
                                }
                            });
                }


            }
        });


    }

    private void read_requests_from_data() {

        db.collection("workers").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                          @Override
                                          public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                              num_of_requests = 0;
                                              if (!queryDocumentSnapshots.isEmpty()) {
                                                  List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                                                  for (DocumentSnapshot doc : docs) {
                                                      db.collection("workers").document(doc.getId()).collection("requests").get()
                                                              .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                                  //add Equal to role
                                                                  @Override
                                                                  public void onSuccess(QuerySnapshot queryDocumentSnapshots_in) {
                                                                      if (!queryDocumentSnapshots_in.isEmpty()) {
                                                                          List<DocumentSnapshot> list = queryDocumentSnapshots_in.getDocuments();
                                                                          for (DocumentSnapshot d : list) {
                                                                              v_worker_id = new Vetrex(true, d.getString("worker_id"));
                                                                              v_reg_shift = new Vetrex(false, d.getString("shift_reg_id"));
                                                                              v_wanted_shift = new Vetrex(false, d.getString("shift_wanted_id"));
                                                                              graph.add_edge(v_reg_shift, v_worker_id, v_wanted_shift);
                                                                              size = graph.graph_size();
//                                                                              Toast.makeText(getBaseContext(), graph.graph_size()+"", Toast.LENGTH_LONG).show();
//                                                                             Toast.makeText(getBaseContext(), v_worker_id.getId(), Toast.LENGTH_LONG).show();
                                                                              num_of_requests++;
                                                                              if (num_of_requests > 1)
                                                                                  start_dfs();


                                                                          }
                                                                      }

                                                                  }

                                                              });
                                                  }
                                              }

                                          }
                                      }
                );


    }

    public void start_dfs() {
        // int counter=0;
        //  Toast.makeText(getBaseContext(), grap_is_ready+" "+counter, Toast.LENGTH_LONG).show();
        //  counter++;
        //   if(grap_is_ready) {
        //  while (num_of_requests<1);
        //   if (num_of_requests == 2) {
//
//           // Toast.makeText(getBaseContext(), "start dfs: " + size, Toast.LENGTH_LONG).show();
//            String vat_neg="";
//            Toast.makeText(getBaseContext(), "graph size: " + graph.graph_size()+"",Toast.LENGTH_LONG).show();
//            for (Vetrex v: graph.getGraph()){
//               vat_neg=v.getId()+" : ";
//           //     Toast.makeText(getBaseContext(), vat_neg, Toast.LENGTH_LONG).show();
//                for (Vetrex u: v.getNeg())
//                    vat_neg+=u.getId()+" , ";
//                Toast.makeText(getBaseContext(), vat_neg, Toast.LENGTH_LONG).show();
//            }

        dfs = new DFS(graph);
        //     path=dfs.dfsCycle();
//            Toast.makeText(getBaseContext(), "path size:" +path.size() + "", Toast.LENGTH_LONG).show();
        boolean has_cycle = true;
        while (has_cycle) {
            path = dfs.dfsCycle();
//            String p = "";
//            Vetrex v = null;
//            for (int i = 0; i < 4; i++) {
//                v = path.pop();
//                p += v.getId();
//                path.add(0, v);
//            }
//            Vetrex u=s.pop();
//            System.out.println(u.id);
//        }
//            Toast.makeText(getBaseContext(), p + "", Toast.LENGTH_LONG).show();
            if (path.empty()) {
                has_cycle = false;
                Toast.makeText(getBaseContext(), "empty", Toast.LENGTH_LONG).show();
            } else {
                int count = 0;
                for (int i = 0; i < path.size(); i++) {
                    if (path.get(i).isIs_user())
                        count++;
                }
                Toast.makeText(getBaseContext(), count + "", Toast.LENGTH_LONG).show();

                while (count > 0) {
                    Vetrex current_shift = path.pop();
                    if (current_shift.isIs_user()) {
                        path.add(0, current_shift);
                        current_shift = path.pop();
                    }

                    current_id_shift_reg = current_shift.getId();
                    Vetrex user = path.pop();
                    Vetrex next_shift = path.pop();
                    current_id_user = user.getId();
                    current_id_shift_wanted = next_shift.getId();
                    next_id_user = path.peek().getId();
                    //   requests.add(current_id_shift_reg + "_" + current_id_shift_wanted);
                    //         shifts_to_deltete.add((cu))

                    Toast.makeText(getBaseContext(), current_id_user, Toast.LENGTH_LONG).show();
                    db.collection("workers").document(next_id_user).collection("shifts").document(current_id_shift_wanted)
                            .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot docSnapshot) {
                            new_shift = new Shift(docSnapshot.getTimestamp("date"), docSnapshot.getString("type"), docSnapshot.getString("role"));
                            db.collection("workers").document(current_id_user).collection("shifts").document(shift_wanted_id).set(new_shift)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            //send massege
                                            Toast.makeText(getBaseContext(), " התבצע חילוף", Toast.LENGTH_LONG).show();
                                        }
                                    });
                        }
                    });


                    //          db.collection("workers").document(current_id_user).collection("request").document(current_id_shift_reg + "_" + current_id_shift_wanted).delete();


//                      db.collection("workers").document(next_id_user).collection("shifts").document(current_id_shift_wanted)
//                              .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                            @Override
//                            public void onSuccess(DocumentSnapshot docSnapshot) {
//                                new_shift = new Shift(docSnapshot.getTimestamp("date"), docSnapshot.getString("type"), docSnapshot.getString("role"));
//                                db.collection("workers").document(current_id_user).collection("shifts").document(shift_wanted_id).set(new_shift)
//                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void aVoid) {
//                                        //send massege
//                                        Toast.makeText(getBaseContext(), " התבצע חילוף", Toast.LENGTH_LONG).show();
//                                    }
//                                });
//                            }
//                        });
//                        db.collection("workers").document(current_id_user).collection("shifts").document(current_id_shift_reg).delete();

//                        db.collection("workers").document(current_id_user).collection("shifts").document(shift_wanted_id).set(new_shift)
//                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void aVoid) {
//                                        //send massege
//                                    }
//                                });
//
                    path.add(0, current_shift);
                    path.add(0, user);
                    path.push(next_shift);
//                    p = "";
//                    for (int i = 0; i < 4; i++) {
//                        v = path.pop();
//                        p += v.getId();
//                        path.add(0, v);
//                    }
//            Vetrex u=s.pop();
//            System.out.println(u.id);
//        }
//                    Toast.makeText(getBaseContext(), p + "", Toast.LENGTH_LONG).show();
                    graph.remove_edge(current_shift, user);
                    graph.remove_edge(user, next_shift);
                    graph.add_edge(next_shift, user);


                    count--;
                    Toast.makeText(getBaseContext(), count + "", Toast.LENGTH_LONG).show();
                }

            }

            //  }
        }
    }
    // }


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
