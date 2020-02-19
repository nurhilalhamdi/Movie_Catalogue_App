package com.example.submission4.ui.Alarm;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.submission4.MainActivity;
import com.example.submission4.R;

import java.util.Calendar;

public class DailyAlarmReceiver extends BroadcastReceiver {
    private int delay = 0;
    @Override
    public void onReceive(Context context, Intent intent) {
       String title = context.getResources().getString(R.string.title_Movie);
       String message = context.getResources().getString(R.string.daily_repeat);
       showAlarmNotification(context,title,message,100);
    }

    public void setDailyRepeatingAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        Intent intent = new Intent(context, DailyAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        Toast.makeText(context,R.string.active_alarmdaily, Toast.LENGTH_SHORT).show();
    }

    public void showAlarmNotification(Context context, String title, String message, int notifId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.getAction();
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, intent, 0);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "daily_repeat")
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
                delay=100;
   /*
        Untuk android Oreo ke atas perlu menambahkan notification channel
        Materi ini akan dibahas lebih lanjut di modul extended
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            /* Create or update. */
            NotificationChannel channel = new NotificationChannel("daily_repeat",
                    "channel",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("channel");
            builder.setChannelId("daily_repeat");

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();

        if (notificationManager != null) {
            notificationManager.notify(notifId, notification);
        }
    }

    public void cancelDailyRepeating(Context context ){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context,DailyAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,100,intent,0);
        pendingIntent.cancel();
        if (alarmManager != null){
            alarmManager.cancel(pendingIntent);
        }

        Toast.makeText(context,R.string.cancel_alarmdaily, Toast.LENGTH_SHORT).show();
    }

}
