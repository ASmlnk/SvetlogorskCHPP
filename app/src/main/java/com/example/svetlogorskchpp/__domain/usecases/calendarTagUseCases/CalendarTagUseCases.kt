package com.example.svetlogorskchpp.__domain.usecases.calendarTagUseCases

import com.example.svetlogorskchpp.__domain.model.CalendarNoteTag
import com.example.svetlogorskchpp.__presentation.shift_schedule.model.CalendarFullDayModel

interface CalendarTagUseCases {
    fun addNoteTagToCalendar(
        calendarFullDayModels: List<CalendarFullDayModel>,
        calendarNoteTags: List<CalendarNoteTag>,
    ): List<CalendarFullDayModel>
}