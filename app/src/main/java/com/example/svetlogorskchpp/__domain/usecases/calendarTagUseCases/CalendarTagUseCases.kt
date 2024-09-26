package com.example.svetlogorskchpp.__domain.usecases.calendarTagUseCases

import com.example.svetlogorskchpp.__domain.model.CalendarMyNoteTag
import com.example.svetlogorskchpp.__domain.model.CalendarRequestWorkTag
import com.example.svetlogorskchpp.__presentation.shift_schedule.model.CalendarFullDayModel

interface CalendarTagUseCases {
       fun addNoteTagToCalendar(
        calendarFullDayModels: List<CalendarFullDayModel>,
        calendarMyNoteTags: List<CalendarMyNoteTag>,
        calendarRequestWorkTag: List<CalendarRequestWorkTag>
    ): List<CalendarFullDayModel>
}