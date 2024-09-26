package com.example.svetlogorskchpp.__domain.model

import com.example.svetlogorskchpp.__data.database.calendarNoteTag.CalendarNoteTagEntity
import java.util.Date


data class CalendarMyNoteTag(
    val date: Date,    //"YYYY-MM-DD"
    val month: Date,    //"YYYY-MM"
    val isTechnical: Boolean,
    val isNotes: Boolean = false,
) {

    fun toCalendarNoteTagEntity(): CalendarNoteTagEntity {
        return CalendarNoteTagEntity(
            date = this.date,
            month = this.month,
            isTechnical = this.isTechnical,
            isNotes = this.isNotes
        )
    }
}