package com.example.svetlogorskchpp.__presentation.shift_schedule.model

import com.example.svetlogorskchpp.__domain.en.Shift

data class ShiftScheduleUiState(
    val calendarList: List<CalendarFullDayModel> = emptyList(),
    val textDateMonth: String = "",
    val selectShift: Shift = Shift.NO_SHIFT,
    val calendarView: String = "1",
    val textTodayDate: String = "",
    val isNotificationNoteTechnical: Boolean = true,
    val isSnackbarShow: Boolean = false,
    val isProgress: Boolean = false
) {
}