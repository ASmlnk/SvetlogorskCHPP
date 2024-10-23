package com.example.svetlogorskchpp.__domain.usecases.shift_schedule

import com.example.svetlogorskchpp.__domain.en.shift_schedule.Shift
import javax.inject.Inject

class ShiftUseCases @Inject constructor() {

    fun stringToShift(str: String): Shift {
        return when (str) {
            "A" -> Shift.A_SHIFT
            "B" -> Shift.B_SHIFT
            "C" -> Shift.C_SHIFT
            "D" -> Shift.D_SHIFT
            "E" -> Shift.E_SHIFT
            else -> Shift.NO_SHIFT
        }
    }
}