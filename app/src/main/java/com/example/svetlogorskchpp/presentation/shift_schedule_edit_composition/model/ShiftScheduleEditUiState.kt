package com.example.svetlogorskchpp.presentation.shift_schedule_edit_composition.model

data class ShiftScheduleEditUiState(
    val jobTitlePersonals: List<JobTitlePersonal> = emptyList(),
    val staffs: List<Staff> = emptyList()
)
