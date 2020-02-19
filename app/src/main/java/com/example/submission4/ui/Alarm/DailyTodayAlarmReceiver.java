package com.example.submission4.ui.Alarm;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.submission4.BuildConfig;
import com.example.submission4.R;
import com.example.submission4.ui.Movie.MovieItem;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class DailyTodayAlarmReceiver extends BroadcastReceiver {
    private static int notifid = 200;
    private static int delay = 0;
    @Override
    public void onReceive(Context context, Intent intent) {

        UriAlarmToday(context);

    }

    public void setDailyTodayAlarm(Context context){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,8);
        Intent intent = new Intent(context,DailyTodayAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,200,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
        Toast.makeText(context,R.string.active_alarmtoday, Toast.LENGTH_SHORT).show();
    }

    public void showAlarmTodayNotification (Context context , String title, String message, int notifid){



       NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"today_repeat")
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_notifications_black_24dp))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);


          /*
        Untuk android Oreo ke atas perlu menambahkan notification channel
        Materi ini akan dibahas lebih lanjut di modul extended
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            /* Create or update. */
            NotificationChannel channel = new NotificationChannel("today_repeat",
                    "channel",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("channel");
            builder.setChannelId("today_repeat");

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();

        if (notificationManager != null) {
            notificationManager.notify(notifid, notification);
        }
    }


    public void UriAlarmToday(final Context context){
        String apikey = BuildConfig.API_KEY;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        final String today = dateFormat.format(date);
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        final ArrayList<MovieItem> listmovie = new ArrayList<>();



        String url = "https://api.themoviedb.org/3/discover/movie?api_key="+apikey+"&primary_release_date.gte="+today+"&primary_release_date.lte="+today;
        asyncHttpClient.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseobject = new JSONObject(result);
                    JSONArray list = responseobject.getJSONArray("results");
                    for (int i = 0; i <list.length() ; i++) {
                        JSONObject movie = list.getJSONObject(i);
                        MovieItem movieItem = new MovieItem();

                        movieItem.setId(movie.getInt("id"));
                        movieItem.setTitle(movie.getString("title"));
                        movieItem.setOverview(movie.getString("overview"));
                        movieItem.setRelease_date(movie.getString("release_date"));
                        movieItem.setPoster_path(movie.getString("poster_path"));
                        listmovie.add(movieItem);

                        String title = movieItem.getTitle();
                        String message = title + context.getResources().getString(R.string.release_message);
//                      String poster = movieItem.getPoster_path();
//                        Log.d("foto",poster);
                        showAlarmTodayNotification(context,title,message,notifid);
                        notifid+=1;
                        delay+=1000;


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public void cancelTodayRepeating(Context context ){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context,DailyAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,200,intent,0);
        pendingIntent.cancel();
        if (alarmManager != null){
            alarmManager.cancel(pendingIntent);
        }

        Toast.makeText(context,R.string.cancel_alarmtoday, Toast.LENGTH_SHORT).show();
    }
}
