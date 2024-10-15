package com.example.svetlogorskchpp.__presentation.shift_schedule.model

import com.example.svetlogorskchpp.__domain.en.Shift

data class CalendarFullDayShiftModel(
    val calendarFullDayModels: List<CalendarFullDayModel> = emptyList(),
    val shiftSelect: Shift = Shift.NO_SHIFT,
    val calendarView: String = "1"
) {
}
