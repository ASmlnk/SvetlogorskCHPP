package com.example.svetlogorskchpp.__presentation.shift_schedule_list_notes.model

import com.example.svetlogorskchpp.__domain.model.Note
import java.util.Calendar

data class NotesListStateUI(
    val notes: List<Note> = emptyList(),
    val todayDate: String,
    val sortedName: String = ""
)

