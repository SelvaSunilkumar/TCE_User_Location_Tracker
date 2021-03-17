package edu.education.myapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.speech.SpeechRecognizer;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

public class BackgroundNotification {

    private PendingIntent pendingIntent;

    public Notification sendNotification (Context context, String title, String text, int icon) {

        if (pendingIntent == null) {
            Intent notificationIntent = new Intent(context, MainActivity.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(context,0, notificationIntent,0);
        }

        Notification notification;

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Permanent Notification";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            String CHANNEL_ID = "TCE";

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);

            String description = "App is running in Background";
            channel.setDescription(description);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }

            notification = builder
                    .setSmallIcon(icon)
                    .setColor(ContextCompat.getColor(context, R.color.colorAccent))
                    .setContentTitle(title)
                    .setContentText(text)
                    .setContentIntent(pendingIntent).build();

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notification = new NotificationCompat.Builder(context,"channel")
                    .setSmallIcon(icon)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent).build();
        } else {
            notification = new NotificationCompat.Builder(context, "channel")
                    .setSmallIcon(icon)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent).build();
        }
        return notification;
    }

}
