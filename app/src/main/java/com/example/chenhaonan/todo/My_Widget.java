package com.example.chenhaonan.todo;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.scu.notes.AllDiaList;

import activitytest.com.example.weatherforecast.WeatherActivity;

/**
 * Implementation of App Widget functionality.
 */
public class My_Widget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int x = 0; x < appWidgetIds.length; x++) {

            //給控件按钮指定响应活动
            Intent intent1 = new Intent(context, MainActivity.class);
            Intent intent2 = new Intent(context, WeatherActivity.class);
            Intent intent3 = new Intent(context, TomatoActivity.class);
            Intent intent4 = new Intent(context, AllDiaList.class);


            PendingIntent pendingIntent1 = PendingIntent.getActivity(context, 0,

                    intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent pendingIntent2 = PendingIntent.getActivity(context, 0,

                    intent2, PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent pendingIntent3 = PendingIntent.getActivity(context, 0,

                    intent3, PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent pendingIntent4 = PendingIntent.getActivity(context, 0,

                    intent4, PendingIntent.FLAG_UPDATE_CURRENT);

            RemoteViews remote = new RemoteViews(context.getPackageName(),

                    R.layout.my__widget);

            //响应点击事件
            remote.setOnClickPendingIntent(R.id.add, pendingIntent1);
            remote.setOnClickPendingIntent(R.id.weather, pendingIntent2);
            remote.setOnClickPendingIntent(R.id.clock, pendingIntent3);
            remote.setOnClickPendingIntent(R.id.diary, pendingIntent4);

            appWidgetManager.updateAppWidget(appWidgetIds[x], remote);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}