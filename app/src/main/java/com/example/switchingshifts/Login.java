package com.example.switchingshifts;

import androidx.appcompat.app.AppCompatActivity;

<<<<<<< HEAD
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Login extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 5000;
=======
import android.os.Bundle;

public class Login extends AppCompatActivity {

>>>>>>> d13b7035958a04c34502f0a8d030712ce9752e3f
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
<<<<<<< HEAD
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent loginIntent = new Intent(Login.this, ChangePass.class);
                startActivity(loginIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }


}
=======
    }
}
>>>>>>> d13b7035958a04c34502f0a8d030712ce9752e3f
