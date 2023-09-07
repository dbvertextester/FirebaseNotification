package com.dbvetex.firebase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebeseMsgService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d("RefresheToken", s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getNotification() != null) {
             pushNotification(remoteMessage.getNotification().getTitle(),
                    remoteMessage.getNotification().getBody()
            );
        }
    }

    private void pushNotification(String title, String msg) {
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Notification notification;

        final String CHANAL_ID = "push_noti";
        Intent iNotify = new Intent(this, MainActivity.class);
        iNotify.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pi = PendingIntent.getActivity(this,100,iNotify,
                PendingIntent.FLAG_UPDATE_CURRENT);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "custom channel";
            String description = "Channel for push Notification";
            int importence = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANAL_ID,name,importence);
            channel.setDescription(description);


            if (nm != null){
                nm.createNotificationChannel(channel);

                  notification = new Notification.Builder(this)
                          .setSmallIcon(R.drawable.ic_launcher_background)
                          .setContentIntent(pi)
                          .setContentTitle(title)
                          .setSubText(msg)
                          .setAutoCancel(true)
                          .setChannelId(CHANAL_ID)
                          .build();
            } else {
                notification = new Notification.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle(title)
                        .setContentIntent(pi)
                        .setSubText(msg)
                        .setAutoCancel(true)
                        .build();
            }
            if(nm != null) nm.notify(1, notification);



        }
    }
}
