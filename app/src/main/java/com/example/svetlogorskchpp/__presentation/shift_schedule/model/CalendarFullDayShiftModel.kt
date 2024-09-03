package com.example.svetlogorskchpp.__presentation.shift_schedule.model

import com.example.svetlogorskchpp.__domain.en.Shift
import com.example.svetlogorskchpp.__widget.model.CalendarFullDayShiftModelParcel


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
