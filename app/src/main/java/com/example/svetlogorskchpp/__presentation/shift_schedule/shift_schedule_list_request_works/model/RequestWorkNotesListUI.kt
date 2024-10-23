package com.example.svetlogorskchpp.__presentation.shift_schedule.shift_schedule_list_request_works.model

import com.example.svetlogorskchpp.__domain.en.shift_schedule.RequestWorkSorted
import com.example.svetlogorskchpp.__domain.model.shift_schedule.Note

data class RequestWorkNotesListUI(
    val requestWork: List<Note.NoteRequestWork>,
    val sortedFlag: RequestWorkSorted
)
