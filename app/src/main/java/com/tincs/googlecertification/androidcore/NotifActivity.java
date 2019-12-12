package com.tincs.googlecertification.androidcore;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.tincs.googlecertification.R;

public class NotifActivity extends AppCompatActivity implements View.OnClickListener{

    //Views
    private MaterialButton notify, update, cancel;

    private static final String ACTION_UPDATE_NOTIFICATION = "com.tincs.googlecertification.androidcore.ACTION_UPDATE_NOTIFICATION";
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    // let us create a notification id;  so that we can can easily update an cancel it from our code
    private static final int NOTIFICATION_ID = 0;
    NotificationManager notificationManager;
    NotificationReceiver notifReceiver = new NotificationReceiver();

    @Override
    protected void onDestroy() {
        //unregistring the NotificationReceiver
        unregisterReceiver(notifReceiver);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif);

        //init views
        notify = findViewById(R.id.notify);
        update = findViewById(R.id.update_notif);
        cancel = findViewById(R.id.cancel_notif);

        notify.setOnClickListener(this);
        update.setOnClickListener(this);
        cancel.setOnClickListener(this);

        //registring the NotificationReceiver
        registerReceiver(notifReceiver, new IntentFilter(ACTION_UPDATE_NOTIFICATION));
        createNotificationChannel();
        //init the buttons state
        setNotificationButtonState(true, false, false);
    }

    // here we are creating a notification channel, is for android 8.0 and higher
    //*************************START*********************Notification methods part***********************************

    public void createNotificationChannel(){
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        /* adding a condition to verify  the target device API version before creating a notification channel
         ** i do that because Notification Channel is only available on API 26 (Android 8.0) an higher **
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "Mascot Notification", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            notificationChannel.setLightColor(Color.LTGRAY);
            notificationChannel.setDescription("Google Developers Certification (Description for notification)");
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public NotificationCompat.Builder getNotificationBuilder(){
        Intent notificationIntent = new Intent(this, AndroidCore.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle("You've been notified! (Title)")
                .setContentText("This is your notification text")
                .setSmallIcon(R.drawable.ic_notify)
                .setContentIntent(notificationPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true);
        return notifyBuilder;
    }

    public void sendNotification(){
        /*Adding the update action to the notification
        attach a pending Intent to update action
         */
        Intent updateIntent = new Intent(ACTION_UPDATE_NOTIFICATION);
        PendingIntent updatePendingIntent = PendingIntent.getBroadcast(
                this, NOTIFICATION_ID, updateIntent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        //Adding the update action to the notification
        notifyBuilder.addAction(R.drawable.ic_update, "Update Notification", updatePendingIntent);
        notificationManager.notify(NOTIFICATION_ID, notifyBuilder.build());
        setNotificationButtonState(false, true, true);
    }

    public void updateNotification(){
        /* convert the image to be displayed into a Bitmap using BitmapFactory
         * make sure that its aspect ratio is 2:1 and its width is 450 dp or less.*/
        Bitmap notifImage = BitmapFactory.decodeResource(getResources(), R.drawable.mascot_1);

        // get the notification Builder object
        NotificationCompat.Builder updateBuilder = getNotificationBuilder();

        // change the notification style
        updateBuilder.setStyle(new NotificationCompat.BigPictureStyle()
                .bigPicture(notifImage)
                .setBigContentTitle("Notification Updated!"));
        notificationManager.notify(NOTIFICATION_ID, updateBuilder.build());
        setNotificationButtonState(false, false, true);
    }

    public void cancelNotification(){
        notificationManager.cancel(NOTIFICATION_ID);
        setNotificationButtonState(true, false, false);
    }

    // disable or enable buttons depending on the notification state
    public void setNotificationButtonState(boolean isNotifyEnabled,
                                           boolean isUpdateEnabled, boolean isCancelEnabled){
        notify.setEnabled(isNotifyEnabled);
        update.setEnabled(isUpdateEnabled);
        cancel.setEnabled(isCancelEnabled);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.notify: sendNotification();break;
            case R.id.update_notif: updateNotification();break;
            case R.id.cancel_notif: cancelNotification();break;
        }
    }

    public class NotificationReceiver extends BroadcastReceiver {

        public NotificationReceiver(){}

        @Override
        public void onReceive(Context context, Intent intent) {
            updateNotification();
        }
    }

    //*************************END*********************Notification methods part***********************************
}
