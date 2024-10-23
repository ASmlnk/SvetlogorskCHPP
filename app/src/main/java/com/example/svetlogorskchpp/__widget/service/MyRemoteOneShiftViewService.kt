package com.example.svetlogorskchpp.__widget.service

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.example.svetlogorskchpp.R
import com.example.svetlogorskchpp.__domain.en.shift_schedule.Shift
import com.example.svetlogorskchpp.__domain.model.shift_schedule.MonthCalendar
import com.example.svetlogorskchpp.__presentation.shift_schedule.shift_schedule_calendar.model.CalendarFullDayModel
import com.example.svetlogorskchpp.__presentation.shift_schedule.shift_schedule_calendar.model.CalendarFullDayShiftModel
import com.example.svetlogorskchpp.__presentation.shift_schedule.shift_schedule_calendar.model.NavigateAddNoteArgs
import com.example.svetlogorskchpp.__widget.ShiftScheduleWidget
import com.google.gson.Gson
import java.util.ArrayList
import java.util.Calendar
import java.util.TimeZone

class MyRemoteOneShiftViewService : RemoteViewsService() {
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
        return MyRemoteOneShiftViewsFactory(
            this.applicationContext, /*shiftScheduleCalendarInteractor,*/
            calendar,
            calendarFullDayShift
        )
    }

    private fun date() = Calendar
        .getInstance(TimeZone.getTimeZone("GMT+3")).apply { firstDayOfWeek = 2 }

    inner class MyRemoteOneShiftViewsFactory(
        private val context: Context,
        private val calendar: Calendar,
        private val calendarFullDayShift: CalendarFullDayShiftModel,
    ) : RemoteViewsFactory {

        override fun onCreate() {
            updateList(calendarFullDayShift.calendarFullDayModels)
        }

        override fun onDataSetChanged() {
        }

        override fun onDestroy() {
        }

        override fun getCount(): Int {
            return calendarItemList.size
        }

        @SuppressLint("RemoteViewLayout")
        override fun getViewAt(position: Int): RemoteViews {
            val calendarItem = calendarItemList[position]

            val layoutId =
                if (calendarItem.month == MonthCalendar.ACTUAL_MONTH) R.layout.item_full_calendar_month_widget_v2 else R.layout.item_full_calendar_prev_month_widget

            val remoteView = RemoteViews(context.packageName, layoutId)

            val shiftText = shift(calendarItem, calendarFullDayShift.shiftSelect)

            val navigateAddNoteArgs = NavigateAddNoteArgs(
                date = calendarItem.data.time.time,
                prevNightShift = calendarItem.prevNightShift,
                dayShift = calendarItem.dayShift,
                nextNightShift = calendarItem.nextNightShift,
                isTechnical = calendarItem.calendarMyNoteTag?.isTechnical ?: false
            )

            val intentSelectDay = Intent(
                this@MyRemoteOneShiftViewService,
                ShiftScheduleWidget::class.java
            ).apply {
                action = "ACTION_SELECT_DAY"
                putExtra("NAVIGATION_ADD_NOTES_ARGS", navigateAddNoteArgs)
            }

            remoteView.setOnClickFillInIntent(R.id.item_layout, intentSelectDay)

            when (calendarItem.month) {
                MonthCalendar.ACTUAL_MONTH -> {
                    remoteView.setTextViewText(R.id.tv_calendar_date, calendarItem.calendarDate)
                    remoteView.setTextViewText(R.id.text_shift, shiftText)
                }

                else -> {
                    remoteView.setTextViewText(
                        R.id.tv_calendar_date,
                        calendarItem.calendarDate
                    )
                }
            }

            if (shiftText == "4" || shiftText == "8/4" || shiftText == "8") {

                remoteView.apply {
                    setInt(
                        R.id.layout_shift,
                        "setBackgroundResource",
                        R.drawable.background_calendar_select_shift_night_v2
                    )
                }
            }
            if (shiftText == "12") {
                remoteView.apply {
                    setInt(
                        R.id.layout_shift,
                        "setBackgroundResource",
                        R.drawable.background_calendar_select_shift_day
                    )
                    setTextColor(R.id.text_shift, context.getColor(R.color.chip_appbar))
                   // setFloat(R.id.layout_shift, "setImageAlpha", 0.85f)
                  // setInt(R.id.layout_shift, "setBackgroundColor", Color.parseColor("#85ff0000"))
                   // setFloat(R.id.layout_shift, "setAlpha", 0.85f)
                }
            }

            if (calendarItem.calendarDayWeekend) {
                remoteView.setTextColor(
                    R.id.tv_calendar_date,
                    context.getColor(R.color.orange_zero_vision)
                )
            }

            if (calendarItem.calendarMyNoteTag?.isTechnical == true) {
                remoteView.apply {
                    setTextViewText(R.id.text_shift, "ТУ")
                    setInt(
                        R.id.layout_shift,
                        "setBackgroundResource",
                        R.drawable.background_calendar_select_technical
                    )
                    setTextColor(R.id.text_shift, context.getColor(R.color.chip_appbar))

                   // setFloat(R.id.layout_shift, "setAlpha", 0.85f)
                }
            }

            if (calendarItem.calendarMyNoteTag?.isNotes == true) {
                remoteView.apply {
                    setInt(
                        R.id.tv_calendar_date,
                        "setBackgroundResource",
                        R.drawable.background_calendar_select_note
                    )
                    setInt(
                        R.id.item_layout,
                        "setBackgroundResource",
                        R.drawable.background_layout_calendar_select_note_widget
                    )
                   // setFloat(R.id.text_day_shift, "setAlpha", 0.85f)

                }
            }

            if (calendarItem.calendarRequestWorkTag != null) {
                remoteView.apply {
                    setTextColor(
                        R.id.tv_calendar_date,
                        context.getColor(R.color.color_request_work_calendar)
                    )
                    setInt(
                        R.id.item_layout,
                        "setBackgroundResource",
                        R.drawable.background_layout_calendar_select_request_work
                    )
                    //  setFloat(R.id.text_day_shift, "setAlpha", 0.85f)
                }
            }

            if (calendarItem.dateDay) {
                remoteView.setInt(
                    R.id.item_layout,
                    "setBackgroundResource",
                    R.drawable.background_callendar_day_actual
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

    fun shift(calendarDateModel: CalendarFullDayModel, shift: Shift): String {
        return if (calendarDateModel.dayShift == shift) {
            "12"
        } else if (calendarDateModel.nextNightShift == shift && calendarDateModel.prevNightShift == shift) {
            "8/4"
        } else if (calendarDateModel.nextNightShift == shift && calendarDateModel.prevNightShift != shift) {
            "4"
        } else if (calendarDateModel.nextNightShift != shift && calendarDateModel.prevNightShift == shift) {
            "8"
        } else "В"
    }
}


