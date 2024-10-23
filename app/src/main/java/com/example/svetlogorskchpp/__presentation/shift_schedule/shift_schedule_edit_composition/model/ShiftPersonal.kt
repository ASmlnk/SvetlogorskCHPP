package com.example.svetlogorskchpp.__presentation.shift_schedule.shift_schedule_edit_composition.model

import com.example.svetlogorskchpp.__domain.en.shift_schedule.JobTitle
import com.example.svetlogorskchpp.__domain.en.shift_schedule.Shift


data class ShiftPersonal(
    val shift: Shift,
    val jobTitle: JobTitle,
    val namePersonal: String
)
