package com.example.svetlogorskchpp.__domain.usecases.shift_schedule.calendarTagUseCases

import com.example.svetlogorskchpp.__domain.model.shift_schedule.CalendarMyNoteTag
import com.example.svetlogorskchpp.__domain.model.shift_schedule.CalendarRequestWorkTag
import com.example.svetlogorskchpp.__presentation.shift_schedule.shift_schedule_calendar.model.CalendarFullDayModel

interface CalendarTagUseCases {
       fun addNoteTagToCalendar(
           calendarFullDayModels: List<CalendarFullDayModel>,
           calendarMyNoteTags: List<CalendarMyNoteTag>,
           calendarRequestWorkTag: List<CalendarRequestWorkTag>
    ): List<CalendarFullDayModel>
}