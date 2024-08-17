package com.example.svetlogorskchpp.domain.usecases.calendarNoteTag

import com.example.svetlogorskchpp.domain.model.CalendarNoteTag
import kotlinx.coroutines.flow.Flow
import java.util.Calendar

interface CalendarNoteTagUseCases {
    fun calendarNoteTagStream(month: Calendar): Flow<List<CalendarNoteTag>>
    fun getTagsByDate(date: Calendar): Flow<CalendarNoteTag?>
    suspend fun insertTag(tagCalendarNote: CalendarNoteTag)
    suspend fun deleteCalendarTag(calendarNoteTagEntity:CalendarNoteTag)
}