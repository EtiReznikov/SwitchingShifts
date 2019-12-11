package com.example.switchingshifts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.view.View;
import android.app.DatePickerDialog;

import java.util.Calendar;

public class AddShift extends AppCompatActivity {
    TextView date;
    Button selectDate;
    Calendar c;
    DatePickerDialog dpd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shift);

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
                        date.setText(mDay + "/" + mMonth + "/"+ mYear);
                    }
                },year, month, day);;
                dpd.getDatePicker().setMinDate(System.currentTimeMillis());
                dpd.show();

            }
        });

    }
}
