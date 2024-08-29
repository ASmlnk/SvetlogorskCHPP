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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.TimeZone

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in [ShiftScheduleWidgetConfigureActivity]
 */
class ShiftScheduleWidget : AppWidgetProvider() {

    private val calendar = Calendar
        .getInstance(TimeZone.getTimeZone("GMT+3")).apply { firstDayOfWeek = 2 }

    private val state = MutableStateFlow(calendar)

    private val actionUpdate = "UPDATE_ITEM"

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray,
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)

            val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                0 or PendingIntent.FLAG_MUTABLE
            } else {
                0
            }

            val addMonthIntent = Intent(context, ShiftScheduleWidget::class.java).apply {
                action = "ACTION_ADD_MONTH"
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            }
            val addMonthPendingIntent = PendingIntent.getBroadcast(context, 0, addMonthIntent,  flag)

            val addIntent = Intent(context, javaClass)
            addIntent.action = actionUpdate



            val addPendingIntent = PendingIntent.getBroadcast(context, 0, addIntent, flag)
            val serviceIntent = Intent(context, MyRemoteViewService::class.java)
            serviceIntent.apply {
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                putExtra("EXTRA_CURRENT_DATE", calendar.timeInMillis)
                data = Uri.parse(this.toUri(Intent.URI_INTENT_SCHEME))
            }

            val remoteViews = RemoteViews(context.packageName, R.layout.shift_schedule_widget)

            remoteViews.apply {
                setTextViewText(R.id.tv_date_month, textDateMonth(calendar) )
                setRemoteAdapter( R.id.gridView, serviceIntent)
                setOnClickPendingIntent(R.id.iv_calendar_next, addMonthPendingIntent)
            }

            appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
        }
    }

    override fun onReceive(context: Context, intent: Intent?) {
        super.onReceive(context, intent)
        val appWidgetId = intent?.getIntExtra(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        ) ?: return



        if (intent.action == "ACTION_ADD_MONTH") {
            updateDate(context, appWidgetId, 1)

        }
    }

    private fun updateDate(contextRecive: Context, appWidgetId: Int, monthChange: Int) {
        // Получаем текущую дату
        val views = RemoteViews(contextRecive.packageName, R.layout.shift_schedule_widget)

        val date = state.value
        date.add(Calendar.MONTH, monthChange)
        state.update { date }
        val t = textDateMonth(date)
println(t)

        // Обновление GridView с новой датой

        views.setRemoteAdapter(R.id.gridView, Intent(contextRecive, MyRemoteViewService::class.java).apply {
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            putExtra("EXTRA_CURRENT_DATE", date.timeInMillis)
            data = Uri.parse(this.toUri(Intent.URI_INTENT_SCHEME))
        })


        val app = AppWidgetManager.getInstance(contextRecive.applicationContext)
        app.updateAppWidget(appWidgetId, views)
        notifyAppWidgetViewDataChanged(contextRecive)

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

    private fun textDateMonth(calendar: Calendar): String {
        val sdf = SimpleDateFormat("MMMM yyyy")
        return sdf.format(calendar.time)
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
