package com.example.svetlogorskchpp.__presentation.shift_schedule_calendar_add_notes.model

import com.example.svetlogorskchpp.__domain.model.CalendarMyNoteTag
import com.example.svetlogorskchpp.__domain.model.Note
import java.util.Calendar

data class NoteUiState(
    val calendarMyNoteTag: CalendarMyNoteTag,
    val timeNote: Calendar? = null,
    val isTimeNote: Boolean,
    val noteMIES: List<Note> = emptyList()
) {
}