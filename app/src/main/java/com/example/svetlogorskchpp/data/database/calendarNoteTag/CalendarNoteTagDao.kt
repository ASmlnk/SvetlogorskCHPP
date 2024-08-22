package com.example.svetlogorskchpp.data.database.calendarNoteTag

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
    suspend fun insertTag(tag: CalendarNoteTagEntity)

    @Query("SELECT * FROM calendar_note_tags")
    fun getAllTags(): Flow<List<CalendarNoteTagEntity>>

    @Query("SELECT * FROM calendar_note_tags WHERE month = :month")
    suspend fun getTagsByMonth(month: Date): List<CalendarNoteTagEntity>

    @Query("SELECT * FROM calendar_note_tags WHERE date = :date LiMIT 1")
    fun getTagsByDate (date: Date): Flow<CalendarNoteTagEntity?>

    @Delete//@Query ("DELETE FROM calendar_note_tags WHERE date = :date")
    suspend fun deleteCalendarTag(calendarNoteTagEntity: CalendarNoteTagEntity)
}