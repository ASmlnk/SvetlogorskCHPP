package com.example.svetlogorskchpp.presentation.shift_schedule.model

import com.example.svetlogorskchpp.domain.model.Shift

data class ShiftScheduleUiState(
    val calendarList: List<CalendarFullDayModel> = emptyList(),
    val textDateMonth: String = "",
    val selectShift: Shift = Shift.NO_SHIFT,
    val calendarView: String = "1"
) {
}