package com.example.svetlogorskchpp.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.RemoteViews
import com.example.svetlogorskchpp.R

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in [ShiftScheduleWidgetConfigureActivity]
 */
class ShiftScheduleWidget : AppWidgetProvider() {

    private val actionUpdate = "UPDATE_ITEM"

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray,
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)

            val addIntent = Intent(context, javaClass)
            addIntent.action = actionUpdate

            val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                0 or PendingIntent.FLAG_MUTABLE
            } else {
                0
            }

            val addPendingIntent = PendingIntent.getBroadcast(context, 0, addIntent, flag)
            val serviceIntent = Intent(context, MyRemoteViewService::class.java)
            serviceIntent.apply {
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                data = Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME))
            }

            val remoteViews = RemoteViews(context.packageName, R.layout.shift_schedule_widget)
            remoteViews.setRemoteAdapter(R.id.gridView, serviceIntent)
        }
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        // When the user deletes the widget, delete the preference associated with it.
        for (appWidgetId in appWidgetIds) {
            deleteTitlePref(context, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
        notifyAppWidgetViewDataChanged(context)
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private fun notifyAppWidgetViewDataChanged(context: Context?) {
        val widgetManager = AppWidgetManager.getInstance(context?.applicationContext)
        widgetManager.notifyAppWidgetViewDataChanged(
            widgetManager.getAppWidgetIds(
                context?.applicationContext?.packageName?.let {
                    ComponentName(
                        it,
                        ShiftScheduleWidget::class.java.name
                    )
                }
            ),
            R.id.gridView
        )
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int,
) {
    val widgetText = loadTitlePref(context, appWidgetId)
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.shift_schedule_widget)
    views.setTextViewText(R.id.appwidget_text, widgetText)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}
