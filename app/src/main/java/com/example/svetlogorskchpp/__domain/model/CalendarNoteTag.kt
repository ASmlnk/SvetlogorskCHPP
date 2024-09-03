package com.example.svetlogorskchpp.__domain.model

import com.example.svetlogorskchpp.__data.database.calendarNoteTag.CalendarNoteTagEntity
import com.example.svetlogorskchpp.__widget.model.CalendarNoteTagParc
import java.util.Date


data class CalendarNoteTag(
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

    fun toCalendarNoteTagParcel(): CalendarNoteTagParc {
        return CalendarNoteTagParc(
            date = this.date.time,
            month = this.month.time,
            isTechnical = this.isTechnical,
            isNotes = this.isNotes
        )
    }
}