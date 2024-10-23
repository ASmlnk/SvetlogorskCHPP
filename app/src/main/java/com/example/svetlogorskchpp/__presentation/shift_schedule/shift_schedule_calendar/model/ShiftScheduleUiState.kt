package com.example.svetlogorskchpp.__presentation.shift_schedule.shift_schedule_calendar.model

import com.example.svetlogorskchpp.__domain.en.shift_schedule.Shift

data class ShiftScheduleUiState(
    val calendarList: List<CalendarFullDayModel> = emptyList(),
    val textDateMonth: String = "",
    val selectShift: Shift = Shift.NO_SHIFT,
    val calendarView: String = "1",
    val textTodayDate: String = "",
    val isSnackbarShow: Boolean = false,
    val isProgress: Boolean = false
) {
}