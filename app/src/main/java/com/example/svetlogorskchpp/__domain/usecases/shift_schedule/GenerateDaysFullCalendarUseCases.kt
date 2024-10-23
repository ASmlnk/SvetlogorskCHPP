package com.example.svetlogorskchpp.__domain.usecases.shift_schedule

import com.example.svetlogorskchpp.__domain.model.shift_schedule.CalendarDayOfMonth
import com.example.svetlogorskchpp.__domain.model.shift_schedule.MonthCalendar
import java.util.Calendar
import javax.inject.Inject

class GenerateDaysFullCalendarUseCases @Inject constructor() {

    fun generateDays(calendarGregorian: Calendar): List<CalendarDayOfMonth> {
        val calendar = calendarGregorian.clone() as Calendar
        val days = mutableListOf<CalendarDayOfMonth>()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)

        val firstOfMonth = Calendar.getInstance()
        firstOfMonth.set(year, month, 1, 0, 0, 0)
        firstOfMonth.set(Calendar.MILLISECOND, 0)
        val daysInMonth = firstOfMonth.getActualMaximum(Calendar.DAY_OF_MONTH)
        val firstDayWeek = firstOfMonth.get(Calendar.DAY_OF_WEEK)
        val firstDayOfWeek = if (firstDayWeek == 1) 7 else firstDayWeek - 1

        // Заполнение дней предыдущего месяца
        val prevMonth = Calendar.getInstance()
        prevMonth.time = firstOfMonth.time
        prevMonth.add(Calendar.MONTH, -1)
        val daysInPrevMonth = prevMonth.getActualMaximum(Calendar.DAY_OF_MONTH)
        for (i in 1 until firstDayOfWeek) {
            val prevMonthDay = Calendar.getInstance()
            prevMonthDay.time = prevMonth.time
            prevMonthDay.set(Calendar.DAY_OF_MONTH, daysInPrevMonth - firstDayOfWeek + i + 1)
            days.add(
                CalendarDayOfMonth(
                    prevMonthDay,
                    MonthCalendar.PREV_MONTH,
                )
            )
        }

        // Заполнение текущего месяца
        for (i in 1..daysInMonth) {
            val day = Calendar.getInstance()
            day.set(year, month, i, 0, 0, 0)
            day.set(Calendar.MILLISECOND, 0)
            days.add(
                CalendarDayOfMonth(
                    day,
                    MonthCalendar.ACTUAL_MONTH,
                )
            )
        }

        // Заполнение дней следующего месяца
        val remainingDays = 42 - days.size
        for (i in 1..remainingDays) {
            val nextMonthDay = Calendar.getInstance()
            nextMonthDay.set(year, month, daysInMonth + i, 0, 0,0)
            nextMonthDay.set(Calendar.MILLISECOND, 0)
            days.add(
                CalendarDayOfMonth(
                    nextMonthDay,
                    MonthCalendar.NEXT_MONTH,
                )
            )
        }
        return days
    }
}