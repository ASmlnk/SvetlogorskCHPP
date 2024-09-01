package com.example.svetlogorskchpp.widget.model

import android.os.Parcelable
import com.example.svetlogorskchpp.domain.en.Shift
import com.example.svetlogorskchpp.presentation.shift_schedule.model.CalendarFullDayModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class CalendarFullDayShiftModelParcel(
    val calendarFullDayModels: List<CalendarFullDayModelParcel> = emptyList(),
    val shiftSelect: Shift = Shift.NO_SHIFT,
    val calendarView: String = "1"
) : Parcelable {
}