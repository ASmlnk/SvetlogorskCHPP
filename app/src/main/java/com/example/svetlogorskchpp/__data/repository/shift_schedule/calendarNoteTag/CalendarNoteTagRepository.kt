package com.example.svetlogorskchpp.__data.repository.shift_schedule.calendarNoteTag

import com.example.svetlogorskchpp.__data.database.calendarNoteTag.CalendarMyNoteTagEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface CalendarNoteTagRepository {
    suspend fun insertTag(tagCalendarNote: CalendarMyNoteTagEntity)
    suspend fun getTagsByMonth(month: Date): List<CalendarMyNoteTagEntity>
    suspend fun deleteCalendarTag(calendarMyNoteTagEntity:CalendarMyNoteTagEntity)
    fun getTagsByDateStream(date: Date): Flow<CalendarMyNoteTagEntity?>
    fun getTagsByDay(date: Date): List<CalendarMyNoteTagEntity>
}