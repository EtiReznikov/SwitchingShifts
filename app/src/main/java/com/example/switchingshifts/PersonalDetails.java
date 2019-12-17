package com.example.switchingshifts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PersonalDetails extends AppCompatActivity implements View.OnClickListener {

    private EditText personalName, lastlName, mail, birthday;
    private Button savedButton;
    private  Worker worker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);
        Toolbar toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        Button button = findViewById(R.id.buttonNewPess);
        button.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View view){
                        startActivity(new Intent(PersonalDetails.this, ChangePass.class));
                    }
                }
        );

        personalName = findViewById(R.id.personalName);
        lastlName = findViewById(R.id.lastlName);
        mail = findViewById(R.id.mail);
        birthday = findViewById(R.id.birthday);
        savedButton = findViewById(R.id.saveButton);
        savedButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View view){
        if(view.getId() == R.id.saveButton){
            String pName = personalName.getText().toString().trim();
            String lName = lastlName.getText().toString().trim();
            String mailString = mail.getText().toString().trim();
            String birthdayString = birthday.getText().toString().trim();
            boolean flag = false;
            if(TextUtils.isEmpty(pName)) {
                personalName.setError("חובה למלא שדה זה");
                flag = true;
            }
            if(TextUtils.isEmpty(lName)) {
                lastlName.setError("חובה למלא שדה זה");
                flag = true;
            }
            if(TextUtils.isEmpty(mailString)) {
                mail.setError("חובה למלא שדה זה");
                flag = true;
            }
            else if(!Patterns.EMAIL_ADDRESS.matcher(mailString).matches()){
                mail.setError("כתובת המייל לא תקינה");
            }
            if(TextUtils.isEmpty(birthdayString)) {
                birthday.setError("חובה למלא שדה זה");
                flag = true;
            }
            if(!flag){
                Toast.makeText(PersonalDetails.this,"הנתונים נשמרו", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PersonalDetails.this, WorkerScreen.class));
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.myShift){
            startActivity(new Intent(PersonalDetails.this, MyShifts.class));
        }
        if(id == R.id.messages){
            startActivity(new Intent(PersonalDetails.this, Messages.class));
        }
        if(id == R.id.personalInfo){
            startActivity(new Intent(PersonalDetails.this, PersonalDetails.class));
        }
        if(id == R.id.homePage){
            if(worker.getEmail().equals("admin@gmail.com")){
                startActivity(new Intent(PersonalDetails.this, MangerScreen.class));
            }
            else {
                startActivity(new Intent(PersonalDetails.this, WorkerScreen.class));
            }
        }
        return true;
    }
}
