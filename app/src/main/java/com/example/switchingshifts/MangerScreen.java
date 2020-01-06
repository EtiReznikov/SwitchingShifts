package com.example.switchingshifts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
/*The main screen of the manager*/
public class MangerScreen extends AppCompatActivity {
    private Button add_worker;
    private Button remove_worker;
    private Button remove_shift;
    private Button add_shift;
    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manger_screen);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        notificationManager = NotificationManagerCompat.from(this);

    /*Moves the user to the required screen by the button he is clicking of*/
        add_worker = (Button)findViewById(R.id.button_add_worker);
        add_worker.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick( View v){
                openActivityAddWorker();
            }
        });
        remove_worker = (Button)findViewById(R.id.button_remove_worker);
        remove_worker.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick( View v){
                openActivityRemoveWorker();
            }
        });
        remove_shift = (Button)findViewById(R.id.button_remove_shift);
        remove_shift.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick( View v){
                openActivityRemoveShift();
            }
        });
        add_shift = (Button)findViewById(R.id.button_add_shift);
        add_shift.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick( View v){
                openActivityAddShift();
            }
        });
    }

//    public void sendOnChannel1(View v){
//        Notification notification = new NotificationCompat.Builder(this, NotificationHelper.channel1_id)
//                .setSmallIcon(R.drawable.ic_message)
//                .setContentTitle("יש לך הודעה חדשה")
//                .setContentText("החיים בזבל")
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
//                .build();
//
//        notificationManager.notify(1, notification);
//    }


    private void openActivityAddWorker(){
        Intent intent = new Intent(this, AddWorker.class);
        startActivity(intent);
    }
    private void openActivityRemoveWorker(){
        Intent intent = new Intent(this,RemoveWorker.class);
        startActivity(intent);
    }
    private void openActivityAddShift(){
        Intent intent = new Intent(this, AddShift.class);
        startActivity(intent);
    }
    private void openActivityRemoveShift(){
        Intent intent = new Intent(this,DeleteShift.class);
        startActivity(intent);
    }
    /*tool bar*/
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /*
    When press one of the items in the toolbar we will go to the required screen.
     */
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.my_shift){
            Intent intent = new Intent(MangerScreen.this, MangerShifts.class);
            startActivity(intent);
        }
        if(id == R.id.personal_info){
            Intent intent = new Intent(MangerScreen.this, PersonalDetails.class);
            startActivity(intent);
        }
        if(id == R.id.home_page){
            Intent intent = new Intent(MangerScreen.this, MangerScreen.class);
            startActivity(intent);
        }
        if(id == R.id.logout){

            Intent intent = new Intent(MangerScreen.this, Login.class);
            startActivity(intent);
        }
        return true;
    }



}