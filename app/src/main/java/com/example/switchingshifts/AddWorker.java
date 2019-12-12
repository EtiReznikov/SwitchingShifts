package com.example.switchingshifts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddWorker extends AppCompatActivity {
    private String first_name, last_name, email, role;
    private EditText textInputEmail;
    private EditText textInputFirstName;
    private EditText textInputLastName;
    private Button ok;
    private TextView error;
    Spinner s_worker_type;
    ArrayAdapter<CharSequence> adapter_worker_type;
    DatabaseReference database_reff;
    Worker worker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database_reff = FirebaseDatabase.getInstance().getReference("worker");
        setContentView(R.layout.activity_add_worker);

        error=(TextView)findViewById(R.id.textError);

        s_worker_type = (Spinner) findViewById(R.id.spinner_worker_type);
        adapter_worker_type = ArrayAdapter.createFromResource(this, R.array.role_type, android.R.layout.simple_spinner_item);
        adapter_worker_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_worker_type.setAdapter(adapter_worker_type);
        s_worker_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), parent.getItemAtPosition(position) + "selected", Toast.LENGTH_LONG).show();
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
                first_name = textInputFirstName.getText().toString();
                last_name = textInputLastName.getText().toString();
                email = textInputEmail.getText().toString();
                role=s_worker_type.getSelectedItem().toString();

                if (vaildateText(first_name) | vaildateText(last_name) | vaildateText(email) | vaildateText(role)){
                    error.setText(R.string.empty_input);
                }
                else if (validateEmail(email))
                    error.setText(R.string.invaild_mail);
                else{
                    ///add worker to database.
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
