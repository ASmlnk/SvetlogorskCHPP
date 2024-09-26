package com.example.svetlogorskchpp.__data.database.calendarNoteTag

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

import java.util.Date

@Dao
interface CalendarNoteTagDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTag(tag: CalendarMyNoteTagEntity)

    @Query("SELECT * FROM calendar_note_tags")
    fun getAllTagsStream(): Flow<List<CalendarMyNoteTagEntity>>

    @Query("SELECT * FROM calendar_note_tags WHERE month = :month")
    suspend fun getTagsByMonth(month: Date): List<CalendarMyNoteTagEntity>

    @Query("SELECT * FROM calendar_note_tags WHERE date = :date")
    fun getTagsByDay(date: Date): List<CalendarMyNoteTagEntity>?

    @Query("SELECT * FROM calendar_note_tags WHERE date = :date LiMIT 1")
    fun getTagsByDateStream (date: Date): Flow<CalendarMyNoteTagEntity?>

    @Delete//@Query ("DELETE FROM calendar_note_tags WHERE date = :date")
    suspend fun deleteCalendarTag(calendarMyNoteTagEntity: CalendarMyNoteTagEntity)
}