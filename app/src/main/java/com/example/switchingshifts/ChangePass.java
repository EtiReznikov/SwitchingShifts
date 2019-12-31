
package com.example.switchingshifts;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ChangePass extends AppCompatActivity implements View.OnClickListener {

    private EditText first_pass, second_pass;
    private Button button;
    private String first_pass_input, second_pass_input;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);

        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);




        first_pass = findViewById(R.id.first_pass);
        second_pass = findViewById(R.id.second_pass);
        button = findViewById(R.id.button);

        button.setOnClickListener(this);

    }
    /* When press the button to change the password we check if they are equal,
    if they are equal we will go to the main screen of the employee
    else we will you will get an error message. */
    public void onClick(View view){
        if(view.getId() == R.id.button){
            first_pass_input = first_pass.getText().toString().trim();
            second_pass_input = second_pass.getText().toString().trim();

            if(!first_pass_input.equals(second_pass_input)){
                first_pass.setError("הסיסמאות לא שוות");
                second_pass.setError("הסיסמאות לא שוות");
            }
            else{
                /* if the password are same- updade tha users password */
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                user.updatePassword(first_pass_input)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(ChangePass.this,"הסיסמה שונתה בהצלחה", Toast.LENGTH_LONG).show();
                                }else {
                                    Toast.makeText(ChangePass.this, task.getException().getMessage() , Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                startActivity(new Intent(ChangePass.this, WorkerScreen.class));
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /* When press one of the items in the toolbar we will go to the required screen. */
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
        if(id == R.id.logout){
            Intent intent = new Intent(ChangePass.this, Login.class);
            startActivity(intent);
        }
        return true;
    }
}