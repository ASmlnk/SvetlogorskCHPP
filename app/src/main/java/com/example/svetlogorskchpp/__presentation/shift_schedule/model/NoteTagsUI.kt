package com.example.svetlogorskchpp.__presentation.shift_schedule.model

import com.example.svetlogorskchpp.__domain.model.CalendarMyNoteTag
import com.example.svetlogorskchpp.__domain.model.CalendarRequestWorkTag

data class NoteTagsUI(
    val myNoteTags: List<CalendarMyNoteTag>,
    val requestWorkTags: List<CalendarRequestWorkTag>
)
