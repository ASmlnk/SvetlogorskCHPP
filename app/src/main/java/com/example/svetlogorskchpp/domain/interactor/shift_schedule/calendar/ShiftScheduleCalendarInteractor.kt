package com.example.svetlogorskchpp.domain.interactor.shift_schedule.calendar

import com.example.svetlogorskchpp.presentation.shift_schedule.model.CalendarFullDayShiftModel
import kotlinx.coroutines.flow.Flow
import java.util.Calendar

interface ShiftScheduleCalendarInteractor {
    fun generateDaysFullCalendar(calendar: Calendar)
    fun getDaysFullCalendarStream(): Flow<CalendarFullDayShiftModel>
    suspend fun setSelectShiftSchedule(shift: String)
    suspend fun setSelectCalendarView(view: String)
}