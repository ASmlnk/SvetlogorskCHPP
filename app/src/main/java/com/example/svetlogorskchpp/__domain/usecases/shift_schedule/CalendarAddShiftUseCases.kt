package com.example.svetlogorskchpp.__domain.usecases.shift_schedule

import com.example.svetlogorskchpp.__domain.model.shift_schedule.CalendarDayOfMonth
import com.example.svetlogorskchpp.__domain.model.shift_schedule.MonthCalendar
import com.example.svetlogorskchpp.__domain.en.shift_schedule.Shift
import com.example.svetlogorskchpp.__presentation.shift_schedule.shift_schedule_calendar.model.CalendarFullDayModel
import java.lang.Math.abs
import java.util.Calendar
import java.util.GregorianCalendar
import javax.inject.Inject

class CalendarAddShiftUseCases @Inject constructor() {

    fun addShiftOfCalendar(calendarDayOfMonths: List<CalendarDayOfMonth>): List<CalendarFullDayModel> {
        val calendarFullDayModelList = mutableListOf<CalendarFullDayModel>()
        for (day in calendarDayOfMonths) {
            val date = day.data
            val todayData = GregorianCalendar(
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH)
            )
            val firstData = GregorianCalendar(2022, 6, 7)

            val intervalCalculation =
                (abs((todayData.timeInMillis - firstData.timeInMillis)) / (1000 * 60 * 60 * 24)) % 8

            if (day.month == MonthCalendar.ACTUAL_MONTH) {
                when (intervalCalculation.toInt()) {
                    0 -> calendarFullDayModelList.add(
                        CalendarFullDayModel(
                            day.data,
                            day.month,
                            Shift.D_SHIFT,
                            Shift.A_SHIFT,
                            Shift.D_SHIFT
                        )
                    )

                    1 -> calendarFullDayModelList.add(
                        CalendarFullDayModel(
                            day.data,
                            day.month,
                            Shift.D_SHIFT,
                            Shift.A_SHIFT,
                            Shift.B_SHIFT
                        )
                    )

                    2 -> calendarFullDayModelList.add(
                        CalendarFullDayModel(
                            day.data,
                            day.month,
                            Shift.B_SHIFT,
                            Shift.C_SHIFT,
                            Shift.B_SHIFT
                        )
                    )

                    3 -> calendarFullDayModelList.add(
                        CalendarFullDayModel(
                            day.data,
                            day.month,
                            Shift.B_SHIFT,
                            Shift.C_SHIFT,
                            Shift.A_SHIFT
                        )
                    )

                    4 -> calendarFullDayModelList.add(
                        CalendarFullDayModel(
                            day.data,
                            day.month,
                            Shift.A_SHIFT,
                            Shift.D_SHIFT,
                            Shift.A_SHIFT
                        )
                    )

                    5 -> calendarFullDayModelList.add(
                        CalendarFullDayModel(
                            day.data,
                            day.month,
                            Shift.A_SHIFT,
                            Shift.D_SHIFT,
                            Shift.C_SHIFT
                        )
                    )

                    6 -> calendarFullDayModelList.add(
                        CalendarFullDayModel(
                            day.data,
                            day.month,
                            Shift.C_SHIFT,
                            Shift.B_SHIFT,
                            Shift.C_SHIFT
                        )
                    )

                    7 -> calendarFullDayModelList.add(
                        CalendarFullDayModel(
                            day.data,
                            day.month,
                            Shift.C_SHIFT,
                            Shift.B_SHIFT,
                            Shift.D_SHIFT
                        )
                    )
                }
            } else {
                calendarFullDayModelList.add(
                    CalendarFullDayModel(
                        day.data,
                        day.month,
                        Shift.NO_SHIFT,
                        Shift.NO_SHIFT,
                        Shift.NO_SHIFT
                    )
                )
            }
        }
        return calendarFullDayModelList.toList()
    }
}