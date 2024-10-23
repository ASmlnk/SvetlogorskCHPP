package com.example.svetlogorskchpp.__presentation.shift_schedule.shift_schedule_calendar.model

import com.example.svetlogorskchpp.__domain.model.shift_schedule.CalendarMyNoteTag
import com.example.svetlogorskchpp.__domain.model.shift_schedule.CalendarRequestWorkTag

data class NoteTagsUI(
    val myNoteTags: List<CalendarMyNoteTag>,
    val requestWorkTags: List<CalendarRequestWorkTag>
)
