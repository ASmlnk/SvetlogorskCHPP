package com.example.svetlogorskchpp.__domain.usecases.calendarNoteTag

import com.example.svetlogorskchpp.__domain.model.CalendarNoteTag
import kotlinx.coroutines.flow.Flow
import java.util.Calendar

interface CalendarNoteTagUseCases {
    suspend fun calendarNoteTagStream(month: Calendar): List<CalendarNoteTag>
    fun getTagsByDate(date: Calendar): Flow<CalendarNoteTag?>
    suspend fun insertTag(tagCalendarNote: CalendarNoteTag)
    suspend fun deleteCalendarTag(calendarNoteTagEntity:CalendarNoteTag)
}