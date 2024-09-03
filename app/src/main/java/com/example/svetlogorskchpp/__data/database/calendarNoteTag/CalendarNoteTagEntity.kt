package com.example.svetlogorskchpp.__data.database.calendarNoteTag

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.svetlogorskchpp.__domain.model.CalendarNoteTag
import java.util.Date


@Entity(tableName = "calendar_note_tags")
data class CalendarNoteTagEntity(
    @PrimaryKey
    val date: Date,    //"YYYY-MM-DD"
    val month: Date,    //"YYYY-MM"
    val isTechnical: Boolean,
    val isNotes: Boolean = false,
) {

    fun toCalendarNoteTag(): CalendarNoteTag {
        return CalendarNoteTag(
            date = this.date,
            month = this.month,
            isTechnical = this.isTechnical,
            isNotes = this.isNotes
        )
    }
}