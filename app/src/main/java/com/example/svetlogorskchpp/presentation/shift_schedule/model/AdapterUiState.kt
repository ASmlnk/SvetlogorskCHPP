package com.example.svetlogorskchpp.presentation.shift_schedule.model

import com.example.svetlogorskchpp.domain.en.Shift

data class AdapterUiState(
    val shift: Shift = Shift.NO_SHIFT,
    val calendarView: String = "1"
) {
}