package com.example.switchingshifts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;



import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;



public class WorkerScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_screen);

        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

    }




//    public boolean onCreateOptionsMenu(Menu menu){
//
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return true;
//    }
//    public boolean onOptionsItemSelected(MenuItem item){
//        int id = item.getItemId();
//        if(id == R.id.myShift){
//            Intent intent = new Intent(WorkerScreen.this, MyShifts.class);
//            startActivity(intent);
//        }
//        if(id == R.id.messages){
//            Intent intent = new Intent(WorkerScreen.this, Messages.class);
//            startActivity(intent);
//        }
//        if(id == R.id.personalInfo){
//            Intent intent = new Intent(WorkerScreen.this, PersonalDetails.class);
//            startActivity(intent);
//        }
//        if(id == R.id.homePage){
//            Intent intent = new Intent(WorkerScreen.this, WorkerScreen.class);
//            startActivity(intent);
//        }
//        return true;
//    }
}
