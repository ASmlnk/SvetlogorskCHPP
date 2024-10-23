package com.example.svetlogorskchpp.__presentation.shift_schedule.shift_schedule_list_request_works.model

import com.example.svetlogorskchpp.__domain.model.shift_schedule.Note

data class NotesListStateUI(
    val notes: List<Note> = emptyList(),
    val todayDate: String,
    val sortedName: String = ""
)

