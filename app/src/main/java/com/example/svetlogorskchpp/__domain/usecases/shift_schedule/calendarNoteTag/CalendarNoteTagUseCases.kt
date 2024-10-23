package com.example.svetlogorskchpp.__domain.usecases.shift_schedule.calendarNoteTag

import com.example.svetlogorskchpp.__domain.model.shift_schedule.CalendarMyNoteTag
import com.example.svetlogorskchpp.__domain.model.shift_schedule.CalendarRequestWorkTag
import kotlinx.coroutines.flow.Flow
import java.util.Calendar

interface CalendarNoteTagUseCases {
    suspend fun calendarMyNoteTag (month: Calendar): List<CalendarMyNoteTag>
    suspend fun calendarRequestWorkTag (month: Calendar): List<CalendarRequestWorkTag>
    fun getTagsByDate(date: Calendar): Flow<CalendarMyNoteTag?>
    suspend fun insertTag(tagCalendarNote: CalendarMyNoteTag)
    suspend fun deleteCalendarTag(calendarMyNoteTagEntity: CalendarMyNoteTag)

    val preferencesRequestWorkViewCalendarFlow: Flow<Boolean>
}