package com.example.svetlogorskchpp.domain.usecases

import com.example.svetlogorskchpp.domain.en.Shift
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

    fun shiftToString(shift: Shift): String {
        return when (shift) {
            Shift.A_SHIFT -> "A"
            Shift.B_SHIFT -> "B"
            Shift.C_SHIFT -> "C"
            Shift.D_SHIFT -> "D"
            Shift.E_SHIFT -> "E"
            Shift.NO_SHIFT -> ""
        }
    }


}