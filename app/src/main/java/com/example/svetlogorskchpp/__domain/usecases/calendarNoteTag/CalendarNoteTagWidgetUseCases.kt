package com.example.svetlogorskchpp.__domain.usecases.calendarNoteTag

import com.example.svetlogorskchpp.__domain.model.CalendarNoteTag
import java.util.Calendar

interface CalendarNoteTagWidgetUseCases {
    suspend fun calendarNoteTagStream(month: Calendar): List<CalendarNoteTag>
}