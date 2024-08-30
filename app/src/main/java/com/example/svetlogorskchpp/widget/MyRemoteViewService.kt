package com.example.svetlogorskchpp.widget

import android.annotation.SuppressLint
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService

import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.domain.en.Shift
import com.example.svetlogorskchpp.domain.interactor.shift_schedule.calendar.ShiftScheduleCalendarInteractor
import com.example.svetlogorskchpp.domain.model.MonthCalendar
import com.example.svetlogorskchpp.presentation.shift_schedule.model.CalendarFullDayModel
import com.example.svetlogorskchpp.presentation.shift_schedule.model.CalendarFullDayShiftModel
import com.example.svetlogorskchpp.presentation.shift_schedule.viewModel.ShiftScheduleViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Calendar
import java.util.TimeZone
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class MyRemoteViewService : RemoteViewsService() {
    private var calendarItemList = ArrayList<CalendarFullDayModel>()

    @Named("Singleton")
    @Inject
    lateinit var shiftScheduleCalendarInteractor: ShiftScheduleCalendarInteractor

    private val scope = CoroutineScope(Dispatchers.IO + Job())


    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        val currentTimeInMillis = intent.getLongExtra("EXTRA_CURRENT_DATE", date().timeInMillis)

        // Создание экземпляра Calendar
        val calendar = Calendar.getInstance().apply {
            timeInMillis = currentTimeInMillis
        }
        val tt = SimpleDateFormat("MMMM yyyy").format(calendar.time)
        println(tt)
        return MyRemoteViewsFactory(this.applicationContext, shiftScheduleCalendarInteractor, calendar)
    }

    private fun date() = Calendar
        .getInstance(TimeZone.getTimeZone("GMT+3")).apply { firstDayOfWeek = 2 }




    inner class MyRemoteViewsFactory(
        private val context: Context,
        private val shiftScheduleCalendarInteractor: ShiftScheduleCalendarInteractor,
        private val calendar: Calendar
    ) : RemoteViewsFactory {

        val calendarFullDayShift = shiftScheduleCalendarInteractor.getDaysFullCalendarStream()
            .stateIn(
                CoroutineScope(Dispatchers.IO),
                SharingStarted.Lazily,
                CalendarFullDayShiftModel()
            )

        override fun onCreate() {
            generateDays()

            scope.launch {
                calendarFullDayShift.collect { list ->
                    updateList(list.calendarFullDayModels)
                    val appWidgetManager = AppWidgetManager.getInstance(context)
                    val appWidgetIds = appWidgetManager.getAppWidgetIds(
                        ComponentName(context, ShiftScheduleWidget::class.java)
                    )
                    appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.gridView)
                }
            }
        }

        override fun onDataSetChanged() {
            if (calendarItemList.isNotEmpty()) {
                getViewAt(0)
            }
        }

        override fun onDestroy() {
            scope.cancel()
        }

        override fun getCount(): Int {
            return calendarItemList.size
        }

        @SuppressLint("RemoteViewLayout")
        override fun getViewAt(position: Int): RemoteViews {
            val calendarItem = calendarItemList[position]

            val layoutId =
                if (calendarItem.month == MonthCalendar.ACTUAL_MONTH) R.layout.item_full_calendar_month_widget else R.layout.item_full_calendar_prev_month_widget

            val remoteView = RemoteViews(context.packageName, layoutId)

            when (calendarItem.month) {
                MonthCalendar.ACTUAL_MONTH -> {
                    remoteView.setTextViewText(R.id.tv_calendar_date, calendarItem.calendarDate)
                    remoteView.setTextViewText(R.id.text_day_shift, shift(calendarItem.dayShift))
                    remoteView.setTextViewText(
                        R.id.text_prev_night_shift,
                        shift(calendarItem.prevNightShift)
                    )
                    remoteView.setTextViewText(
                        R.id.text_nex_night_shift,
                        shift(calendarItem.nextNightShift)
                    )
                }

                else -> {
                    remoteView.setTextViewText(
                        R.id.tv_calendar_date,
                        calendarItem.calendarDate
                    )
                }
            }
            return remoteView
        }

        override fun getLoadingView(): RemoteViews? {
            return null
        }

        override fun getViewTypeCount(): Int {
            return 2
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun hasStableIds(): Boolean {
            return true
        }

        private fun updateList(newList: List<CalendarFullDayModel>) {
            calendarItemList.clear()
            calendarItemList.addAll(newList)
            onDataSetChanged()
        }

        private fun generateDays() {
            //updateTag()
            val monthCalendar = calendar.clone() as Calendar
            monthCalendar.set(Calendar.DAY_OF_MONTH, 1)
            val sdf = SimpleDateFormat("MMMM yyyy")
            val text =  sdf.format(calendar.time)
            println(text)


            shiftScheduleCalendarInteractor.generateDaysFullCalendar(monthCalendar)
        }


    }

    fun shift(shift: Shift): String {
        return when (shift) {
            Shift.A_SHIFT -> "А"
            Shift.B_SHIFT -> "Б"
            Shift.C_SHIFT -> "В"
            Shift.D_SHIFT -> "Г"
            Shift.E_SHIFT -> "Д"
            Shift.NO_SHIFT -> ""
        }
    }
}


