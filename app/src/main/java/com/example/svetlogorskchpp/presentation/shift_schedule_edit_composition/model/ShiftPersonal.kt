package com.example.svetlogorskchpp.presentation.shift_schedule_edit_composition.model

import com.example.svetlogorskchpp.domain.en.JobTitle
import com.example.svetlogorskchpp.domain.en.Shift


data class ShiftPersonal(
    val shift: Shift,
    val jobTitle: JobTitle,
    val namePersonal: String
)
