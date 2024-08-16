package com.example.svetlogorskchpp.data.database.calendarNoteTag

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

import java.util.Date

@Dao
interface CalendarNoteTagDao {

    @Insert
    suspend fun insertTag(tag: CalendarNoteTagEntity)

    @Query("SELECT * FROM calendar_note_tags")
    fun getAllTags(): Flow<List<CalendarNoteTagEntity>>

    @Query("SELECT * FROM calendar_note_tags WHERE month = :month")
    fun getTagsByMonth(month: Date): Flow<List<CalendarNoteTagEntity>>

    @Delete
    suspend fun deleteCalendarTag(tag: CalendarNoteTagEntity)
}