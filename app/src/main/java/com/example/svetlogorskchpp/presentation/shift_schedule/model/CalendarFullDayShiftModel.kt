package com.example.svetlogorskchpp.presentation.shift_schedule.model

import android.os.Parcelable
import com.example.svetlogorskchpp.domain.en.Shift
import com.example.svetlogorskchpp.widget.model.CalendarFullDayShiftModelParcel
import kotlinx.parcelize.Parcelize


data class CalendarFullDayShiftModel(
    val calendarFullDayModels: List<CalendarFullDayModel> = emptyList(),
    val shiftSelect: Shift = Shift.NO_SHIFT,
    val calendarView: String = "1"
) {
    fun toCalendarFullDayShiftModelParcel(): CalendarFullDayShiftModelParcel {
        return CalendarFullDayShiftModelParcel(
            calendarFullDayModels = this.calendarFullDayModels.map { it.toCalendarFullDayModelParcel() },
            shiftSelect = this.shiftSelect,
            calendarView = this.calendarView
        )
    }
}
