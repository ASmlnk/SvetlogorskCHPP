package com.example.svetlogorskchpp.presentation.shift_schedule_calendar_add_notes.model

import com.example.svetlogorskchpp.domain.model.CalendarNoteTag
import java.util.Calendar

data class NoteUiState(
    val calendarNoteTag: CalendarNoteTag,
    val timeNote: Calendar? = null,
    val isTimeNote: Boolean
) {
}