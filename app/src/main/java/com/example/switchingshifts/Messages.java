package com.example.switchingshifts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import backend.Worker;

public class Messages extends AppCompatActivity {
    private Worker worker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /*When press one of the items in the toolbar we will go to the required screen.*/
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.my_shift){
            startActivity(new Intent(Messages.this, MyShifts.class));
        }
        if(id == R.id.messages){
            startActivity(new Intent(Messages.this, Messages.class));
        }
        if(id == R.id.personal_info){
            startActivity(new Intent(Messages.this, PersonalDetails.class));
        }
        if(id == R.id.home_page){
            /* if the current unique id equal to maneger unique id go to maneger else worker */
            if(worker.getEmail().equals("admin@gmail.com")){
                startActivity(new Intent(Messages.this, MangerScreen.class));
            }
            else {
                startActivity(new Intent(Messages.this, WorkerScreen.class));
            }
        }
        if(id == R.id.logout){
            Intent intent = new Intent(Messages.this, Login.class);
            startActivity(intent);
        }
        return true;
    }
}
