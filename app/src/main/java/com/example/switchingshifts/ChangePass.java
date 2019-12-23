
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

    private EditText first_pess, second_pess;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);

        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        first_pess = findViewById(R.id.first_pess);
        second_pess = findViewById(R.id.second_pess);
        button = findViewById(R.id.button);

        button.setOnClickListener(this);

    }
    public void onClick(View view){
        if(view.getId() == R.id.button){
            String first_pess_input = first_pess.getText().toString().trim();
            String second_pess_input = second_pess.getText().toString().trim();

            if(!first_pess_input.equals(second_pess_input)){
                first_pess.setError("הסיסמאות לא שוות");
                second_pess.setError("הסיסמאות לא שוות");
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
        if(id == R.id.my_shift){
            startActivity(new Intent(ChangePass.this, MyShifts.class));
        }
        if(id == R.id.messages){
            startActivity(new Intent(ChangePass.this, Messages.class));
        }
        if(id == R.id.personal_info){
            startActivity(new Intent(ChangePass.this, PersonalDetails.class));
        }
        if(id == R.id.home_page){
            startActivity(new Intent(ChangePass.this, WorkerScreen.class));
        }
        return true;
    }
}