package com.example.switchingshifts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View;
import android.app.DatePickerDialog;
import java.util.Calendar;

public class AddShift extends AppCompatActivity {
    TextView date;
    Button selectDate;
    Calendar c;
    DatePickerDialog dpd;

    Spinner s_shift_type;
    ArrayAdapter<CharSequence> adapter_shift_type;
    Spinner s_worker_type;
    ArrayAdapter<CharSequence> adapter_worker_type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shift);

        s_shift_type= (Spinner)findViewById(R.id.spinner_shift_type);
        adapter_shift_type= ArrayAdapter.createFromResource(this,R.array.shift_type,android.R.layout.simple_spinner_item);
        adapter_shift_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_shift_type.setAdapter(adapter_shift_type);
        s_shift_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), parent.getItemAtPosition(position)+"selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        s_worker_type= (Spinner)findViewById(R.id.spinner_worker_type);
        adapter_worker_type= ArrayAdapter.createFromResource(this,R.array.role_type,android.R.layout.simple_spinner_item);
        adapter_worker_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_worker_type.setAdapter(adapter_worker_type);
        s_worker_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), parent.getItemAtPosition(position)+"selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        selectDate = (Button)findViewById(R.id.button_day);
        date = (TextView) findViewById(R.id.txt_date);


        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c= Calendar.getInstance();
                int day= c.get(Calendar.DAY_OF_MONTH);
                int month= c.get(Calendar.MONTH);
                int year= c.get(Calendar.YEAR);

                dpd=new DatePickerDialog(AddShift.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mDay, int mMonth, int mYear) {
                        date.setText(mYear + "/" + mMonth + "/"+ mDay);
                    }
                },year, month, day);;
                dpd.getDatePicker().setMinDate(System.currentTimeMillis());
                dpd.show();

            }
        });

    }
}
