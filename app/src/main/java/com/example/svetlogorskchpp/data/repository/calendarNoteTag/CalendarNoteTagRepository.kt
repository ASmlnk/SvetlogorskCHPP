package com.example.svetlogorskchpp.data.repository.calendarNoteTag

import com.example.svetlogorskchpp.data.database.calendarNoteTag.CalendarNoteTagEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface CalendarNoteTagRepository {
    suspend fun insertTag(tagCalendarNote: CalendarNoteTagEntity)
    fun getTagsByMonth(month: Date): Flow<List<CalendarNoteTagEntity>>
    suspend fun deleteCalendarTag(date: Date)
    fun getTagsByDate(date: Date): Flow<CalendarNoteTagEntity?>
}