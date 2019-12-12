package com.example.switchingshifts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PersonalDetails extends AppCompatActivity implements View.OnClickListener {

    private EditText personalName, lastlName, mail, birthday;
    private TextView output;
    private Button savedButton;

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
        output = findViewById(R.id.output);
        savedButton = findViewById(R.id.savedButton);

        savedButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View view){
        if(view.getId() == R.id.savedButton){
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
        if(TextUtils.isEmpty(birthdayString)) {
            birthday.setError("חובה למלא שדה זה");
            flag = true;
        }
        if(!flag){
            StringBuilder sb = new StringBuilder();
            sb.append("personal name" + " " + pName)
                    .append(" last name" + " " + lName)
                    .append(" email" + " " + mailString)
                    .append(" birthday" + " " + birthdayString);
            output.setText(sb.toString());
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
            Intent intent = new Intent(PersonalDetails.this, MyShifts.class);
            startActivity(intent);
        }
        if(id == R.id.messages){
            Intent intent = new Intent(PersonalDetails.this, Messages.class);
            startActivity(intent);
        }
        if(id == R.id.personalInfo){
            Intent intent = new Intent(PersonalDetails.this, PersonalDetails.class);
            startActivity(intent);
        }
        if(id == R.id.homePage){
            Intent intent = new Intent(PersonalDetails.this, WorkerScreen.class);
            startActivity(intent);
        }
        return true;
    }
}
