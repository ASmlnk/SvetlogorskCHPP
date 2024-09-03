package com.example.svetlogorskchpp.__presentation.shift_schedule.model

import com.example.svetlogorskchpp.__domain.en.Shift

data class AdapterUiState(
    val shift: Shift = Shift.NO_SHIFT,
    val calendarView: String = "1"
) {
}