package com.example.switchingshifts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MyShifts extends AppCompatActivity {
    private Worker worker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shifts);

        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

    }

    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.myShift){
            startActivity(new Intent(MyShifts.this, MyShifts.class));
        }
        if(id == R.id.messages){
            startActivity(new Intent(MyShifts.this, Messages.class));
        }
        if(id == R.id.personalInfo){
            startActivity(new Intent(MyShifts.this, PersonalDetails.class));
        }
        if(id == R.id.homePage){
            if(worker.getEmail().equals("admin@gmail.com")){
                startActivity(new Intent(MyShifts.this, MangerScreen.class));
            }
            else {
                startActivity(new Intent(MyShifts.this, WorkerScreen.class));
            }
        }
        return true;
    }
}
