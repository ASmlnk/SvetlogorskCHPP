package com.example.svetlogorskchpp.__presentation.shift_schedule.shift_schedule_calendar.model

import com.example.svetlogorskchpp.__domain.en.shift_schedule.Shift

data class CalendarFullDayShiftModel(
    val calendarFullDayModels: List<CalendarFullDayModel> = emptyList(),
    val shiftSelect: Shift = Shift.NO_SHIFT,
    val calendarView: String = "1"
) {
}
