package com.example.svetlogorskchpp.__domain.model

import com.example.svetlogorskchpp.__data.database.note.NoteEntity
import java.util.Calendar
import java.util.Date

sealed class Note {

    data class NoteMy(
        val id: Long = 0,
        val tagDate: Date,        //авязь с CalendarTag
        val dateNotes: Calendar,        //"YYYY-MM-DD HH:mm"
        val isTimeNotes: Boolean = false,
        val content: String,
        val isRequestsWork: Boolean = false,
    ): Note() {

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

    data class NoteRequestWork(
        val id: Long = 0,
        val tagDate: Date,
        val numberRequestWork: String,
        val dateOpen: Calendar,
        val dateClose: Calendar,
        val accession: String,
        val reason: String,
        val additionally: String,
        val isExtend: Boolean = false,
        val contentExtend: String,
    ): Note()

}



