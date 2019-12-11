package com.example.switchingshifts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MangerScreen extends AppCompatActivity {
    private Button add_worker;
    private Button remove_worker;
    private Button remove_shift;
    private Button add_shift;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manger_screen);

        add_worker= (Button)findViewById(R.id.button_add_worker);
        add_worker.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick( View v){
                openActivityAddWorker();
            }
        });
        remove_worker= (Button)findViewById(R.id.button_remove_worker);
        remove_worker.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick( View v){
                openActivityRemoveWorker();
            }
        });
        remove_shift= (Button)findViewById(R.id.button_remove_shift);
        remove_shift.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick( View v){
                openActivityRemoveShift();
            }
        });
        add_shift= (Button)findViewById(R.id.button_add_shift);
        add_shift.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick( View v){
                openActivityAddShift();
            }
        });
    }

   private void openActivityAddWorker(){
        Intent intent=new Intent(this, AddWorker.class);
        startActivity(intent);
   }
    private void openActivityRemoveWorker(){
        Intent intent=new Intent(this,RemoveWorker.class);
        startActivity(intent);
    }
    private void openActivityAddShift(){
        Intent intent=new Intent(this, AddShift.class);
        startActivity(intent);
    }
    private void openActivityRemoveShift(){
        Intent intent=new Intent(this,DeleteShift.class);
        startActivity(intent);
    }


}

