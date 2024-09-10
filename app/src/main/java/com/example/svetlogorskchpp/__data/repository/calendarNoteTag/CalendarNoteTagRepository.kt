package com.example.svetlogorskchpp.__data.repository.calendarNoteTag

import com.example.svetlogorskchpp.__data.database.calendarNoteTag.CalendarNoteTagEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface CalendarNoteTagRepository {
    suspend fun insertTag(tagCalendarNote: CalendarNoteTagEntity)
    suspend fun getTagsByMonth(month: Date): List<CalendarNoteTagEntity>
    suspend fun deleteCalendarTag(calendarNoteTagEntity:CalendarNoteTagEntity)
    fun getTagsByDateStream(date: Date): Flow<CalendarNoteTagEntity?>
    fun getTagsByDay(date: Date): List<CalendarNoteTagEntity>
}