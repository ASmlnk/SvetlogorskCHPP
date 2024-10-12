package com.example.svetlogorskchpp.__presentation.shift_schedule_list_request_works.model

import com.example.svetlogorskchpp.__domain.en.RequestWorkSorted
import com.example.svetlogorskchpp.__domain.model.Note

data class RequestWorkNotesListUI(
    val requestWork: List<Note.NoteRequestWork>,
    val sortedFlag: RequestWorkSorted
)
