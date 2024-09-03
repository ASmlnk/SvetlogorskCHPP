package com.example.svetlogorskchpp.__widget.model

import android.os.Parcelable
import com.example.svetlogorskchpp.__domain.en.Shift
import kotlinx.parcelize.Parcelize

@Parcelize
data class CalendarFullDayShiftModelParcel(
    val calendarFullDayModels: List<CalendarFullDayModelParcel> = emptyList(),
    val shiftSelect: Shift = Shift.NO_SHIFT,
    val calendarView: String = "1"
) : Parcelable {
}