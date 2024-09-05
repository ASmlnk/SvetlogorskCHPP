package com.example.svetlogorskchpp.__widget.service

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.navigation.NavDeepLinkBuilder

import com.example.svetlogorskchpp.MainActivity
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.__domain.en.Shift
import com.example.svetlogorskchpp.__domain.model.MonthCalendar
import com.example.svetlogorskchpp.__presentation.shift_schedule.model.CalendarFullDayModel
import com.example.svetlogorskchpp.__presentation.shift_schedule.model.CalendarFullDayShiftModel
import com.example.svetlogorskchpp.__presentation.shift_schedule.model.NavigateAddNoteArgs
import com.example.svetlogorskchpp.__presentation.shift_schedule_calendar_add_notes.fragment.ShiftScheduleAddNotesFragmentArgs
import com.google.gson.Gson
import java.util.ArrayList
import java.util.Calendar
import java.util.TimeZone

class MyRemoteAllShiftViewService : RemoteViewsService() {
    private var calendarItemList = ArrayList<CalendarFullDayModel>()

    @SuppressLint("NewApi")
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        val currentTimeInMillis = intent.getLongExtra("EXTRA_CURRENT_DATE", date().timeInMillis)
        val calendarFullDayShiftJson = intent.getStringExtra("CALENDAR_FULL_DAY_SHIFT")

        val calendarFullDayShift =
            Gson().fromJson(calendarFullDayShiftJson, CalendarFullDayShiftModel::class.java)

        // Создание экземпляра Calendar
        val calendar = Calendar.getInstance().apply {
            timeInMillis = currentTimeInMillis
        }
        return MyRemoteViewsFactory(
            this.applicationContext, /*shiftScheduleCalendarInteractor,*/
            calendar,
            calendarFullDayShift
        )
    }

    private fun date() = Calendar
        .getInstance(TimeZone.getTimeZone("GMT+3")).apply { firstDayOfWeek = 2 }

    inner class MyRemoteViewsFactory(
        private val context: Context,
        private val calendar: Calendar,
        private val calendarFullDayShift: CalendarFullDayShiftModel,
    ) : RemoteViewsFactory {

        override fun onCreate() {
            if (calendarFullDayShift.calendarFullDayModels.isNotEmpty()) {
                updateList(calendarFullDayShift.calendarFullDayModels)
            }
        }

        override fun onDataSetChanged() {
            if (calendarFullDayShift.calendarFullDayModels.isNotEmpty()) {
                getViewAt(0)
            }
        }

        override fun onDestroy() {
        }

        override fun getCount(): Int {
            return calendarItemList.size
        }

        //  @SuppressLint("RemoteViewLayout")
        override fun getViewAt(position: Int): RemoteViews {
            val calendarItem = calendarItemList[position]

            val layoutId =
                if (calendarItem.month == MonthCalendar.ACTUAL_MONTH) R.layout.item_full_calendar_month_widget else R.layout.item_full_calendar_prev_month_widget

            val remoteView = RemoteViews(context.packageName, layoutId)
            val navigateAddNoteArgs = NavigateAddNoteArgs(
                date = calendarItem.data.time.time,
                prevNightShift = calendarItem.prevNightShift,
                dayShift = calendarItem.dayShift,
                nextNightShift = calendarItem.nextNightShift,
                isTechnical = calendarItem.calendarNoteTag?.isTechnical ?: false
            )
            val bundle = Bundle().apply {
                putParcelable("navigateAddNoteArgs", navigateAddNoteArgs)
            }

            /*val args = ShiftScheduleAddNotesFragmentArgs(navigateAddNoteArgs)
            val pendingIntent = NavDeepLinkBuilder(applicationContext)
                .setComponentName(MainActivity::class.java)
                .setGraph(R.navigation.nav_graph)
                .setDestination(R.id.shiftScheduleAddNotesFragment)
                .setArguments(args.toBundle())
                .createPendingIntent()*/

            val intent = Intent(context, MainActivity::class.java).apply {
                putExtra("openFragment", "yourFragmentTag")
            }

            val pendingIntent = PendingIntent.getActivity(
                context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            remoteView.setOnClickPendingIntent(R.id.item_layout, pendingIntent)



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
            if (calendarItem.prevNightShift == calendarFullDayShift.shiftSelect) {
                remoteView.apply {
                    setInt(
                        R.id.image_prev_night_shift,
                        "setBackgroundResource",
                        R.drawable.background_calendar_select_shift_day
                    )
                }
            }

            if (calendarItem.dayShift == calendarFullDayShift.shiftSelect)
                remoteView.apply {
                    setInt(
                        R.id.text_day_shift,
                        "setBackgroundResource",
                        R.drawable.background_calendar_select_shift_day
                    )

                    setFloat(R.id.text_day_shift, "setAlpha", 0.85f)
                }

            if (calendarItem.nextNightShift == calendarFullDayShift.shiftSelect) {
                remoteView.apply {
                    setInt(
                        R.id.image_nex_night_shift,
                        "setBackgroundResource",
                        R.drawable.background_calendar_select_shift_night
                    )
                }
            }

            if (calendarItem.dateDay) {
                remoteView.setInt(
                    R.id.item_layout,
                    "setBackgroundResource",
                    R.drawable.background_callendar_day_actual
                )
            }

            if (calendarItem.calendarDayWeekend) {
                remoteView.setTextColor(
                    R.id.tv_calendar_date,
                    context.getColor(R.color.orange_zero_vision)
                )
            }
            return remoteView
        }

        @SuppressLint("RemoteViewLayout")
        override fun getLoadingView(): RemoteViews? {
            // Установите ваше дефолтное представление
            return RemoteViews(context.packageName, R.layout.item_full_calendar_prev_month_widget)
        }

        override fun getViewTypeCount(): Int {
            return 2
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun hasStableIds(): Boolean {
            return false
        }

        private fun updateList(newList: List<CalendarFullDayModel>) {
            if (newList.isNotEmpty()) {
                calendarItemList.clear()
                calendarItemList.addAll(newList)
                onDataSetChanged()
            }
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

