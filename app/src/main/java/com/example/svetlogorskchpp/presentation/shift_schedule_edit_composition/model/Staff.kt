package com.example.svetlogorskchpp.presentation.shift_schedule_edit_composition.model

import com.example.svetlogorskchpp.domain.en.Shift

data class Staff(val shift: Shift = Shift.NO_SHIFT, val name: String = "")