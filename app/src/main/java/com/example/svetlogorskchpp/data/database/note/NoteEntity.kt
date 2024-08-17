package com.example.svetlogorskchpp.data.database.note

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.svetlogorskchpp.data.database.calendarNoteTag.CalendarNoteTagEntity
import java.util.Date

@Entity(
    tableName = "notes",
    foreignKeys = [ForeignKey(
        entity = CalendarNoteTagEntity::class,
        parentColumns = ["date"],
        childColumns = ["tagDate"],
        onDelete = ForeignKey.CASCADE
    )]
)

data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val tagDate: Date,        //авязь с CalendarTag
    val dateNotes: Date,        //"YYYY-MM-DD HH:mm"
    val isTimeNotes: Boolean = false,
    val content: String,
)
