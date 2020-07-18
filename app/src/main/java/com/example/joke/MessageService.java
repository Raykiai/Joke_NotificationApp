package com.example.joke;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class MessageService extends IntentService {

    public static final String EXTRA_MESSAGE = "MESSAGE";

    public static final int NOTIFICATION_ID=1;
    private static final String CHANNEL_ID = "12";
    private Handler handler;

    public MessageService() {
        super("MessageService");
    }
    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        handler = new Handler();
        return super.onStartCommand(intent, flags, startId);
    }




    @Override
    protected void onHandleIntent(Intent intent) {
        synchronized (this){
            try{
                wait(10000);
            }catch(InterruptedException error){
                error.printStackTrace();
            }
        }
        String text  = intent.getStringExtra(EXTRA_MESSAGE);
        showText(text);


    }


    private void showText(final String text){
        Log.v("DelayedMessageService", "What is the secret of comedy?:" + text);
        Intent intent = new Intent(this,MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent =
        stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);

        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
            }
        });

//        Notification notification = new Notification.Builder(this)
//                .setSmallIcon(R.mipmap.ic_joke)
//                .setContentTitle(getString(R.string.app_name))
//                .setContentText(text)
//                .setAutoCancel(true)
//
//                .setPriority(Notification.PRIORITY_MAX)
//                .setDefaults(Notification.DEFAULT_VIBRATE)
//                .setContentIntent(pendingIntent)
//                .build();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_joke)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(text)
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(pendingIntent);
        Notification notification = builder.build();

        NotificationManager notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID,notification);







    }


}
