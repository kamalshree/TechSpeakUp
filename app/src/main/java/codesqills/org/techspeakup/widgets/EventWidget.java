package codesqills.org.techspeakup.widgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import codesqills.org.techspeakup.R;

/**
 * Created by kamalshree on 11/15/2018.
 */

public class EventWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.events_widget);
            appWidgetManager.updateAppWidget(appWidgetId, views);
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

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        String eventName, eventLocation,eventDate;

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.events_widget);
        SharedPreferences sharedPref = context.getSharedPreferences("EventDetails", Context.MODE_PRIVATE);

        eventName = sharedPref.getString("SharedPrefeventName", "");
        eventLocation = sharedPref.getString("SharedPrefeventLocation", "");
        eventDate = sharedPref.getString("SharedPrefeventDate", "");

        views.setTextViewText(R.id.tv_widget_eventname, eventName);
        views.setTextViewText(R.id.tv_widget_eventlocation, eventLocation);
        views.setTextViewText(R.id.tv_widget_eventdate, eventDate);

        AppWidgetManager.getInstance(context).updateAppWidget(
                new ComponentName(context, EventWidget.class), views);

    }
}
