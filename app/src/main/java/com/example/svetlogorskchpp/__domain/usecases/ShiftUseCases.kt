package com.example.svetlogorskchpp.__domain.usecases

import com.example.svetlogorskchpp.__domain.en.Shift
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