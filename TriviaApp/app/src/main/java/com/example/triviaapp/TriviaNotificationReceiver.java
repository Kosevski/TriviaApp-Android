package com.example.triviaapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.preference.PreferenceManager;

public class TriviaNotificationReceiver extends BroadcastReceiver {

    PendingIntent notificationPendingIntent;
    NotificationManager notificationManager;

    @Override
    public void onReceive(final Context context, Intent intent) {

        Intent notificationIntent = new Intent(context, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(notificationIntent);

        notificationPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("CHANNEL_ID",
                    "Channel 1",
                    NotificationManager.IMPORTANCE_DEFAULT);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
        Notification notification = new NotificationCompat.Builder(context, "CHANNEL_ID")
                .setSmallIcon(R.drawable.ic_question_answer)
                .setContentTitle("Trivia Time!")
                .setContentText("Stop whatever you are doing and take this challenge! Play another Trivia")
                .setContentIntent(notificationPendingIntent)
                .setAutoCancel(true)
                .build();

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean notifications_on_of = sharedPreferences.getBoolean("notifications_on_off", true);
        if (notifications_on_of) {
            if (notificationManager != null) {
                notificationManager.notify(1, notification);
            }
        }
    }
}

