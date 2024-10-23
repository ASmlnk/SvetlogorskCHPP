package com.example.svetlogorskchpp.__presentation.shift_schedule.shift_schedule_edit_composition.model

data class ShiftScheduleEditUiState(
    val jobTitlePersonals: List<JobTitlePersonal> = emptyList(),
    val staffs: List<Staff> = emptyList()
)
