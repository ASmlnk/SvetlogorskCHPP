package com.example.svetlogorskchpp.__widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.navigation.NavDeepLinkBuilder
import com.example.svetlogorskchpp.MainActivity
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.__di.Widget
import com.example.svetlogorskchpp.__domain.en.Shift
import com.example.svetlogorskchpp.__domain.interactor.shift_schedule.calendar.ShiftScheduleCalendarInteractor
import com.example.svetlogorskchpp.__domain.usecases.calendarNoteTag.CalendarNoteTagWidgetUseCases
import com.example.svetlogorskchpp.__domain.usecases.calendarTagUseCases.CalendarTagUseCases
import com.example.svetlogorskchpp.__presentation.shift_schedule.model.CalendarFullDayShiftModel
import com.example.svetlogorskchpp.__presentation.shift_schedule.model.NavigateAddNoteArgs
import com.example.svetlogorskchpp.__presentation.shift_schedule_calendar_add_notes.fragment.ShiftScheduleAddNotesFragmentArgs
import com.example.svetlogorskchpp.__widget.service.MyRemoteAllShiftViewService
import com.example.svetlogorskchpp.__widget.service.MyRemoteOneShiftViewService
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.TimeZone
import javax.inject.Inject

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in [ShiftScheduleWidgetConfigureActivity]
 */
@Suppress("DEPRECATION")
@AndroidEntryPoint
class ShiftScheduleWidget : AppWidgetProvider() {

    @Widget
    @Inject
    lateinit var shiftScheduleCalendarInteractor: ShiftScheduleCalendarInteractor

    @Inject
    lateinit var calendarNoteTagWidgetUseCases: CalendarNoteTagWidgetUseCases

    @Widget
    @Inject
    lateinit var calendarTagUseCases: CalendarTagUseCases

    private val APPWIDGET_CONFIGURE = "android.appwidget.action.APPWIDGET_CONFIGURE"

    private val calendar = Calendar
        .getInstance(TimeZone.getTimeZone("GMT+3")).apply {
            firstDayOfWeek = 2
        }

    private val scope = CoroutineScope(Dispatchers.Main + Job())

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray,
    ) {
        generateDays()

        val calendarFullDayShift = shiftScheduleCalendarInteractor.getDaysFullCalendarStream()
            .stateIn(
                CoroutineScope(Dispatchers.IO),
                SharingStarted.Lazily,
                CalendarFullDayShiftModel()
            )
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                0 or PendingIntent.FLAG_MUTABLE
            } else {
                0
            }



            //val flag = PendingIntent.FLAG_UPDATE_CURRENT

            val nextMonthIntent = Intent(context, ShiftScheduleWidget::class.java).apply {
                setAction("ACTION_NEXT_MONTH")
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            }
            val nextMonthPendingIntent =
                PendingIntent.getBroadcast(context, 0, nextMonthIntent, flag)

            val prevMonthIntent = Intent(context, ShiftScheduleWidget::class.java).apply {
                setAction("ACTION_PREV_MONTH")
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            }
            val prevMonthPendingIntent =
                PendingIntent.getBroadcast(context, 0, prevMonthIntent, flag)

            val intent = Intent(context, ShiftScheduleWidgetConfigureActivity::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)

            val pendingIntent = PendingIntent.getActivity(
                context,
                appWidgetId,
                intent,
                flag
            )

            val remoteViews =
                RemoteViews(context.packageName, R.layout.shift_schedule_widget)
            Log.d("0000000000", "remote")

            remoteViews.apply {
                setOnClickPendingIntent(
                    R.id.iv_calendar_previous,
                    prevMonthPendingIntent
                )
                setOnClickPendingIntent(
                    R.id.button_setting,
                    pendingIntent
                )
               // setFloat(
                  //  R.id.widget_layout,"setAlpha", 0.95F
               // )

            }
            // intent.setAction(APPWIDGET_CONFIGURE + appWidgetId)

            scope.launch {
                calendarFullDayShift.collect { calendarFullDayShiftModel ->

                    if (calendarFullDayShiftModel.calendarFullDayModels.isNotEmpty()) {

                        Log.d("0000000000", "calendarFullDayShiftModel  $calendarFullDayShiftModel")

                        val tags = calendarNoteTagWidgetUseCases.calendarNoteTagStream(calendar)
                        val calendarFullDayShiftModelTags = calendarFullDayShiftModel.copy(
                            calendarFullDayModels = calendarTagUseCases.addNoteTagToCalendar(
                                calendarFullDayShiftModel.calendarFullDayModels,
                                tags
                            )
                        )

                        Log.d("0000000000", "calendarFullDayShiftModelTags $calendarFullDayShiftModelTags")

                        val gson = Gson()
                        val json = gson.toJson(calendarFullDayShiftModelTags)

                        Log.d("0000000000", "json $json")

                        val serviceIntent = if (calendarFullDayShiftModel.calendarView == "1") {
                            Intent(context, MyRemoteAllShiftViewService::class.java)
                        } else {
                            Intent(context, MyRemoteOneShiftViewService::class.java)
                        }

                        serviceIntent.apply {
                            putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                            putExtra("EXTRA_CURRENT_DATE", calendar.timeInMillis)

                            putExtra("CALENDAR_FULL_DAY_SHIFT", json)
                            data = Uri.parse(this.toUri(Intent.URI_INTENT_SCHEME))
                        }

                        remoteViews.apply {
                            setTextViewText(R.id.tv_date_month, textDateMonth(calendar))
                            setRemoteAdapter(R.id.gridView, serviceIntent)
                            setOnClickPendingIntent(R.id.iv_calendar_next, nextMonthPendingIntent)
                        }
                        appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
                    }
                }
            }

           // appWidgetManager.updateAppWidget(appWidgetId, remoteViews)

            val clickIntent = Intent(context, ShiftScheduleWidget::class.java)
            clickIntent.action = "ACTION_SELECT_DAY"
            val clickPendingIntent = PendingIntent.getBroadcast(context, 0, clickIntent, flag)
            remoteViews.setPendingIntentTemplate(R.id.gridView, clickPendingIntent)
        }
    }

    override fun onReceive(context: Context, intent: Intent?) {
        super.onReceive(context, intent)
        val appWidgetId = intent?.getIntExtra(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        ) ?: return

        when (intent.action) {
            "ACTION_NEXT_MONTH" -> {
                // updateDate(context, appWidgetId, 1)
                monthOffset++
                calendar.apply {
                    add(Calendar.MONTH, monthOffset)
                }
            }

            "ACTION_PREV_MONTH" -> {
                monthOffset--
                // generateDays()
                calendar.apply {
                    add(Calendar.MONTH, monthOffset)
                }
            }

            "ACTION_UPDATE_WIDGET" -> {
                monthOffset = 0
            }

            AppWidgetManager.ACTION_APPWIDGET_UPDATE -> {
                monthOffset = 0
            }

            "ACTION_SELECT_DAY" -> {
                val navigateAddNoteArgs =
                    intent.getParcelableExtra<NavigateAddNoteArgs>("NAVIGATION_ADD_NOTES_ARGS")
                navigateAddNoteArgs?.let {
                    val args = ShiftScheduleAddNotesFragmentArgs(navigateAddNoteArgs)
                    val pendingIntent2 = NavDeepLinkBuilder(context)
                        .setGraph(R.navigation.nav_graph)
                        .setDestination(R.id.shiftScheduleAddNotesFragment)
                        .setArguments(args.toBundle())
                        .createPendingIntent()

                    pendingIntent2.send()
                }
                monthOffset = 0
            }
        }

        val appWidgetManager = AppWidgetManager.getInstance(context)
        val thisWidget = ComponentName(context, ShiftScheduleWidget::class.java)
        val appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget)
        onUpdate(context, appWidgetManager, appWidgetIds)
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        // When the user deletes the widget, delete the preference associated with it.
        for (appWidgetId in appWidgetIds) {
            deleteTitlePref(context, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
        // notifyAppWidgetViewDataChanged(context)
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
        scope.cancel()
    }

    private fun textDateMonth(calendar: Calendar): String {
        val sdf = SimpleDateFormat("MMMM yyyy")
        return sdf.format(calendar.time)
    }

    private fun generateDays() {
        //updateTag()
        val monthCalendar = calendar.clone() as Calendar
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)

        shiftScheduleCalendarInteractor.generateDaysFullCalendar(monthCalendar)
    }

    companion object {
        var monthOffset = 0
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int,
) {
    // val widgetText = loadTitlePref(context, appWidgetId)
    // Construct the RemoteViews object
    val thisWidget = ComponentName(context, ShiftScheduleWidget::class.java)

    val views = RemoteViews(context.packageName, R.layout.shift_schedule_widget)
    // views.setTextViewText(R.id.appwidget_text, widgetText)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}

