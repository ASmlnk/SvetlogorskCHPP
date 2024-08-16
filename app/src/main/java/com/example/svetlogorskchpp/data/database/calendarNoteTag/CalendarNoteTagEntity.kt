package com.example.svetlogorskchpp.data.database.calendarNoteTag

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "calendar_note_tags")
data class CalendarNoteTagEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: Date, 	//"YYYY-MM-DD"
    val month: Date, 	//"YYYY-MM"
    val isTechnical: Boolean,
    val isNotes: Boolean = false)