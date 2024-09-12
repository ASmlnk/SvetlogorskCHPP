package com.example.svetlogorskchpp.__domain.model

import com.example.svetlogorskchpp.__data.database.note.NoteEntity
import java.util.Calendar
import java.util.Date

data class Note(
    val id: Long = 0,
    val tagDate: Date,        //авязь с CalendarTag
    val dateNotes: Calendar,        //"YYYY-MM-DD HH:mm"
    val isTimeNotes: Boolean = false,
    val content: String,
    val isRequestsWork: Boolean = false,
) {

    fun toNoteEntity(): NoteEntity {
        return NoteEntity(
            id = this.id,
            tagDate = this.tagDate,
            dateNotes = this.dateNotes.time,
            isTimeNotes = this.isTimeNotes,
            content = this.content,
            isRequestsWork = this.isRequestsWork
        )
    }
}