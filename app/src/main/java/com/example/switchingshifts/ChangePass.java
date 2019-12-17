
package com.example.switchingshifts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class ChangePass extends AppCompatActivity implements View.OnClickListener {

    private EditText firstPess, secondPess;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);

        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        firstPess = findViewById(R.id.firstPess);
        secondPess = findViewById(R.id.secondPess);
        button = findViewById(R.id.button);

        button.setOnClickListener(this);

    }
    public void onClick(View view){
        if(view.getId() == R.id.button){
            String firstPessInput = firstPess.getText().toString().trim();
            String secondPessInput = secondPess.getText().toString().trim();

            if(!firstPessInput.equals(secondPessInput)){
                firstPess.setError("הסיסמאות לא שוות");
                secondPess.setError("הסיסמאות לא שוות");
            }
            else{
                Toast.makeText(ChangePass.this,"הסיסמא שונתה בהצלחה", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ChangePass.this, WorkerScreen.class));
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.myShift){
            startActivity(new Intent(ChangePass.this, MyShifts.class));
        }
        if(id == R.id.messages){
            startActivity(new Intent(ChangePass.this, Messages.class));
        }
        if(id == R.id.personalInfo){
            startActivity(new Intent(ChangePass.this, PersonalDetails.class));
        }
        if(id == R.id.homePage){
            startActivity(new Intent(ChangePass.this, WorkerScreen.class));
        }
        return true;
    }
}