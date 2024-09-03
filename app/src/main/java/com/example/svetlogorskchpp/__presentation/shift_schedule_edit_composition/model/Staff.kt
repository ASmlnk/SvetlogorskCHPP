package com.example.svetlogorskchpp.__presentation.shift_schedule_edit_composition.model

import com.example.svetlogorskchpp.__domain.en.Shift

data class Staff(val shift: Shift = Shift.NO_SHIFT, val name: String = "")