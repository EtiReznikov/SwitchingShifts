package com.example.switchingshifts;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class NotificationHelper extends Application {
    public static final String channel1_id = "channel1_id";
    public static final String channel1_name = "channel1_name";
    public static final String channel2_id = "channel2_id";
    public static final String channel2_name = "channel2_name";
    @Override
    public void onCreate(){
        super.onCreate();

        createNotificationChannels();
    }

    private void createNotificationChannels(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel1 = new NotificationChannel(channel1_id, "Channel 1", NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("this is channel 1");

            NotificationChannel channel2 = new NotificationChannel(channel2_id, "Channel 1", NotificationManager.IMPORTANCE_HIGH);
            channel2.setDescription("this is channel 2");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);

        }
    }
}
