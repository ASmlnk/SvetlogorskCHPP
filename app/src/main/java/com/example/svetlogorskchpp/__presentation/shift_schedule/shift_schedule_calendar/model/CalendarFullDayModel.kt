package com.example.svetlogorskchpp.__presentation.shift_schedule.shift_schedule_calendar.model

import com.example.svetlogorskchpp.__domain.model.shift_schedule.MonthCalendar
import com.example.svetlogorskchpp.__domain.en.shift_schedule.Shift
import com.example.svetlogorskchpp.__domain.model.shift_schedule.CalendarMyNoteTag
import com.example.svetlogorskchpp.__domain.model.shift_schedule.CalendarRequestWorkTag
import java.util.Calendar


data class CalendarFullDayModel(
    val data: Calendar,
    val month: MonthCalendar,
    val prevNightShift: Shift,
    val dayShift: Shift,
    val nextNightShift: Shift,
    val calendarMyNoteTag: CalendarMyNoteTag? = null,
    val calendarRequestWorkTag: CalendarRequestWorkTag? = null
)  {

    val calendarDayWeekend: Boolean
        get() {
            val dayOfWeek = data.get(Calendar.DAY_OF_WEEK)
            return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY
        }

    val calendarDate: String
        get() {
            val cal = Calendar.getInstance()
            cal.time = data.time
            return cal[Calendar.DAY_OF_MONTH].toString()
        }

    val dateDay: Boolean
        get() {
            val cal = Calendar.getInstance()
            return data.get(Calendar.YEAR) == cal.get(Calendar.YEAR) &&
                    data.get(Calendar.MONTH) == cal.get(Calendar.MONTH) &&
                    data.get(Calendar.DAY_OF_MONTH) == cal.get(Calendar.DAY_OF_MONTH)
        }
}
