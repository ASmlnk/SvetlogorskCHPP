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
import com.example.svetlogorskchpp.presentation.shift_schedule.model.CalendarFullDayModel
import com.example.svetlogorskchpp.presentation.shift_schedule.model.CalendarFullDayShiftModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.ArrayList
import java.util.Calendar
import java.util.TimeZone
import javax.inject.Inject

@AndroidEntryPoint
class MyRemoteViewService: RemoteViewsService() {
    private var calendarItemList = ArrayList< CalendarFullDayModel>()




    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return MyRemoteViewsFactory(this.applicationContext)
    }

   inner class MyRemoteViewsFactory
       constructor (private val context: Context): RemoteViewsService.RemoteViewsFactory {
       @Inject lateinit var shiftScheduleCalendarInteractor: ShiftScheduleCalendarInteractor

        override fun onCreate() {
            generateDays()
            val calendarFullDayShift = shiftScheduleCalendarInteractor.getDaysFullCalendarStream()
                .stateIn(CoroutineScope(Dispatchers.IO), SharingStarted.Lazily, CalendarFullDayShiftModel())
            CoroutineScope(Dispatchers.IO).launch {
                calendarFullDayShift.collect{ list ->
                    updateList(list.calendarFullDayModels)
                }
            }

        }

        override fun onDataSetChanged() {
            if (calendarItemList.isNotEmpty()) {
                getViewAt(0)
            }
        }

        override fun onDestroy() {

        }

        override fun getCount(): Int {
            return calendarItemList.size
        }

        @SuppressLint("RemoteViewLayout")
        override fun getViewAt(position: Int): RemoteViews {
            val calendarItem = calendarItemList[position]

            val remoteView = RemoteViews(context.packageName, R.layout.item_full_calendar_month)
            remoteView.setTextViewText(R.id.tv_calendar_date, calendarItem.calendarDate)
            remoteView.setTextViewText(R.id.text_day_shift,shift(calendarItem.dayShift) )
            remoteView.setTextViewText(R.id.text_prev_night_shift, shift(calendarItem.prevNightShift))
            remoteView.setTextViewText(R.id.text_nex_night_shift, shift(calendarItem.nextNightShift))
            return remoteView

        }

        override fun getLoadingView(): RemoteViews? {
            return null
        }

        override fun getViewTypeCount(): Int {
            return 1
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
           val monthCalendar = date().clone() as Calendar
           monthCalendar.set(Calendar.DAY_OF_MONTH, 1)
           shiftScheduleCalendarInteractor.generateDaysFullCalendar(monthCalendar)


       }

       private fun date() = Calendar
           .getInstance(TimeZone.getTimeZone("GMT+3")).apply { firstDayOfWeek = 2 }

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

