package com.example.svetlogorskchpp.domain.model

import com.example.svetlogorskchpp.data.database.note.NoteEntity
import java.util.Calendar
import java.util.Date

data class Note(
    val id: Long = 0,
    val tagDate: Date,        //авязь с CalendarTag
    val dateNotes: Calendar,        //"YYYY-MM-DD HH:mm"
    val isTimeNotes: Boolean = false,
    val content: String,
) {

    fun toNoteEntity(): NoteEntity {
        return NoteEntity(
            id = this.id,
            tagDate = this.tagDate,
            dateNotes = this.dateNotes.time,
            isTimeNotes = this.isTimeNotes,
            content = this.content
        )
    }
}